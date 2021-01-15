package com.apoem.mmxx.eventtracking.infrastructure.po.entity;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.BasicEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collection="et_relationship")
@Data
@CompoundIndexes({
        @CompoundIndex(name = "idx1", def = "{'open_id' : 1, 'unique_id': 1}")
})
public class RelationshipEntity extends BasicEntity {

    private String openId;
    private String uniqueId;
}
