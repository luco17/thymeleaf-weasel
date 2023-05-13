package com.tamingthymeleaf.application.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserRepository extends CrudRepository<User, UserId>, PagingAndSortingRepository<User, UserId>, UserRepositoryCustom {
    boolean existsByEmail(Email email);
}