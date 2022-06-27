package code.service.model.application;

import code.dto.LoanOfferDTO;
import code.model.LoanOffer;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface LoanOfferService {
    LoanOffer createNewLoanOffer(LoanOfferDTO dto) throws NoSuchAlgorithmException, InvalidKeySpecException;

}
