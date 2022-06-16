package code.service;

import code.dto.CreditDTO;
import code.model.AdditionalServices;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface AdditionalServicesService {

    AdditionalServices createNewAdditionalServices(CreditDTO dto) throws NoSuchAlgorithmException,
            InvalidKeySpecException;
}
