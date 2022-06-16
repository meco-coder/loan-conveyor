package code.repository;

import code.model.PaymentScheduleElement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentScheduleElementRepository extends JpaRepository<PaymentScheduleElement, Long> {
}
