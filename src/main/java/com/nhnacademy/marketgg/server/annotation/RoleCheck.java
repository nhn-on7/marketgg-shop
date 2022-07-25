package com.nhnacademy.marketgg.server.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 권한을 확인할 수 있는 Annotation.
 *
 * @version 1.0.0
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleCheck {

    Role accessLevel() default Role.LOGIN;

}
