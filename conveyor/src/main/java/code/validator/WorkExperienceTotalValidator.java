package code.validator;

import code.annotation.WorkExperienceTotal;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class WorkExperienceTotalValidator implements ConstraintValidator<WorkExperienceTotal, Integer> {
    @Override
    public void initialize(WorkExperienceTotal constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        log.info("WorkExperienceTotal validation");
        return value >= 12;
    }
}
