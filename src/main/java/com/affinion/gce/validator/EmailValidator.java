package com.affinion.gce.validator;

import com.affinion.gce.model.asset.type.Email;
import com.affinion.gce.repository.AssetRepository;
import com.affinion.gce.service.RuleService;

public class EmailValidator extends AssetDataValidator<Email> {
    public EmailValidator(AssetRepository repository,
                          RuleService service,
                          String brmsToken,
                          Email asset) {
        super(repository, service, brmsToken, asset);
    }
}
