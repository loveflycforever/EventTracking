package com.apoem.mmxx.eventtracking.interfaces.dto;

public class TaskDto {
    private final String beanName;
    private final String methodName;

    public TaskDto(String beanName, String methodName) {
        this.beanName = beanName;
        this.methodName = methodName;
    }

    public String getBeanName() {
        return beanName;
    }

    public String getMethodName() {
        return methodName;
    }
}
