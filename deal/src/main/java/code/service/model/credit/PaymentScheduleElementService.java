package code.service.model.credit;

import code.dto.CreditDTO;
import code.model.PaymentScheduleElement;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public interface PaymentScheduleElementService {
    List<PaymentScheduleElement> createPaymentScheduleElementsList(CreditDTO dto) throws NoSuchAlgorithmException,
            InvalidKeySpecException;
}
