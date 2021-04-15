<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>매칭 수정</title>
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
			<h3>매칭 정보 수정</h3>
			<hr color="white">
			<form action="matchupdate2">
	
				아이디: <input name="Mid" type="text" value="${vo.mid}" readonly><br>
	
				<hr color="white">
				<table border="1" style="background-color: white">
					<tr>
						<th>음주</th>
						<td><input type="radio"
							name="drink" value="1"
							<c:if test="${vo.drink == 1}"> checked="checked"</c:if>>전혀 마시지
							않아요<br> <input type="radio" name="drink" value="2"
							<c:if test="${vo.drink == 2}"> checked="checked"</c:if>>어쩔 수 없을
							때만 마셔요<br> <input type="radio" name="drink" value="3"
							<c:if test="${vo.drink == 3}"> checked="checked"</c:if>>가끔 마셔요<br>
							<input type="radio" name="drink" value="4"
							<c:if test="${vo.drink == 4}"> checked="checked"</c:if>>어느정도 즐기는
							편이에요 <br> <input type="radio" name="drink" value="5"
							<c:if test="${vo.drink == 5}"> checked="checked"</c:if>>자주 술자리를
							가지는 편이에요</td>
					</tr>
	
					<tr>
						<th>흡연</th>
						<td><input type="radio"
							name="smoke" value="1"
							<c:if test="${vo.smoke == 1}"> checked="checked"</c:if>>흡연자에요<br>
							<input type="radio" name="smoke" value="2"
							<c:if test="${vo.smoke == 2}"> checked="checked"</c:if>>담배를 전혀
							피우지 않아요</td>
					</tr>
					<tr>
						<th>종교</th>
						<td><input type="radio"
							name="believe" value="1"
							<c:if test="${vo.believe == 1}"> checked="checked"</c:if>>개신교<br>
							<input type="radio" name="believe" value="2"
							<c:if test="${vo.believe == 2}"> checked="checked"</c:if>>천주교<br>
							<input type="radio" name="believe" value="3"
							<c:if test="${vo.believe == 3}"> checked="checked"</c:if>>불교<br>
							<input type="radio" name="believe" value="4"
							<c:if test="${vo.believe == 4}"> checked="checked"</c:if>>무교<br>
						</td>
					</tr>
					<tr>
						<th>키</th>
						<th><input name="tall" value="${vo.tall}">cm</th>
					</tr>
	
					<tr>
						<th>MBTI</th>
						<th><input name="mbti" value="${vo.mbti}"></th>
					</tr>
	
					<tr>
						<th>선호장소</th>
						<td><input type="radio"
							name="place" value="1"
							<c:if test="${vo.place == 1}"> checked="checked"</c:if>>실내<br>
							<input type="radio" name="place" value="2"
							<c:if test="${vo.place == 2}"> checked="checked"</c:if>>야외</td>
					<tr>
						<th>선호연락수단</th>
						<td> <input type="radio"
							name="contact" value="1"
							<c:if test="${vo.contact == 1}"> checked="checked"</c:if>>카톡<br>
							<input type="radio" name="contact" value="2"
							<c:if test="${vo.contact == 2}"> checked="checked"</c:if>>전화</td>
					</tr>
				</table>
	
				<button id="b1" type="submit" value="회원 매칭 정보 수정">매칭 정보 수정</button>
				<br> <br> <br> <br>
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