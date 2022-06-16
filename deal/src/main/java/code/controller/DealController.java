package code.controller;


import code.dto.CreditDTO;
import code.dto.FinishRegistrationRequestDTO;
import code.dto.LoanApplicationRequestDTO;
import code.dto.LoanOfferDTO;
import code.dto.ScoringDataDTO;
import code.enums.ApplicationStatus;
import code.feign.ConveyorClient;
import code.model.Application;
import code.model.Client;
import code.model.LoanOffer;
import code.repository.ApplicationRepository;
import code.service.impl.ApplicationServiceImpl;
import code.service.impl.ClientServiceImpl;
import code.service.impl.CreditServiceImpl;
import code.service.impl.LoanOfferServiceImpl;
import code.service.impl.ScoringDataDTOServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@RequestMapping("${base-url}/deal")
@RestController
@Slf4j
public class DealController {
    private static final String APPLICATION = "/application";
    private static final String OFFER = "/offer";
    private static final String CALCULATE = "/calculate/{id}";
    private final ConveyorClient conveyorClient;
    private final ClientServiceImpl clientService;
    private final ApplicationServiceImpl applicationService;
    private final LoanOfferServiceImpl loanOfferService;
    private final ApplicationRepository applicationRepository;
    private final ScoringDataDTOServiceImpl scoringDataDTOService;
    private final CreditServiceImpl creditService;

    public DealController(ConveyorClient conveyorClient, ClientServiceImpl clientService,
                          ApplicationServiceImpl applicationService, LoanOfferServiceImpl loanOfferService,
                          ApplicationRepository applicationRepository,
                          ScoringDataDTOServiceImpl scoringDataDTOService, CreditServiceImpl creditService) {
        this.conveyorClient = conveyorClient;
        this.clientService = clientService;
        this.applicationService = applicationService;
        this.loanOfferService = loanOfferService;
        this.applicationRepository = applicationRepository;
        this.scoringDataDTOService = scoringDataDTOService;
        this.creditService = creditService;
    }

    @Operation(summary = "Get LoanOfferDTO List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content(schema = @Schema(implementation = LoanOfferDTO.class)), description = "LoanOfferDTO List from "
                    + "conveyor ms"),
            @ApiResponse(responseCode = "400", description = "Invalid LoanApplicationRequestDTO")
    })
    @PostMapping(APPLICATION)
    public List<LoanOfferDTO> getLoanOfferDTOList(@Parameter(description = "LoanApplicationRequestDTO")
                                                  @RequestBody final LoanApplicationRequestDTO dto) {
        log.info("input LoanApplicationRequestDTO: " + dto.toString());
        final List<LoanOfferDTO> loanOfferDTOList = conveyorClient.getLoanOfferDTOList(dto);
        log.info("get request from conveyor ms: " + loanOfferDTOList.toString());
        Client client = clientService.createNewClient(dto);
        Application application = applicationService.createNewApplication(client);
        for (LoanOfferDTO loanOfferDTO : loanOfferDTOList) {
            loanOfferDTO.setApplicationId(application.getId());
        }
        log.info("update loanOfferDTOList from conveyor ms and return: " + loanOfferDTOList);
        return loanOfferDTOList;
    }

    @Operation(summary = "Selecting one of the offers")
    @ApiResponse(responseCode = "200", description = "Application saved")
    @PutMapping(OFFER)
    public void offer(@Parameter(description = "LoanOfferDTO") @RequestBody final LoanOfferDTO dto) {
        log.info("input LoanOfferDTO: " + dto.toString());
        LoanOffer loanOffer = loanOfferService.createNewLoanOffer(dto);
        applicationService.updateApplication(loanOffer, ApplicationStatus.CC_APPROVED);
        log.info("OK");
    }

    @Operation(summary = "Completion of registration and full credit calculation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accepted and saved"),
            @ApiResponse(responseCode = "400", description = "incorrect data")})
    @PutMapping(CALCULATE)
    public void calculate(@Parameter(description = "FinishRegistrationRequestDTO")
                          @RequestBody final FinishRegistrationRequestDTO dto,
                          @Parameter(description = "id Application") @PathVariable final long id)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        log.info("FinishRegistrationRequestDTO: " + dto.toString());
        Application application = applicationRepository.findById(id).get();
        log.info("find application by id: " + application.toString());
        clientService.updateClient(dto, application.getClient());
        ScoringDataDTO scoringDataDTO = scoringDataDTOService.createNewScoringDataDTO(application, dto);
        CreditDTO creditDTO = conveyorClient.getCreditDTO(scoringDataDTO);
        application.setCredit(creditService.createNewCredit(creditDTO));
        log.info("application set credit");
        applicationRepository.save(application);
        log.info("application save in repository");
        log.info("OK");
    }


}
