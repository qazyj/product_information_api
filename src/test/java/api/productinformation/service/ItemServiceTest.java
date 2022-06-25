package api.productinformation.service;

import api.productinformation.entity.Item;
import api.productinformation.dto.item.*;
import api.productinformation.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
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
        NewItem newItem = new NewItem("bb", "일반", 20000L, 10,
                "2022.01.01",
                "2023.01.01");
        ItemDto itemDto = (ItemDto) itemService.saveItem(newItem).getBody();

        //when
        Item findItem = itemRepository.findById(itemDto.getId()).get();

        //then
        assertThat(itemDto.getId()).isEqualTo(findItem.getId());
        assertThat(itemDto.getItemName()).isEqualTo(findItem.getItemName());
        assertThat(itemDto.getItemType()).isEqualTo("일반");
        assertThat(itemDto.getItemPrice()).isEqualTo(findItem.getItemPrice());
        assertThat(itemDto.getStartDate()).isEqualTo(findItem.getStartDate());
        assertThat(itemDto.getEndDate()).isEqualTo(findItem.getEndDate());
    }

    @Test
    public void 아이템_등록_후_삭제() throws Exception {
        //given
        NewItem newItem = new NewItem("bb", "일반", 20000L, 10,
                "2022.1.1",
                "2023.1.1");
        ItemDto itemDto = (ItemDto) itemService.saveItem(newItem).getBody();
        itemRepository.findById(itemDto.getId()).get();
        itemService.deleteItem(itemDto.getId());

        //when
        Optional<Item> findItem = itemRepository.findById(itemDto.getId());

        //then
        assertThat(findItem).isEqualTo(Optional.empty());
    }

    @Test
    public void 아이템_프로모션_api() throws Exception {
        //given

        //when
        ItemPromotionDto itemPromotionDto = (ItemPromotionDto) itemService.findItemPromotionById(1L).getBody();

        //then
        assertThat(itemPromotionDto.getSalePrice()).isEqualTo(19000L);
    }

    @Test
    public void 아이템_프로모션_api_프로모션이_없는_경우_ItemPrice_같다_salePrice() throws Exception {
        //given

        //when
        ItemPromotionDto itemPromotionDto = (ItemPromotionDto) itemService.findItemPromotionById(2L).getBody();

        //then
        assertThat(itemPromotionDto.getItemPrice()).isEqualTo(itemPromotionDto.getSalePrice());
    }
}