package com.study.springStudy.upload;


import com.study.springStudy.springmvc.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@Slf4j
public class UploadController {
    //업로드 루트 경로
    private String rootPath = "/Users/jinsanghun/Desktop/springPng/springUpload";
    @GetMapping("/upload/form")
    public String uploadFrom () {
        return "upload/upload-form";
    }

    @PostMapping("/upload/file")
    public String uploadFile(@RequestParam("thumbnail") MultipartFile file) {
        log.info("file-name : {}" , file.getOriginalFilename());
        log.info("file-size : {}", file.getSize() / 1024.0/1024.0);
        log.info("file-type : {}", file.getContentType());

        //첨부파일 서버에 저장하기
        //1. 루트 디렉토리를 생성
        File root = new File(rootPath); // 경로로 파일에 접근할 수 있게 객체를 생성함.
        if (!root.exists()) root.mkdirs(); // 만약에 경로에 폴더가 없으면 폴더 생성하기

        //2. 첨부파일의 경로를 만들어서 파일 객체로 포장
//        File uploadFile = new File(rootPath, file.getOriginalFilename());
        FileUtil.uploadFile(rootPath, file);

        //3. MultipartFile 객체로 저장명령
//        try {
//            file.transferTo(uploadFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return "redirect:/upload/form";
    }
}
