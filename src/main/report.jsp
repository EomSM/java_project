<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
	<link rel="stylesheet" type="text/css" href="resources/css/style.css">
	<link rel="shortcut icon" href="#">
	<script type="text/javascript" src="resources/js/jquery-3.6.0.js"></script>
	<script type="text/javascript">
	function idnull() {
		alert('로그인이 필요합니다.')
		location.href = "login.jsp"
	}
	
	$(function () {
		$('#b1').show();  // 페이지 시작시 버튼을 보이고
		$('#check').hide(); //check 이미지는 보이지 않음
		
		$('#rtarget').change(function() { //만약 input에 변화가 생기면
			$('#b1').show();  // 다시 버튼을 보이고
			$('#check').hide(); // 이미지를 지움
			$('#rtarget').attr("check_result",0)  //rtarget의 속성 checkresult의 값을 0으로 변경
		})
		
		$('#b1').click(function() {
			$.ajax({
				url : "targetchk",
				type : "POST",
				data : {
					IDSEND : $('#rtarget').val(),
					IDRE : $('#rwriter').val()
				},
				success : function(data) {
					if (data == 0) {
						$('#rtarget').val("")
						alert('매칭내역이 없습니다.')
					} else {
						alert(data +'건의 쪽지내역이 있습니다.')
						$('#b1').hide();
						$('#check').show();
						$('#rtarget').attr("check_result",data);
					}
				}//success
			})//ajax
			
		})//b1
		
		$('#b2').click(function() {
			if ($('#rtarget').attr("check_result") == 0) {
				alert('아이디 검색은 필수사항입니다.')
				$('#rtarget').focus();
				return
			}
			$.ajax({
				url : "reportinsert",
				type : "POST",
				data : {
					rtarget : $('#rtarget').val(),
					rreason : $('input[name=rreason]:checked').val(),
					rwriter : $('#rwriter').val()
				},
				success : function() {
					alert('신고가 완료되었습니다.')
					location.href = "bbsall.jsp"
				}//success
			})//ajax
		})//b2
		
	})//start
	</script>
</head>
<body>
	
	<header>
		<!-- 메뉴바 왼쪽상단 로고 -->
		<nav id = "logo">
			<a href = "main.jsp">
				<img src = "resources/img/logo.png" width="80" height="80">
			</a>
		</nav>
		
		<!-- 로그인/로그아웃 결과-->
 		<div id = "welcome">
 			<% if(session.getAttribute("Mid") == null) {%>
 				로그인이 필요합니다.
 			<%} else { %>
 				${Mid}님 환영합니다.<br>
 				<a href="memberlogout.jsp">로그아웃</a>
 			<%} %>
 		</div>
 		
 		<!-- 상단메뉴 -->
		<nav id = "menu">
			<ul id = "mainmenu">
			
				<% if(session.getAttribute("Mid") == null) {%>
				<li><a href="memberlogin.jsp">로그인</a></li>
				<%} else { %>
				<li><a>쪽지함</a>
					<ul class = "submenu">
						<li><a href="messageListReceive.jsp">받은쪽지함</a></li>
						<li><a href="messageListSend.jsp">보낸쪽지함</a></li>
						<li><a href="messageSend.jsp">쪽지보내기</a></li>
					</ul>
				</li>
				<%} %>
				
				<li><a>고객센터</a>
					<ul class = "submenu">
						<li><a href="bbsall.jsp">게시판</a></li>
						<% if(session.getAttribute("Mid") == null) {%>
						<li><a onclick="idnull()" style="cursor:pointer;">불량회원신고</a></li>
						<%} else { %>
						<li><a href="report.jsp">불량회원신고</a></li>
						<%} %>
					</ul>
				</li>
				
				<% if (session.getAttribute("Mid") != null && session.getAttribute("Mid").equals("admin")) {%>
				<!-- null에 대한 정의를 해주지 않으면 예외처리에서 오류가 남 따라서 조건을 두개 주었음 -->
				<!-- 세션값을 조회했을 때 반환값은 오브젝트로 스트링과 비교할땐 equals 사용. 세션값을 string으로 형변환시켜서 비교할땐 equals 문법에러가 남 -->
				<li><a href="admin.jsp">관리자</a></li>
				<%} else if (session.getAttribute("Mid") == null) { %>
				<li><a onclick="idnull()" style="cursor:pointer;">매칭</a></li>
				<%} else {%>
				<li><a href="matching.jsp">매칭</a>
					<ul class = "submenu">
						<li><a href="TypeInsert.jsp">이상형정보입력</a></li>
                     	<li><a href="TypeSelect.jsp">이상형정보수정</a></li>
					</ul>
				</li>
				<%}  %>
				
				<% if(session.getAttribute("Mid") == null) {%>
				<li><a onclick="idnull()" style="cursor:pointer;">마이페이지</a>
				<%} else { %>
				<li><a href="memberinfo?mid=${Mid}">마이페이지</a>
				<%} %>
					<ul class = "submenu">
						<% if(session.getAttribute("Mid") == null) {%>
						<li><a onclick="idnull()" style="cursor:pointer;">개인정보수정</a></li>
						<li><a onclick="idnull()" style="cursor:pointer;">매칭정보</a></li>
						<%} else { %>
						<li><a href="memberupdate?mid=${Mid}">개인정보수정</a></li>
						<li><a href="match_info?mid=${Mid}">매칭정보</a></li>
						<%} %>
					</ul>
				</li>
			</ul>
		</nav>
	</header>
	
	<!-- content 안으로 각 페이지 내용넣기 (img 지우고 본인 내용 넣어주시면 됩니다!) -->
	<BODY>
		<div id ="content">
			<div id = "bbsname" style = "width : 1300px; height : 100px; font-size:50px; font-weight:bold; color:white;
			 text-align:center; display:table-cell; vertical-align:middle;">
			<!-- 글씨를 수평정렬, 수직정렬하기위함. textalign은 수평 verticalalign은 수직인데 inline요소에만 적용되기 때문에 table형태로 display  -->
				불량회원신고
			</div>
			<p align = "center" style = "font-size : 17px;">
				불쾌감을 준 회원을 신고해주세요.<br>
				내부 검열 후 조치를 취하겠습니다.
			</p>
			<table style = "margin:auto; border-top:solid 2px; font-size:18px;">
				<tr style = "padding : 10px;">
					<th style = "width:100px; height:100px;">
						신고대상
					</th>
					<td>
						<input type = "text" id = "rtarget" check_result = 0 required placeholder="아이디"
						style = "width:380px; height:45px; border-radius: 5px; font-size:25px; text-align:center;">
						<input type = "button" id = "b1" value = "검색"
						style = "width:70px; height:47px;">
						<img id = "check" src="resources/img/check.png" width="60px" height="40px">
					</td>
				</tr>
				<tr>
					<td>
						
					</td>
				</tr>
				<tr>
					<th style = "width:100px; height:100px;">
						신고사유
					</th>
					<td>
						<label><input type = "radio" name = "rreason" value = "무례/모욕">무례하거나 모욕적입니다.</label><br>
						<label><input type = "radio" name = "rreason" value = "타인사칭">신고자 본인,또는 타인을 사칭하고 있습니다.</label><br>
						<label><input type = "radio" name = "rreason" value = "혐오조장">인종, 종교, 성별 또는 성향에 대한 혐오를 조장합니다.</label><br>
						<label><input type = "radio" name = "rreason" value = "위협/위해">폭력적 위협 또는 신체적인 위해를 가하고 있습니다.</label>
					</td>
				</tr>
				<tr>
					<th style = "width:100px; height:100px;">
						신고자
					</th>
					<td>
						<input type = "text" id = "rwriter" value = <%= session.getAttribute("Mid")%> readonly
						style = "width:380px; height:45px; border-radius: 5px; font-size:25px; text-align:center;">
					</td>
				</tr>
				<tr>
					<td colspan=2 style="text-align:center; height:100px;">
					<input type = "button" id="b2" value="신고하기" style = "width:250px;height:40px;font-size:15px;">
					</td>
				</tr>
			</table>
		</div>
	</BODY>
	
	<footer>
		<div id="foot">
		<br>
		회사소개 | 이용약관  | 개인정보 취급 방침  | 청소년 보호정책  | 마케팅 정보 수신동의  | 위치정보 이용약관 | <a href ="chart1.jsp">설문조사</a><br>
		<br>
		Copyright@mega co., Ltd. All rights reserved.
		</div>
	</footer>

</body>
</html>