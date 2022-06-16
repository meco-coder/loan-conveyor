package code.service.impl;

import code.dto.CreditDTO;
import code.model.AdditionalServices;
import code.repository.AdditionalServicesRepository;
import code.service.AdditionalServicesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@Slf4j
public class AdditionalServicesServiceImpl implements AdditionalServicesService {
    private final AdditionalServicesRepository additionalServicesRepository;

    public AdditionalServicesServiceImpl(AdditionalServicesRepository additionalServicesRepository) {
        this.additionalServicesRepository = additionalServicesRepository;
    }

    @Override
    public AdditionalServices createNewAdditionalServices(CreditDTO dto) {
        log.info("start createNewAdditionalServices method");
        AdditionalServices additionalServices = additionalServicesRepository.save(AdditionalServices.builder()
                .isInsuranceEnabled(dto.getIsInsuranceEnabled())
                .isSalaryClient(dto.getIsSalaryClient()).build());
        log.info("create new additional service, save in repository");
        log.info("return additional services: " + additionalServices.toString());
        log.info("end createNewAdditionalServices method");
        return additionalServices;
    }
}
