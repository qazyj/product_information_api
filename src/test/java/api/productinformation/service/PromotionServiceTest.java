package api.productinformation.service;

import api.productinformation.entity.promotion.Promotion;
import api.productinformation.entity.promotion.PromotionAdd;
import api.productinformation.entity.promotion.PromotionDto;
import api.productinformation.entity.promotion.PromotionSearch;
import api.productinformation.repository.PromotionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class PromotionServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    PromotionService promotionService;
    @Autowired
    PromotionRepository promotionRepository;

    @Test
    public void 프로모션_discountAmount_null_등록() throws Exception {
        //given
        PromotionAdd promotionAdd = new PromotionAdd("promotion", null, 0.05,
                LocalDate.of(2022,1,1),
                LocalDate.of(2023,1,1));
        PromotionDto promotionDto = promotionService.savePromotion(promotionAdd);

        //when
        Promotion findPromotion = promotionRepository.findById(promotionDto.getId()).get();

        //then
        assertThat(promotionDto.getId()).isEqualTo(findPromotion.getId());
        assertThat(promotionDto.getPromotionName()).isEqualTo(findPromotion.getPromotionName());
        assertThat(promotionDto.getDiscountAmount()).isEqualTo(findPromotion.getDiscountAmount());
        assertThat(promotionDto.getStartDate()).isEqualTo(findPromotion.getStartDate());
        assertThat(promotionDto.getEndDate()).isEqualTo(findPromotion.getEndDate());
    }

    @Test
    public void 프로모션_discountRate_null_등록() throws Exception {
        //given
        PromotionAdd promotionAdd = new PromotionAdd("promotion", 1000, null,
                LocalDate.of(2022,1,1),
                LocalDate.of(2023,1,1));
        PromotionDto promotionDto = promotionService.savePromotion(promotionAdd);

        //when
        Promotion findPromotion = promotionRepository.findById(promotionDto.getId()).get();

        //then
        assertThat(promotionDto.getId()).isEqualTo(findPromotion.getId());
        assertThat(promotionDto.getPromotionName()).isEqualTo(findPromotion.getPromotionName());
        assertThat(promotionDto.getDiscountAmount()).isEqualTo(findPromotion.getDiscountAmount());
        assertThat(promotionDto.getStartDate()).isEqualTo(findPromotion.getStartDate());
        assertThat(promotionDto.getEndDate()).isEqualTo(findPromotion.getEndDate());
    }

    @Test
    public void 프로모션_등록_후_삭제() throws Exception {
        //given
        //given
        PromotionAdd promotionAdd = new PromotionAdd("promotion", null, 0.05,
                LocalDate.of(2022,1,1),
                LocalDate.of(2023,1,1));
        PromotionDto promotionDto = promotionService.savePromotion(promotionAdd);

        promotionRepository.findById(promotionDto.getId()).get();
        PromotionSearch promotionSearch = new PromotionSearch();
        promotionSearch.setId(promotionDto.getId());
        promotionService.deletePromotion(promotionSearch);

        //when
        Optional<Promotion> findPromotion = promotionRepository.findById(promotionDto.getId());

        //then
        assertThat(findPromotion).isEqualTo(Optional.empty());
    }

}