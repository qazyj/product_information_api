package api.productinformation.repository;

import api.productinformation.entity.User;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.Optional;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom{
    private final EntityManager em;

    @Override
    public Optional<User> findOrderlistById(Long id) {
        return Optional.ofNullable(em.createQuery(
                "select user from User user" +
                " join fetch user.orders orders" +
                " where user.id = :id", User.class)
                .setParameter("id", id)
                .getSingleResult());
    }
}
