package code.validator;

import code.annotation.WorkExperienceCurrent;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class WorkExperienceCurrentValidator implements ConstraintValidator<WorkExperienceCurrent, Integer> {
    @Override
    public void initialize(WorkExperienceCurrent constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        log.info("WorkExperienceCurrent validation");
        return value >= 3;
    }
}
