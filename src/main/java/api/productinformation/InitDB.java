package api.productinformation;

import api.productinformation.entity.*;
import api.productinformation.entity.enumType.ItemType;
import api.productinformation.entity.enumType.UserState;
import api.productinformation.entity.enumType.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 샘플 데이터 추가
 */
@Component
@RequiredArgsConstructor
public class InitDB {
    private final InitDBService initDBService;

    @PostConstruct
    public void init() {
        initDBService.init();
    }

    @Component
    static class InitDBService {
        @PersistenceContext
        private EntityManager em;

        @Transactional
        public void init() {


            User user1 = User.createUser("이수경", UserType.NORMAL, UserState.UNUSE, new Address("인천", "남동구 논고개로", "21667"));
            User user2 = User.createUser("최상면", UserType.CORPORATE, UserState.USE, new Address("인천", "남동구 논고개로", "21667"));
            User user3 = User.createUser("강재석", UserType.NORMAL, UserState.USE, new Address("인천", "남동구 논고개로", "21667"));
            User user4 = User.createUser("김구현", UserType.NORMAL, UserState.USE, new Address("인천", "남동구 논고개로", "21667"));
            em.persist(user1);
            em.persist(user2);
            em.persist(user3);
            em.persist(user4);

            Item item1 = Item.createItem("노브랜드 버터링", ItemType.NORMAL, 20000L, 10,
                    LocalDate.of(2022,1,1),
                            LocalDate.of(2022,1,1));
            Item item2 = Item.createItem("매일 아침 우유", ItemType.NORMAL, 1000L,10,
                    LocalDate.of(2021,1,1),
                    LocalDate.of(2023,5,5));
            Item item3 = Item.createItem("나이키 운동화", ItemType.CORPORATE, 40000L,10,
                    LocalDate.of(2020,1,1),
                    LocalDate.of(2023,12,31));
            Item item4 = Item.createItem("스타벅스 써머 텀블러", ItemType.NORMAL, 15000L,10,
                    LocalDate.of(2021,1,1),
                    LocalDate.of(2022,8,1));
            Item item5 = Item.createItem("크리스마스 케이크", ItemType.NORMAL, 30000L,10,
                    LocalDate.of(2022,12,24),
                    LocalDate.of(2022,12,31));

            em.persist(item1);
            em.persist(item2);
            em.persist(item3);
            em.persist(item4);
            em.persist(item5);

            Promotion promotion1 = Promotion.createPromotion("2022 쓱데이", 1000, null,
                    "2022.5.1", "2022.7.1");
            Promotion promotion2 = Promotion.createPromotion("스타벅스몰 오픈기념", null, 0.05,
                    "2021.1.5", "2022.12.31");
            Promotion promotion3 = Promotion.createPromotion("2021 쓱데이", 2000, null,
                    "2021.1.1", "2021.1.31");
            em.persist(promotion1);
            em.persist(promotion2);
            em.persist(promotion3);

            ItemPromotion itemPromotion1 = ItemPromotion.createItemPromotion(item1, promotion1);
            ItemPromotion itemPromotion2 = ItemPromotion.createItemPromotion(item2, promotion1);
            ItemPromotion itemPromotion3 = ItemPromotion.createItemPromotion(item3, promotion1);
            ItemPromotion itemPromotion4 = ItemPromotion.createItemPromotion(item4, promotion1);
            ItemPromotion itemPromotion5 = ItemPromotion.createItemPromotion(item5, promotion1);
            ItemPromotion itemPromotion6 = ItemPromotion.createItemPromotion(item4, promotion2);
            ItemPromotion itemPromotion7 = ItemPromotion.createItemPromotion(item1, promotion3);
            ItemPromotion itemPromotion8 = ItemPromotion.createItemPromotion(item2, promotion3);
            ItemPromotion itemPromotion9 = ItemPromotion.createItemPromotion(item3, promotion3);
            em.persist(itemPromotion1);
            em.persist(itemPromotion2);
            em.persist(itemPromotion3);
            em.persist(itemPromotion4);
            em.persist(itemPromotion5);
            em.persist(itemPromotion6);
            em.persist(itemPromotion7);
            em.persist(itemPromotion8);
            em.persist(itemPromotion9);


            OrderItem orderItem = OrderItem.createOrderItem(item1, 19000L, 2);
            OrderItem orderItem2 = OrderItem.createOrderItem(item1, 19000L, 2);
            OrderItem orderItem3 = OrderItem.createOrderItem(item1, 19000L, 2);
            OrderItem orderItem4 = OrderItem.createOrderItem(item1, 19000L, 2);
            OrderItem orderItem5 = OrderItem.createOrderItem(item2, 18000L, 2);
            em.persist(orderItem);
            em.persist(orderItem2);
            em.persist(orderItem3);
            em.persist(orderItem4);
            em.persist(orderItem5);

            Order savedOrder = Order.createOrder(user2, orderItem);
            Order savedOrder2 = Order.createOrder(user3, orderItem2);
            Order savedOrder3 = Order.createOrder(user3, orderItem3);
            Order savedOrder4 = Order.createOrder(user3, orderItem4);
            Order savedOrder5 = Order.createOrder(user2, orderItem5);
            em.persist(savedOrder);
            em.persist(savedOrder2);
            em.persist(savedOrder3);
            em.persist(savedOrder4);
            em.persist(savedOrder5);
        }
    }
}
