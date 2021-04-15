<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자모드</title>
	<link rel="stylesheet" type="text/css" href="resources/css/style.css">
	<link rel="stylesheet" type="text/css" href="resources/css/table.css">
	<link rel="shortcut icon" href="#">
	<script type="text/javascript" src="resources/js/jquery-3.6.0.js"></script>
	<script type="text/javascript">
	function idnull() {
		alert('로그인이 필요합니다.')
		location.href = "memberlogin.jsp"
	}
	$(function() {
		$.ajax({
			url:"reportcount", // 총 데이터 수를 세주는 controller 가상주소
			success: function(data) {
				var totalData = data; //전체 데이터 수
				var dataPerPage = 6; //한 페이지에 표현할 데이터
				
				// 페이징 함수 정의
                function paging(totalData, dataPerPage) {
					
                	var totalPage = Math.ceil(totalData / dataPerPage); 
                    //총페이지 = 총데이터 / 페이지별 보여줄 데이터 수     를 올림해서 보여줌. 
                  
                    var html = ''; // 페이지 화면에 넣을 코드 초기화
                    
                    // 페이징. 총페이지 수만큼 페이징 넘버를 생성해줌
                    for (var i = 1; i <= totalPage; i++) {
                        html += '<input type="button" value="'+i+'" id="b'+i+'">';
                     }
                    
                    // 페이징화면에 html코드 입력
                    $("#paging").html(html);
                   
                    $("#paging input").css({
                        "background-color" : "white",
                        "color" : "black",
                        "font-weight" : "bold",
                        "width" : "30px",
                        "height" : "30px",
                        "border-radius" : "3px",
                        "font-size" : "15px"});
                    
				}// paging 정의
				// 페이징 화면에 뿌리기				
				paging(totalData,dataPerPage);
				
				// 페이징에서 input 버튼 클릭시 이벤트 정의
				$('#paging input').click(function() {
					// 선택한 버튼의 value를 가져와 페이지 넘버 가져오기
					var pageno = $(this).attr('value');

					$.ajax({
						url:"reportpage",
						data:{
							page : pageno
						},
						success: function(data){
							var datalist = data;
							var tablebody = '<table class="type02" style = "width :98%"><tr><th>신고번호</th><th>신고자</th><th>신고대상</th><th>사유</th><th>신고일자</th></tr>';
							for (var i = 0; i < datalist.length; i++) {
								tablebody += '<tr><td>'+datalist[i].rid + '</td><td>'
								tablebody += datalist[i].rwriter + '</td><td>'
								tablebody += datalist[i].rtarget + '</td><td>'
								tablebody += datalist[i].rreason + '</td><td>'
								tablebody += datalist[i].rdate + '</td></tr>'
							}//for
							tablebody += '</table>'
							$('#reportcontent').html(tablebody)
						}//success2
					})
				})//click정의
				$("#b1").trigger("click");
			}//success1
		})//ajax1
	})//start
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
			<div id = "adminname" style = "width : 1300px; height : 100px; font-size:50px; font-weight:bold; color:white;
			 text-align:center; display:table-cell; vertical-align:middle;">
			<!-- 글씨를 수평정렬, 수직정렬하기위함. textalign은 수평 verticalalign은 수직인데 inline요소에만 적용되기 때문에 table형태로 display  -->
				신고함
			</div>
			<hr>
			<p align = "center" style = "font-size : 17px;">
				회원들의 불량회원신고내역입니다.<br>
				최근 이력부터 조회 가능합니다.
			</p>
			<hr>
			<div id="reportcontent"></div>
			<div id = "paging" style="text-align:center;"></div>
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