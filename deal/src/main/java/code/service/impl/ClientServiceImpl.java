package code.service.impl;

import code.dto.FinishRegistrationRequestDTO;
import code.dto.LoanApplicationRequestDTO;
import code.model.Client;
import code.repository.ClientRepository;
import code.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final PassportServiceImpl passportService;
    private final EmploymentServiceImpl employmentService;

    public ClientServiceImpl(ClientRepository clientRepository, PassportServiceImpl passportService,
                             EmploymentServiceImpl employmentService) {
        this.clientRepository = clientRepository;
        this.passportService = passportService;
        this.employmentService = employmentService;
    }

    @Override
    public Client createNewClient(LoanApplicationRequestDTO dto) {
        log.info("start method createNewClient");
        Client client = Client.builder()
                .birthDate(dto.getBirthdate())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .middleName(dto.getMiddleName())
                .email(dto.getEmail())
                .passport(passportService.createNewPassport(dto))
                .build();
        log.info("create new client and save in repository");
        log.info("return client: " + client.toString());
        log.info("end method createNewClient");
        return clientRepository.save(client);
    }

    @Override
    public void updateClient(FinishRegistrationRequestDTO dto, Client client) {
        log.info("start method updateClient");
        client.setAccount(dto.getAccount());
        client.setDependentAmount(dto.getDependentAmount());
        client.setEmployment(employmentService.createNewEmployment(dto.getEmployment()));
        client.setPassport(passportService.updatePassport(dto, client.getPassport()));
        client.setGender(dto.getGender());
        client.setMaritalStatus(dto.getMaritalStatus());
        log.info("update client and save in repository");
        log.info("return updated client: " + client);
        log.info("end method updateClient");
        clientRepository.save(client);
    }
}
