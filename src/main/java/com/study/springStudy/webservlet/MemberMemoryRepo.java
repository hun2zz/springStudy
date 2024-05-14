package com.study.springStudy.webservlet;

import com.study.springStudy.webservlet.entity.Member;

import java.util.ArrayList;
import java.util.List;

public class MemberMemoryRepo {

    private MemberMemoryRepo() {
    }

    private static MemberMemoryRepo repo = new MemberMemoryRepo();

    public static MemberMemoryRepo getInstance() {
        return repo;
    }

    //필드
    private List<Member> memberList = new ArrayList<>();

    // 멤버 저장 기능
    public void save(Member member) {
        memberList.add(member);
    }


    //멤버 전체조회 기능
    public List<Member> findAll() {
        return memberList;
    }
}
