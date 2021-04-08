<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 Login</title>
</head>
<link rel="stylesheet" type="text/css" href="resources/css/style.css">
<link rel="shortcut icon" href="#">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	
</script>
<body>
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
			if (session.getAttribute("mid") == null) {
			%>
			로그인이 필요합니다.
			<%
			} else {
			%>
			${mid}님 환영합니다. <a href="memberlogout.jsp">로그아웃</a>
			<%
			}
			%>
		</div>
		<!-- 상단메뉴 -->
		<nav id="menu">
			<ul id="mainmenu">

				<%
				if (session.getAttribute("mid") == null) {
				%>
				<li><a href="#memberlogin.jsp">로그인</a></li>
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
				<li><a href="my_info.jsp">마이페이지</a>
					<ul class="submenu">
						<li><a href="#">이상형정보수정</a></li>
						<li><a href="memberupdate.jsp">개인정보수정</a></li>
					</ul></li>
		</nav>
	</header>
<BODY>
	<div id="content">
		<%
		if (session.getAttribute("mid") == null) {
		%>
		<h3>회원 로그인</h3>
		<hr color="green">
		<form action="memberlogin">
			아이디: <input type="text" name="mid"> <br> 패스워드: <input
				type="text" name="pw"> <br> <input type="submit"
				value="로그인처리"> <br> <a href="memberinsert.jsp">회원가입</a>

			<%
			} else {
			%>
			${mid}님 환영합니다. <a href="memberlogout.jsp">로그아웃</a>
			<%
			}
			%>

		</form>
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