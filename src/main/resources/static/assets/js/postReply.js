import {BASE_URL} from "./reply.js";
import {renderReplies} from "./getReply.js";
// 서버에 댓글 등록을 요총하는 비동기 함수
export const fetchReplyPost = async () => {
    const textInput = document.getElementById("newReplyText")
    const writerInput = document.getElementById("newReplyWriter")
    const requestInfo = {
        method: "POST",
        headers: {
            "content-type": "application/json",
        },
        body: JSON.stringify({
            // JSON 방식으로 변환해줘야함.
            // 여기서 보내주는 건 입력에서 받는 Dto로 줘야함.
            text: textInput.value,
            author: writerInput.value,
            bno : document.getElementById('wrap').dataset.bno,
        }),
    };

    // 겟 방식 말고는 전부 2번째에 request를 추가해줘야함!!!!
    const res = await fetch(BASE_URL, requestInfo);
    const json = await res.json();
    renderReplies(json);
    textInput.value = '';
    writerInput.value = '';
}