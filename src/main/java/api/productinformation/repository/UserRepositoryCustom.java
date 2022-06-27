package api.productinformation.repository;

import api.productinformation.entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<User> findOrderlistById(Long id);
}
