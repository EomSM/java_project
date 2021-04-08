<%@page import="com.mega.mvc39.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member_Upate</title>
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
				if (session.getAttribute("mid") == null) {
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
				<li><a href="my_info.jsp">마이페이지</a>
					<ul class="submenu">
						<li><a href="#">이상형정보수정</a></li>
						<li><a href="memberupdate.jsp">개인정보수정</a></li>
					</ul></li>
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
		pw=$("#pw").val()
		pw1=$("#pw1").val()
		if (pw==pw1) {
		$("#div2").append("패스워드 확인에 성공했습니다. 계속 진행하세요!")
		}
		else {
		$("#div2").append("입력하신 패스워드가 다릅니다. 다시 입력해주세요!")	
		}
		/* 패스워드 확인 */
	}) //b2


}
  </script>
<BODY>
	<div id="content">
		<h3>회원 정보 수정</h3>
		<hr color="green">
		<form action="memberupdate.mega">



			1 아이디: <input name="mid" type="text" value="${Mid}">
		
			<div id="div1"></div>
			<!-- id는 사실 세션 가져오는거라 인풋 안 받아도 됨. 연습용. value 안에 넣을 값 테스트 겸 -->
			
			2 패스워드: <input name="pw" type="password" > <input name="pw1"
				type="password">
			<button id="b3">패스워드 확인</button>
			<br>
			<div id="div2"></div>
			<!-- pw 넣기 확인 체크  -->
			3 이름: <input name="name"><br> 
			4 태어난 년도: <input
				name="birthdate"><br>
			5 이메일: <input name="email"><br>
			6 전화번호: <input name="tel"><br> 
			7 카카오톡 아이디: <input
				name="kakaotalk"><br>
			
			8 성별: <input type="radio" name="gender" value="m">남성 <input
				type="radio" name="gender" value="f">여성<br> <br>

			<button id="b1" type="submit" value="회원 정보 수정">회원 정보 수정</button>
			<br>



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