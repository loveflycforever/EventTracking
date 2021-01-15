package com.apoem.mmxx.eventtracking.infrastructure.dao.support;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.TaskTableDao;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.TaskTableEntity;

import java.time.LocalDateTime;

public abstract class TaskDo {

    public abstract TaskTableDao taskTableDao();

    protected void startTask(LocalDateTime localDateTime) {
        TaskTableEntity taskTableEntity = new TaskTableEntity();
        taskTableEntity.setDateDay(DateUtils.numericalYyyyMmDd(localDateTime));
        TaskLabel annotation = this.getClass().getAnnotation(TaskLabel.class);
        if (annotation != null) {
            taskTableEntity.setTaskName(annotation.name());
            taskTableEntity.setTaskDesc(annotation.desc());
            taskTableEntity.setOrder(annotation.order());
        } else {
            taskTableEntity.setTaskName(this.getClass().getCanonicalName());
            taskTableEntity.setTaskDesc(this.getClass().getCanonicalName());
            taskTableEntity.setOrder(-1);
        }

        taskTableDao().upsert(taskTableEntity, TaskStatusEnum.RUNNING);
    }

    protected void finishTask(LocalDateTime localDateTime) {

        TaskTableEntity taskTableEntity = new TaskTableEntity();
        taskTableEntity.setDateDay(DateUtils.numericalYyyyMmDd(localDateTime));
        TaskLabel annotation = this.getClass().getAnnotation(TaskLabel.class);
        if (annotation != null) {
            taskTableEntity.setTaskName(annotation.name());
            taskTableEntity.setTaskDesc(annotation.desc());
            taskTableEntity.setOrder(annotation.order());
        } else {
            taskTableEntity.setTaskName(this.getClass().getCanonicalName());
            taskTableEntity.setTaskDesc(this.getClass().getCanonicalName());
            taskTableEntity.setOrder(-1);
        }
        taskTableDao().update(taskTableEntity, TaskStatusEnum.FINISH);
    }
}
