package com.study.springStudy.springmvc.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// 로컬 서버에 저장된 이미지를 웹 브라우저에서 불러올 수 있도록
//로컬 서버 파일경로를 웹 서버 URL로 생성하는 설정

@Configuration
public class LocalResourceConfig implements WebMvcConfigurer {
    @Value("${file.upload.root-path}")
    private String rootPath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * ResourceLocations : 로컬에 있는 경로
         * ResourceHandler : 해당 로컬 경로를 Web Url로 변환시켜줌.
         *
         * ex :
         * D://xxx/dog.jpg
         * Local 접근- file:D:/xxx/dog.jpg
         * web 접근 - http://localhost:8383/local/dog.jpg
         */
        registry
                .addResourceHandler("/local/**")
                .addResourceLocations("file:" + rootPath);
    }
}
