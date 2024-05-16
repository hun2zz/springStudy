package com.study.springStudy.webservlet.chap02.v4.controller;

import com.study.springStudy.webservlet.MemberMemoryRepo;
import com.study.springStudy.webservlet.Model;
import com.study.springStudy.webservlet.ModelAndView;
import com.study.springStudy.webservlet.entity.Member;

import java.util.List;
import java.util.Map;

public class ShowController implements ControllerV4{
    private MemberMemoryRepo repo = MemberMemoryRepo.getInstance();
    @Override
    public String process(Map<String, String> paramMap, Model model) {
        List<Member> memberList= repo.findAll();

        //2. 해당 회원정보를 jsp 파일에 전송하기 위한 세팅을 함
        //3. 적절한 jsp를 찾아서 화면 렌더링
        model.addAttribute("memberList", memberList);
        return "v4/m-list";
    }
}
