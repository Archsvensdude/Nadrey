package com.web.mb;

import com.web.mb.AttractionSearching.model.Attraction_Detail;
import com.web.mb.AttractionSearching.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPageRepository extends JpaRepository<Users, String> {
    // JpaRepository의 findById를 그대로 사용하므로 추가 코드 필요 없음
}
