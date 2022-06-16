package code.service.impl;

import code.dto.CreditDTO;
import code.dto.PaymentScheduleElementDTO;
import code.model.PaymentScheduleElement;
import code.repository.PaymentScheduleElementRepository;
import code.service.PaymentScheduleElementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Slf4j
@Service
public class PaymentScheduleElementServiceImpl implements PaymentScheduleElementService {
    private final PaymentScheduleElementRepository paymentScheduleElementRepository;

    public PaymentScheduleElementServiceImpl(PaymentScheduleElementRepository paymentScheduleElementRepository) {
        this.paymentScheduleElementRepository = paymentScheduleElementRepository;
    }

    @Override
    public List<PaymentScheduleElement> createPaymentScheduleElementsList(CreditDTO dto) {
        log.info("start createPaymentScheduleElementList method");
        List<PaymentScheduleElement> paymentScheduleElementsList = new ArrayList<>();
        for (PaymentScheduleElementDTO paymentScheduleElement : dto.getPaymentSchedule()) {
            paymentScheduleElementsList.add(paymentScheduleElementRepository.save(PaymentScheduleElement.builder()
                    .debtPayment(paymentScheduleElement.getDebtPayment())
                    .interestPayment(paymentScheduleElement.getInterestPayment())
                    .totalPayment(paymentScheduleElement.getTotalPayment())
                    .date(paymentScheduleElement.getDate())
                    .number(paymentScheduleElement.getNumber())
                    .remainingDebt(paymentScheduleElement.getRemainingDebt())
                    .build()));
        }
        log.info("create payment schedule elements list and save in repository");
        log.info("return payment schedule elements list");
        log.info("end createPaymentScheduleElementsList method");
        return paymentScheduleElementsList;
    }
}
