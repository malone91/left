package com.melo.gatewaysdk.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiProducerClazz {

    String interfaceName() default "";

    String interfaceVersion() default "";
}
