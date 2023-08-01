package com.melo.gatewaysdk.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiProducerMethod {

    String methodName() default "";
    String uri() default "";

    String httpCommandType() default "GET";

    int auth() default 0;
}
