<%@page import="com.mega.mvc39.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member_update</title>
<link rel="stylesheet" type="text/css" href="resources/css/style.css">
<link rel="shortcut icon" href="#">
</head>
<body bgcolor="pink">
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
</head>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">
  $(function() {

	$("#b2").keyup(function() {
/* "ajax 기능 쓴다면 이런 거"
pw=$("#pw").val()
		pw1=$("#pw1").val()
		if (pw==pw1) {
		$("#div2").append("패스워드 확인에 성공했습니다. 계속 진행하세요!")
		}
		else {
		$("#div2").append("입력하신 패스워드가 다릅니다. 다시 입력해주세요!")	
		}
 */		/* 패스워드 확인 */
	}) //b2


}
  </script>
<BODY>
	<div id="content">
		<h3>회원 정보 수정</h3>
		<hr color="white">
		<form action="memberupdate2">


			* 변동 불가능하게 할 데이터: 아이디. 성별.<br>
			<table border="1"
				style="width: 600; height: 1000; background-color: white">
				<tr>
					<th>아이디</th>
					<th><input name="Mid" type="text" value="${vo.mid}"></th>
				</tr>
				<tr>
					<th>성별</th>
					<th><input type="text" name="gender" value="${vo.gender}"></th>
				</tr>
			</table>

			<hr color="white">
			<table border="1"
				style="width: 600; height: 1000; background-color: white">
				<tr>
					<th>패스워드</th>
					<th><input name="Pw" type="text" value="${vo.pw}"></th>
				</tr>
				<tr>
					<th>이름</th>
					<th><input name="name" value="${vo.name}"></th>
				</tr>
				<tr>
					<th>태어난 년도</th>
					<th><input name="birthdate" value="${vo.birthdate}"></th>
				</tr>
				<tr>
					<th>이메일</th>
					<th><input name="email" value="${vo.email}"></th>
				</tr>
				<tr>
					<th>전화번호</th>
					<th><input name="tel" value="${vo.tel}"></th>
				</tr>
				<tr>
					<th>카카오톡 아이디</th>
					<th><input name="kakaotalk" value="${vo.kakaotalk}"></th>
				</tr>
			</table>
			<br>


			<button id="b1" type="submit" value="회원 정보 수정">회원 정보 수정</button>
			<br>
			<!-- db에 저장하여 memberupdate2로 이동  -->
			<br> <br> <br> <a href="memberdelete.jsp">회원 탈퇴</a>
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