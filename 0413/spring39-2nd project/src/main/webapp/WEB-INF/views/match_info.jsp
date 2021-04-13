<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MATCHs info -view</title>
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

		컨트롤러를 거쳐 연결된 view의 match_info.jsp <br>
		<table border="1" style="background: white">
			<tr>
				<th>아이디</th>
				<th>${vo.mid}</th>
			</tr>
			<tr>
				<th>음주</th>
				<th>${vo.drink}</th>
			</tr>
			<tr>
				<th>흡연</th>
				<th>${vo.smoke}</th>
			</tr>
			<tr>
				<th>신앙</th>
				<th>${vo.believe}</th>
			</tr>
			<tr>
				<th>신장</th>
				<th>${vo.tall}</th>
			</tr>
			<tr>
				<th>MBTI</th>
				<th>${vo.mbti}</th>
			</tr>
			<tr>
				<th>장소</th>
				<th>${vo.place}</th>
			</tr>
			<tr>
				<th>연락</th>
				<th>${vo.contact}</th>
			</tr>
		</table>
		<br>
		<br> 
		<a href="memberlogout">로그아웃</a><br> <a
			href="memberinfo?mid=${Mid}">내 정보(my_info)</a><br> <a
			href="memberupdate?mid=${Mid}">회원정보수정</a><br> <a
			href="matchupdate?mid=${Mid}">회원매칭정보수정</a><br>

	</div>
</body>
</html>