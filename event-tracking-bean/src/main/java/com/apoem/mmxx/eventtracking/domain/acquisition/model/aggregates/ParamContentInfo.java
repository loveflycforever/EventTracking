package com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ParamContentInfo {

    private String loginAccount;
    private String advId;
    private String courseId;
    private String posterCode;
    private String type;
    private String actId;
    private String storeId;
    private String companyId;
    private String test;

    public ParamContentInfo init() {
        this.loginAccount = StringUtils.EMPTY;
        this.advId = StringUtils.EMPTY;
        this.courseId = StringUtils.EMPTY;
        this.posterCode = StringUtils.EMPTY;
        this.type = StringUtils.EMPTY;
        this.actId = StringUtils.EMPTY;
        this.storeId = StringUtils.EMPTY;
        this.companyId = StringUtils.EMPTY;
        return this;
    }

    public String getLoginAccount() {
        return StringUtils.trimToEmpty(loginAccount);
    }

    public String getAdvId() {
        return StringUtils.trimToEmpty(advId);
    }

    public String getCourseId() {
        return StringUtils.trimToEmpty(courseId);
    }

    public String getPosterCode() {
        return StringUtils.trimToEmpty(posterCode);
    }

    public String getType() {
        return StringUtils.trimToEmpty(type);
    }

    public String getActId() {
        return StringUtils.trimToEmpty(actId);
    }

    public String getStoreId() {
        return StringUtils.trimToEmpty(storeId);
    }

    public String getCompanyId() {
        return StringUtils.trimToEmpty(companyId);
    }

    public String getTest() {
        String test = StringUtils.trimToEmpty(this.test);
        if("1".equalsIgnoreCase(test) || "YES".equalsIgnoreCase(test)) {
            return "YES";
        }
        return "NO";
    }
}
