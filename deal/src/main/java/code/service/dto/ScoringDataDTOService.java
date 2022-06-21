package code.service.dto;

import code.dto.FinishRegistrationRequestDTO;
import code.dto.ScoringDataDTO;
import code.model.Application;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface ScoringDataDTOService {
    ScoringDataDTO createNewScoringDataDTO(Application application,
                                            FinishRegistrationRequestDTO finishRegistrationRequestDTO)
            throws NoSuchAlgorithmException, InvalidKeySpecException;
}
