package code.service;

import code.dto.LoanApplicationRequestDTO;
import code.dto.LoanOfferDTO;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public interface LoanOfferDTOListService {
        List<LoanOfferDTO> getLoanOfferDTOList(LoanApplicationRequestDTO dto) throws NoSuchAlgorithmException,
                InvalidKeySpecException;
}
