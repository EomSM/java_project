<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MBTI로 만나는 나의 상대 : 매칭 정보 입력</title>
<link rel="stylesheet" type="text/css" href="resources/css/style.css">
<link rel="shortcut icon" href="#">
</head>
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
		<!--세선만 대문자. 나머지는 소문자로.-->
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
<BODY>
	<div id="content">


		좀 더 나은 매칭을 위해 스스로의 정보를 완성해 주세요 <br>
		<hr>
		<form action="matchinsert">
			<table border="1"
				style="background-color: white">
				<tr>
					<th>1 아이디(세션/fk):</th>
					<td><input name="mid" value="${Mid}"></td>
				</tr>
				<tr>
					<th>2 음주:</th>

					<td><input type="radio" name="drink" value="1" >전혀
						마시지 않아요<br> <input type="radio" name="drink" value="2">어쩔
						수 없을 때만 마셔요<br> <input type="radio" name="drink" value="3">가끔
						마셔요<br> <input type="radio" name="drink" value="4">어느정도
						즐기는 편이에요<br> <input type="radio" name="drink" value="5">자주
						술자리를 가지는 편이에요</td>
				</tr>
				<tr>
					<th>3 흡연:</th>
					<td><input type="radio" name="smoke" value="1" >흡연자에요<input
						type="radio" name="smoke" value="2">담배를 전혀 피우지 않아요</td>
				</tr>
				<tr>
					<th>4 종교:</th>
					<td><input type="radio" name="believe" value="1" >개신교
						<input type="radio" name="believe" value="2">천주교 <input
						type="radio" name="believe" value="3">불교 <input
						type="radio" name="believe" value="4">무교</td>
				</tr>
				<tr>
					<th>5 키:</th>
					<td><input name="tall" >cm</td>
				</tr>
				<tr>
					<th>6 MBTI:</th>
					<td><input type="text" name="mbti" >
						<a href="https://www.16personalities.com/ko/"> 참조: MBTI성격유형테스트</a></td>
				</tr>
				<tr>
					<th>7 선호장소:</th>
					<td><input type="radio" name="place" value="1" >실내
						<input type="radio" name="place" value="2">야외</td>
				</tr>
				<tr>
					<th>8 선호연락수단:</th>
					<td><input type="radio" name="contact" value="1" >카톡
						<input type="radio" name="contact" value="2">전화</td>
				</tr>
			</table>

			<button type="submit" value="매칭정보">매칭 정보 입력</button>
		</form>

	</div>
</BODY>

<footer>
	<div id="foot">
		<br> 회사소개 | 이용약관 | 개인정보 취급 방침 | 청소년 보호정책 | 마케팅 정보 수신동의 | 위치정보
		이용약관 <br> <br> Copyright@mega co., Ltd. All rights reserved.
	</div>
</footer>

</html>
