package code.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "loan_offers")
public class LoanOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long applicationId;
    private BigDecimal totalAmount;
    private BigDecimal requestedAmount;
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;
}
