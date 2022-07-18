package code.validator;

import code.annotation.EmploymentStatus;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
@Slf4j
public class EmploymentStatusValidator implements ConstraintValidator<EmploymentStatus, code.enums.EmploymentStatus> {


    @Override
    public void initialize(EmploymentStatus constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(code.enums.EmploymentStatus value, ConstraintValidatorContext context) {
        log.info("EmploymentStatus validation");
        return !value.equals(code.enums.EmploymentStatus.UNEMPLOYED);
    }
}
