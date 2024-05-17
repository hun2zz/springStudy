package com.study.springStudy.springmvc.chap04.repository;

import com.study.springStudy.springmvc.chap04.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardJdbcRepository implements BoardRepository{
    private final JdbcTemplate template; // 필수


    @Override
    public List<Board> findAll(String sort) {
        String sql = "SELECT * FROM tbl_board";
        return template.query(sql, (rs, n) -> new Board(rs));
    }


    @Override
    public boolean save(Board board) {
                String sql = "INSERT INTO tbl_board (title, content, writer " +
                        ") VALUES (?, ?, ?)";
        return template.update(sql, board.getTitle(), board.getContent(), board.getWriter()) == 1;
    }

    @Override
    public boolean delete(int boardNum) {
        String sql = "DELETE FROM tbl_board WHERE board_no = ?";
        return template.update(sql,boardNum) == 1;
    }

    @Override
    public Board findOne(int num) {
        String sql = "SELECT * FROM tbl_board WHERE board_no = ?";
        return template.queryForObject(sql, (rs,n) -> new Board(rs), num);
    }

    @Override
    public boolean updateLook(Board board, int num) {
        String sql = "UPDATE tbl_board SET view_count = ? WHERE board_no = ?";
        return template.update(sql,board.getViewCount()+1, num) == 1;
    }



}
