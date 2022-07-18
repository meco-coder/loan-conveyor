package code.service.controller.calculate;

import code.dto.FinishRegistrationRequestDTO;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface CalculateService {

    void calculate(FinishRegistrationRequestDTO dto, long id) throws NoSuchAlgorithmException, InvalidKeySpecException;

}
