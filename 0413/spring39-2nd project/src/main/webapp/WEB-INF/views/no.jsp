<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 실패</title>
</head>
<body bgcolor="green">
	해당하는 id/pw가 없어 로그인에 실패했습니다. 확인 후 재로그인 해주세요.
	<hr>
	<a href="memberlogin.jsp">로그인 페이지로</a>
	<br>
	<a href="memberinfo?mid=${mid}">회원정보 페이지로</a>
	<br>

	<a href="main.jsp">메인으로 돌아가기</a>
	<br>
	<a href="matchinsert.jsp">매칭정보입력</a>
	<br>
	<a href="matchupdate?mid=${Mid}">매칭정보수정</a>
	<br>
	<a href="memberlogout.jsp">로그아웃</a>
	<br>
	<a href="memberdelete.jsp">회원 탈퇴</a>
</body>
</html>