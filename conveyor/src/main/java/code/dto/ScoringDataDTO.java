package code.dto;


import code.annotation.Age;
import code.annotation.Birthdate;
import code.annotation.Salary;
import code.enums.Gender;
import code.enums.MaritalStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Salary(message = "Denied. 20 of your salaries are less than the loan amount")
public class ScoringDataDTO {

    @DecimalMin(value = "10000", message = "at least 10000")
    private BigDecimal amount;

    @DecimalMin(value = "6", message = "at least 6")
    private Integer term;

    @Pattern(regexp = "^[A-Za-z]+$", message = "need latin letters")
    @Size(min = 2, max = 30, message = "size from 2 to 30 characters")
    private String firstName;

    @Pattern(regexp = "^[A-Za-z]+$", message = "need latin letters")
    @Size(min = 2, max = 30, message = "size from 2 to 30 characters")
    private String lastName;

    @Size(min = 2, max = 30, message = "size from 2 to 30 characters")
    private String middleName;


    private Gender gender;

    @Past
    @Birthdate(message = "age less 18")
    @Age(message = "Denied. Your age is under 20 or over 60")
    private LocalDate birthdate;

    @Size(min = 4, max = 4, message = "Need 4 numbers")
    private String passportSeries;

    @Size(min = 6, max = 6, message = "Need 6 numbers")
    private String passportNumber;


    private LocalDate passportIssueDate;

    @NotBlank
    private String passportIssueBranch;

    private MaritalStatus maritalStatus;

    @NotNull
    private Integer dependentAmount;

    @Valid
    @NotNull
    private EmploymentDTO employment;

    @NotBlank
    private String account;

    @NotNull
    private Boolean isInsuranceEnabled;

    @NotNull
    private Boolean isSalaryClient;

}
