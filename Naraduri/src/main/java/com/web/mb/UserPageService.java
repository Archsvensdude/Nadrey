package com.web.mb;

import com.web.mb.AttractionSearching.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserPageService {

    @Autowired
    private UserPageRepository userPageRepository;

    public Optional<Users> getUsersById(String id) {
        return userPageRepository.findById(id); // JPA 기본 메서드 사용
    }
}
