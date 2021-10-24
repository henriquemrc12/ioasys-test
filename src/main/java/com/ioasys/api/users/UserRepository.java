package com.ioasys.api.users;

import com.ioasys.api.users._enums.UserStatus;
import com.ioasys.api.users._enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    Optional<List<UserEntity>> findAllByUserType(UserType userType);
    Optional<UserEntity> findByEmail(String email);

    Optional<List<UserEntity>> findAllByUserTypeAndUserStatus(UserType userType, UserStatus userStatus);
}
