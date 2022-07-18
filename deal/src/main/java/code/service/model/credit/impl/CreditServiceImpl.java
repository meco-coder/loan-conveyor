package code.service.model.credit.impl;

import code.dto.CreditDTO;
import code.enums.CreditStatus;
import code.model.Credit;
import code.repository.CreditRepository;
import code.service.model.credit.CreditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Transactional
@Slf4j
@Service
public class CreditServiceImpl implements CreditService {
    private final CreditRepository creditRepository;
    private final PaymentScheduleElementServiceImpl paymentScheduleElementService;
    private final AdditionalServicesServiceImpl additionalServicesService;

    public CreditServiceImpl(CreditRepository creditRepository,
                             PaymentScheduleElementServiceImpl paymentScheduleElementService,
                             AdditionalServicesServiceImpl additionalServicesService) {
        this.creditRepository = creditRepository;
        this.paymentScheduleElementService = paymentScheduleElementService;
        this.additionalServicesService = additionalServicesService;
    }

    @Override
    public Credit createNewCredit(CreditDTO dto) throws NoSuchAlgorithmException, InvalidKeySpecException {
        log.info("start createNewCredit method");
        Credit credit = creditRepository.save(Credit.builder()
                .additionalServices(additionalServicesService.createNewAdditionalServices(dto))
                .creditStatus(CreditStatus.CALCULATED)
                .amount(dto.getAmount())
                .monthlyPayment(dto.getMonthlyPayment())
                .paymentSchedule(paymentScheduleElementService.createPaymentScheduleElementsList(dto))
                .psk(dto.getPsk())
                .rate(dto.getRate())
                .term(dto.getTerm())
                .build());
        log.info("create new credit and save repository");
        log.info("return credit: " + credit);
        log.info("end createNewCredit method");
        return credit;
    }
}
