package io.github.profilr.web;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.ws.rs.NameBinding;

@Documented
@NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface Authorized {

	boolean preSecured() default false;
	
}
