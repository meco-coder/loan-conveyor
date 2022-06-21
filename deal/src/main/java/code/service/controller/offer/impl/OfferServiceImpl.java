package code.service.controller.offer.impl;

import code.dto.LoanOfferDTO;
import code.enums.ApplicationStatus;
import code.model.LoanOffer;
import code.service.controller.offer.OfferService;
import code.service.model.application.impl.ApplicationServiceImpl;
import code.service.model.application.impl.LoanOfferServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OfferServiceImpl implements OfferService {

    private final ApplicationServiceImpl applicationService;
    private final LoanOfferServiceImpl loanOfferService;

    public OfferServiceImpl(ApplicationServiceImpl applicationService, LoanOfferServiceImpl loanOfferService) {
        this.applicationService = applicationService;
        this.loanOfferService = loanOfferService;
    }

    @Override
    public void createOffer(LoanOfferDTO dto) {
        log.info("start method createOffer");
        LoanOffer loanOffer = loanOfferService.createNewLoanOffer(dto);
        applicationService.updateApplication(loanOffer, ApplicationStatus.CC_APPROVED);
        log.info("end method createOffer");
    }
}
