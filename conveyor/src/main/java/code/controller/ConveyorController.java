package code.controller;

import code.dto.CreditDTO;
import code.dto.LoanApplicationRequestDTO;
import code.dto.LoanOfferDTO;
import code.dto.ScoringDataDTO;
import code.service.CreditService;
import code.service.LoanOfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


import java.util.List;


@RequestMapping("${base-url}/conveyor")
@RestController
@Slf4j
public class ConveyorController {

    private static final String OFFERS = "/offers";
    private static final String CALCULATION = "/calculation";


    private final LoanOfferService loanOfferService;

    private final CreditService creditService;

    public ConveyorController(LoanOfferService loanOfferService, CreditService creditService) {
        this.loanOfferService = loanOfferService;
        this.creditService = creditService;
    }


    @Operation(summary = "Get LoanOfferDTO List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content(schema = @Schema(implementation = LoanOfferDTO.class)), description = "LoanOfferDTO List"),
            @ApiResponse(responseCode = "400", description = "Invalid LoanApplicationRequestDTO")
    })
    @PostMapping(OFFERS)
    public ResponseEntity<List<LoanOfferDTO>> getLoanOfferDTOList(@RequestBody final LoanApplicationRequestDTO dto) {
        log.info("input data: " + dto.toString());
        return ResponseEntity.ok()
                .body( loanOfferService.getLoanOffers(dto));
    }

    @Operation(summary = "Get CreditDTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content(schema = @Schema(implementation = CreditDTO.class)), description = "CreditDTO"),
            @ApiResponse(responseCode = "400", description = "Invalid ScoringDataDTO")
    })
    @PostMapping(CALCULATION)
    public ResponseEntity<CreditDTO> getCreditDTO(@RequestBody @Valid final ScoringDataDTO dto) {
        return ResponseEntity.ok()
                .body( creditService.getCreditDTO(dto));
    }
}
