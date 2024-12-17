package com.web.mb.AiSearching.repository;

import com.web.mb.AttractionSearching.model.UserLikeTools;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLikeToolsRepository extends JpaRepository<UserLikeTools, Long> {
    List<UserLikeTools> findByUserId(String userId); // 사용자 ID로 좋아요 데이터 검색
}