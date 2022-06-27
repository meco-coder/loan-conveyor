package code.service.model.application.impl;

import code.enums.ApplicationStatus;
import code.model.Application;
import code.model.ApplicationStatusHistory;
import code.model.Client;
import code.model.LoanOffer;
import code.repository.ApplicationRepository;
import code.service.model.application.ApplicationService;
import code.service.model.application.ApplicationStatusHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationStatusHistoryService applicationStatusHistoryService;
    private final ApplicationRepository applicationRepository;

    public ApplicationServiceImpl(ApplicationStatusHistoryService applicationStatusHistoryService,
                                  ApplicationRepository applicationRepository) {
        this.applicationStatusHistoryService = applicationStatusHistoryService;
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Application createNewApplication(Client client) {
        log.info("start createNewApplication method");
        List<ApplicationStatusHistory> applicationStatusHistoryList = List.of(applicationStatusHistoryService
                .createNewApplicationStatusHistory());
        Application application = applicationRepository.save(Application.builder()
                .client(client)
                .status(applicationStatusHistoryList.get(0).getChangeType())
                .statusHistory(applicationStatusHistoryList)
                .build());
        log.info("create new application and save in repository");
        log.info("return new application: " + application);
        log.info("end createNewApplication method");
        return application;
    }

    @Override
    public void updateApplication(LoanOffer loanOffer, ApplicationStatus applicationStatus) {
        log.info("start updateApplication method");
        Application application = applicationRepository.findById(loanOffer.getApplicationId()).get();
        log.info("find by id application");
        application.setAppliedOffer(loanOffer);
        List<ApplicationStatusHistory> applicationStatusHistoryList = application.getStatusHistory();
        applicationStatusHistoryList.add(applicationStatusHistoryService.
                updateApplicationStatusHistory(application.getStatus(), applicationStatus));
        application.setStatus(applicationStatus);
        application.setSignDate(LocalDate.now());
        log.info("update application and save in repository");
        log.info("return updated application: " + application);
        log.info("end updateApplication method");
        applicationRepository.save(application);
    }
}
