package code.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class CreditDTO {

private BigDecimal amount;

private  Integer term;

private  BigDecimal monthlyPayment;

private BigDecimal rate;

private BigDecimal psk;

private Boolean isInsuranceEnabled;

private Boolean isSalaryClient;

private List<PaymentScheduleElementDTO> paymentSchedule;
}
