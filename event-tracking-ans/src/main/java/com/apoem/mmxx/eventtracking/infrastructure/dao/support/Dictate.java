package com.apoem.mmxx.eventtracking.infrastructure.dao.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author papafan
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dictate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name = "";
    private Integer priority = 0;
    private Integer mark = 0;

    private String sourceQuery = "";

    private String sourceCollection = "";

    private String sourceUniqueFieldName = "";

    private String keyFieldNames = "";

    private String valueFieldNames = "";

    private String temporaryCollection = "";

    private String actionName = "";

    private String period = "";

    private String targetQuery = "";

    private String targetCollection = "";
}
