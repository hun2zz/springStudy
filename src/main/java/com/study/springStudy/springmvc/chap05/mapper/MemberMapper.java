package com.study.springStudy.springmvc.chap05.mapper;


import com.study.springStudy.springmvc.chap05.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    //회원가입
    boolean save(Member member);
    //회원정보 개별 조회
    Member findOne (String account);
    //중복확인 ( 아이디, 이메일 )

    /**
     *
     * @param type - 어떤 걸 중복검사할지 (ex : account OR Email)
     * @param keyword - 중복검사할 실제 값
     * @return - 중복이면 true, 아니면 false
     */
    boolean existsById(@Param("type") String type, @Param("keyword") String keyword);

}
