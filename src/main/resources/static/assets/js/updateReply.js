
//수정 이벤트 등록 함수

export function updateReplytwo () {
    document.getElementById('replyData').addEventListener('click', e => {
        e.preventDefault()
        if (!e.target.matches('#replyModBtn')) return;
            document.getElementById('modReplyText').value = e.target.parentElement.previousElementSibling.textContent;
    })
}