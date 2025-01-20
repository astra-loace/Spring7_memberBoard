/**
 * 개인정보 수정정 시 필요한 검증 작업 
 */


let pwdCheck = false;   // 비번이랑 비번확인에 입력된 값 모두 일치해야지~

$(function() {
    $('#userPwd').on('focus', function() { // focus 되면 pwdCheck쪽에 있는 값 지워줘!
        $('#userPwdCheck').val('');
    })

    $('#submitBtn').on('click', updateProc);
});

// 2) 회원가입을 위한 나머지 검증작업
function updateProc() {
    let userPwd = $('#userPwd').val();
    if (userPwd.trim().length < 3 || userPwd.trim().length > 5) {
        $('#confirmPwd').css('color', 'red');
        $('#confirmPwd').html("비밀번호는 3~5자 이내로 입력하세요.")    
        pwdCheck = false;
        return false; // submit에 보내는 거라서
    }

    let userPwdCheck = $('#userPwdCheck').val();
    if (userPwd.trim() != userPwdCheck.trim()) {
        $('#confirmPwd').css('color', 'red');
        $('#confirmPwd').html("비밀번호와 확인값을 같은 값으로.")    
        pwdCheck = false;
        return false; // submit에 보내는 거라서
    }

    return true;

}
