<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My info -view</title>
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

		컨트롤러를 거쳐 연결된 view의 memberinfo.jsp <br>
		<table border="1" style="witdh: 1000; height: 1000; background: white">

			<tr>
				<th>아이디</th>
				<th>${vo.mid}</th>
			</tr>
			<tr>
				<th>패스워드</th>
				<th>${vo.pw}</th>
			</tr>
			<tr>
				<th>이름</th>
				<th>${vo.name}</th>
			</tr>
			<tr>
				<th>생년</th>
				<th>${vo.birthdate}</th>
			</tr>
			<tr>
				<th>이메일</th>
				<th>${vo.email}</th>
			</tr>
			<tr>
				<th>전화번호</th>
				<th>${vo.tel}</th>
			</tr>
			<tr>
				<th>카카오톡</th>
				<th>${vo.kakaotalk}</th>
			</tr>
			<tr>
				<th>성별</th>
				<th>${vo.gender}</th>
			</tr>
		</table>
		<br> <a href="memberlogout.jsp">로그아웃</a> <br> <a
			href="memberupdate?mid=${Mid}">회원 정보 수정</a> <br> <a
			href="matchinsert.jsp">매칭 정보 입력</a> <br> <a
			href="match_info?mid=${Mid}">매칭 정보 확인</a> <br>
	</div>
</body>
</html>