package com.study.springStudy.springmvc.chap04.service;

import com.study.springStudy.springmvc.chap04.comon.Search;
import com.study.springStudy.springmvc.chap04.dto.BoardDetailResponseDto;
import com.study.springStudy.springmvc.chap04.dto.BoardFindAllDto;
import com.study.springStudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springStudy.springmvc.chap04.dto.BoardWriterDto;
import com.study.springStudy.springmvc.chap04.entity.Board;
import com.study.springStudy.springmvc.chap04.mapper.BoardMapper;
import com.study.springStudy.springmvc.chap05.entity.ViewLog;
import com.study.springStudy.springmvc.chap05.mapper.ReactionMapper;
import com.study.springStudy.springmvc.chap05.mapper.ReplyMapper;
import com.study.springStudy.springmvc.chap05.mapper.ViewLogMapper;
import com.study.springStudy.springmvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.study.springStudy.springmvc.util.LoginUtil.*;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;
    private final ReplyMapper replyMapper;
    private final ViewLogMapper viewLogMapper;
    private final ReactionMapper reactionMapper;

    public List<BoardListResponseDto> getList(Search page) {
        List<BoardFindAllDto> all = boardMapper.findAll(page);

        //조회해온 게시물 리스트에서 각 게시물들의 조회수를 확인하여
        // 조회수가 5이상인 게시물에 특정 마킹
        return all.stream().map(BoardListResponseDto::new).collect(Collectors.toList());
    }

    public void save(BoardWriterDto boarddto, HttpSession session) {
        //계정명을 엔터티에 추가
        Board entity = boarddto.toEntity();
        entity.setAccount(LoginUtil.getLoggedUser(session));
        boardMapper.save(entity);
    }

    public BoardDetailResponseDto detail(int bno,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {

        // 게시물 정보 조회
        Board b = boardMapper.findOne(bno);

        HttpSession session = request.getSession();

        // 비회원이거나 본인 글이면 조회수 증가 방지
        String currentUserAccount = getLoggedUser(session);
        int boardNo = b.getBoardNo(); // 게시물 번호
        if (!isLoggedIn(session) || b.getAccount().equals(currentUserAccount)) {
            return new BoardDetailResponseDto(b);
        }

        // 조회수가 올라가는 조건처리 (쿠키버전)
//        if (shouldIncreaseViewCOunt(bno, request, response)) boardMapper.updateLook(bno);
//        return new BoardDetailResponseDto(b);
        //조회수가 올라가는 조건처리 ( DB 버전 )
        //1. 지금 조회하는 기록에 있는지 확인
        ViewLog viewLog = viewLogMapper.findOne(currentUserAccount, boardNo);
        boolean shouldIncrease = false; // 조회수 올려도 되는지 여부
        ViewLog viewLogEntity = ViewLog.builder()
                .account(currentUserAccount)
                .boardNo(boardNo)
                .viewTime(LocalDateTime.now())
                .build();
        if (viewLog == null) {
            //2. 이 게시물이 이 회원에 의해 처음 조회됨
            viewLogMapper.insertViewLog(viewLogEntity);
            shouldIncrease = true;
        } else {
            //3. 조회기록이 있는 경우 - 1시간 이내 인지
            //혹시 1시간이 지난 게시물인지 확인
            LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
            if (viewLog.getViewTime().isBefore(oneHourAgo)) {
                viewLogMapper.updateViewLog(viewLogEntity);
                shouldIncrease = true;
            }

        }
        if (shouldIncrease){
            boardMapper.updateLook(boardNo);
        }
        return new BoardDetailResponseDto(b);
    }


    //조회수 증가 여부를 판단
    /*
    만약 내가 처음 조회한 상대방의 게시물이면
    해당 게시물 번호로 쿠키를 생성 쿠키 안에는 생성 시간을 저장
    이후 게시물 조회시 반복해서 쿠키를 조회한 후 해당 쿠키가 존재할 시 false를 리턴해서 조회수 증가를 ㅂㅇ지
     */
    private boolean shouldIncreaseViewCOunt(int bno,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {

        // 쿠키 검사
        String cookieName = "view_" + bno;
        Cookie viewCookie = WebUtils.getCookie(request, cookieName);

        // 이 게시물에 대한 쿠키가 존재 -> 아까 조회한 게시물
        if (viewCookie != null) {
            return false;
        }

        // 쿠키 생성
        makeViewCookie(cookieName, response);
        return true;
    }
    private void makeViewCookie(String cookiename, HttpServletResponse response) {
        Cookie newCookie = new Cookie(cookiename, LocalDateTime.now().toString());
        newCookie.setPath("/");
        newCookie.setMaxAge(60 * 60);
        response.addCookie(newCookie);

    }


    public void delete(int num) {
        boardMapper.delete(num);
    }

    public int getCount(Search page) {
        return boardMapper.getCount(page);
    }
}
