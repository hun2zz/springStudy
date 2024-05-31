package com.study.springStudy.springmvc.util;

import com.study.springStudy.springmvc.chap05.dto.response.LoginUserInfoDto;

import javax.servlet.http.HttpSession;

public class LoginUtil {
    public static final String LOGIN = "login";

    //로그인 여부 확인
    public static boolean isLoggedIn (HttpSession session) {
        return session.getAttribute(LOGIN) != null;
    }


    public static String getLoggedUser (HttpSession session) {
        LoginUserInfoDto currentUser = (LoginUserInfoDto) session.getAttribute(LOGIN);
        return  (currentUser != null) ? currentUser.getAccount() : null;
    }
}
