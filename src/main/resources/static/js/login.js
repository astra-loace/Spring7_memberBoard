/**
 * 로그인 시 필요한 검증 작업 
 */

$(function() {
    $('#submitBtn').on('click', login);
});

// 1) 로그인을 위한 검증작업
function login() {
    let userId = $('#userId').val();

    //길이check~
    if(userId.trim().length < 3 || userId.trim().length > 5) {
        $('#confirmId').css('color', 'red');
        $('#confirmId').html("아이디는 3~5자 이내로 입력하세요.")
    
        return false;
    }

    //비밀번호 맞게 입력했나 check~
    let userPwd = $('#userPwd').val();
    if (userPwd.trim().length < 3 || userPwd.trim().length() > 5) {
        $('#confirmPwd').css('color', 'red');
        $('#confirmPwd').html("아이디는 3~5자 이내로 입력하세요.")

        return false;
    }

    return true;

}



