package api.productinformation.service;

import api.productinformation.entity.Promotion;
import api.productinformation.dto.promotion.NewPromotion;
import api.productinformation.dto.promotion.PromotionDto;
import api.productinformation.repository.PromotionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
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
        NewPromotion newPromotion = new NewPromotion("promotion", null, 0.05,
                "2022.01.01",
                "2023.01.01");
        PromotionDto promotionDto = (PromotionDto) promotionService.savePromotion(newPromotion).getBody();

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
        NewPromotion newPromotion = new NewPromotion("promotion", 1000, null,
                "2022.01.01",
                "2023.01.01");
        PromotionDto promotionDto = (PromotionDto) promotionService.savePromotion(newPromotion).getBody();

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
        NewPromotion newPromotion = new NewPromotion("promotion", null, 0.05,
                "2022.01.01",
                "2023.01.01");
        PromotionDto promotionDto = (PromotionDto) promotionService.savePromotion(newPromotion).getBody();

        promotionRepository.findById(promotionDto.getId()).get();
        promotionService.deletePromotion(promotionDto.getId());

        //when
        Optional<Promotion> findPromotion = promotionRepository.findById(promotionDto.getId());

        //then
        assertThat(findPromotion).isEqualTo(Optional.empty());
    }

}