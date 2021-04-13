<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member_Insert</title>
<link rel="stylesheet" type="text/css" href="resources/css/style.css">
<link rel="shortcut icon" href="#">
</head>
<body bgcolor="pink">
	<!-- ajax 아직 구현 전 -->
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
			${mid}님 환영합니다. <a href="#">로그아웃</a>
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
</head>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">


<BODY>
	<div id="content">
		<h3>회원 가입</h3>
		<hr color="green">
		<form action="memberinsert">
			<table border="1"
				style="width: 600; height: 1000; background-color: white">
				<tr>
					<th>아이디</th>
					<th><input name="mid" value="${Mid}"></th>
				</tr>

				<tr>
					<th>패스워드</th>
					<th><input name="pw" type="password" value="asdf"></th>
				</tr>
				<tr>
					<th>이름</th>
					<th><input name="name" value="asdf"><br></th>
				</tr>
				<tr>
					<th>태어난 년도</th>
					<th><input name="birthdate" value="2000"><br></th>
				</tr>


				<tr>
					<th>이메일</th>
					<th><input name="email" value="asdf"></th>
				</tr>


				<tr>
					<th>전화번호</th>
					<th><input name="tel" value="asdf"><br></th>
				</tr>

				<tr>
					<th>카카오톡 아이디</th>
					<th><input name="kakaotalk" value="asdf"><br></th>
				</tr>
				<tr>
					<th>성별</th>
					<th><input type="radio" name="gender" value="m" checked>남성
						<input type="radio" name="gender" value="f">여성</th>
				</tr>
			</table>

			<button id="b1" type="submit" value="회원 가입 처리">회원 가입</button>
		</form>
		<br>
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