<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
	<link rel="stylesheet" type="text/css" href="resources/css/style.css">
	<link rel="stylesheet" type="text/css" href="resources/css/table.css">
	<link rel="shortcut icon" href="#">
	<script type="text/javascript" src="resources/js/jquery-3.6.0.js"></script>
	<script type="text/javascript">
	function pageload(x){
		location.href = "bbsupdate?bid="+x
	}
	function confirmdel(){
		if (confirm("정말 삭제하시겠습니까?") == true) {
			alert('삭제가 완료되었습니다!')
			location.href = "bbsdelete?bid=${bbsVO.bid}"
		}else{
			return
		}
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
	<!-- content 안으로 각 페이지 내용넣기 (img 지우고 본인 내용 넣어주시면 됩니다!) -->
	<BODY>
		<div id ="content">
			<div id = "bbsname" style = "width : 1300px; height : 100px; font-size:50px; font-weight:bold; color:white;
			 text-align:center; display:table-cell; vertical-align:middle;">
			<!-- 글씨를 수평정렬, 수직정렬하기위함. textalign은 수평 verticalalign은 수직인데 inline요소에만 적용되기 때문에 table형태로 display  -->
				공지사항
			</div>
			<div id = "divmenu" style = "width : 1300px; height : 50px; float : left;">
				<% if (session.getAttribute("Mid") != null && session.getAttribute("Mid").equals("admin")) {%> 
				<input type="button" value= "수정" onclick = "pageload(${bbsVO.bid})" style = "width : 80px; height : 35px; font-size: 15px; margin-left :75%; ">
				<input type="button" value= "삭제" onclick = "confirmdel()" style = "width : 80px; height : 35px; font-size: 15px;">
				<input type="button" value= "목록" onclick = "location.href = 'bbsall.jsp'" style = "width : 80px; height : 35px; font-size: 15px;">
				<%}else{ %>
				<input type="button" value= "목록" onclick = "location.href = 'bbsall.jsp'" style = "width : 80px; height : 35px; font-size: 15px; margin-left : 89%;">
				<%} %>
			</div>
			<div id = "bbscontent" align = center >
				<table class = "type02" style = "font-size : 15px; width : 90%;">
					<tr>
						<th>게시글 제목</th>
						<td style = "text-align : left;">${bbsVO.btitle}</td>
					</tr>
					<tr>
						<th>등록일자</th>
						<td style = "text-align : left;">${bbsVO.bdate}</td>
					</tr>
					<tr>
						<th>작성자</th>
						<td style = "text-align : left;">${bbsVO.bwriter}</td>
					</tr>
					<tr>
						<th style = "line-height: 20;">내용</th>
						<td style = "text-align : left;">${bbsVO.bcontent}</td>
					</tr>
				</table>
			</div>
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