package code.service.controller.application.impl;

import code.dto.LoanApplicationRequestDTO;
import code.dto.LoanOfferDTO;
import code.feign.ConveyorClient;
import code.model.Application;
import code.model.Client;
import code.service.controller.application.LoanOfferDTOListService;
import code.service.model.application.impl.ApplicationServiceImpl;
import code.service.model.client.impl.ClientServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;



@Slf4j
@Service
public class LoanOfferDTOListServiceImpl implements LoanOfferDTOListService {


    private final ConveyorClient conveyorClient;
    private final ClientServiceImpl clientService;
    private final ApplicationServiceImpl applicationService;

    public LoanOfferDTOListServiceImpl(ConveyorClient conveyorClient,
                                       ClientServiceImpl clientService,
                                       ApplicationServiceImpl applicationService) {
        this.conveyorClient = conveyorClient;
        this.clientService = clientService;
        this.applicationService = applicationService;
    }


    @Override
    public List<LoanOfferDTO> createLoanOfferDTOList(LoanApplicationRequestDTO dto) {
        log.info("start method createLoanOfferDTOList");
        ResponseEntity<List<LoanOfferDTO>> response = conveyorClient.getLoanOfferDTOList(dto);
        final List<LoanOfferDTO> loanOfferDTOList = response.getBody();
        log.info("get request from conveyor ms: " + loanOfferDTOList.toString());
        Client client = clientService.createNewClient(dto);
        Application application = applicationService.createNewApplication(client);
        for (LoanOfferDTO loanOfferDTO : loanOfferDTOList) {
            loanOfferDTO.setApplicationId(application.getId());
        }
        log.info("update loanOfferDTOList from conveyor ms and return: " + loanOfferDTOList);
        log.info("end method createLoanOfferDTOList");
        return loanOfferDTOList;
    }
}
