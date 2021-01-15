package com.apoem.mmxx.eventtracking.infrastructure.po.ro.support2;

import com.apoem.mmxx.eventtracking.infrastructure.po.ro.support.FieldNamingStrategy;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MrValue {

    FieldNamingStrategy fieldNamingStrategy() default FieldNamingStrategy.SNAKE;

    ScopeSign scope() default ScopeSign.NORMAL;

    String sourceFieldName() default "";
}
