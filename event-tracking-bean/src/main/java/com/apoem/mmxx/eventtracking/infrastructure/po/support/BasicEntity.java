package com.apoem.mmxx.eventtracking.infrastructure.po.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: BaseEntity </p>
 * <p>Description: 基础实体类 </p>
 * <p>Date: 2020/7/16 12:13 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BasicEntity implements Serializable {

    /**
     * 修改时间
     */
    @LastModifiedDate
    private Date updateTime;

    /**
     * 修改者
     */
    private String updateUserId;

    /**
     * 修改者名称
     */
    private String updateUserName;

    /**
     * 创建时间
     * 需要手动声明
     */
    @CreatedDate
    private Date createTime;

    /**
     * 逻辑删除标记字段，0 未标记，1 标记
     */
    private Integer mark;
}
