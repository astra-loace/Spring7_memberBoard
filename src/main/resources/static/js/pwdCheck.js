/**
 * 개인정보 수정 시 필요한 검증 작업 
 */

$(function() {
    $('#submitBtn').on('click', login);
});

// 1) 개인정보 수정을 위한 검증작업
function validation() {
    let userPwd = $('#userId').val();

    //비밀번호 맞게 입력했나 check~
    if (userPwd.trim().length < 3 || userPwd.trim().length() > 5) {
        $('#confirmPwd').css('color', 'red');
        $('#confirmPwd').html("비밀번호는 3~5자 이내로 입력하세요.")

        return false;
    }

    return true;

}



