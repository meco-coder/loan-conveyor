package code.dto;

import code.annotation.Birthdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoanApplicationRequestDTO {

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

    @Email(message = "invalid email")
    private String email;

    @Birthdate(message = "age less 18")
    private LocalDate birthdate;

    @Size(min = 4, max = 4, message = "Need 4 numbers")
    private String passportSeries;

    @Size(min = 6, max = 6, message = "Need 6 numbers")
    private String passportNumber;


}
