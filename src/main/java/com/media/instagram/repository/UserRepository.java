package com.media.instagram.repository;

import com.media.instagram.domain.User;
import com.media.instagram.domain.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getByEmail(String userEmail);

    int countByEmailAndUserType(String email, UserType userType);

    long countByNickname(String nickname);

    User getByEmailAndUserType(String email, UserType userType);
}
