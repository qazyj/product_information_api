package api.productinformation.repository;

import api.productinformation.dto.item.ItemDto;
import api.productinformation.entity.ItemPromotion;
import api.productinformation.entity.enumType.ItemType;
import api.productinformation.entity.enumType.UserType;
import api.productinformation.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    EntityManager em;

    @Test
    public void 유저가_살_수_있는_아이템_목록_수_일반_회원() throws Exception {
        //given
        // initDB에서 추가한 기본 데이터

        //when
        List<ItemDto> results = itemRepository.findCanBuyItemListByType(UserType.NORMAL).stream()
                .map(ItemDto::from).collect(Collectors.toList());

        //then
        assertThat(results.size()).isEqualTo(2);
    }

    @Test
    public void 유저가_살_수_있는_아이템_목록_수_기업_회원() throws Exception {
        //given
        // initDB에서 추가한 기본 데이터

        //when
        List<ItemDto> results = itemRepository.findCanBuyItemList().stream()
                .map(ItemDto::from).collect(Collectors.toList());

        //then
        assertThat(results.size()).isEqualTo(3);
    }

    @Test
    public void 유저가_살_수_있는_아이템_목록_수_아이템_추가_후() throws Exception {
        //given
        // initDB에서 추가한 기본 데이터
        for(int i = 0; i < 100; i++){
            Item item = Item.createItem("item"+i, ItemType.NORMAL, 1000L, 10,
                    LocalDate.of(2022,1,1),
                    LocalDate.of(2022,12,24));
            itemRepository.save(item);
        }
        em.flush();
        em.clear();

        //when
        List<ItemDto> results = itemRepository.findCanBuyItemList().stream()
                .map(ItemDto::from).collect(Collectors.toList());

        //then
        assertThat(results.size()).isEqualTo(103);
    }

    @Test
    public void 기업_아이템_추가_후_일반_유저_기업_유저회원이_살_수_있는_아이템_목록_수_테스트() throws Exception {
        //given
        // initDB에서 추가한 기본 데이터
        for(int i = 0; i < 100; i++){
            Item item = Item.createItem("item"+i, ItemType.CORPORATE, 1000L, 10,
                    LocalDate.of(2022,1,1),
                    LocalDate.of(2022,12,24));
            itemRepository.save(item);
        }
        em.flush();
        em.clear();

        //when
        List<ItemDto> normalResults = itemRepository.findCanBuyItemListByType(UserType.NORMAL).stream()
                .map(ItemDto::from).collect(Collectors.toList());
        List<ItemDto> corporateResults = itemRepository.findCanBuyItemList().stream()
                .map(ItemDto::from).collect(Collectors.toList());

        //then
        assertThat(normalResults.size()).isEqualTo(2);
        assertThat(corporateResults.size()).isEqualTo(103);
    }

    @Test
    public void 살_수_없는_날짜_아이템_추가_후_테스트() throws Exception {
        //given
        for(int i = 0; i < 100; i++){
            Item item = Item.createItem("item"+i, ItemType.CORPORATE, 1000L, 10,
                    LocalDate.of(2022,1,1),
                    LocalDate.of(2022,1,1));
            itemRepository.save(item);
        }
        em.flush();
        em.clear();

        //when
        List<ItemDto> results = itemRepository.findCanBuyItemListByType(UserType.NORMAL).stream()
                .map(ItemDto::from).collect(Collectors.toList());

        //then
        assertThat(results.size()).isEqualTo(2);
    }

    @Test
    public void 아이템_프로모션_정보_가져온_후_salesPrice_정렬() throws Exception {
        //given

        //when
        Item byIdIncludeMinPromotion = itemRepository.findByIdIncludePromotion(1L).get();
        Collections.sort(byIdIncludeMinPromotion.getItemPromotions(), new Comparator<ItemPromotion>() {
            @Override
            public int compare(ItemPromotion o1, ItemPromotion o2) {
                return o1.getSalePrice().intValue() - o2.getSalePrice().intValue();
            }
        });

        //then
        assertThat(byIdIncludeMinPromotion.getItemPromotions().get(0).getSalePrice()).isEqualTo(18000L);
        assertThat(byIdIncludeMinPromotion.getItemPromotions().get(1).getSalePrice()).isEqualTo(19000L);
    }
}