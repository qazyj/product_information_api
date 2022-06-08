package api.productinformation.controller;

import api.productinformation.entity.UserItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
            UserItem userItem1 = UserItem.createUserItem();
            UserItem userItem2 = UserItem.createUserItem();
            em.persist(userItem1);
            em.persist(userItem2);
        }
    }
}
