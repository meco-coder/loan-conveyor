package code.service;

import code.dto.CreditDTO;
import code.model.Credit;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface CreditService {
    Credit createNewCredit(CreditDTO dto) throws NoSuchAlgorithmException, InvalidKeySpecException;
}
