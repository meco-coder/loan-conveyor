package code.controller;

import code.dto.LoanApplicationRequestDTO;
import code.dto.LoanOfferDTO;
import code.service.impl.ApplicationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationServiceImpl applicationService;

    @Operation(summary = "Get LoanOfferDTO List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content(schema = @Schema(implementation = LoanOfferDTO.class)), description = "LoanOfferDTO List from "
                    + "deal ms"),
            @ApiResponse(responseCode = "400", description = "Invalid LoanApplicationRequestDTO")
    })
    @PostMapping("/application")
    public ResponseEntity<List<LoanOfferDTO>> getLoanOfferDTOList(@Parameter(description = "LoanApplicationRequestDTO")
                                                                  @RequestBody
                                                                  @Valid final LoanApplicationRequestDTO dto) {
        log.info("input LoanApplicationRequestDTO: " + dto.toString());
        return ResponseEntity.ok()
                .body(applicationService.getLoanOfferList(dto));
    }

    @Operation(summary = "Selecting one of the offers")
    @ApiResponse(responseCode = "200", description = "Send LoanOfferDTO in deal ms")
    @PostMapping("/application/offer")
    public void offer(@Parameter(description = "LoanOfferDTO") @RequestBody final LoanOfferDTO dto) {
        log.info("input LoanOfferDTO: " + dto.toString());
        applicationService.sendOfferInDealClient(dto);
        log.info("response status OK");
    }
}
