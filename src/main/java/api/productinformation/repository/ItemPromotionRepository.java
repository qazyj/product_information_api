package api.productinformation.repository;

import api.productinformation.entity.ItemPromotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPromotionRepository extends JpaRepository<ItemPromotion, Long> {
}
