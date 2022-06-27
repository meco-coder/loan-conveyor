package code.service.model.client.impl;

import code.dto.EmploymentDTO;
import code.model.Employment;
import code.repository.EmploymentRepository;
import code.service.model.client.EmploymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@Slf4j
public class EmploymentServiceImpl implements EmploymentService {
    private final EmploymentRepository employmentRepository;

    public EmploymentServiceImpl(EmploymentRepository employmentRepository) {
        this.employmentRepository = employmentRepository;
    }

    @Override
    public Employment createNewEmployment(EmploymentDTO dto) {
        log.info("start createNewEmployment method");
        Employment employment = Employment.builder()
                .employer(dto.getEmployerINN())
                .employmentStatus(dto.getEmploymentStatus())
                .position(dto.getPosition())
                .salary(dto.getSalary())
                .workExperienceCurrent(dto.getWorkExperienceCurrent())
                .workExperienceTotal(dto.getWorkExperienceTotal())
                .build();
        log.info("create new employment and save in repository");
        log.info("return employment");
        log.info("end createNewEmployment method");
        return employmentRepository.save(employment);
    }
}
