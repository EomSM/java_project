<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 성공</title>
<link rel="stylesheet" type="text/css" href="resources/css/style.css">
	<link rel="shortcut icon" href="#">
</head>
	<BODY>
		
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
 				${Mid}님 환영합니다.
 				<a href="memberlogout.jsp">로그아웃</a>
 			<%} %>
 		</div>
 		
 		<!-- 상단메뉴 -->
		<nav id = "menu">
			<ul id = "mainmenu">
			
				<% if(session.getAttribute("Mid") == null) {%>
				<li><a href="#memberlogin.jsp">로그인</a></li>
				<%} else { %>
				<li><a href="#">쪽지함</a></li>
				<%} %>
				
				<li><a href="#">고객센터</a>
					<ul class = "submenu">
						<li><a href="#">게시판</a></li>
						<li><a href="#">불량회원신고</a></li>
					</ul>
				</li>
				<li><a href="#">매칭</a></li>
				<li><a href="#">마이페이지</a>
					<ul class = "submenu">
						<li><a href="#">이상형정보수정</a></li>
						<li><a href="#">개인정보수정</a></li>
					</ul>
				</li>
			</ul>
		</nav>
	</header>
		<div id ="content">
${Mid}님, 안녕하세요? <br>
<!-- main.jsp로 이동 가능하게 a 태그 넣기? 아니면 몇 초 뒤에 자동으로 넘기기-->

<a href="my_info.jsp"> ${Mid}님의 개인 정보 보기 </a><br>
<a href="main.jsp">메인으로 돌아가기</a><br>
<a href="memberlogout.jsp">로그아웃 하기</a>



		</div>
	</BODY>
	
	<footer>
		<div id="foot">
		<br>
		회사소개 | 이용약관  | 개인정보 취급 방침  | 청소년 보호정책  | 마케팅 정보 수신동의  | 위치정보 이용약관 <br>
		<br>
		Copyright@mega co., Ltd. All rights reserved.
		</div>
	</footer>

</body>
</html>