package code.service;

import code.dto.FinishRegistrationRequestDTO;
import code.dto.LoanApplicationRequestDTO;
import code.model.Passport;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface PassportService {
    Passport createNewPassport(LoanApplicationRequestDTO dto) throws NoSuchAlgorithmException, InvalidKeySpecException;
    Passport updatePassport(FinishRegistrationRequestDTO dto,
                            Passport passport) throws NoSuchAlgorithmException, InvalidKeySpecException;
}
