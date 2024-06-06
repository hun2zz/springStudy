import {BASE_URL} from "./reply.js";
import {showSpinner, hideSpinner} from "./spinner.js";
import { callApi } from "./api.js";

function getRelativeTime(createAt) {
    //현재 시간
    const now = new Date();
    //등록 시간 날짜타입으로 변환
    const past = new Date(createAt);

    //사이 시간 구하기
    const diff = now - past
    // console.log(diff)
    const seconds = Math.floor(diff / 1000);
    const minutes = Math.floor(diff / 1000 / 60);
    const hours = Math.floor(diff / 1000 / 60 / 60);
    const days = Math.floor(diff / 1000 / 60 / 60 / 24);
    const weeks = Math.floor(diff / 1000 / 60 / 60 / 24 / 7);
    const years = Math.floor(diff / 1000 / 60 / 60 / 24 / 365);


    if (seconds < 60) {
        return '방금 전';
    } else if (minutes < 60) {
        return `${minutes}분 전`;
    } else if (hours < 24) {
        return `${hours}시간 전`;
    } else if (days < 7) {
        return `${days}일 전`;
    } else if (weeks < 52) {
        return `${weeks}주 전`;
    } else {
        return `${years}년 전`;
    }
}


export async function updateReply({replies, pageInfo}) {
    document.querySelectorAll('#replyModBtn').forEach($mod => {
        $mod.addEventListener('click', async  e=> {
            e.preventDefault();
            const replyId = e.target.closest('.card-body').dataset.replyId;
            console.log(replyId)
            console.log(document.getElementById("modReplyText").value)
            // const requestInfo = {
            //     method : "PUT",
            //     headers : {
            //         "content-type": "application/json",
            //     },
            //     body: JSON.stringify({
            //         replyText: document.getElementById("modReplyText").value,
            //         replyNo: 3866
            //     })
            // }
            // let response = await fetch(`${BASE_URL}`, requestInfo);
            // if (response.ok) {
            //                 fetchReplies(pageInfo.replyNo);
            //             }
        })
    })
    // document.querySelectorAll('#replyModBtn').forEach($modBtn => {
    //     $modBtn.addEventListener('click', async e => {
    //         e.preventDefault();
    //         // const replyId = e.target.closest('.card-body').dataset.replyId;
    //         const requestInfo = {
    //             method: "PUT",
    //             headers: {
    //                 "content-type": "application/json",
    //             },
    //             body: JSON.stringify({
    //                 // JSON 방식으로 변환해줘야함.
    //                 // 여기서 보내주는 건 입력에서 받는 Dto로 줘야함.
    //                 replyText: document.getElementById("modReplyText").value,
    //                 replyNo: pageInfo.replyNo
    //
    //             }),
    //         };
    //         // 겟 방식 말고는 전부 2번째에 request를 추가해줘야함!!!!
    //         let response = await fetch(`${BASE_URL}`, requestInfo);
    //         if (response.ok) {
    //             fetchReplies(pageInfo.replyNo);
    //         }
    //     })
    // })
}


function renderPage({begin, end, pageInfo, prev, next}) {
    let tag = '';
    //prev aksemfrl
    if (prev) tag += `<li class='page-item'><a class='page-link page-active' href='${begin - 1}'>이전</a></li>`;
    //페이지 번호 태그 만들기
    for (let i = begin; i <= end; i++) {
        let active = ``;
        if (pageInfo.pageNo == i) active = `p-active`;
        tag += `<li class='page-item ${active}'><a class='page-link page-custom' href='${i}'>${i}</a></li>`;
    }
    if (next) tag += `<li class='page-item'><a class='page-link page-active' href='${end + 1}'>다음</a></li>`;
    //페이지 태그 ul에 붙이기
    const $pageUl = document.querySelector('.pagination')
    $pageUl.innerHTML = tag;

}

export function renderReplies(replies) {
    // 댓글 수 렌더링

    document.getElementById('replyCnt').textContent = `${pageInfo.totalCount}`

    // 댓글 목록 렌더링
    let tag = '';
    if (replies && replies.length > 0) {
        replies.forEach(({reply_no, writer, createAt, text}) => {
            tag += `
    <div id='replyContent' class='card-body' data-reply-id='${reply_no}'>
        <div class='row user-block'>
            <span class='col-md-3'>
                <b>${writer}</b>
            </span>
            <span class='offset-md-6 col-md-3 text-right'><b>${getRelativeTime(createAt)}</b></span>
        </div><br>
        <div class='row'>
            <div class='col-md-9'>${text}</div>
            <div class='col-md-3 text-right'>
                <a id='replyModBtn' class='btn btn-sm btn-outline-dark' data-bs-toggle='modal' data-bs-target='#replyModifyModal'>${replies.account}</a>&nbsp;
                <a id='replyDelBtn' class='btn btn-sm btn-outline-dark' href='#'>삭제</a>
            </div>
        </div>
    </div>
    `;
        });
    } else {
        tag = `<div id=\'replyContent\' class=\'card-body\'>댓글이 아직 없습니다! ㅠㅠ</div>`;
    }

    document.getElementById('replyData').innerHTML = tag;
    //페이지 태그 렌더링
    renderPage(pageInfo)
    deleteReply(pageInfo)
    updateReply({replies, pageInfo})
}


//서버에서 댓글 목록 가져오는 비동기 요청 함수
export async function fetchReplies(pageNo = 1) {
    const bno = document.getElementById('wrap').dataset.bno; // 게시물 글번호
    const res = await fetch(`${BASE_URL}/${bno}/page/${pageNo}`);
    const replies = await res.json();

    // 댓글 목록 렌더링
    console.log(replies)
    renderReplies(replies)
}

//페이지 클릭 이벤트 생성 함수
export function replyPageClickEvent(e) {
    document.querySelector('.pagination').addEventListener('click', e => {
        e.preventDefault();
        fetchReplies(e.target.getAttribute('href'));

    })
}


//무한 스크롤 전용 함수
let currentPage = 1; // 현재 무한 스크롤 시 진행되고 잇는 페이지 번호
let isFetching = false; // 데이터를 불러오는 중에는 더 가져오지 않게 제어하기 위한 논리 변수
let totalReplies = 0; //총 댓글 수
let loadedReplies = 0; // 로딩된 댓글 수
function appendReplies({ replies, account, auth}) {
    console.log(replies)
    const loginAccount = account;
    // 댓글 목록 렌더링
    let tag = '';
    if (replies && replies.length > 0) {
        replies.forEach(({ reply_no: rno, writer, text, createAt, account, profile}) => {
            tag += `
        <div id='replyContent' class='card-body' data-reply-id='${rno}'>
            <div class='row user-block'>
                <span class='col-md-3'>
                    <b>${writer}</b>
                    ${profile !== null ? `
                  <div class="profile-box">
                        <img src="${profile}" alt="profile image">
                        </div>
                    ` : `
                      <div class="profile-box">
                            <img src="/assets/img/anonymous.jpg" alt="profile image">
                      </div>`
            }
                </span>
                <span class='offset-md-6 col-md-3 text-right'><b>${getRelativeTime(
                createAt
            )}</b></span>
            </div><br>
            <div class='row'>
                <div class='col-md-9'>${text}</div>
                <div class='col-md-3 text-right'>
                   ${account === loginAccount || auth === 'ADMIN' ? `
                    <a id='replyModBtn' class='btn btn-sm btn-outline-dark' data-bs-toggle='modal' data-bs-target='#replyModifyModal'>수정</a>&nbsp;
                    <a id='replyDelBtn' class='btn btn-sm btn-outline-dark' href='#'>삭제</a>
                    ` : ''}
                </div>
            </div>
        </div>
        `;
        });
    } else {
        tag = `<div id='replyContent' class='card-body'>댓글이 아직 없습니다! ㅠㅠ</div>`;
    }

    document.getElementById('replyData').innerHTML += tag;

    //로드된 댓글 수 업데이트
    loadedReplies += replies.length;



}

//서버에서 댓글 데이터를 페칭
export async function fetchInfScrollReplies(pageNo=1) {
    if (isFetching) return; //서버에서 데이터를 가져오는 중이면 return 시킴!!
    isFetching = true;
    const bno = document.getElementById('wrap').dataset.bno; // 게시물 글번호
    const res = await fetch(`${BASE_URL}/${bno}/page/${pageNo}`);
    const replies = await res.json();


    if (pageNo === 1 ) {
        //총 댓글 수 전역변수 값 세팅
        totalReplies = replies.pageInfo.totalCount;
        loadedReplies = 0; // 댓글 입력, 삭제 시 다시 1페이지 로딩시 초기값으로 만들기
        //댓글 수 렌더링
        document.getElementById('replyCnt').textContent = totalReplies;
        //초기 댓글 리셋
        document.getElementById('replyData').innerHTML = '';
        setupInfiniteScroll()

    }

    // 댓글 목록 렌더링
    appendReplies(replies)
    currentPage = pageNo;
    isFetching = false;
    hideSpinner()
    //댓글을 전부 가져올 시 스크롤 이벤트 제거하기
    if (loadedReplies >= totalReplies) {
        window.removeEventListener('scroll', scrollHandler)
    }
}

//스크롤 이벤트 핸들러 함수
async function scrollHandler(e) {

    //스크롤이 최하단부로 내려갔을 때만 이벤트를 발생시켜야 함
    //현재창에 보이는 세로길이 + 스크롤을 내린 길이 == 브라우저 전체 세로 길이
    if (window.innerHeight + window.scrollY >= document.body.offsetHeight + 200
    && !isFetching){
    //서버에서 데이터를 비동기로 불러와야 함.
        //2초의 대기열이 생성되면 다음 대기열 생성까지 2초를 기다려야 함.
        showSpinner()
        await new Promise(resolve => setTimeout(resolve, 500));
    fetchInfScrollReplies(currentPage + 1);
    }
}

//무한 스크롤 이벤트 생성 함수
export function setupInfiniteScroll() {
    window.addEventListener('scroll', scrollHandler)
}


