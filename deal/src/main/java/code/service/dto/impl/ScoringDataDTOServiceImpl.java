package code.service.dto.impl;

import code.dto.FinishRegistrationRequestDTO;
import code.dto.ScoringDataDTO;
import code.model.Application;
import code.service.dto.ScoringDataDTOService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScoringDataDTOServiceImpl implements ScoringDataDTOService {
    @Override
    public ScoringDataDTO createNewScoringDataDTO(Application application,
                                                  FinishRegistrationRequestDTO finishRegistrationRequestDTO) {
        log.info("start createNewScoringDataDTO method");
        ScoringDataDTO scoringDataDTO = ScoringDataDTO.builder()
                .account(finishRegistrationRequestDTO.getAccount())
                .isSalaryClient(application.getAppliedOffer().getIsSalaryClient())
                .isInsuranceEnabled(application.getAppliedOffer().getIsInsuranceEnabled())
                .amount(application.getAppliedOffer().getTotalAmount())
                .birthdate(application.getClient().getBirthDate())
                .firstName(application.getClient().getFirstName())
                .lastName(application.getClient().getLastName())
                .middleName(application.getClient().getMiddleName())
                .dependentAmount(finishRegistrationRequestDTO.getDependentAmount())
                .employment(finishRegistrationRequestDTO.getEmployment())
                .gender(finishRegistrationRequestDTO.getGender())
                .maritalStatus(finishRegistrationRequestDTO.getMaritalStatus())
                .passportIssueBranch(finishRegistrationRequestDTO.getPassportIssueBrach())
                .passportIssueDate(finishRegistrationRequestDTO.getPassportIssueDate())
                .passportNumber(application.getClient().getPassport().getPassportNumber())
                .passportSeries(application.getClient().getPassport().getPassportSeries())
                .term(application.getAppliedOffer().getTerm())
                .build();
        log.info("create new scoringDataDTO and return");
        log.info("end createNewScoringDataDTO method");
        return scoringDataDTO;
    }
}
