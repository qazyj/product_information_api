package api.productinformation.repository;

import api.productinformation.entity.Item;
import api.productinformation.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    @Query("select p from Promotion p" +
            " where (p.startDate between :startDate and :endDate)" +
            " or (p.endDate between :startDate and :endDate)" +
            " or (:startDate between p.startDate and p.endDate)" +
            " or (:endDate between p.startDate and p.endDate)")
    List<Promotion> findItemConnectablePromotion(
            @Param("startDate") LocalDate startDate,
            @Param("endDate")LocalDate endDate);
}
