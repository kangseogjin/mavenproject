<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>

<%@ include file="../top2.jsp"%>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>



 <script type="text/javascript">
 function idCheck(id){
      location.href="idCheck.do?id="+id;
      return
   }
 
 function validatePassword() {
       var password = document.getElementById('pw').value;
       var passwordMessage = document.getElementById('passwordMessage');
       
       var passwordSC = /^(?=.*[!@#$%^&*])[A-Za-z\d@$!%*#?&/]{8,}$/;

       if (password === '') {
           passwordMessage.textContent = '';
       } else if (!passwordSC.test(password)) {
           passwordMessage.textContent = '비밀번호는 8자리 이상이어야 하며, 특수문자를 하나 이상 포함해야 합니다.';
           return false;
       } else {
           passwordMessage.textContent = '';
       }

       return true;
   }
   // 비밀번호 확인 값 확인
   function validatePasswordok()   {
       var password = document.getElementById('pw').value;  
          var confirmPassword = document.getElementById('ConfirmPassword').value;
          var confirmPasswordMessage = document.getElementById('confirmPasswordMessage');
      
       
   if (confirmPassword === '') {
       confirmPasswordMessage.textContent = '';
       
   } else if (password !== confirmPassword) {
       confirmPasswordMessage.textContent = '비밀번호가 일치하지 않습니다.';
       return false;
   } else {
       confirmPasswordMessage.textContent = '';
   }

   return true;
   }
   
   
   
   
   var formDisabled = false; // 폼 활성/비활성 여부를 나타내는 변수
   
// 부모 창의 폼(submit 버튼) 비활성화 함수
   function disableForm() {
       // 폼 요소들을 선택하여 disabled 속성 추가
       var formElements = document.querySelectorAll('form input, form button');
       formElements.forEach(function(element) {
           element.disabled = true;
       });
       formDisabled = true;
   }

   // 부모 창의 폼(submit 버튼) 활성화 함수
   function enableForm() {
       // 폼 요소들을 선택하여 disabled 속성 제거
       var formElements = document.querySelectorAll('form input, form button');
       formElements.forEach(function(element) {
           element.disabled = false;
       });
       formDisabled = false;
   }
   
 
   function emailVerification() {
	    var popupWindow;
	    var emailValue = document.querySelector('input[name="email1"]').value.trim();
	    var emailDomain = document.querySelector('input[name="email2"]').value;
	    var email = emailValue + "@" + emailDomain;

	    if (emailValue === "") {
	        console.log("이메일을 입력하세요");
	        return;
	    }

	    // 화면 중앙에서 오른쪽으로 이동한 위치 계산
	    var screenWidth = window.screen.width;
	    var popupWidth = 500; // 팝업 창의 너비

	    // 팝업 창이 화면 중앙에서 오른쪽으로 이동한 위치 계산
	    var leftPosition = (screenWidth - popupWidth) / 2 + 400;

	    var url = "EmailVerification.do?email=" + email;

	    // 부모 창의 폼(submit 버튼) 비활성화
	    disableForm();

	    // 팝업 창 열기
	    popupWindow = window.open(url, "_blank", "width=500,height=500,left=" + leftPosition);

	    // 팝업 창이 닫힐 때 이벤트 처리
	    popupWindow.onunload = function() {
	        // 부모 창의 폼(submit 버튼) 활성화
	        enableForm();
	    };

	    console.log("이메일 인증을 하세요");
	}
   
   
   
   
   $(document).ready(function() {
	    $('#selectEmail').change(function(){
	        var selectedOptionValue = $(this).val();
	        if(selectedOptionValue === '1') { // 직접입력일 경우
	            $("#email2").val('').prop("disabled", false); // 값 초기화 및 활성화
	        } else { // 직접입력이 아닐 경우
	            $("#email2").val(selectedOptionValue).prop("disabled", true); // 선택값 입력 및 비활성화
	        }
	    });
	});
  
   
</script>


<head>

    <link rel="stylesheet" type="text/css" href="resources/css/signn.css">
    <link rel="stylesheet" type="text/css" href="resources/css/signnn.css">
    
</head>
<body class="main">

   <script type="text/javascript" async="" src="https://www.googletagmanager.com/gtag/js?id=G-CDKFSSN8GP&amp;cx=c&amp;_slc=1"></script>
   <script async="" defer="" src="https://cdn.megadata.co.kr/dist/prod/enp_tracker_self_hosted.min.js"></script>
   <script async="" src="//www.google-analytics.com/analytics.js"></script>
    <script src="https://code.jquery.com/jquery-3.0.0.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/notify/0.4.2/notify.min.js"></script>


<div class="wrap">


   <section id="ccss-1c8isl7" class="subpage">
   <div class="contents">
         <h2><img src="https://img.sonyunara.com/2019/asset/pc/img/sub/member/tit_join1.png" alt="JOIN US"></h2>
         <div class="member">
            <div class="join">
               <form name="joinform" method="post" action="f4_input_ok.do" onsubmit="return join_chk();" autocomplete="off">
                  <fieldset>
                     <legend>회원가입</legend>
                     <ul class="form-box">
                        <li>
                           <p class="tit">아이디</p>
                           <p class="form">
                           <c:if test="${isMember}">
                              <input type="text" class=" placeholder" placeholder="아이디를 입력해주세요" required name="id" title="아이디를 입력하세요" autocomplete="new-password">
                              </c:if>
                               <c:if test="${!isMember}">
                                  <input type="text" class=" placeholder" placeholder="아이디를 입력해주세요" required value="${id}" name="id" title="아이디를 입력하세요" autocomplete="new-password">
                              </c:if>   
                              <button type="button" class="amugeona" name="dbCheckId" onclick="javascript:idCheck(joinform.id.value)" >중복 확인</button>
                           </p>
                        </li>
                        <li>
                           <p class="tit">비밀번호</p>
                           <p class="form">
                              <input type="password" name="pw" id="pw" placeholder="비밀번호를 입력 해주세요" required oninput="validatePassword()"><br>
                          <span id="passwordMessage" style="color: red;"></span>
                           </p>
                        </li>
                        <li>
                           <p class="tit">비밀번호 확인</p>
                           <p class="form">
                               <input type="password" name="confirmpassword" id="ConfirmPassword" placeholder="비밀번호를 확인 해주세요" required oninput=" validatePasswordok()"><br>
                            <span id="confirmPasswordMessage" style="color: red;"></span>
                           </p>
                        </li>
                        <li>
                           <p class="tit">이름</p>
                           <p class="form">
                              <input type="text" name="name" required placeholder="이름을 입력하세요" autocomplete="new-password">
                              
                           </p>
                        </li>
                        <li>
                           <p class="tit">휴대전화</p>
                           <ul class="form-tel">
                              <li>
                                 <select name="tel1">
                                    <option value="010">010</option>
                                    <option value="011">011</option>
                                 </select>
                              </li>
                              <li><input type="text" maxlength="4" title="휴대전화 앞자리" name="tel2" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" autocomplete="new-password" required></li>
                              <li><input type="text" maxlength="4" title="휴대전화 뒷자리" name="tel3" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" autocomplete="new-password" required></li>
                           </ul>
                           <p class="alert3" style="">※숫자만 입력 가능합니다.</p>
                        </li>
                        
                      <input type="hidden" maxlength="6" title="주민번호 앞자리" name="ssn1" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" autocomplete="new-password" required value="123">
                             <input type="hidden" maxlength="7" title="주민번호 뒷자리" name="ssn2" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" autocomplete="new-password" required value="123">
                        
                        <li>
                           <p class="tit">이메일</p>
                           <ul class="form-email"    style="width: 500px;">
                              <li>
                                 <input type="text" name="email1" autocomplete="new-password" required>
                                 <span>@</span>
                              </li>
                                     <li>
                                 <input type="text" name="email2"  id="email2" autocomplete="new-password"  disabled value="naver.com">
                              </li>
                              
                              
                              <li>
                                 <select name="selectEmail" id="selectEmail">
                                     
                                    <option value="naver.com" >naver.com</option>
                                    <option value="gmail.com">gmail.com</option>              
                                          <option value="kakao.com">kakao.com</option>
                                          <option value="1" >직접 입력</option>
                                 </select>
                              </li>
                           <li>
                             <button onclick="emailVerification()"  style="border-bottom: solid 0px #9d9d9d; border: 1px solid #000;"> <p class="tit" align="center">이메일 인증</p></button>
                            </li>
                           </ul>
                           
                                                 
                           
                           <p class="caution caution_email" style="padding-top: 10px;">※영문,숫자만 입력 가능합니다.</p>

                        </li>
                     </ul>
                     <ul class="btnbox">
                        <li><button type="submit" class="btn-m-red"><span>가입</span></button></li>
                        <li><button type="button" class="btn-m-line" onclick="javascript:location.href='"><span>취소</span></button></li>
                     </ul>
                     <br>
                     <br>
                     <br>
                  </fieldset>
               </form>
            </div><!-- //join1 -->
         </div><!-- //member -->
      </div><!-- //contents -->
   </section>

<script>

//한글입력 안되게 처리
$("input[name=email1],input[name=email2]").keyup(function(event){
   if (!(event.keyCode >=37 && event.keyCode<=40)) {
      var inputVal = $(this).val();
      $(this).val(inputVal.replace(/[^a-z0-9@_.-]/gi,''));
   }
});


</script>

</div><!-- //wrap -->


</body>

<%@ include file="../bottom.jsp"%> 