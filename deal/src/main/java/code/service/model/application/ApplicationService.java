package code.service.model.application;

import code.enums.ApplicationStatus;
import code.model.Application;
import code.model.Client;
import code.model.LoanOffer;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface ApplicationService {

    Application createNewApplication(Client client) throws NoSuchAlgorithmException,
            InvalidKeySpecException;

    void updateApplication(LoanOffer loanOffer, ApplicationStatus applicationStatus)
            throws NoSuchAlgorithmException, InvalidKeySpecException;

}
