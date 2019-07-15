package io.github.profilr.web;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)

/**
 * Resource classes using the @PreAuth annotation will not be redirected to the login page
 *
 */
public @interface PreAuth {

}
