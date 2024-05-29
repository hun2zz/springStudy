
//수정 이벤트 등록 함수
import {BASE_URL} from "./reply.js";
import {fetchInfScrollReplies} from "./getReply.js";

export function updateReplytwo () {

    //수정 모드 진입 이벤트임!
    document.getElementById('replyData').addEventListener('click', e => {
        e.preventDefault()
        if (!e.target.matches('#replyModBtn')) return;
            document.getElementById('modReplyText').value = e.target.parentElement.previousElementSibling.textContent;
            const rno = e.target.closest('#replyContent').dataset.replyId;

            //모달에 클릭한 댓글번호 달아놓기
            document.querySelector('.modal').dataset.rno = rno;
    })

    //수정 요청 처리 이벤트
    document.getElementById('replyModBtn').addEventListener('click', e=>{
        fetchReplyModify();
    })
}

async function fetchReplyModify() {
    const payload = {
        rno : document.querySelector('.modal').dataset.rno,
        newText : document.getElementById('modReplyText').value,
        bno : document.getElementById('wrap').dataset.bno
    };
    // console.log(payload)
    const res = fetch(BASE_URL, {
        method: 'PUT',
        headers : {
            'content-type' : 'application/json'
        },
        body : JSON.stringify(payload),
    })
    if (!(await res).ok) {
        alert('수정 실패!')
    }
    // if (!res.ok) {
    //     alert('수정 실패!')
    // }
    document.getElementById('modal-close').click()

    window.scrollTo(0,500)
    await fetchInfScrollReplies()

}
