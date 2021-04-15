package com.spring.project2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TypeDAO {
	
	
		@Autowired
		SqlSessionTemplate my; //SqlSessionTemplate는 mybatis! 
		
		public void insert(TypeVO typeVO) throws Exception {
		      //myBatis에 시길 커예요.
		      my.insert("idealtype.insert", typeVO); //준비해둔 sql문 생성, 전송 역할
		}	
		public TypeVO select(TypeVO typeVO) throws Exception {
			System.out.println(typeVO.getTid());
			return my.selectOne("idealtype.select", typeVO);
			/*
			 * ResultSet rs = ps.executeQuery();
			 * 
			 * MemberVO bag = new MemberVO(); if (rs.next()) {
			 * 
			 * String id2 = rs.getString("id"	); String pw = rs.getString(2); String name =
			 * rs.getString(3); String company = rs.getString(4); String tel =
			 * rs.getString(5); String birth = rs.getString(6); String email =
			 * rs.getString(7); String card = rs.getString(8); String cardnum =
			 * rs.getString(9); bag.setId(id2); bag.setPw(pw); bag.setName(name);
			 * bag.setCompany(company); bag.setTel(tel); bag.setBirth(birth);
			 * bag.setEmail(email); bag.setCard(card); bag.setCardnum(cardnum); } return
			 * bag; 이런식의 코드는 mybatis에서 어떤식으로 이루어지는가?
			 */	
		}
		public TypeVO select2(String tid) throws Exception {
			TypeVO tvo = my.selectOne("idealtype.select2", tid);
			return tvo;
			
		}
		public void update(TypeVO typeVO) throws Exception {
			my.update("idealtype.update", typeVO);
		}
		
		public void delete(TypeVO typeVO) throws Exception{
			my.delete("idealtype.delete", typeVO);
		}
		
			
}
