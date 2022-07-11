package code.service.impl;

import code.dto.LoanApplicationRequestDTO;
import code.dto.LoanOfferDTO;
import code.feign.DealClient;
import code.service.LoanOfferDTOListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;



@Slf4j
@Service
public class LoanOfferDTOListServiceImpl implements LoanOfferDTOListService {
    private final DealClient dealClient;;

    public LoanOfferDTOListServiceImpl(DealClient dealClient) {
        this.dealClient = dealClient;
    }
    @Override
    public List<LoanOfferDTO> getLoanOfferDTOList(LoanApplicationRequestDTO dto) {
        log.info("start method getLoanOfferDTOList");
        ResponseEntity<List<LoanOfferDTO>> response = dealClient.getLoanOfferDTOList(dto);
        log.info("get response from deal ms");
        log.info("return LoanOfferDTOList: " + response.getBody().toString());
        return response.getBody();
    }
}
