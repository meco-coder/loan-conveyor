package code.service.controller.offer;

import code.dto.LoanOfferDTO;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface OfferService {

    void createOffer(LoanOfferDTO dto) throws NoSuchAlgorithmException, InvalidKeySpecException;
}

