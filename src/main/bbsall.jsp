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
	function idnull() {
		alert('로그인이 필요합니다.')
		location.href = "memberlogin.jsp"
	}
	function detail(x){
		location.href = "bbscontent?bid="+x
	}
	
	$(function() {
		$.ajax({
			url : "bbscount",
			success : function(data) {
				var totalData = data; //전체 데이터
                var dataPerPage = 8; //한 페이지에 표현할 데이터
                var pagePerGroup = 4; // 한 화면에 나타낼 페이지 수
                
                paging(totalData, dataPerPage, pagePerGroup, 1)
                function paging(totalData, dataPerPage, pagePerGroup, currentPage) {
                	console.log("currentPage : " + currentPage);
                	
                	var totalPage = Math.ceil(totalData / dataPerPage); 
                    // 총페이지 = 총데이터 / 페이지별 보여줄 데이터 수     를 올림해서 보여줌.
                    var pageGroup = Math.ceil(currentPage / pagePerGroup); 
                    // 현재 페이지그룹 = 현재페이지 / 화면에 나타낼 페이지 수 올림
                    console.log("pageGroup : " + pageGroup);
                    var last = pageGroup * pagePerGroup; 
                    //화면에 보여줄 페이지 그룹의 마지막 페이지 = 페이지그룹 * 화면에 나타낼 페이지 수 
                    var first = last - (pagePerGroup - 1); //그룹의 첫페이지 = 마지막페이지 - (그룹당페이지수-1)
                    
                    if (last > totalPage) {
                    	last = totalPage;
                    	 //마지막 페이지가 총 페이지수보다 크게나오면 마지막페이지를 토탈페이지로 바꿔줌
                    	first = pageGroup*pagePerGroup - (pagePerGroup-1) ;
					}
                    var next = last + 1; //페이지 그룹 다음으로
                    var prev = first - 1; //페이지 그룹 이전으로                  
                    console.log("last : " + last);
                    console.log("first : " + first);
                    console.log("next : " + next);
                    console.log("prev : " + prev);                  
                    
                    var html = ''; //화면에 뿌릴 페이지 html코드
                    if (prev > 0) {
                        html += '<button id="prev" value ="prev">prev</button>';
                    }                   
                    for (var i = first; i <= last; i++) {
                       html += '<button id="p'+ i + '" value = "'+i+'">'+ i +'</button>';
                    }
                    if (last < totalPage) {
                       html += '<button id="next" value ="next">next</button>';
                    }
                    $("#bbspaging").html(html);
                    
                    $("#bbspaging button").click(function() {
                        var selectedPage = $(this).attr('value');
                        console.log("selected : " + selectedPage);
                        
                        if (selectedPage == 'next'){
                            selectedPage = next;
                            first = selectedPage;
                        	//value가 next이면 next에 해당하는 페이지넘버를 넣어
                        }
                         if (selectedPage == 'prev'){
                            selectedPage = prev;
                         }
                        paging(totalData, dataPerPage, pagePerGroup, selectedPage)    
                        $.ajax({
                        	url : "pagedata",
                        	data : {
                        		page : selectedPage
                        	},
                        	success : function(data) {
								console.log(data)
								var datalist = data;
								var tablebody = '';
								tablebody += '<table class="type01">';
								tablebody += '<tr>';
								tablebody += '<th>게시글 번호</th><th>게시글 제목</th><th>등록일자</th><th>작성자</th>';
								tablebody += '</tr>';
								for (var i = 0; i < datalist.length; i++) {
									tablebody += '<tr onclick = "detail('+datalist[i].bid+')" style="cursor:pointer;"><td>'+datalist[i].bid + '</td>'
									tablebody += '<td onmouseover="this.style.color=#FFE6E6" onmouseout="this.style.color=black">' +datalist[i].btitle + '</td><td>'
									tablebody += datalist[i].bdate + '</td><td>'
									tablebody += datalist[i].bwriter + '</td>'
									tablebody += '</tr>';
								}//for
								tablebody += '</table>';
								$('#bbscontent').html(tablebody)
								
							}//success2
                        })//ajax2
                        
                    }) //click
                    
                }//paging정의
                
                //화면에 페이지 뿌리기
                
                $("#p1").trigger("click");
			}//success
		}) //ajax1
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
			<div id = "bbsname" style = "width : 1300px; height : 100px; font-size:50px; font-weight:bold; color:white;
			 text-align:center; display:table-cell; vertical-align:middle;">
			<!-- 글씨를 수평정렬, 수직정렬하기위함. textalign은 수평 verticalalign은 수직인데 inline요소에만 적용되기 때문에 table형태로 display  -->
				공지사항
			</div>
			<div id = "divmenu" style = "width : 1300px; height : 50px;">
				<% if (session.getAttribute("Mid") != null && session.getAttribute("Mid").equals("admin")) {%> 
				<input type="button" value= "글등록" onclick = "location.href = 'bbscreate.jsp'" style = "width : 80px; height : 35px; font-size: 15px; margin-left : 82%">
				<%} %>
			</div>
			<div id = "bbscontent">
				
			</div>
			<div id = "bbspaging"  style="text-align:center;">
				
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