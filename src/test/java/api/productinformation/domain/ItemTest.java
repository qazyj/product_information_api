package api.productinformation.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemTest {
    @Autowired
    EntityManager em;

    @Test
    public void 아이템_엔티티_등록() throws Exception {
        //given
        UserItem userItem = new UserItem();
        Item item = Item.createItem("bb", "일반", 20000L, userItem,
                LocalDate.of(2022,1,1),
                LocalDate.of(2023,1,1));
        em.persist(userItem);
        em.persist(item);
        em.flush();
        em.clear();

        //when
        Item findItem = em.find(Item.class, item.getId());

        //then
        assertThat(findItem.getItemName()).isEqualTo("bb");
        assertThat(findItem.getItemType()).isEqualTo(Type.NORMAL);
        assertThat(findItem.getItemPrice()).isEqualTo(20000L);
        assertThat(findItem.getStartDate()).isEqualTo(LocalDate.of(2022,1,1));
        assertThat(findItem.getEndDate()).isEqualTo(LocalDate.of(2023,1,1));

    }
}