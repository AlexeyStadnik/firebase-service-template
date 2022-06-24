package com.service.template.user.repository;

import com.service.template.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    @Modifying
    @Query(value = "UPDATE template_user SET deleted_at = now() WHERE id = :userId ", nativeQuery = true)
    void softDeleteById(@Param("userId") String userId);
}
