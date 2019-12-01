package net.chrisgrollier.cloud.apps.common.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import net.chrisgrollier.cloud.apps.common.internal.validation.constraintvalidators.EnumNamePatternValidator;

/**
 * To validate enum types
 * 
 * @author Atos
 *
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
		ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EnumNamePatternValidator.class)
public @interface EnumNamePattern {
	String regexp();

	String message() default "must match \"{regexp}\"";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}