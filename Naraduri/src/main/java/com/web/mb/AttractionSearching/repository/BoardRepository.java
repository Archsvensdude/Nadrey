package com.web.mb.AttractionSearching.repository;

import com.web.mb.AttractionSearching.model.Board;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    // NO 컬럼 기준으로 오름차순 정렬된 데이터 조회
    List<Board> findAllByOrderByNoAsc();

    // 특정 관광지의 site 이름을 통해 게시글 조회
    List<Board> findBySite(String site);

    // 좋아요 순으로 상위 5개 관광지 반환
    List<Board> findTop5ByOrderByLikesDesc();

    // 사용자 개인화 추천 쿼리 (필요 시 JPQL 사용 가능)
    @Query("SELECT b FROM Board b WHERE b.site IN (SELECT l.siteName FROM UserLikes l WHERE l.userId = :userId)")
    List<Board> findPersonalizedRecommendations(@Param("userId") String userId);

    @Modifying // 데이터베이스 수정 작업을 명시
    @Query("DELETE FROM Board b WHERE b.site = :site")
    void deleteBySite(String site);
}