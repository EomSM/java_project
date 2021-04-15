<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 1. MID or DATE를 기준으로 내림차순 정렬
	2. SQL에서 30개까지만 가져오는게 됨
	3. for문으로 i<30까지 반복
	4. ajax에 결과 출력 -->
<hr>
<table border="1" id="result" class="type02">
	<tr align ="center">
		<th style='width: 50px;'>번호</th>
		<th style='width: 100px;'>발신자</th>
		<th style='width: 1000px;'>내용</th>
		<th style='width: 200px;'>시각</th>
	</tr>
		<%-- <c:forEach var="vo" items="${list}" begin="1" end="15"> --%>
		<c:forEach var="vo" items="${list2}">
		<tr>
			<td style='width: 50px;'>${vo.MID}</td>
			<td style='width: 100px;'>${vo.IDSEND}</td>
			<td style='width: 1000px;'>${vo.CONTENT}</td>
			<td style='width: 200px;'>${vo.MTIME}</td>
		</tr>
	</c:forEach>
		
</table>