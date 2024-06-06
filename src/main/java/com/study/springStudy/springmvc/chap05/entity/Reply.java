package com.study.springStudy.springmvc.chap05.entity;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

//       -- 댓글 테이블 생성
//CREATE TABLE tbl_reply (
//        reply_no INT(8) PRIMARY KEY auto_increment,
//reply_text VARCHAR (1000) NOT NULL,
//reply_writer VARCHAR(100) NOT NULL,
//reply_date DATETIME default current_timestamp,
//board_no INT(8),
//constraint fk_reply
//foreign key (board_no)
//references tbl_board (board_no)
//            -- cascade = 원본글이 지워짐녀 댓글도 지워라!!!
//on delete cascade
//        );
@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reply {
    private long replyNo;
    private String replyText;
    private String replyWriter;
    private LocalDateTime replyDate;
    private long boardNo;
    @Setter
    private String account;
}
