package com.affinion.gce.exception.handler;

import com.affinion.gce.exception.CyberException;
import com.affinion.gce.exception.DataValidationException;
import com.affinion.gce.exception.ErrorCode;
import com.affinion.gce.exception.RestClientException;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.retry.RetryExhaustedException;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    public GlobalErrorAttributes() {
        super(true);
    }

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, includeStackTrace);
        CyberException exception = handleException(this.getError(request));
        errorAttributes.put("status", exception.getHttpStatusCode());
        errorAttributes.put("message", exception.getMessage());
        errorAttributes.put("cyberErrorCode", exception.getErrorCode());
        return errorAttributes;
    }

    private CyberException handleException(Throwable e) {
        if (e instanceof DataValidationException) {
            return new CyberException(e.getMessage(), ErrorCode.BAD_REQUEST_PARAMS);
        }
        if (e instanceof RestClientException) {
            return handleRestClientException((RestClientException) e);
        }
        return new CyberException(e.getMessage(), e, ErrorCode.GENERAL);
    }

    private CyberException handleRestClientException(RestClientException e) {
        if (e.getCause() instanceof RetryExhaustedException) {
            return new CyberException(e.getCause().getMessage(), e, ErrorCode.GENERAL);
        } else if (e.getCause() instanceof WebClientResponseException) {
            WebClientResponseException cause = (WebClientResponseException) e.getCause();
            return new CyberException(cause.getMessage(), cause, ErrorCode.byStatusCode(cause.getStatusCode()));
        }

        return new CyberException(e.getMessage(), e, ErrorCode.INVALID_ARGUMENTS);
    }
}
