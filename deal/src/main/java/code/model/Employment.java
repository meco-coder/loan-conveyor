package code.model;


import code.enums.EmploymentStatus;
import code.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "employments")
public class Employment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus;
    private String employer;
    private BigDecimal salary;
    @Enumerated(EnumType.STRING)
    private Position position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;


}
