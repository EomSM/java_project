<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="resources/css/style.css">
<link rel="shortcut icon" href="#">
<link rel="stylesheet" type="text/css" href="resources/css/message.css">
<script type="text/javascript" src="resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript">
function idnull() {
	alert('로그인이 필요합니다.')
	location.href = "memberlogin.jsp"
}
	/* 보낸 쪽지함 - 들어오자마자 리스트가 뜨는 상황(비동기식 - ajax사용) */
	$(function() {
		$.ajax({
			url : 'listSend', /* 컨트롤러와 주소일치!! */
			data : {
				IDSEND : $('#IDSEND').val(),
			},
			success : function(json) {
				$(json).each(function(index, vo) { // foreach문을 사용하여 리스트에 쌓인 각 컬럼의 데이터를 반복해서 출력
					
					console.log(vo) // 에러 확인을 위해 콘솔에 찍어봄

					item = "<tr><td style='width: 50px;'>"+vo.mid+"</td><td style='width: 100px;'>"+vo.idre+"</td><td style='width: 1000px;'>"+vo.content+"</td><td style='width: 200px;'>"+vo.mtime+"</td></tr>"
					$('#result').append(item)
				})
			}

		})
		
		/* 쪽지 삭제 */
		$('#messageDelete').click(function() { // 버튼 클릭시 실행
			if($('#MID').val().replace(/|s/g, "").length == 0){ // 공백일 경우 실행을 안되게 하기 위함.
					alert('빈칸 없이 입력란을 작성하세요.')
			}else{
				var result = confirm('보냈던 메시지를 삭제/취소 하시겠습니까?'); // 정말로 삭제할지 재확인
					if (result) { //yes(확인)
						location.href="messageDelete?MID=" + $('#MID').val() 
							} else { //no(취소), 아무일도 일어나지 않음
							}
				}
			})

	})
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
	
			<!-- view부분!! -->
			<h3>보낸 쪽지함</h3>
			<hr color="red">
			로그인 중인 발신자:
			<input type="text" id="IDSEND" value="<%=session.getAttribute("Mid")%>" readonly>
			<br>
			
			<hr>
			<table border="1" id="result" class="type02">
				<tr align ="center">
					<th style='width: 50px;'>번호</th>
					<th style='width: 100px;'>수신자</th>
					<th style='width: 1000px;'>내용</th>
					<th style='width: 200px;'>시각</th>
				</tr>
			</table>
				<!-- 세션 임시 부여 중. 원래는 로그인중인 세션으로 받아와야함. -->
			
			<form action='messageSearch'> <!-- form action 사용 - 페이지 넘김(동기식) -->
			<input type="hidden" name="IDSEND" value="<%=session.getAttribute("Mid")%>">
			수신자: <input type="text" name="IDRE" size=10 required>
			<button>검색</button>
			</form><br>
			
			번호: <input id='MID' size=1 ></input>
			<button id='messageDelete'>삭제하기</button><br><br>
			
			<a href = "messageListReceive.jsp">받은 쪽지함</a>
			
			<a href = "messageSend.jsp">쪽지 보내기</a>
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