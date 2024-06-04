package com.study.springStudy.springmvc.config;


import com.study.springStudy.springmvc.interceptor.AfterLoginInterceptor;
import com.study.springStudy.springmvc.interceptor.ApiAuthInterceptor;
import com.study.springStudy.springmvc.interceptor.AutoLoginInterceptor;
import com.study.springStudy.springmvc.interceptor.BoardInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//만들어 놓은 인터셉터들을 스프링 컨텍스트에 등록하는 설정 파일
@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
    private final AfterLoginInterceptor afterLoginInterceptor;
    private final BoardInterceptor boardInterceptor;
    private final AutoLoginInterceptor autoLoginInterceptor;
    private final ApiAuthInterceptor authInterceptor;
    private final ApiAuthInterceptor apiAuthInterceptor;

    //설정 메서드


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(afterLoginInterceptor)
                //해당 인터셉터가 동작할 URL을 설정
                .addPathPatterns("/members/sign-up", "/members/sign-in");


        registry.addInterceptor(boardInterceptor)
                .addPathPatterns("/board/*")
                .excludePathPatterns("/board/list", "/board/detail", "/board/like", "/board/dislike");

        registry.addInterceptor(autoLoginInterceptor)
                .addPathPatterns("/**");

        //REST API 인터셉터 등록
        registry.addInterceptor(apiAuthInterceptor)
                .addPathPatterns("/api/v1/**")
                .excludePathPatterns("/api/v1/replies/*/page/*");

    }


}
