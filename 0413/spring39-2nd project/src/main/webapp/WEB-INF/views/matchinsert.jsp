<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MBTI로 만나는 나의 상대 : 매칭 정보 입력</title>
<link rel="stylesheet" type="text/css" href="resources/css/style.css">
<link rel="shortcut icon" href="#">
</head>
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

<BODY>
	<div id="content">
		매칭정보 입력<br>${Mid}님의 매칭 정보가 입력되었습니다.<br>
		<a href="match_info?mid=${Mid}">매칭 정보 확인 </a><br>
		<a href="matchupdate?mid=${Mid}">매칭 정보 수정</a><br>
		<a href="memberupdate?mid=${Mid}">회원정보 수정</a><br>
		<a href="memberlogout.jsp">로그아웃</a><br>
		<a href="memberdelete.jsp">회원 탈퇴</a><br> 
	</div>
</BODY>

<footer>
	<div id="foot">
		<br> 회사소개 | 이용약관 | 개인정보 취급 방침 | 청소년 보호정책 | 마케팅 정보 수신동의 | 위치정보
		이용약관 <br> <br> Copyright@mega co., Ltd. All rights reserved.
	</div>
</footer>

</html>
