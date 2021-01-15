package com.apoem.mmxx.eventtracking.infrastructure.dao.support;

import com.apoem.mmxx.eventtracking.infrastructure.enums.FieldEnum;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: WonderfulPage </p>
 * <p>Description:  </p>
 * <p>Date: 2020/9/14 19:47 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class WonderfulPage {

    private final FieldEnum fieldEnum;
    private final Integer pageNumber;
    private final Integer pageSize;

    public WonderfulPage(String field, Integer pageNumber) {
        this.fieldEnum = FieldEnum.find(field);
        this.pageNumber = pageNumber;
        this.pageSize = pageNumber == 0L ? 24 : 25;
    }

    public FieldEnum getFieldEnum() {
        return this.fieldEnum;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public long getOffset() {
        return (pageNumber == 0L ? 0 : 24 + 25 * (pageNumber - 1));
    }
}
