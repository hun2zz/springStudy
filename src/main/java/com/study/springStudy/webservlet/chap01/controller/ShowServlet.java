package com.study.springStudy.webservlet.chap01.controller;

import com.study.springStudy.webservlet.MemberMemoryRepo;
import com.study.springStudy.webservlet.entity.Member;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/chap01/show")
public class ShowServlet extends HttpServlet {
    private MemberMemoryRepo repo = MemberMemoryRepo.getInstance();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 적절한 저장소에서 회원정보들을 가져옴
        List<Member> memberList= repo.findAll();

        //2. 해당 회원정보를 jsp 파일에 전송하기 위한 세팅을 함
        req.setAttribute("memberList", memberList);
        //3. 적절한 jsp를 찾아서 화면 렌더링
        String view = "/WEB-INF/views/m-list.jsp";
        RequestDispatcher dp = req.getRequestDispatcher(view);
        dp.forward(req, resp);
    }
}
