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
 				${Mid}님 환영합니다.<br>
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
		<div id ="content">

WebAPP의 memberinfo<br><hr>
:<h2>${Mid}</h2>님, 무엇을 도와드릴까요?
<hr> <br>
<br>
<br>
<a href="memberinfo?mid=${Mid}">마이페이지 : 컨트롤러를 거쳐 연결된 View의 memberinfo.jsp</a><br>
		<a href="matchinsert.jsp">매칭 정보 입력</a><br>
		<a href="match_info?mid=${Mid}">매칭 정보 확인 </a><br>
		<a href="matchupdate?mid=${Mid}">매칭 정보 수정</a><br>
		<a href="memberupdate?mid=${Mid}">회원정보 수정</a><br>
		<a href="memberlogout.jsp">로그아웃</a><br>
		<a href="memberdelete.jsp">회원 탈퇴</a><br> 
		
	
	<br>

</div>
</body>
</html>