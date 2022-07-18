package code.service.controller.calculate.impl;

import code.dto.CreditDTO;
import code.dto.FinishRegistrationRequestDTO;
import code.dto.ScoringDataDTO;
import code.feign.ConveyorClient;
import code.model.Application;
import code.repository.ApplicationRepository;
import code.service.controller.calculate.CalculateService;
import code.service.model.client.impl.ClientServiceImpl;
import code.service.model.credit.impl.CreditServiceImpl;
import code.service.dto.impl.ScoringDataDTOServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


@Slf4j
@Service
public class CalculateServiceImpl implements CalculateService {
    private final ConveyorClient conveyorClient;
    private final ClientServiceImpl clientService;
    private final ApplicationRepository applicationRepository;
    private final ScoringDataDTOServiceImpl scoringDataDTOService;
    private final CreditServiceImpl creditService;

    public CalculateServiceImpl(ConveyorClient conveyorClient,
                                ClientServiceImpl clientService,
                                ApplicationRepository applicationRepository,
                                ScoringDataDTOServiceImpl scoringDataDTOService,
                                CreditServiceImpl creditService) {
        this.conveyorClient = conveyorClient;
        this.clientService = clientService;
        this.applicationRepository = applicationRepository;
        this.scoringDataDTOService = scoringDataDTOService;
        this.creditService = creditService;
    }


    @Override
    public void calculate(FinishRegistrationRequestDTO dto, long id) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        log.info("start method calculate");
        Application application = applicationRepository.findById(id).get();
        log.info("find application by id: " + application.toString());
        clientService.updateClient(dto, application.getClient());
        ScoringDataDTO scoringDataDTO = scoringDataDTOService.createNewScoringDataDTO(application, dto);
        ResponseEntity<CreditDTO> response = conveyorClient.getCreditDTO(scoringDataDTO);
        CreditDTO creditDTO = response.getBody();
        application.setCredit(creditService.createNewCredit(creditDTO));
        log.info("application set credit");
        applicationRepository.save(application);
        log.info("application save in repository");
        log.info("end method calculate");
    }
}
