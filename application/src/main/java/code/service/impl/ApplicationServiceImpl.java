package code.service.impl;

import code.dto.LoanApplicationRequestDTO;
import code.dto.LoanOfferDTO;
import code.feign.DealClient;
import code.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final DealClient dealClient;
    ;

    @Override
    public List<LoanOfferDTO> getLoanOfferList(LoanApplicationRequestDTO dto) {
        log.info("start method getLoanOfferDTOList");
        ResponseEntity<List<LoanOfferDTO>> response = dealClient.getLoanOfferDTOList(dto);
        log.info("get response from deal ms");
        log.info("return LoanOfferDTOList: " + Objects.requireNonNull(response.getBody()));
        return response.getBody();
    }

    @Override
    public void sendOfferInDealClient(LoanOfferDTO dto) {
        log.info("start method sendOfferInDealClient");
        log.info("send offer in deal ms");
        dealClient.offer(dto);
        log.info("end method sendOfferInDealClient");
    }
}
