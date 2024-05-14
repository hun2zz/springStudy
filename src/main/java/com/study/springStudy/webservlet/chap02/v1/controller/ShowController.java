package com.study.springStudy.webservlet.chap02.v1.controller;

import com.study.springStudy.webservlet.MemberMemoryRepo;
import com.study.springStudy.webservlet.entity.Member;

import javax.imageio.IIOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowController implements ControllerV1 {
    private MemberMemoryRepo repo = MemberMemoryRepo.getInstance();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        //1. 적절한 저장소에서 회원정보들을 가져옴
        List<Member> memberList= repo.findAll();

        //2. 해당 회원정보를 jsp 파일에 전송하기 위한 세팅을 함
        request.setAttribute("memberList", memberList);
        //3. 적절한 jsp를 찾아서 화면 렌더링
        String view = "/WEB-INF/views/v1/m-list.jsp";
        RequestDispatcher dp = request.getRequestDispatcher(view);
        dp.forward(request, response);
    }
}
