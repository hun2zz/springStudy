<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../include/static-head.jsp"%>

    <style>
        #img-input {
            display: none;
        }
        .upload-box {
            width: 150px;
            height: 150px;
            border: 3px dashed orange;
            display: flex;
            margin-top: 100px;
            justify-content: center;
            align-items: center;
            color: red;
            font-weight: 700;
            cursor: pointer;
        }
    </style>
</head>
<body>
<%@include file="../include/header.jsp"%>

<h1>파일 업로드 예제</h1>

<div class="upload-box">여기를 눌러 파일을 올려주세요</div>

<form action="members/profile-update" method="post" enctype="multipart/form-data">

    <input type="file" name="thumbnail" id="img-input" accept="image/*">

    <button type="submit">전송</button>
</form>
    <script>
        document.querySelector('.upload-box').onclick = e=> {
            document.getElementById('img-input').click();
        }
    </script>
<script>
//프로필 사진 동그라미 썸네일 부분

//실제 프로필사진이 첨부될 input
const $fileInput = document.getElementById('img-input');


//프로필 사진 선택 시 썸네일 보여주기
$fileInput.addEventListener('change', e=> {
console.log('file changed!')
//사용자가 첨부한 파잎 데이터 읽어오기
const fileData = $fileInput.files[0];
// console.log(fileData)

//첨부파일 이미지의 로우데이터(바이트)를 읽는 객체 생성
const reader = new FileReader();

//파일의 데이터를 읽어서 img 태그에 src 속성에 넣기 위해
// 파일을 URL 형태로 변경
reader.readAsDataURL(fileData)

//첨부파일이 등록되는 순간
// img 태그에 임지ㅣ를 세팅
reader.onloadend = e => {
const $img = document.querySelector('.thumbnail-box img')
$img.src = reader.result;
}


})

</script>
</body>
</html>