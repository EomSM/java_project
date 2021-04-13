<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member Insert</title>

<link rel="stylesheet" type="text/css" href="resources/css/style.css">
<link rel="shortcut icon" href="#">
</head>
<BODY>
	<header>
		<!-- 메뉴바 왼쪽상단 로고 -->
		<nav id="logo">
			<a href="main.jsp"> <img src="resources/img/logo.png" width="80"
				height="80">
			</a>
		</nav>

		<!-- 로그인/로그아웃 결과-->
		<div id="welcome">
			<%
			if (session.getAttribute("Mid") == null) {
			%>
			<!--세선만 대문자. 나머지는 소문자로.-->
			로그인이 필요합니다.
			<%
			} else {
			%>
			${Mid}님 환영합니다.<br> <a href="memberlogout.jsp">로그아웃</a>
			<%
			}
			%>
		</div>

		<!-- 상단메뉴 -->
		<nav id="menu">
			<ul id="mainmenu">

				<%
				if (session.getAttribute("Mid") == null) {
				%>
				<li><a href="memberlogin.jsp">로그인</a></li>
				<%
				} else {
				%>
				<li><a href="#">쪽지함</a></li>
				<%
				}
				%>

				<li><a href="#">고객센터</a>
					<ul class="submenu">
						<li><a href="#">게시판</a></li>
						<li><a href="#">불량회원신고</a></li>
					</ul></li>
				<li><a href="#">매칭</a></li>
				<li><a href="memberinfo?mid=${Mid}">마이페이지</a>
					<ul class="submenu">
						<li><a href="matchinsert.jsp">매칭정보입력</a></li>
						<li><a href="matchupdate?mid=${Mid}">매칭정보수정</a></li>
						<li><a href="memberupdate?mid=${Mid}">개인정보수정</a></li>
						<li><a href="memberlogout.jsp">로그아웃</a></li>
					</ul></li>
			</ul>

		</nav>
	</header>

	<div id="content">
		<h3>
			<br> 회원 가입이 완료되었습니다. 감사합니다
		</h3>
		<br> <a href="memberlogin.jsp">로그인</a> <br> 
		<a href="memberlogout.jsp">로그아웃</a><br>
		<a href="matchinsert.jsp">매칭정보입력</a><br>
		<a href="memberupdate?mid=${Mid}">개인정보수정</a>

	</div>
</BODY>

<footer>
	<div id="foot">
		<br> 회사소개 | 이용약관 | 개인정보 취급 방침 | 청소년 보호정책 | 마케팅 정보 수신동의 | 위치정보
		이용약관 <br> <br> Copyright@mega co., Ltd. All rights reserved.
	</div>
</footer>

</body>
</html>