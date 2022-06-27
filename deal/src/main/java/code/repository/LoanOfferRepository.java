package code.repository;

import code.model.LoanOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanOfferRepository extends JpaRepository<LoanOffer, Long> {
}
