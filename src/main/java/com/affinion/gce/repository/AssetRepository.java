package com.affinion.gce.repository;

import com.affinion.gce.jpa.entity.AssetEntity;
import com.affinion.gce.model.asset.AssetType;
import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AssetRepository extends ReactiveCrudRepository<AssetEntity, Long> {

    @Query("select count(a.id) from AssetEntity a where a.memberId=:memberId and a.type=:type")
    public Mono<Long> findAssetCountByMemberIdAndType(Long memberId, AssetType type);

    public Mono<Long> findAssetCountByMemberIdAndJoinA();
}
