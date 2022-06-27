package code.utils;

import code.enums.ApplicationStatus;
import code.model.Application;
import code.model.ApplicationStatusHistory;
import code.model.Client;
import code.model.Passport;
import code.repository.ApplicationStatusHistoryRepository;
import code.repository.ClientRepository;
import code.repository.PassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class TestModelUtils {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ApplicationStatusHistoryRepository applicationStatusHistoryRepository;
    @Autowired
    private PassportRepository passportRepository;

    public Passport getPassport() {

        return passportRepository.save(Passport.builder()
                .passportSeries("1111").passportNumber("666666").build());

    }

    public Client getClient() {

        return clientRepository.save(Client.builder()
                .middleName("Ivanovich")
                .birthDate(LocalDate.of(1995, 11, 9))
                .email("example@example.com")
                .firstName("Ivan")
                .passport(getPassport())
                .lastName("Ivanov")
                .build());
    }

    public ApplicationStatusHistory getApplicationStatusHistory() {
        return applicationStatusHistoryRepository.save(ApplicationStatusHistory.builder()
                .changeType(ApplicationStatus.PREAPPROVAL)
                .build());


    }

    public Application getApplication() {
        return Application.builder()
                .client(getClient())
                .status(ApplicationStatus.PREAPPROVAL)
                .statusHistory(List.of(getApplicationStatusHistory()))
                .build();
    }
}
