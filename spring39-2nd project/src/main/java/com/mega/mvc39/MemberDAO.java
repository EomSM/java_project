package com.mega.mvc39;

import javax.servlet.http.HttpServletResponse;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberDAO {
//마이바티스 싱글톤을 쓰기 위해 주소를 주입할 것임.

	@Autowired
	SqlSessionTemplate my;
	//mybatis 스프링 연동. 세션을 구현/대체

	public void create(MemberVO memberVO) throws Exception {
		my.insert("member2.insert", memberVO);
	}

	public void delete(MemberVO memberVO) throws Exception {
		System.out.println("dao test===========" + memberVO);
		my.delete("member2.delete", memberVO);
	}

	public MemberVO login(MemberVO memberVO) {
		return my.selectOne("member2.login", memberVO);
	}

	public void logout(HttpServletResponse response) {
		my.selectOne("member2.logout", response);
	}

	public void read(MemberVO memberVO) {
		my.selectOne("member2.select", memberVO);
	}
	
	public void update(MemberVO memberVO) throws Exception {
		my.update("member2.update", memberVO);
	}



}