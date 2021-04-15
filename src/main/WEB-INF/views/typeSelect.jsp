<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
		<div id ="content">
			<form action="typeUpdate">
					<!-- 자바스크립트에서는 오타에 큰 주의를! 어디에서 오타가 난건지 쉽게 알 수 없다. 에러를 보고 브라우저의 요청과 db의 응답의 흐름 사이에서 어디에서 오타가 난건지 파악하자. 팁은 각각의 장소에서 하나씩 프린트해보기 jsp,vo,controller,mapper,dao,db,view확인 -->
				아이디: <input name="tid" value="${vo2.tid}" readonly="readonly"><br>
				음주여부<br>
			  			 <label><input type="radio" name="drinking" value="1" <c:if test="${vo2.drinking == 1}">checked</c:if>>전혀 마시지 않았으면 좋겠어요</label><br>
						 <label><input type="radio" name="drinking" value="2" <c:if test="${vo2.drinking == 2}">checked</c:if>>어쩔 수 없을때만 마시면 좋겠어요</label><br>
						 <label><input type="radio" name="drinking" value="3" <c:if test="${vo2.drinking == 3}">checked</c:if>>가끔 마셨으면 좋겠어요</label><br>
						 <label><input type="radio" name="drinking" value="4" <c:if test="${vo2.drinking == 4}">checked</c:if>>어느정도 즐기면 좋겠어요</label><br>
						 <label><input type="radio" name="drinking" value="5" <c:if test="${vo2.drinking == 5}">checked</c:if>>자주 술자리를 가지면 좋겠어요</label><br>
						 <label><input type="radio" name="drinking" value="6" <c:if test="${vo2.drinking == 6}">checked</c:if>>상관 없어요</label><br>
			   	흡연여부<br>
			   	         <label><input type="radio" name="smoking" value="1" <c:if test="${vo2.smoking == 1}">checked</c:if>>흡연 했으면 좋겠어요</label><br>		 
						 <label><input type="radio" name="smoking" value="2" <c:if test="${vo2.smoking == 2}">checked</c:if>>흡연 하지 않았으면 좋겠어요</label><br>
						 <label><input type="radio" name="smoking" value="3" <c:if test="${vo2.smoking == 3}">checked=</c:if>>상관없어요</label><br>
							 
				이상형 종교<br>
						<label><input type="radio" name="religion" value="1" <c:if test="${vo2.religion == 1}">checked</c:if>>개신교</label>
						<label><input type="radio" name="religion" value="2" <c:if test="${vo2.religion == 2}">checked</c:if>>가톨릭</label>
						<label><input type="radio" name="religion" value="3" <c:if test="${vo2.religion == 3}">checked</c:if>>불교</label>
						<label><input type="radio" name="religion" value="4" <c:if test="${vo2.religion == 4}">checked</c:if>>기타</label>
						<label><input type="radio" name="religion" value="5" <c:if test="${vo2.religion == 5}">checked</c:if>>상관없음</label><br>
				이상형 키<br>	
						<label><input type="radio" name="height" value="185" <c:if test="${vo2.height == 185}">checked</c:if>>185이상</label>
						<label><input type="radio" name="height" value="180" <c:if test="${vo2.height == 180}">checked</c:if>>180~185</label><br>
						<label><input type="radio" name="height" value="175" <c:if test="${vo2.height == 175}">checked</c:if>>175~180</label>
						<label><input type="radio" name="height" value="170" <c:if test="${vo2.height == 170}">checked=</c:if>>170~175</label><br>
						<label><input type="radio" name="height" value="165" <c:if test="${vo2.height == 165}">checked</c:if>>165~170</label>
						<label><input type="radio" name="height" value="160" <c:if test="${vo2.height == 160}">checked</c:if>>160~165</label><br>
						<label><input type="radio" name="height" value="150" <c:if test="${vo2.height == 150}">checked</c:if>>150~160</label>
						<label><input type="radio" name="height" value="145" <c:if test="${vo2.height == 145}">checked"</c:if>>150이하</label><br>
				선호 데이트 장소: <label><input type="radio" name="place" value="1" <c:if test="${vo2.place == 1}">checked</c:if>>실내</label>
								  <label><input type="radio" name="place" value="2" <c:if test="${vo2.place == 2}">checked"</c:if>>야외</label><br>		
				선호 연락방식: <label><input type="radio" name="contact" value="1" <c:if test="${vo2.contact == 1}">checked</c:if>>카톡</label>
							 <label><input type="radio" name="contact" value="2" <c:if test="${vo2.contact == 2}">checked</c:if>>전화</label><br>
				이상형 나이: <label><input type="radio" name="wage" value="1" <c:if test= "${vo2.wage == 1}">checked</c:if>>연상</label>
							<label><input type="radio" name="wage" value="2" <c:if test="${vo2.wage == 2}">checked</c:if>>연하</label>
							<label><input type="radio" name="wage" value="3" <c:if test="${vo2.wage == 3}">checked</c:if>>동갑</label><br>		  
				<input type="submit" value="입력 완료">
				
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