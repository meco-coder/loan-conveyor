package code.service;

import code.dto.FinishRegistrationRequestDTO;
import code.dto.LoanApplicationRequestDTO;
import code.model.Client;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface ClientService {

    Client createNewClient(LoanApplicationRequestDTO dto) throws NoSuchAlgorithmException, InvalidKeySpecException;

    void updateClient(FinishRegistrationRequestDTO dto,
                              Client client) throws NoSuchAlgorithmException, InvalidKeySpecException;

}
