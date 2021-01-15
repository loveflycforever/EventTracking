package com.apoem.mmxx.eventtracking.infrastructure.po.entity;

import com.apoem.mmxx.eventtracking.infrastructure.po.support.BasicEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CommunityInfoEntity </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/125 15:01 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Document(collection="et_community_info")
public class CommunityInfoEntity extends BasicEntity {

    private Integer dateDay;
    private String city;
    private String communityId;
    private String communityName;
    private Double communityLongitude;
    private Double communityLatitude;
    private String communityAvgPrice;
    private String plateId;
    private String plateName;

}
