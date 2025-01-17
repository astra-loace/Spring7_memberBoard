/**
 * 회원가입 시 필요한 검증 작업 
 */

let idCheck = false;
let pwdCheck = false;   // 비번이랑 비번확인에 입력된 값 모두 일치해야지~

$(function() {
    $('#userId').on('keyup', confirmId);
    $('#userPwd').on('focus', function() { // focus 되면 pwdCheck쪽에 있는 값 지워줘!
        $('#userPwdCheck').val('');
    })

    $('#submitBtn').on('click', join);
});

// 2) 회원가입을 위한 나머지 검증작업
function join() {
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
    // 이름 입력했니?
    let userName = $('#userName').val();
    if (userName.trim().length < 1) {
        $('#confirmUserName').css('color', 'red');
        $('#confirmUserName').html("실명을 입력해주세요.")    
        
        return false; // submit에 보내는 거라서
    }

    // 이메일 입력했니?
    let email = $('#email').val();
    if (email.trim().length < 1) {
        $('#confirmEmail').css('color', 'red');
        $('#confirmEmail').html("이메일을 입력해주세요.")    
    
        return false; // submit에 보내는 거라서
    }

    return true;

}



//1) 아이디 검증하기 (길이check & 중복check)
function confirmId() {
    let userId = $('#userId').val();

    //길이check~
    if(userId.trim().length < 3 || userId.trim().length > 5) {
        $('#confirmId').css('color', 'red');
        $('#confirmId').html("아이디는 3~5자 이내로 입력하세요.")
    
        return;
    }
    
    $('#confirmId').html('') // 없어도 됨
    //아이디 중복확인
    $.ajax({
        url: '/user/idCheck'
        , method: 'POST'
        , data : {"userId" : userId}
        , success : function(resp) { // resp가 true라면 회원가입
            if(resp) {
                $('#confirmId').css('color', 'blue');
                $('#confirmId').html("가입 가능한 아이디입니다.");
                idCheck = true;
            } else {
                $('#confirmId').css('color', 'red');
                $('#confirmId').html("사용할 수 없는 아이디입니다.");
                idCheck = false;
            }
        }

    });
    
}


