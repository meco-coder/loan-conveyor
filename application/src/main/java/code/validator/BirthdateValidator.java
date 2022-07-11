package code.validator;

import code.annotation.Birthdate;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;
@Slf4j
public class BirthdateValidator implements ConstraintValidator<Birthdate, LocalDate> {



    @Override
    public void initialize(Birthdate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        log.info("birthdate validation start");
            int age =  Period.between(value, LocalDate.now(Clock.systemUTC())).getYears();
            log.info("age = " + age);
        return age >= 18;
    }

}
