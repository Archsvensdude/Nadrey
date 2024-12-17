package com.web.mb.login.repository;

import com.web.mb.AttractionSearching.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, String> {
    Optional<Users> findByIdAndPassword(@NonNull String id, @NonNull String password);
    boolean existsById(@NonNull String id);         // 아이디 중복 체크
    boolean existsByEmail(@NonNull String email);   // 이메일 중복 체크

    Optional<Users> findByNameAndEmail(String name, String email);

    Optional<Users> findByNameAndIdAndEmail(String name, String id, String email);

    // 사용자 ID 업데이트
    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.id = :newId WHERE u.id = :currentId")
    void updateUserId(@Param("currentId") String currentId, @Param("newId") String newId);

    @Modifying
    @Query("UPDATE Users u SET u.userLikes = u.userLikes + 1 WHERE u.id = :id")
    void incrementUserLikes(@Param("id") String id);
}