package com.apoem.mmxx.eventtracking.infrastructure.po.support;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: SerialNumber </p>
 * <p>Description: 序列号 </p>
 * <p>Date: 2020/7/21 17:29 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Document(collection="tb_serial_number")
@Data
public class SerialNumber {

    /**
     * 日期作为id
     * 20200721
     */
    @Id
    private String date;

    private Long number;

}
