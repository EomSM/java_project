<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id = "simple" style = "width : 250px; vertical-align: top; display : inline-block; ">
<h2>1순위 회원 기본 정보</h2>
아이디 : ${mvo.mid}<br>
이름 : ${mvo.name}<br>
나이 : ${2021 - mvo.birthdate + 1}세<br>
MBTI : ${mcvo.mbti}<br>
매칭점수 : ${point1}점	<br>
</div>
<div id = "detail1" style = "width : 400px; vertical-align: top; display : inline-block;">
<h2>회원 상세 정보</h2>
음주 성향 : <c:choose>
       <c:when test="${mcvo.drink == 1}">전혀 마시지 않아요</c:when>
	   <c:when test="${mcvo.drink == 2}">어쩔 수 없을 때만 마셔요</c:when>
	   <c:when test="${mcvo.drink == 3}">가끔 마셔요</c:when>
	   <c:when test="${mcvo.drink == 4}">어느정도 즐기는 편이에요</c:when>
	   <c:when test="${mcvo.drink == 5}">자주술자리를 가지는 편이에요</c:when>	
</c:choose><br>
흡연 성향 :<c:choose>
	   <c:when test="${mcvo.smoke == 1}">흡연자에요</c:when>
	   <c:when test="${mcvo.smoke == 2}">담배를 전혀 피우지 않아요</c:when>
</c:choose><br>
종교 : <c:choose>
       <c:when test="${mcvo.believe == 1}">개신교</c:when>
	   <c:when test="${mcvo.believe == 2}">천주교</c:when>
	   <c:when test="${mcvo.believe == 3}">불교</c:when>
	   <c:when test="${mcvo.believe == 4}">무교</c:when>
</c:choose><br>
키 : ${mcvo.tall}<br>
선호 데이트 장소 : <c:choose>
	   <c:when test="${mcvo.place == 1}">실내</c:when>
	   <c:when test="${mcvo.place == 2}">야외</c:when>
</c:choose><br>
선호 연락수단 : <c:choose>
	   <c:when test="${mcvo.contact == 1}">카톡</c:when>
	   <c:when test="${mcvo.contact == 2}">전화</c:when>
</c:choose><br>
</div>
<div id = "detail2" style = "width : 400px; vertical-align: top; display : inline-block;">
<h2>상대방이 원하는 성향</h2>
음주 성향 : <c:choose>
       <c:when test="${tvo.drinking == 1}">전혀 마시지 않아요</c:when>
	   <c:when test="${tvo.drinking == 2}">어쩔 수 없을 때만 마셔요</c:when>
	   <c:when test="${tvo.drinking == 3}">가끔 마셔요</c:when>
	   <c:when test="${tvo.drinking == 4}">어느정도 즐기는 편이에요</c:when>
	   <c:when test="${tvo.drinking == 5}">자주술자리를 가지는 편이에요</c:when>	
	   <c:when test="${tvo.drinking == 6}">상관없어요</c:when>	
</c:choose><br>
흡연 성향 :<c:choose>
	   <c:when test="${tvo.smoking == 1}">흡연자에요</c:when>
	   <c:when test="${tvo.smoking == 2}">담배를 전혀 피우지 않아요</c:when>
	   <c:when test="${tvo.smoking == 3}">상관없어요</c:when>
</c:choose><br>
종교 : <c:choose>
       <c:when test="${tvo.religion == 1}">개신교</c:when>
	   <c:when test="${tvo.religion == 2}">천주교</c:when>
	   <c:when test="${tvo.religion == 3}">불교</c:when>
	   <c:when test="${tvo.religion == 4}">무교</c:when>
	   <c:when test="${tvo.religion == 5}">상관없음</c:when>
</c:choose><br>
키 : <c:choose>
       <c:when test="${tvo.height == 185}">185이상</c:when>
	   <c:when test="${tvo.height == 180}">180~185</c:when>
	   <c:when test="${tvo.height == 175}">175~180</c:when>
	   <c:when test="${tvo.height == 170}">170~175</c:when>
	   <c:when test="${tvo.height == 165}">165~170</c:when>	
	   <c:when test="${tvo.height == 160}">160~165</c:when>	
	   <c:when test="${tvo.height == 150}">150~160</c:when>	
	   <c:when test="${tvo.height == 145}">150이하</c:when>	
</c:choose><br>
선호 데이트 장소 : <c:choose>
	   <c:when test="${tvo.place == 1}">실내</c:when>
	   <c:when test="${tvo.place == 2}">야외</c:when>
</c:choose><br>
선호 연락수단 : <c:choose>
	   <c:when test="${tvo.contact == 1}">카톡</c:when>
	   <c:when test="${tvo.contact == 2}">전화</c:when>
</c:choose><br>
선호 나이 : <c:choose>
	   <c:when test="${tvo.wage == 1}">연상</c:when>
	   <c:when test="${tvo.wage == 2}">연하</c:when>
	   <c:when test="${tvo.wage == 3}">동갑</c:when>
</c:choose><br>
<a href="messageSend2?IDRE=${mvo.mid}">선택</a>
</div>