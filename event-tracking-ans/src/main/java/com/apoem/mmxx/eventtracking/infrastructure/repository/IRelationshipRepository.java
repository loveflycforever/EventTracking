package com.apoem.mmxx.eventtracking.infrastructure.repository;

import com.apoem.mmxx.eventtracking.infrastructure.po.entity.RelationshipEntity;

import java.util.List;

public interface IRelationshipRepository {

    List<RelationshipEntity> selectWith(String uniqueId);
}
