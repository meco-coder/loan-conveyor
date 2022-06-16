package code.service.impl;

import code.dto.LoanOfferDTO;
import code.model.LoanOffer;
import code.repository.LoanOfferRepository;
import code.service.LoanOfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class LoanOfferServiceImpl implements LoanOfferService {
    private final LoanOfferRepository loanOfferRepository;

    public LoanOfferServiceImpl(LoanOfferRepository loanOfferRepository) {
        this.loanOfferRepository = loanOfferRepository;
    }

    @Override
    public LoanOffer createNewLoanOffer(LoanOfferDTO dto) {
        log.info("start createNewLoanOffer method");
        LoanOffer loanOffer = LoanOffer.builder()
                .applicationId(dto.getApplicationId())
                .isInsuranceEnabled(dto.getIsInsuranceEnabled())
                .isSalaryClient(dto.getIsInsuranceEnabled())
                .monthlyPayment(dto.getMonthlyPayment())
                .rate(dto.getRate())
                .term(dto.getTerm())
                .totalAmount(dto.getTotalAmount())
                .build();
        log.info("create new loanOffer and save in repository");
        log.info("return loanOffer: " + loanOffer.toString());
        log.info("end createNewLoanOffer method");
        return loanOfferRepository.save(loanOffer);
    }
}
