package api.productinformation.repository;

import api.productinformation.entity.promotion.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}
