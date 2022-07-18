package code.feign;

import code.dto.LoanApplicationRequestDTO;
import code.dto.LoanOfferDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "deal", url = "${deal.service.url}")
public interface DealClient {
    @PostMapping("/deal/application")
    ResponseEntity<List<LoanOfferDTO>> getLoanOfferDTOList(@RequestBody LoanApplicationRequestDTO dto);
    @PutMapping("/deal/offer")
    void offer(@RequestBody LoanOfferDTO dto);

}
