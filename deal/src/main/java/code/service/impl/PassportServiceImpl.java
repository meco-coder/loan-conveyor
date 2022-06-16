package code.service.impl;

import code.dto.FinishRegistrationRequestDTO;
import code.dto.LoanApplicationRequestDTO;
import code.model.Passport;
import code.repository.PassportRepository;
import code.service.PassportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class PassportServiceImpl implements PassportService {
    private final PassportRepository passportRepository;

    public PassportServiceImpl(PassportRepository passportRepository) {
        this.passportRepository = passportRepository;
    }

    @Override
    public Passport createNewPassport(LoanApplicationRequestDTO dto) {
        log.info("start createNewPassport method");
        Passport passport = Passport.builder()
                .passportSeries(dto.getPassportSeries())
                .passportNumber(dto.getPassportNumber()).build();
        log.info("create new passport and save in repository");
        log.info("return passport: " + passport.toString());
        log.info("end createNewPassport method");
        return passportRepository.save(passport);
    }

    @Override
    public Passport updatePassport(FinishRegistrationRequestDTO dto, Passport passport) {
        log.info("start updatePassport method");
        passport.setPassportIssueBranch(dto.getPassportIssueBrach());
        passport.setPassportIssueDate(dto.getPassportIssueDate());
        log.info("update passport and save in repository");
        log.info("return updated passport: " + passport);
        log.info("end updatePassport method");
        return passportRepository.save(passport);
    }
}
