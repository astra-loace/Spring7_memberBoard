/**
 * 
 */

$(function(){
    // 첨부파일이 있을 경우 삭제 
        $('.deleteFile').on('click', function(){
           let boardSeq = $(this).attr('data-seq');
           let searchItem = $(this).attr('data-item');
           let searchWord = $(this).attr('data-word');
           
           let answer = confirm("정말로 삭제하시겠습니까?"); 
           if(answer) {
              location.href="/board/deleteFile?boardSeq=" + boardSeq + "&searchItem=" + searchItem + "&searchWord=" + searchWord; 
           } else {
              alert("삭제작업을 취소합니다.")
           }
        });
       
       init();   // 전체 댓글 읽어서 화면에 출력
       
       // 댓글 입력
       $('#replyBtn').on('click', replyInsert); 
       $('#replyUpdateProc').on('click', replyUpdateProc); 
       $('#replyCancel').on('click', replyCancel); 
       
     });

// 댓글 전체 조회 
function init(){
 let boardSeq = $('#boardSeq').val();
 
 $.ajax({
    url: '/reply/replyAll'
    , method: 'GET'
    , data  : {"boardSeq" : boardSeq}   //requestParam으로 받기  
    , success:    output// resp 는 [{}, {}, {}]; 이다. 
    
 });
 
}

/* 댓글 출력 */
function output(resp){
 
 let loginId = $('#loginId').val(); 
 
 let tag = `
 <table>
    <tr>
       <th >번호</th>
       <th >내용</th>
       <th >글쓴이</th>
       <th >날짜</th>
       <th ></th>
    </tr>
          `; 
 
 $.each(resp, function(index, item){      // resp =[{}, {}, {}]
    
    tag += `
          <tr>
             <td class='no'>${index+1}</td>
             <td class='content'>${item['replyContent']}</td>
             <td class='writer'>${item['replyWriter']}</td>
             <td class='date'>${item['createDate'].substr(0,10)}</td>
             <td class='btns'>
             <input type="button" value="삭제" class="btn btn-danger deleteBtn"
             data-seq="${item['replySeq' ]}"
             ${item['replyWriter'] == loginId ? '' : 'disabled'}>
                
             <input type="button" value="수정" class="btn btn-secondary updateBtn" 
                   data-seq="${item['replySeq']}"
                   ${item['replyWriter'] == loginId ? '' : 'disabled'}> 
             </td>
          </tr>
          `
    
 }); 
 
 tag += `</table>`; 
 
 $('#reply_list').html(tag); 
 
 $('.deleteBtn').on('click', replyDelete); 
 $('.updateBtn').on('click', replyUpdate); 
 
 
}


// 댓글 삭제 함수 
function replyDelete(){
 
 let replySeq = $(this).attr('data-seq') // 속성 값 중에 data-seq 불러오기 
 alert(replySeq); 
 
 let answer = confirm('삭제하시겟습니까?'); 
 
 if(!answer) return; 
 
 $.ajax({
    
    url: '/reply/replyDelete' 
    ,method : 'GET'
    , data : {"replySeq": replySeq}
    , success: init 
    
    
 }); 
 
}

// 댓글 수정을 위한 조회 함수   
function replyUpdate(){
 
 let replySeq = $(this).attr('data-seq'); 
 
 // ajax로 controller에 요청 보내기 
 $.ajax({
    url: '/reply/replyUpdate'
    , method: 'GET'
    , data : {"replySeq" : replySeq}
    , success: function(resp){

       // 데이터를 입력창에 넣고 나서 
       let content = resp['replyContent']; 
       
       $('#replyContent').val(content); 
       
       // 버튼을 뒤집는다 
       
       $('#replyBtn').css('display', 'none')
       $('#replyUpdateProc').css('display', 'inline-block'); 
       $('#replyCancel').css('display', 'inline-block');
       
    }
    
 });
}

// 댓글 수정 처리 함수 
function replyUpdateProc(){
 
 let replySeq= $(this).attr('data-seq'); 
 
}

// 댓글 수정ㅊ취소 
function replyCancel(){
 
 // 버튼을 뒤집기 
 $('#replyBtn').css('display', 'inline-block')
 $('#replyUpdateProc').css('display', 'none'); 
 $('#replyCancel').css('display', 'none');
 
 $('#replyContent').val(''); 
 
}


// 댓글 입력 함수 
function replyInsert(){

 let replyContent = $('#replyContent').val();
 let boardSeq = $('#boardSeq').val();
 

 if(replyContent.trim().length == 0){
    alert("댓글을 입력하세요"); 
    return;
 }   
 
 let sendData = {"boardSeq" : boardSeq , "replyContent" :replyContent};
 
//ajax로 댓글 송신 
 $.ajax({
    url: '/reply/replyInsert'
    , method : 'POST'
    , data : sendData 
    , success : function(resp){
       init(); 
       $('#replyContent').val('');
    }
 })
 
 
}