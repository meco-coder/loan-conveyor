package code.annotation;


import code.validator.BirthdateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = BirthdateValidator.class)
@Documented
public @interface Birthdate {
    String message() default "{Birthdate.invalid}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

}
