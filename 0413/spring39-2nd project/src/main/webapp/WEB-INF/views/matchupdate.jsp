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
</head>
<BODY>

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
		<br> 회사소개 | 이용약관 | 개인정보 취급 방침 | 청소년 보호정책 | 마케팅 정보 수신동의 | 위치정보
		이용약관 <br> <br> Copyright@mega co., Ltd. All rights reserved.
	</div>
</footer>
</body>
</html>