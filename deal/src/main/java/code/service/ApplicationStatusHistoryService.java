package code.service;

import code.enums.ApplicationStatus;
import code.model.ApplicationStatusHistory;
import code.repository.ApplicationStatusHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Slf4j
@Service
public class ApplicationStatusHistoryService {
    private final ApplicationStatusHistoryRepository repository;

    public ApplicationStatusHistoryService(ApplicationStatusHistoryRepository repository) {
        this.repository = repository;
    }

    public ApplicationStatusHistory createNewApplicationStatusHistory() {
        log.info("start createNewApplicationStatusHistory method");
        ApplicationStatusHistory applicationStatusHistory = repository.save(ApplicationStatusHistory.builder()
                .changeType(ApplicationStatus.PREAPPROVAL)
                .build());
        log.info("create new application status history and save in repository");
        log.info("return application status history: " + applicationStatusHistory.toString());
        log.info("end createNewApplicationStatusHistory method");
        return applicationStatusHistory;

    }

    public ApplicationStatusHistory updateApplicationStatusHistory(ApplicationStatus oldApplicationStatus,
                                                                   ApplicationStatus newApplicationStatus) {
        log.info("start updateApplicationStatusHistory method");
        ApplicationStatusHistory applicationStatusHistory = repository.save(ApplicationStatusHistory.builder()
                .status(oldApplicationStatus)
                .changeType(newApplicationStatus)
                .build());
        log.info("update application status history");
        log.info("return updated application status history: " + applicationStatusHistory.toString());
        log.info("end updateApplicationStatusHistory method");
        return applicationStatusHistory;

    }

}
