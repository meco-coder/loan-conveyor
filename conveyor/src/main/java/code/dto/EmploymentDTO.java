package code.dto;

import code.annotation.WorkExperienceCurrent;
import code.annotation.WorkExperienceTotal;
import code.enums.EmploymentStatus;
import code.enums.Position;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;


@NoArgsConstructor
@Getter
@Setter
public class EmploymentDTO {
    @code.annotation.EmploymentStatus(message = "Denied because you are unemployed")
    private EmploymentStatus employmentStatus;

    @NotBlank
    private String employerINN;


    private BigDecimal salary;


    private Position position;

    @WorkExperienceTotal(message = "Denied because your total work experience is less than 12 months")
    private Integer workExperienceTotal;

    @WorkExperienceCurrent(message = "Denied because your current work experience is less than 3 months")
    private Integer workExperienceCurrent;

}
