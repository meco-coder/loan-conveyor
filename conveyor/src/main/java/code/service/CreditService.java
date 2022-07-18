package code.service;

import code.dto.CreditDTO;
import code.dto.ScoringDataDTO;
import code.enums.EmploymentStatus;
import code.enums.Gender;
import code.enums.MaritalStatus;
import code.enums.Position;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;

@Slf4j
@Service
public class CreditService {
    @Value("${base-rate:}")
    private BigDecimal rate;

    private BigDecimal newRate;


    private final PaymentScheduleElementService paymentScheduleElementService;


    private final LoanOfferService loanOfferService;

    public CreditService(PaymentScheduleElementService paymentScheduleElementService, LoanOfferService loanOfferService) {
        this.paymentScheduleElementService = paymentScheduleElementService;
        this.loanOfferService = loanOfferService;
    }


    public CreditDTO getCreditDTO(ScoringDataDTO dto) {
        log.info("getCreditDTO method start");
        newRate = new BigDecimal(rate.longValue());
        rateCalculation(dto);
        log.info("totalAmount calculation");
        final BigDecimal totalAmount = loanOfferService.calculatedTotalAmount(dto.getAmount(),
                newRate,
                dto.getIsInsuranceEnabled());
        log.info("monthlyPayment calculation");
        final BigDecimal monthlyPayment = loanOfferService.paymentInMouthCalculation(newRate,
                dto.getTerm(),
                totalAmount);
        log.info("psk calculation");
        final BigDecimal psk = calculationPSK(monthlyPayment, totalAmount, dto.getTerm());

        CreditDTO creditDTO = CreditDTO.builder()
                .amount(dto.getAmount())
                .term(dto.getTerm())
                .rate(newRate)
                .monthlyPayment(monthlyPayment)
                .paymentSchedule(paymentScheduleElementService.calculatePaymentScheduleElementList(dto.getTerm(),
                        totalAmount,
                        newRate,
                        monthlyPayment))
                .psk(psk)
                .isInsuranceEnabled(dto.getIsInsuranceEnabled())
                .isSalaryClient(dto.getIsSalaryClient())
                .build();
        log.info("return " + creditDTO);
        log.info("getCreditDTO method end");
        return creditDTO;

    }

    private void rateCalculation(ScoringDataDTO dto) {
        log.info("rateCalculation method start");
        rateCalculationWithEmploymentStatus(dto.getEmployment().getEmploymentStatus());
        rateCalculationWithPosition(dto.getEmployment().getPosition());
        rateCalculationWithMaritalStatus(dto.getMaritalStatus());
        rateCalculationWithDependentAmount(dto.getDependentAmount());
        rateCalculationWithGenderAndAge(dto.getGender(), dto.getBirthdate());
        rateCalculationWithIsInsuranceEnabledAndIsSalaryClient(dto.getIsInsuranceEnabled(), dto.getIsSalaryClient());
        log.info("newRate = " + newRate);
        log.info("rateCalculation method end");
    }


    private void rateCalculationWithEmploymentStatus(final EmploymentStatus employmentStatus) {
        log.info("rateCalculationWithEmploymentStatus method start");

        switch (employmentStatus) {
            case SELF_EMPLOYED -> newRate = newRate.add(new BigDecimal("1"));
            case BUSINESS_OWNER -> newRate = newRate.add(new BigDecimal("3"));
            default -> throw new IllegalArgumentException("Value not found");
        }
        log.info("employmentStatus = " + employmentStatus);
        log.info("newRate = " + newRate);
        log.info(" rateCalculationWithEmploymentStatus method end");
    }

    private void rateCalculationWithPosition(final Position position) {
        log.info("rateCalculationWithPosition method start");
        switch (position) {
            case MIDDLE_MANAGER -> newRate = newRate.subtract(new BigDecimal("2"));
            case TOP_MANAGER -> newRate = newRate.subtract(new BigDecimal("4"));
            default -> throw new IllegalArgumentException("Value not found");
        }
        log.info("position = " + position);
        log.info("newRate = " + newRate);
        log.info("rateCalculationWithPosition method end");
    }

    private void rateCalculationWithMaritalStatus(final MaritalStatus maritalStatus) {
        log.info("rateCalculationWithMaritalStatus method start");
        switch (maritalStatus) {
            case MARRIED -> newRate = newRate.subtract(new BigDecimal("3"));
            case NOT_MARRIED -> newRate = newRate.add(new BigDecimal("1"));
            default -> throw new IllegalArgumentException("Value not found");
        }
        log.info("maritalStatus = " + maritalStatus);
        log.info("newRate = " + newRate);
        log.info("rateCalculationWithMaritalStatus method end");
    }

    private void rateCalculationWithDependentAmount(final Integer dependentAmount) {
        log.info("rateCalculationWithDependentAmount method start");
        if (dependentAmount > 1) {
            log.info("dependentAmount = " + dependentAmount);
            log.info("dependentAmount > 1");
            newRate = newRate.add(new BigDecimal("1"));
            log.info("newRate = " + newRate);
        }
        log.info("rateCalculationWithDependentAmount method end");
    }

    private void rateCalculationWithGenderAndAge(final Gender gender, final LocalDate birthdate) {
        log.info("rateCalculationWithGenderAndAge method start");
        final int age = Period.between(birthdate, LocalDate.now(Clock.systemUTC())).getYears();
        log.info("age = " + age);
        if ((age >= 35 && age <= 60) && gender.equals(Gender.FEMALE)) {
            log.info("age >= 35 and <= 60 and gender = female");
            newRate = newRate.subtract(new BigDecimal("3"));
            log.info("newRate = " + newRate);
        } else if ((age >= 30 && age <= 55) && gender.equals(Gender.MALE)) {
            log.info("age >= 30 and <= 55 and gender = male");
            newRate = newRate.subtract(new BigDecimal("3"));
            log.info("newRate = " + newRate);
        } else {
            newRate = newRate.add(new BigDecimal("3"));
            log.info("newRate = " + newRate);
        }
        log.info("rateCalculationWithGenderAndAge method end");
    }

    private void rateCalculationWithIsInsuranceEnabledAndIsSalaryClient(final Boolean isInsuranceEnabled,
                                                                        final Boolean isSalaryClient) {
        log.info("rateCalculationWithIsInsuranceEnabledAndIsSalaryClient method start");
        if (isInsuranceEnabled && isSalaryClient) {
            log.info("isInsuranceEnabled = true and  isSalaryClient = true");
            newRate = newRate.subtract(new BigDecimal("3")).subtract(new BigDecimal("1"));
            log.info("newRate = " + newRate);
        } else if (!isInsuranceEnabled && isSalaryClient) {
            log.info("isInsuranceEnabled = false and  isSalaryClient = true");
            newRate = newRate.subtract(new BigDecimal("1"));
            log.info("newRate = " + newRate);
        } else if (isInsuranceEnabled) {
            log.info("isInsuranceEnabled = true and  isSalaryClient = false");
            newRate = newRate.subtract(new BigDecimal("3"));
            log.info("newRate = " + newRate);
        }
        log.info("rateCalculationWithIsInsuranceEnabledAndIsSalaryClient method end");
    }

//    Расчет ПСК производился по формуле
//    из данного источника: https://temabiz.com/finterminy/psk-uproshhennyj-raschet.html
    private BigDecimal calculationPSK(final BigDecimal monthlyPayment, final BigDecimal totalAmount,
                                      final Integer term) {
        log.info("calculationPSK method start");
        BigDecimal psk = monthlyPayment.multiply(new BigDecimal(term))
                .divide(totalAmount, MathContext.DECIMAL128)
                .subtract(new BigDecimal("1"))
                .multiply(new BigDecimal("12"))
                .divide(new BigDecimal(term), MathContext.DECIMAL128)
                .multiply(new BigDecimal("100"))
                .setScale(2, RoundingMode.HALF_UP);
        log.info("calculationPSK = " + psk);
        log.info("calculationPSK method end");
        return psk;
    }

}
