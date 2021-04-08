<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Match</title>

</head>  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>  </script>
<body>
<form action="matchinsert.mega">

	스스로의 매칭 정보를 완성해 주세요
	<br>
	<hr>
	1 아이디(세션/fk):<br>
	<!--현재는 member 테이블의 mid를 FK로 받는 거 삭제해놓음(일단 DB 관리 되는지 확인을 위해)  -->
	<input name="mid" value="a"><br> 
	2 음주:<br>
	<input type="radio" name="drink" value="1" checked>전혀
	마시지 않아요
	<br>
	<input type="radio" name="drink" value="2">어쩔 수 없을
	때만 마셔요
	<br>
	<input type="radio" name="drink" value="3">가끔 마셔요
	<br>
	<input type="radio" name="drink" value="4">어느정도
	즐기는 편이에요
	<br>
	<input type="radio" name="drink" value="5">자주
	술자리를 가지는 편이에요
	<br> 3 흡연:<br>
	<input type="radio" name="smoke" value="1" checked>흡연자에요
	<br>
	<input type="radio" name="smoke" value="2">담배를 전혀
	피우지 않아요
	<br> 4 종교:<br>
	<input type="radio" name="believe" value="1" checked>개신교
	<br>
	<input type="radio" name="believe" value="2">천주교
	<br>
	<input type="radio" name="believe" value="3">불교
	<br>
	<input type="radio" name="believe" value="4">무교
	<br> 5 키:
	<input name="tall" value="170">
	<br> 6 mbti:
	<input name="mbti" value="ESTJ">
	<br> 7 선호장소:<br>
	<input type="radio" name="place" value="1" checked>실내
	<br>
	<input type="radio" name="place" value="2">야외
	<br> 8 선호연락수단:<br>
	<input type="radio" name="contact" value="1" checked>카톡
	<br>
	<input type="radio" name="contact" value="2">전화
	<br>
	
	<button type="submit" value="매칭정보">매칭 정보 입력 </button>
</form>
</body>
</html>