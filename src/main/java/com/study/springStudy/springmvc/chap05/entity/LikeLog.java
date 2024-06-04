package com.study.springStudy.springmvc.chap05.entity;


import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeLog {

//    CREATE TABLE like_log (
//            id INT primary key auto_increment,
//            account VARCHAR(50),
//    board_no INT,
//    view_time DATETIME
//);
//
//    ALTER TABLE like_log
//    ADD CONSTRAINT fk_member_likelog
//    FOREIGN KEY (account)
//    REFERENCES tbl_member (account);
//
//
//    ALTER TABLE like_log
//    ADD CONSTRAINT fk_board_likelog
//    FOREIGN KEY (board_no)
//    REFERENCES tbl_board (board_no);


    private String reactionId;
    private long boardNo;
    private String account;
    private String reactionType;
    private LocalDateTime reactionDate;
}
