<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <%@include file="../include/static-head.jsp" %>


    <link rel="stylesheet" href="/assets/css/detail.css">
</head>
<body>
<%@include file="../include/header.jsp" %>
<div id="wrap" class="form-container" data-bno="${num.boardNo}">
    <h1>${num.boardNo}</h1>
    <h2>${num.regDateTime}</h2>

    <label for="writer">작성자</label>
    <input type="text" id="writer" name="writer" value="${num.writer}" readonly>

    <label for="title">제목</label>
    <input type="text" id="title" name="title" value="${num.title}" readonly>
    <label for="content">내용</label>
    <div id="content">${num.content}</div>

    <div class="buttons">
        <div class="reaction-buttons">
            <button id="like-btn">
                <i class="fas fa-thumbs-up"></i> 좋아요
                <span id="like-count">${num.likeCount}</span>
            </button>
            <button
                    id="dislike-btn"
                    class="dislike-btn"
            >
                <i class="fas fa-thumbs-down"></i> 싫어요
                <span id="dislike-count">${num.disLikeCount}</span>
            </button>
        </div>

        <button
                class="list-btn"
                type="button"
                onclick="window.location.href='${ref}'"
        >
            목록
        </button>
    </div>


    <!-- 댓글 영역 -->

    <div id="replies" class="row">
        <div class="offset-md-1 col-md-10">
            <!-- 댓글 쓰기 영역 -->
            <div class="card">
                <div class="card-body">
                    <c:if test="${!num.loginTrue}">
                        <a href="/members/sign-in?redirect=/board/detail?bno=${num.boardNo}">댓글은 로그인 후 작성해주세요!!</a>
                    </c:if>

                    <c:if test="${num.loginTrue}">
                        <div class="row">
                            <div class="col-md-9">
                                <div class="form-group">
                                    <label for="newReplyText" hidden>댓글 내용</label>
                                    <textarea rows="3" id="newReplyText" name="replyText" class="form-control"
                                              placeholder="댓글을 입력해주세요."></textarea>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="newReplyWriter" hidden>댓글 작성자</label>
                                    <input id="newReplyWriter" name="replyWriter" type="text" value="${login.nickName}"
                                           readonly
                                           class="form-control" placeholder="작성자 이름"
                                           style="margin-bottom: 6px;">
                                    <button id="replyAddBtn" type="button"
                                            class="btn btn-dark form-control">등록
                                    </button>
                                    <div class="profile-box">
                                        <c:choose>
                                            <c:when test="${login != null && login.profileImg != null}">
                                                <img src="${login.profileImg}" alt="profile image">
                                            </c:when>
                                            <c:otherwise>
                                                <img src="/assets/img/anonymous.jpg" alt="profile image">
                                            </c:otherwise>
                                        </c:choose>
                                    </div>                                </div>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div> <!-- end reply write -->

            <!--댓글 내용 영역-->
            <div class="card">
                <!-- 댓글 내용 헤더 -->
                <div class="card-header text-white m-0" style="background: #343A40;">
                    <div class="float-left">댓글 (<span id="replyCnt">0</span>)</div>
                </div>

                <!-- 댓글 내용 바디 -->
                <div id="replyCollapse" class="card">
                    <div id="replyData">
                        <!--
                        < JS로 댓글 정보 DIV삽입 >
                    -->
                    </div>

                    <!-- 댓글 페이징 영역 -->
                    <ul class="pagination justify-content-center">
                        <!--
                        < JS로 댓글 페이징 DIV삽입 >
                    -->
                    </ul>
                </div>
            </div> <!-- end reply content -->
        </div>
    </div> <!-- end replies row -->

    <!-- 댓글 수정 모달 -->
    <div class="modal fade bd-example-modal-lg" id="replyModifyModal">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">

                <!-- Modal Header -->
                <div class="modal-header" style="background: #343A40; color: white;">
                    <h4 class="modal-title">댓글 수정하기</h4>
                    <button type="button" class="close text-white" data-bs-dismiss="modal">X</button>
                </div>

                <!-- Modal body -->
                <div class="modal-body">
                    <div class="form-group">
                        <input id="modReplyId" type="hidden">
                        <label for="modReplyText" hidden>댓글내용</label>
                        <textarea id="modReplyText" class="form-control" placeholder="수정할 댓글 내용을 입력하세요."
                                  rows="3"></textarea>
                    </div>
                </div>

                <!-- Modal footer -->
                <div class="modal-footer">
                    <button id="replyModBtn" type="button" class="btn btn-dark">수정</button>
                    <button id="modal-close" type="button" class="btn btn-danger"
                            data-bs-dismiss="modal">닫기
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!—- end replyModifyModal -->
    <!-- 로딩 스피너 -->
    <div class="spinner-container" id="loadingSpinner">
        <div class="spinner-border text-light" role="status">
            <span class="visually-hidden">Loading...</span>
        </div>
    </div>

</div>
<script type="module" src="/assets/js/reply.js"></script>
<script>

    //렌더링 ㅇ초기에 버튼활성화
    const userReaction = '${num.userReaction}'
    updateReactionButtons(userReaction);

    //서버에 좋아요, 싫어요 요청을 보내는 함수
    async function sendReaction(reactionType) {
        console.log(reactionType)
        const bno = document.getElementById('wrap').dataset.bno;
        const res = await fetch(`/board/\${reactionType}?bno=\${bno}`);
        if (res.status === 403) {
            alert('로그인이 필요한데?ㅋㅋ')
            return
        }
        const {likeCount, disLikeCount, userReaction} = await res.json();

        document.getElementById('like-count').textContent = likeCount;
        document.getElementById('dislike-count').textContent = disLikeCount;

        //버튼 활성화 스타일 처이
        updateReactionButtons(userReaction);


    }

    function updateReactionButtons(userReaction) {
        const $likeBtn = document.getElementById('like-btn');
        const $disLikeBtn = document.getElementById('dislike-btn');
        const ACTIVE = 'active';
        // 좋아요버튼이 눌렸을 경우
        if (userReaction === 'LIKE') {
            $likeBtn.classList.add(ACTIVE);
            $disLikeBtn.classList.remove(ACTIVE);
        } else if (userReaction === 'DISLIKE') {
            $disLikeBtn.classList.add(ACTIVE);
            $likeBtn.classList.remove(ACTIVE);
        } else {
            $disLikeBtn.classList.remove(ACTIVE);
            $likeBtn.classList.remove(ACTIVE);
        }
    }

    //좋아요 클릭 이벤트
    document.getElementById('like-btn').addEventListener('click', e => {
        // console.log('like')
        sendReaction('like');
    })

    //싫어요 클릭 이벤트
    document.getElementById('dislike-btn').addEventListener('click', e => {
        // console.log('like')
        sendReaction('dislike');
    })
</script>
</body>
</html>
