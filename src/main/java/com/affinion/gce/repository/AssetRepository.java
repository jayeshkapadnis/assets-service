package com.affinion.gce.repository;

import com.affinion.gce.jpa.entity.AssetEntity;
import com.affinion.gce.model.asset.AssetType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends CrudRepository<AssetEntity, Long> {

    public Long countAllByMemberIdAndType(Long memberId, AssetType type);

    //public boolean existsByIdAndTypeAndAttributeName(Long id, AssetType type, String key);
}
