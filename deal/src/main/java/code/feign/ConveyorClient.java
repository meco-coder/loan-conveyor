package code.feign;

import code.dto.CreditDTO;
import code.dto.LoanApplicationRequestDTO;
import code.dto.LoanOfferDTO;
import code.dto.ScoringDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "conveyor", url = "${conveyor.service.url}")
public interface ConveyorClient {

    @PostMapping("${base-url}/conveyor/offers")
    ResponseEntity<List<LoanOfferDTO>> getLoanOfferDTOList(@RequestBody LoanApplicationRequestDTO dto);

    @PostMapping("${base-url}/conveyor/calculation")
    ResponseEntity<CreditDTO> getCreditDTO(@RequestBody ScoringDataDTO dto);
}
