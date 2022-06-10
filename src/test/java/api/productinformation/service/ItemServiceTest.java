package api.productinformation.service;

import api.productinformation.entity.Type;
import api.productinformation.entity.item.*;
import api.productinformation.repository.ItemRepository;
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
class ItemServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 아이템_등록() throws Exception {
        //given
        ItemAdd itemAdd = new ItemAdd("bb", "일반", 20000L,
                "2022.01.01",
                "2023.01.01");
        ItemDto itemDto = itemService.saveItem(itemAdd);

        //when
        Item findItem = itemRepository.findById(itemDto.getId()).get();

        //then
        assertThat(itemDto.getId()).isEqualTo(findItem.getId());
        assertThat(itemDto.getItemName()).isEqualTo(findItem.getItemName());
        if(findItem.getItemType().equals(Type.NORMAL))
            assertThat(itemDto.getItemType()).isEqualTo("일반");
        else
            assertThat(itemDto.getItemType()).isEqualTo("기업회원상품");
        assertThat(itemDto.getItemPrice()).isEqualTo(findItem.getItemPrice());
        assertThat(itemDto.getStartDate()).isEqualTo(findItem.getStartDate());
        assertThat(itemDto.getEndDate()).isEqualTo(findItem.getEndDate());
    }

    @Test
    public void 아이템_등록_후_삭제() throws Exception {
        //given
        ItemAdd itemAdd = new ItemAdd("bb", "일반", 20000L,
                "2022.1.1",
                "2023.1.1");
        ItemDto itemDto = itemService.saveItem(itemAdd);
        itemRepository.findById(itemDto.getId()).get();
        ItemSearch itemSearch = new ItemSearch();
        itemSearch.setId(itemDto.getId());
        itemService.deleteItem(itemSearch);

        //when
        Optional<Item> findItem = itemRepository.findById(itemDto.getId());

        //then
        assertThat(findItem).isEqualTo(Optional.empty());
    }

    @Test
    public void 아이템_프로모션_api() throws Exception {
        //given

        //when
        Optional<ItemPromotionDto> itemPromotionDto = itemService.findItemPromotionById(1L);

        //then
        assertThat(itemPromotionDto.get().getSalePrice()).isEqualTo(19000L);
    }

    @Test
    public void 아이템_프로모션_api_saleprice가_0인_경우_null_반환() throws Exception {
        //given

        //when
        Optional<ItemPromotionDto> itemPromotionDto = itemService.findItemPromotionById(2L);

        //then
        assertThat(itemPromotionDto).isEqualTo(Optional.empty());
    }
}