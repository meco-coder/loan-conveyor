package code.validator;

import code.annotation.Salary;
import code.dto.ScoringDataDTO;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

@Slf4j
public class MultiScoringDataValidator implements ConstraintValidator<Salary, ScoringDataDTO> {
    @Override
    public void initialize(Salary constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ScoringDataDTO value, ConstraintValidatorContext context) {
        log.info("ScoringDataDTO validation");
        final BigDecimal amount = value.getAmount();
        log.info("amount = " + amount);
        final BigDecimal salary = value.getEmployment().getSalary();
        log.info("salary = " + salary);
        return salary.multiply(new BigDecimal("20")).compareTo(amount) >= 0;
    }
}
