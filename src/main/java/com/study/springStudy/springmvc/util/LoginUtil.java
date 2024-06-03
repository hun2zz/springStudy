package com.study.springStudy.springmvc.util;

import com.study.springStudy.springmvc.chap05.dto.response.LoginUserInfoDto;
import com.study.springStudy.springmvc.chap05.entity.Auth;

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


    public static LoginUserInfoDto getLogUser(HttpSession session) {
        return (LoginUserInfoDto) session.getAttribute(LOGIN);
    }

    public static boolean isAdmin(HttpSession session) {
        LoginUserInfoDto loggedInUser = getLogUser(session);
        Auth auth = null;
        if (isLoggedIn(session)) {
            auth = Auth.valueOf(loggedInUser.getAuth());
        }
        return auth == Auth.ADMIN;
    }
}
