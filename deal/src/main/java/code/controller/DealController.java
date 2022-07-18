package code.controller;

import code.dto.FinishRegistrationRequestDTO;
import code.dto.LoanApplicationRequestDTO;
import code.dto.LoanOfferDTO;
import code.service.controller.application.impl.LoanOfferDTOListServiceImpl;
import code.service.controller.calculate.impl.CalculateServiceImpl;
import code.service.controller.offer.impl.OfferServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@RequestMapping("/deal")
@RestController
@Slf4j
public class DealController {
    private static final String APPLICATION = "/application";
    private static final String OFFER = "/offer";
    private static final String CALCULATE = "/calculate/{id}";
    private final LoanOfferDTOListServiceImpl loanOfferDTOList;
    private final OfferServiceImpl offerService;
    private final CalculateServiceImpl calculateService;

    public DealController(LoanOfferDTOListServiceImpl loanOfferDTOList,
                          OfferServiceImpl offerService,
                          CalculateServiceImpl calculateService) {
        this.loanOfferDTOList = loanOfferDTOList;
        this.offerService = offerService;
        this.calculateService = calculateService;
    }


    @Operation(summary = "Get LoanOfferDTO List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content(schema = @Schema(implementation = LoanOfferDTO.class)), description = "LoanOfferDTO List from "
                    + "conveyor ms"),
            @ApiResponse(responseCode = "400", description = "Invalid LoanApplicationRequestDTO")
    })
    @PostMapping(APPLICATION)
    public ResponseEntity<List<LoanOfferDTO>> getLoanOfferDTOList(@Parameter(description = "LoanApplicationRequestDTO")
                                                                  @RequestBody final LoanApplicationRequestDTO dto) {
        log.info("input LoanApplicationRequestDTO: " + dto.toString());
        return ResponseEntity.ok()
                .body(loanOfferDTOList.createLoanOfferDTOList(dto));
    }

    @Operation(summary = "Selecting one of the offers")
    @ApiResponse(responseCode = "200", description = "Application saved")
    @PutMapping(OFFER)
    public void offer(@Parameter(description = "LoanOfferDTO") @RequestBody final LoanOfferDTO dto) {
        log.info("input LoanOfferDTO: " + dto.toString());
        offerService.createOffer(dto);
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
        calculateService.calculate(dto, id);
        log.info("OK");
    }


}
