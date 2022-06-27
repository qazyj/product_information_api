package api.productinformation.repository;

import api.productinformation.dto.user.UserOrderItemDto;
import api.productinformation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    /*@Query(value = "select user from User user" +
            " join fetch user.orders orders" +
            " where user.id = :id", nativeQuery = true)
    User findOrderlistById(Long id);*/
}