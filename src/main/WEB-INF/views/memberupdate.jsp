<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member_update</title>
<link rel="stylesheet" type="text/css" href="resources/css/style.css">
<link rel="shortcut icon" href="#">
	<script type="text/javascript" src="resources/js/jquery-3.6.0.js"></script>
	<script type="text/javascript">
	function idnull() {
		alert('로그인이 필요합니다.')
		location.href = "memberlogin.jsp"
	}
	</script>
</head>
<body>
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
				<li><a href="memberlogin.jsp">로그인</a></li>
				<%} else { %>
				<li><a>쪽지함</a>
					<ul class = "submenu">
						<li><a href="messageListReceive.jsp">받은쪽지함</a></li>
						<li><a href="messageListSend.jsp">보낸쪽지함</a></li>
						<li><a href="messageSend.jsp">쪽지보내기</a></li>
					</ul>
				</li>
				<%} %>
				
				<li><a>고객센터</a>
					<ul class = "submenu">
						<li><a href="bbsall.jsp">게시판</a></li>
						<% if(session.getAttribute("Mid") == null) {%>
						<li><a onclick="idnull()" style="cursor:pointer;">불량회원신고</a></li>
						<%} else { %>
						<li><a href="report.jsp">불량회원신고</a></li>
						<%} %>
					</ul>
				</li>
				
				<% if (session.getAttribute("Mid") != null && session.getAttribute("Mid").equals("admin")) {%>
				<!-- null에 대한 정의를 해주지 않으면 예외처리에서 오류가 남 따라서 조건을 두개 주었음 -->
				<!-- 세션값을 조회했을 때 반환값은 오브젝트로 스트링과 비교할땐 equals 사용. 세션값을 string으로 형변환시켜서 비교할땐 equals 문법에러가 남 -->
				<li><a href="admin.jsp">관리자</a></li>
				<%} else if (session.getAttribute("Mid") == null) { %>
				<li><a onclick="idnull()" style="cursor:pointer;">매칭</a></li>
				<%} else {%>
				<li><a href="matching.jsp">매칭</a>
					<ul class = "submenu">
						<li><a href="TypeInsert.jsp">이상형정보입력</a></li>
                     	<li><a href="TypeSelect.jsp">이상형정보수정</a></li>
					</ul>
				</li>
				<%}  %>
				
				<% if(session.getAttribute("Mid") == null) {%>
				<li><a onclick="idnull()" style="cursor:pointer;">마이페이지</a>
				<%} else { %>
				<li><a href="memberinfo?mid=${Mid}">마이페이지</a>
				<%} %>
					<ul class = "submenu">
						<% if(session.getAttribute("Mid") == null) {%>
						<li><a onclick="idnull()" style="cursor:pointer;">개인정보수정</a></li>
						<li><a onclick="idnull()" style="cursor:pointer;">매칭정보</a></li>
						<%} else { %>
						<li><a href="memberupdate?mid=${Mid}">개인정보수정</a></li>
						<li><a href="match_info?mid=${Mid}">매칭정보</a></li>
						<%} %>
					</ul>
				</li>
			</ul>
		</nav>
	</header>

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
					<th><input name="Mid" type="text" value="${vo.mid}" readonly ></th>
				</tr>
				<tr>
					<th>성별</th>
					<th><input type="text" name="gender" value="${vo.gender}" readonly ></th>
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
		<br>
		회사소개 | 이용약관  | 개인정보 취급 방침  | 청소년 보호정책  | 마케팅 정보 수신동의  | 위치정보 이용약관 | <a href ="chart1.jsp">설문조사</a><br>
		<br>
		Copyright@mega co., Ltd. All rights reserved.
		</div>
	</footer>
</body>
</html>