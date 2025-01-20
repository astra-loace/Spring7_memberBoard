/**
 * 회원 수정 시 필요한 검증 작업
 */

let pwdCheck = false; // 비밀번호, 비밀번호 확인의 동일 여부

$(function () {
  // 비번 확인 통과했는데 다시 비번 바꾸면 비번 확인 input을 지우기
  $("#userPwd").on("focus", function () {
    $("#userPwdCheck").val("");
  });

  $("#submitBtn").on("click", update);
});

// 1) 회원 정보 수정을 위한 나머지 검증 작업
function update() {
  let userPwd = $("#userPwd").val();

  // 비밀번호 길이 체크
  if (userPwd.trim().length < 3 || userPwd.trim().length > 5) {
    $("#confirmPwd").css("color", "red");
    $("#confirmPwd").html("비밀번호는 3~5자 이내");
    pwdCheck = false;
    return false;
  }

  let userPwdCheck = $("#userPwdCheck").val();

  // 비밀번호 확인 길이 체크
  if (userPwdCheck.trim() != userPwd.trim()) {
    $("#confirmPwd").css("color", "red");
    $("#confirmPwd").html("비밀번호와 확인값을 같은 값으로~!");
    pwdCheck = false;
    return false;
  }

  // 이메일 입력 체크
  let email = $("#email").val();
  if (email.trim().length < 1) {
    $("#confirmEmail").css("color", "red");
    $("#confirmEmail").html("이메일은 한 글자 이상 입력");
    return false;
  }

  return true;
}
