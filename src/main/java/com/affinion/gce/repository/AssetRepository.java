package com.affinion.gce.repository;

import com.affinion.gce.jpa.entity.AssetEntity;
import com.affinion.gce.model.asset.AssetType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends CrudRepository<AssetEntity, Long> {

    Long countAllByMemberIdAndType(Long memberId, AssetType type);

    @Query("select count(a) from AssetEntity a, IN(a.attributes) as at where at.key = ?3 and at.value = ?4 " +
            "and a.memberId = ?1 and a.type = ?2")
    Long countByIdAndTypeAndAttribute(Long memberId, AssetType type, String key, String value);
}
