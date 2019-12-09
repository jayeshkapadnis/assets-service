package com.affinion.gce.model.asset;

import com.affinion.gce.jackson.AssetTypeResolver;
import com.affinion.gce.jpa.entity.AssetAttribute;
import com.affinion.gce.jpa.entity.AssetEntity;
import com.affinion.gce.model.Hashable;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
@JsonTypeResolver(AssetTypeResolver.class)
public abstract class Asset implements Hashable {
    private AssetId id;
    private Long memberId;
    private Long tenantId;
    private Boolean active = false;

    public Asset(AssetEntity entity) {
        this.id = new AssetId(entity.getId(), entity.getType());
        this.tenantId = entity.getTenantId();
        this.memberId = entity.getMemberId();
        this.active = entity.isActive();
    }

    public AssetType type() {
        return id.getType();
    }

    public List<AssetAttribute> attributes() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<AssetAttribute> hashAttributes() {
        return attributes().stream().map(a ->
                a.getKey().equals(type().id()) ? new AssetAttribute(a.getKey(), hashSequence(a.getValue())) : a
        ).collect(Collectors.toList());
    }

    protected Optional<AssetAttribute> newAttribute(String key, String value) {
        return StringUtils.isEmpty(value) ? Optional.empty() :
                Optional.of(new AssetAttribute(key, value));
    }

    protected List<AssetAttribute> fromStream(Stream<Optional<AssetAttribute>> attributes) {
        return attributes.filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }
}
