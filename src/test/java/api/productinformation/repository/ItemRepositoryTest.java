package api.productinformation.repository;

import api.productinformation.entity.Type;
import api.productinformation.entity.item.Item;
import api.productinformation.entity.item.ItemDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

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
        }

        //when
        List<ItemDto> results = itemRepository.findCanBuyItemList();

        //then
        assertThat(results.size()).isEqualTo(3);
    }
}