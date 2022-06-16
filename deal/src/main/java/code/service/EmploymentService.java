package code.service;

import code.dto.EmploymentDTO;
import code.model.Employment;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface EmploymentService {
    Employment createNewEmployment(EmploymentDTO dto) throws NoSuchAlgorithmException, InvalidKeySpecException;
}
