package code.validator;

import code.annotation.Age;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;
@Slf4j
public class AgeValidator implements ConstraintValidator<Age, LocalDate> {
    @Override
    public void initialize(Age constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        log.info("age validation start");
        int age =  Period.between(value, LocalDate.now(Clock.systemUTC())).getYears();
        log.info("age = " + age);
        return age >= 20 && age <= 60;
    }
}
