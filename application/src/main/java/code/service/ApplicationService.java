package code.service;

import code.dto.LoanApplicationRequestDTO;
import code.dto.LoanOfferDTO;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public interface ApplicationService {
    List<LoanOfferDTO> getLoanOfferList(LoanApplicationRequestDTO dto) throws NoSuchAlgorithmException,
            InvalidKeySpecException;

    void sendOfferInDealClient(LoanOfferDTO dto);
}
