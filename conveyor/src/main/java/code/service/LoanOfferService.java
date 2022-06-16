package code.service;

import code.dto.LoanApplicationRequestDTO;
import code.dto.LoanOfferDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LoanOfferService {

    @Value("${base-rate:}")
    private BigDecimal rate;

    public List<LoanOfferDTO> getLoanOffers(final LoanApplicationRequestDTO dto) {
        log.info("getLoanOffers method start");
        List<LoanOfferDTO> loanOffers = new ArrayList<>();
        log.info("create LoanOfferDTO with isInsuranceEnabled = true, isSalaryClient = true");
        loanOffers.add(formationLoanOffer(true, true, dto));
        log.info("create LoanOfferDTO with isInsuranceEnabled = true, isSalaryClient = false");
        loanOffers.add(formationLoanOffer(true, false, dto));
        log.info("create LoanOfferDTO with isInsuranceEnabled = true, isSalaryClient = false");
        loanOffers.add(formationLoanOffer(false, true, dto));
        log.info("create LoanOfferDTO with isInsuranceEnabled = false, isSalaryClient = false");
        loanOffers.add(formationLoanOffer(false, false, dto));
        log.info("getLoanOffers method return " + loanOffers);
        log.info("getLoanOffers method end");
        return loanOffers;
    }

    public LoanOfferDTO formationLoanOffer(final Boolean isInsuranceEnabled, final Boolean isSalaryClient,
                                           final LoanApplicationRequestDTO dto) {
        log.info("formationLoanOffer method start");

        LoanOfferDTO loanOfferDTO = new LoanOfferDTO();

        if (isInsuranceEnabled && isSalaryClient) {
            log.info("isInsuranceEnabled = true, isSalaryClient = true");
            log.info("newRate calculation");
            final BigDecimal newRate = rate.subtract(new BigDecimal("3")).subtract(new BigDecimal("1"));
            log.info("newRate = " + newRate);
            log.info("totalAmount calculation");
            final BigDecimal totalAmount = calculatedTotalAmount(dto.getAmount(), newRate, true);
            log.info("paymentInMouth calculation");
            BigDecimal paymentInMouth = paymentInMouthCalculation(newRate, dto.getTerm(), totalAmount);
            loanOfferDTO = LoanOfferDTO.builder()
                    .isInsuranceEnabled(true)
                    .isSalaryClient(true)
                    .rate(newRate)
                    .term(dto.getTerm())
                    .totalAmount(totalAmount)
                    .monthlyPayment(paymentInMouth)
                    .build();
            log.info("loanOfferDto = " + loanOfferDTO);

        } else if (!isInsuranceEnabled && isSalaryClient) {
            log.info("isInsuranceEnabled = false, isSalaryClient = true");
            log.info("newRate calculation");
            final BigDecimal newRate = rate.subtract(new BigDecimal("1"));
            log.info("newRate = " + newRate);
            log.info("totalAmount calculation");
            final BigDecimal totalAmount = calculatedTotalAmount(dto.getAmount(), newRate, false);
            log.info("paymentInMouth calculation");
            final BigDecimal paymentInMouth = paymentInMouthCalculation(newRate, dto.getTerm(), totalAmount);
            loanOfferDTO = LoanOfferDTO.builder()
                    .isInsuranceEnabled(false)
                    .isSalaryClient(true)
                    .rate(newRate)
                    .term(dto.getTerm())
                    .totalAmount(totalAmount)
                    .monthlyPayment(paymentInMouth)
                    .build();
            log.info("loanOfferDto = " + loanOfferDTO);

        } else if (isInsuranceEnabled) {
            log.info("isInsuranceEnabled = true, isSalaryClient = false");
            log.info("newRate calculation");
            final BigDecimal newRate = rate.subtract(new BigDecimal("3"));
            log.info("newRate = " + newRate);
            log.info("totalAmount calculation");
            final BigDecimal totalAmount = calculatedTotalAmount(dto.getAmount(), newRate, true);
            log.info("paymentInMouth calculation");
            final BigDecimal paymentInMouth = paymentInMouthCalculation(newRate, dto.getTerm(), totalAmount);
            loanOfferDTO = LoanOfferDTO.builder()
                    .isInsuranceEnabled(true)
                    .isSalaryClient(false)
                    .rate(newRate)
                    .term(dto.getTerm())
                    .totalAmount(totalAmount)
                    .monthlyPayment(paymentInMouth)
                    .build();
            log.info("loanOfferDto = " + loanOfferDTO);
        } else {
            log.info("isInsuranceEnabled = true, isSalaryClient = false");
            final BigDecimal newRate = rate;
            log.info("totalAmount calculation");
            final BigDecimal totalAmount = calculatedTotalAmount(dto.getAmount(), newRate, false);
            log.info("paymentInMouth calculation");
            final BigDecimal paymentInMouth = paymentInMouthCalculation(newRate, dto.getTerm(), totalAmount);
            loanOfferDTO = LoanOfferDTO.builder()
                    .isInsuranceEnabled(false)
                    .isSalaryClient(false)
                    .rate(newRate)
                    .term(dto.getTerm())
                    .totalAmount(totalAmount)
                    .monthlyPayment(paymentInMouth)
                    .build();
            log.info("loanOfferDto = " + loanOfferDTO);
        }
        log.info("formationLoanOffer method return " + loanOfferDTO);
        log.info("formationLoanOffer method end");
        return loanOfferDTO;
    }

    private BigDecimal insurancePriceCalculation(final BigDecimal amount, final BigDecimal rate) {
        log.info("insurancePriceCalculation method start");
        BigDecimal baseInsurance = rate.divide(new BigDecimal("100"), MathContext.DECIMAL128)
                .multiply(amount)
                .add(amount);
        log.info("baseInsurance = " + baseInsurance);

        BigDecimal insurancePrice = new BigDecimal("1.5")
                .divide(new BigDecimal("100"), MathContext.DECIMAL128)
                .multiply(baseInsurance)
                .setScale(2, RoundingMode.HALF_UP);
        log.info("insurancePrice = " + insurancePrice);
        log.info("insurancePriceCalculation end");
        return insurancePrice;
    }

    public BigDecimal paymentInMouthCalculation(final BigDecimal newRate, final Integer term,
                                                final BigDecimal totalAmount) {
        log.info("paymentInMouth method start");
        BigDecimal monthlyRate = newRate.divide(new BigDecimal("100"), MathContext.DECIMAL128)
                .divide(new BigDecimal("12"), MathContext.DECIMAL128);
        log.info("monthlyRate = " + monthlyRate);

        BigDecimal paymentInMouth = monthlyRate.divide((new BigDecimal("1").add(monthlyRate)).pow(term)
                        .subtract(new BigDecimal("1")), MathContext.DECIMAL128)
                .add(monthlyRate)
                .multiply(totalAmount, MathContext.DECIMAL128)
                .setScale(2, RoundingMode.HALF_UP);
        log.info("paymentInMouth = " + paymentInMouth);
        log.info("paymentInMouth method end");
        return paymentInMouth;
    }

    public BigDecimal calculatedTotalAmount(final BigDecimal amount, final BigDecimal newRate,
                                            final Boolean isInsuranceEnabled) {
        log.info("calculatedTotalAmount method start");
        if (isInsuranceEnabled) {
            log.info("isInsuranceEnabled = true");
            BigDecimal totalAmount = amount.add(insurancePriceCalculation(amount, newRate));
            log.info("totalAmount = " + totalAmount);
            log.info("calculatedTotalAmount method end");
            return totalAmount;
        }
        log.info("isInsuranceEnabled = false");
        log.info("totalAmount = " + amount);
        log.info("calculatedTotalAmount method end");
        return amount;
    }

}
