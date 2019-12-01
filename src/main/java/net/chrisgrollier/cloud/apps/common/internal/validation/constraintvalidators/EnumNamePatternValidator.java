package net.chrisgrollier.cloud.apps.common.internal.validation.constraintvalidators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.chrisgrollier.cloud.apps.common.validation.constraints.EnumNamePattern;

/**
 * A validator for {@link EnumNamePattern} annotation.
 * 
 * @author Atos
 *
 */
public final class EnumNamePatternValidator implements ConstraintValidator<EnumNamePattern, Enum<?>> {

	private Pattern pattern;

	@Override
	public void initialize(EnumNamePattern annotation) {
		try {
			pattern = Pattern.compile(annotation.regexp());
		} catch (PatternSyntaxException e) {
			throw new IllegalArgumentException("Given regex is invalid", e);
		}
	}

	@Override
	public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		Matcher m = pattern.matcher(value.name());
		return m.matches();
	}
}
