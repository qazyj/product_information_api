package api.productinformation.repository;

import api.productinformation.entity.Type;
import api.productinformation.entity.item.Item;
import api.productinformation.entity.item.ItemDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
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
        List<ItemDto> results = itemRepository.findCanBuyItemListByType(Type.NORMAL);

        //then
        assertThat(results.size()).isEqualTo(2);
    }

    @Test
    public void 유저가_살_수_있는_아이템_목록_수_기업_회원() throws Exception {
        //given
        // initDB에서 추가한 기본 데이터

        //when
        List<ItemDto> results = itemRepository.findCanBuyItemList();

        //then
        assertThat(results.size()).isEqualTo(3);
    }

    @Test
    public void 유저가_살_수_있는_아이템_목록_수_아이템_추가_후() throws Exception {
        //given
        // initDB에서 추가한 기본 데이터
        for(int i = 0; i < 100; i++){
            Item item = Item.createItem("item"+i, "일반", 1000L, "2021.1.1", "2022.12.24");
            itemRepository.save(item);
        }
        em.flush();
        em.clear();

        //when
        List<ItemDto> results = itemRepository.findCanBuyItemList();

        //then
        assertThat(results.size()).isEqualTo(103);
    }

    @Test
    public void 기업_아이템_추가_후_일반_유저_기업_유저회원이_살_수_있는_아이템_목록_수_테스트() throws Exception {
        //given
        // initDB에서 추가한 기본 데이터
        for(int i = 0; i < 100; i++){
            Item item = Item.createItem("item"+i, "기업회원상품", 1000L, "2021.1.1", "2022.12.24");
            itemRepository.save(item);
        }
        em.flush();
        em.clear();

        //when
        List<ItemDto> normalResults = itemRepository.findCanBuyItemListByType(Type.NORMAL);
        List<ItemDto> corporateResults = itemRepository.findCanBuyItemList();

        //then
        assertThat(normalResults.size()).isEqualTo(2);
        assertThat(corporateResults.size()).isEqualTo(103);
    }

    @Test
    public void 살_수_없는_날짜_아이템_추가_후_테스트() throws Exception {
        //given
        for(int i = 0; i < 100; i++){
            Item item = Item.createItem("item"+i, "기업회원상품", 1000L, "2021.1.1", "2022.1.1");
            itemRepository.save(item);
        }
        em.flush();
        em.clear();

        //when
        List<ItemDto> results = itemRepository.findCanBuyItemListByType(Type.NORMAL);

        //then
        assertThat(results.size()).isEqualTo(2);
    }
}