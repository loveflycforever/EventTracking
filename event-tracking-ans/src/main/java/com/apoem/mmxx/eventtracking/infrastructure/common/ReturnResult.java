package com.apoem.mmxx.eventtracking.infrastructure.common;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ReturnResult </p>
 * <p>Description:
 * Copy from
 *      <groupId>com.unknown</groupId>
 *      <artifactId>unknown-framework</artifactId>
 *      <version>1.0.0-SNAPSHOT</version>
 * com.unknown.ws.system.ReturnResult
 * </p>
 * <p>Date: 2020/8/5 15:28 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public enum ReturnResult {
    /**/
    SUCCESS("10000", "操作成功！"),
    CREATE_SUCESS("10001", "新增成功，请查看详情！"),
    EDIT_SUCESS("10002", "编辑成功，请查看详情！"),
    DELETE_SUCESS("10003", "删除成功！"),
    FAIL("-10000", "系统异常，请联系系统管理员，给您带来的不便请见谅，谢谢！"),
    NOT_FOUND("-10001", "系统内部异常！"),
    ILLGEAL_PARAMTER("-10002", "参数异常！"),
    CONTENT_EMPTY("-10003", "关联内容为空异常！"),
    CHILD_NOT_EMPTY("-10011", "请先删除子部门或清除所有员工后再进行删除！");

    private final String resultCode;
    private final String resultMessage;

    ReturnResult(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public String getResultCode() {
        return this.resultCode;
    }

    public String getResultMessage() {
        return this.resultMessage;
    }
}