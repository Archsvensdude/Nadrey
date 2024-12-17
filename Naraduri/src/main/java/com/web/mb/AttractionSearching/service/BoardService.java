package com.web.mb.AttractionSearching.service;

import com.web.mb.AttractionSearching.model.Board;
import com.web.mb.AttractionSearching.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // 정렬된 모든 게시글 조회
    public List<Board> getAllBoardsSortedByNo() {
        return boardRepository.findAllByOrderByNoAsc();
    }

    // 특정 site에 해당하는 모든 게시글 조회
    public List<Board> getBoardsBySite(String site) {
        return boardRepository.findBySite(site);
    }

    // 특정 site에 해당하는 모든 게시글 조회 (리스트 반환)
    public List<Board> findBySite(String site) {
        return boardRepository.findBySite(site); // List<Board> 형식으로 반환
    }

    // 단일 게시글 반환 (첫 번째 게시글 또는 null 반환)
    public Board findFirstBySite(String site) {
        return boardRepository.findBySite(site).stream().findFirst().orElse(null);
    }

    public void saveBoard(Board board) {
        boardRepository.save(board);
    }

    @Transactional // 트랜잭션 시작
    public void deleteBoardsBySite(String site) {
        boardRepository.deleteBySite(site);
    }

}
