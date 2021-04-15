<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%  String Mid = (String)session.getAttribute("Mid"); %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="resources/css/style.css">
<link rel="shortcut icon" href="#">
<script type="text/javascript" src="resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript">
function idnull() {
	alert('로그인이 필요합니다.')
	location.href = "memberlogin.jsp"
}
	$(function() {
		$('#b1').click(function() {
			$.ajax({
			 url: "matching",
			 success: function(x) {
				$('#result').html(x)
			},
			error: function() {
				$('#result').html('현재 회원님과 매칭 가능한 회원이 없습니다.')
			}
	
			})//ajax
		})//b1
		$('#b2').click(function() {
				$.ajax({
					url: "matching2",
					 
					 success: function(x) {
						$('#result').html(x)
					},
					 error: function() {
							$('#result').html('현재 2순위 매칭 회원이 없습니다.')
					}
							
			})//ajax 
		})//b2
		$('#b3').click(function() {
			$.ajax({
				url: "matching3",
				
				 success: function(x) {
					$('#result').html(x)
				},
				error: function() {
					$('#result').html('현재 3순위 매칭 회원이 없습니다.')
				}
				
			})//ajax
		})//b3
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
	
	<BODY>
		<div id ="content">
			매칭 상대 선택 
			<hr color="red">
			<button id="b1" style="width: 300px; height: 300px; font-size: 20px; background: pink;">1번</button>
			<button id="b2" style="width: 300px; height: 300px; font-size: 20px; background: pink;">2번</button>
			<button id="b3" style="width: 300px; height: 300px; font-size: 20px; background: pink;">3번</button>
			<hr color="red">
			<div id="result" style = "margine: 0 auto;"></div>
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