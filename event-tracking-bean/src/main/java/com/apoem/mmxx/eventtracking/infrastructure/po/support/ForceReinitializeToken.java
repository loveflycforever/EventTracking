package com.apoem.mmxx.eventtracking.infrastructure.po.support;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ForceReinitializeTokenEntity </p>
 * <p>Description: 操作令牌 </p>
 * <p>Date: 2020/7/27 14:57 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@Document(collection="tb_force_reinitialize_token")
public class ForceReinitializeToken {

    @Id
    private String token;

    @Indexed(expireAfterSeconds = 100)
    @CreatedDate
    private Date time;
}