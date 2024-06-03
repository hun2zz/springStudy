package com.study.springStudy.springmvc.chap04.service;

import com.study.springStudy.springmvc.chap04.comon.Search;
import com.study.springStudy.springmvc.chap04.dto.BoardDetailResponseDto;
import com.study.springStudy.springmvc.chap04.dto.BoardFindAllDto;
import com.study.springStudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springStudy.springmvc.chap04.dto.BoardWriterDto;
import com.study.springStudy.springmvc.chap04.entity.Board;
import com.study.springStudy.springmvc.chap04.mapper.BoardMapper;
import com.study.springStudy.springmvc.chap05.mapper.ReplyMapper;
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

    public List<BoardListResponseDto> getList (Search page) {
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
        if (!isLoggedIn(session) || b.getAccount().equals(getLoggedUser(session))) {
            return new BoardDetailResponseDto(b);
        }

        // 조회수가 올라가는 조건처리
        if (shouldIncreaseViewCOunt(bno, request, response)) boardMapper.updateLook(bno);
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
