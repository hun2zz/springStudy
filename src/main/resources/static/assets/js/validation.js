// //회원 가입 입력 검증 처리
// const $idInput = document.getElementById('user_id');
// const $pwInput = document.getElementById('password');
// const $pwInputCh = document.getElementById('password_check');
// const $nameInputCh = document.getElementById('user_name');
// const $emailInput = document.getElementById('user_email');
// const $successButton = document.querySelectorAll('.success');
// const $successBtn = document.querySelector('.btn');
//
//
// let pwValue;
//
// let idFlag = false;
// let emailFlag = false;
// //계정 중복검사 비동기 요청 보내기
// async function fetchDuplicateCheck(idValue) {
//     const res = await fetch(`http://localhost:8383/members/check?type=account&keyword=${idValue}`)
//     const flag = await res.json();
//     idFlag = flag;
// }
//
// async function fetchDuplicateCheckEmail(emailValue) {
//     const res = await fetch(`http://localhost:8383/members/check?type=email&keyword=${emailValue}`)
//     const flag = await res.json();
//     emailFlag = flag;
// }
//
// function button () {
//     const $successInput = document.querySelectorAll('.success');
//     const $button = document.getElementById('signup-btn');
//     $button.disabled = [...$successInput].length !== 5;
//     $button.style.background = ([...$successInput].length === 5) ? 'orange' : 'gray';
//
// }
// //계정 입력 검증
// $idInput.addEventListener('keyup', async e=>{
//     // 아이디 검사 정규표현식
//     const accountPattern = /^[a-zA-Z0-9]{4,14}$/;
//   //입력값 읽어오기
//   const idValue = $idInput.value;
//
//     // 검증 메시지를 입력한 span
//     const $idChk = document.getElementById('idChk')
//     if (idValue.trim() === '') {
//         $idInput.style.borderColor = 'red';
//         $idChk.innerHTML = `<b class="warning">[아이디는 필수값입니다.]</b>`;
//     } else if (!accountPattern.test(idValue)) {
//         $idInput.style.borderColor = 'red';
//         $idChk.innerHTML = `<b class="warning">[아이디는 4~12글자 사이 영문, 숫자로 입력해주세요.]</b>`;
//     } else {
//         //아이디 중복검사
//         await fetchDuplicateCheck(idValue);
//         if (idFlag) {
//             $idInput.style.borderColor = 'red';
//             $idChk.innerHTML = `<b class="warning">[아이디가 중복되었습니다.]</b>`;
//         } else {
//         $idInput.style.borderColor = 'skyblue';
//         $idChk.innerHTML = `<b class="success">[사용가능한 아이디입니다.]</b>`;
//
//         }
//
//     }
//     button()
// })
//
//
// //비번 입력 검증
// $pwInput.addEventListener('keyup', async e => {
//     const passwordPattern = /([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/;
//     pwValue = $pwInput.value;
//     const $pwChk = document.getElementById('pwChk')
//     if (pwValue.trim() === '') {
//         $pwInput.style.borderColor = 'red';
//         $pwChk.innerHTML = `<b class="warning">[비밀번호는 필수값입니다.]</b>`;
//     } else if (!passwordPattern.test(pwValue)) {
//         $pwInput.style.borderColor = 'red';
//         $pwChk.innerHTML = `<b class="warning">[비밀번호는 영문과 특수문자를 포함한 최소 8글자 입니다.]</b>`;
//     } else {
//         $pwInput.style.borderColor = 'skyblue';
//         $pwChk.innerHTML = `<b class="success">[사용가능한 비밀번호입니다.]</b>`;
//         }
//     button()
// })
//
// $pwInputCh.addEventListener('keyup', async e => {
//     const chValue = $pwInputCh.value;
//     const $pwChk2 = document.getElementById('pwChk2')
//     if (chValue.trim() === '') {
//         $pwInput.style.borderColor = 'red';
//         $pwChk2.innerHTML = `<b class="warning">[비밀번호 확인은 필수값입니다.]</b>`;
//     } else if (chValue !== pwValue) {
//         $pwInput.style.borderColor = 'red';
//         $pwChk2.innerHTML = `<b class="warning">[비밀번호가 일치하지 않습니다..]</b>`;
//     } else {
//         $pwInput.style.borderColor = 'skyblue';
//         $pwChk2.innerHTML = `<b class="success">[비밀번호가 일치합니다.]</b>`;
//     }
//     button()
// })
//
// $nameInputCh.addEventListener('keyup', async  e=> {
//     const nameValue = $nameInputCh.value;
//     const $nameChk = document.getElementById('nameChk')
//
// // 이름 검사 정규표현식
//     const namePattern = /^[가-힣]+$/;
//     if (nameValue.trim() === '') {
//         $nameInputCh.style.borderColor = 'red';
//         $nameChk.innerHTML = `<b class="warning">[이름은 필수값입니다.]</b>`;
//     } else if (!namePattern.test(nameValue)) {
//         $nameInputCh.style.borderColor = 'red';
//         $nameChk.innerHTML = `<b class="warning">[이름은 한글로만 입력가능합니다.]</b>`;
//     } else {
//         $nameInputCh.style.borderColor = 'purple';
//         $nameChk.innerHTML = `<b class="success">[사용가능한 이름입니다.]</b>`;
//     }
//         button()
// })
//
//
//
// $emailInput.addEventListener('keyup', async  e=> {
//     // 이메일 검사 정규표현식
//     const emailPattern = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
//
//     const emailValue = $emailInput.value;
//     const $emailChk = document.getElementById('emailChk')
//     if (emailValue.trim() === '') {
//         $emailInput.style.borderColor = 'red';
//         $emailChk.innerHTML = `<b class="warning">[이메일은 필수값입니다.]</b>`;
//     } else if (!emailPattern.test(emailValue)) {
//         $emailInput.style.borderColor = 'red';
//         $emailChk.innerHTML = `<b class="warning">[이메일의 양식에따라 입력해주세요.]</b>`;
//     } else {
//         //아이디 중복검사
//         await fetchDuplicateCheckEmail(emailValue);
//         if (emailFlag) {
//             $emailInput.style.borderColor = 'red';
//             $emailChk.innerHTML = `<b class="warning">[이메일이 중복되었습니다.]</b>`;
//         } else {
//             $emailInput.style.borderColor = 'skyblue';
//             $emailChk.innerHTML = `<b class="success">[사용가능한 이메일입니다.]</b>`;
//
//         }
//     }
//             button()
// })
//
//
//
