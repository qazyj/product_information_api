package api.productinformation.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PromotionTest {
    @Autowired
    EntityManager em;

    @Test
    public void 프로모션_엔티티_등록() throws Exception {
        //given
        Promotion promotion =
                Promotion.createPromotion("promotion", null, 0.05,
                "2022,1,1", "2022,1,1");
        em.persist(promotion);
        em.flush();
        em.clear();

        //when
        Promotion findPromotion = em.find(Promotion.class, promotion.getId());

        //then
        assertThat(promotion.getId()).isEqualTo(findPromotion.getId());
        assertThat(promotion.getPromotionName()).isEqualTo(findPromotion.getPromotionName());
        assertThat(promotion.getDiscountAmount()).isEqualTo(findPromotion.getDiscountAmount());
        assertThat(promotion.getDiscountRate()).isEqualTo(findPromotion.getDiscountRate());
        assertThat(promotion.getStartDate()).isEqualTo(findPromotion.getStartDate());
        assertThat(promotion.getEndDate()).isEqualTo(findPromotion.getEndDate());
    }

    @Test
    public void 프로모션_아이템_연관관계() throws Exception {
        //given
        Promotion promotion =
                Promotion.createPromotion("promotion", null, 0.05,
                        "2022,1,1", "2022,1,1");
        Item item = Item.createItem("bb", "일반", 20000L,
                "2022,1,1", "2022,1,1");
        em.persist(promotion);
        em.persist(item);
        em.flush();
        em.clear();

        Item findItem = em.find(Item.class, item.getId());
        Promotion findPromotion = em.find(Promotion.class, promotion.getId());
        ItemPromotion itemPromotion = ItemPromotion.createItemPromotion(findItem, findPromotion);
        em.persist(itemPromotion);
        em.flush();
        em.clear();

        //when
        Promotion findPromotion2 = em.find(Promotion.class, promotion.getId());
        Item findItem2 = em.find(Item.class, item.getId());

        //then
        // itemPromotion, promotion의 start,end date는 같아야 한다.
        assertThat(findPromotion2.getItemPromotions().get(0).getStartDate()).isEqualTo(findPromotion.getStartDate());
        assertThat(findPromotion2.getItemPromotions().get(0).getEndDate()).isEqualTo(findPromotion.getEndDate());
        // promotion -> itempromotion -> item과 item의 start, end date는 같아야 한다.
        assertThat(findPromotion2.getItemPromotions().get(0).getItem().getStartDate()).isEqualTo(findItem.getStartDate());
        assertThat(findPromotion2.getItemPromotions().get(0).getItem().getEndDate()).isEqualTo(findItem.getEndDate());
        // item -> itempromotion과 item -> itempromotion -> promotion의 start, end date는 같아야 한다.
        assertThat(findItem2.getItemPromotions().get(0).getStartDate()).isEqualTo(findItem2.getItemPromotions().get(0).getPromotion().getStartDate());
        assertThat(findItem2.getItemPromotions().get(0).getEndDate()).isEqualTo(findItem2.getItemPromotions().get(0).getPromotion().getEndDate());
    }

    @Test
    public void discountRate() throws Exception {
        //given
        Promotion promotion =
                Promotion.createPromotion("promotion", null, 0.05,
                        "2022,1,1", "2022,1,1");
        Item item = Item.createItem("bb", "일반", 20000L,
                "2022,1,1", "2022,1,1");
        em.persist(promotion);
        em.persist(item);
        em.flush();
        em.clear();

        Item findItem = em.find(Item.class, item.getId());
        Promotion findPromotion = em.find(Promotion.class, promotion.getId());
        ItemPromotion itemPromotion = ItemPromotion.createItemPromotion(findItem, findPromotion);
        em.persist(itemPromotion);
        em.flush();
        em.clear();

        //when
        Promotion findPromotion2 = em.find(Promotion.class, promotion.getId());

        //then
        assertThat(findPromotion2.getItemPromotions().get(0).getSalePrice()).isEqualTo(20000L-(long)(20000L*0.05));
    }

    @Test
    public void discountAmount() throws Exception {
        //given
        Promotion promotion =
                Promotion.createPromotion("promotion", 2000, null,
                        "2022,1,1", "2022,1,1");
        Item item = Item.createItem("bb", "일반", 20000L,
                "2022,1,1", "2022,1,1");
        em.persist(promotion);
        em.persist(item);
        em.flush();
        em.clear();

        Item findItem = em.find(Item.class, item.getId());
        Promotion findPromotion = em.find(Promotion.class, promotion.getId());
        ItemPromotion itemPromotion = ItemPromotion.createItemPromotion(findItem, findPromotion);
        em.persist(itemPromotion);
        em.flush();
        em.clear();

        //when
        Promotion findPromotion2 = em.find(Promotion.class, promotion.getId());

        //then
        assertThat(findPromotion2.getItemPromotions().get(0).getSalePrice()).isEqualTo(20000L-2000);
    }
}