package com.study.springStudy.springmvc.chap05;

import com.study.springStudy.springmvc.chap04.entity.Board;
import com.study.springStudy.springmvc.chap04.mapper.BoardMapper;
import com.study.springStudy.springmvc.chap05.entity.Reply;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyMapperTest {

    @Autowired
    BoardMapper boardMapper;
    @Autowired
    ReplyMapper replyMapper;

    @Test
    @DisplayName("")
    void bulkInsert() {
        //게시물 100개와 댓글 5000개를 랜덤으로 등록
        for (int i = 0; i < 100; i++) {
            Board b = Board.builder().title("재밌는 글" + i).content("응 개노잼이야"+ i).writer("아무무나" + i).build();

            boardMapper.save(b);
        } // 게시물 100개 생성

        for (int i = 0; i < 5000; i++) {
            Reply r = Reply.builder().replyText("하하호호 댓글" + i).replyWriter("밍밍~" + i)
                    .boardNo((long) (Math.random() * 100 + 1))
                    .build();

            replyMapper.save(r);
        }
        //given

        //when

        //then
    }


}