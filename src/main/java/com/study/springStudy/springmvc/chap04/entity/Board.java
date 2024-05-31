package com.study.springStudy.springmvc.chap04.entity;

import com.study.springStudy.springmvc.chap03.entity.Grade;
import com.study.springStudy.springmvc.chap04.dto.BoardDto;
import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

//CREATE TABLE tbl_board (
//        board_no INT(8) PRIMARY KEY auto_increment,
//title VARCHAR(200) NOT NULL,
//content TEXT,
//writer varchar(100) NOT NULL,
//view_count INT(8) DEFAULT 0,
//reg_date_time DATETIME DEFAULT current_timestamp
//);


// entitydp 있는 클래스는 DB와 1대1 관계여야 한다. 필드에 있는 값이 DB에도 그대로 있어야 한다는 뜻.
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder // 테스트에서 너무 많은 값을 입력해야 하므로 빌더를 만들어줌.
public class Board {
    private int boardNo;
    private String title;
    private String content;
    private String writer; //작성자
    private int viewCount; //조회수
    private LocalDateTime regDateTime; // 작성일시
    private String account; // 글쓴이 계정명

    public Board(BoardDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.writer = dto.getWriter();
    }

    public Board(ResultSet rs ) throws SQLException {
        this.boardNo = rs.getInt("board_no");
        this.title = rs.getString("title");
        this.content = rs.getString("content");
        this.writer = rs.getString("writer");
        this.viewCount = rs.getInt("view_count");
        this.regDateTime = rs.getTimestamp("reg_date_time").toLocalDateTime();
    }
}
