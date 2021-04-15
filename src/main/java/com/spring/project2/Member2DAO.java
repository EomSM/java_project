package com.spring.project2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component //MemberDAO dao = new MemberDAO(); 한개만 객체 생성!
public class Member2DAO {
   
   //myBatis싱글톤 주소를 주입!!
   @Autowired
   SqlSessionTemplate my;
   	
       public List<Member2VO> all() {
    	   return my.selectList("member2.all");
       }
	
   	   public List<Member2VO> select1(List<Member2VO> vo1) throws Exception{
   		   return my.selectList("member2.select1", vo1);
   		   
   	   }
   	   public List<Member2VO> select2(char gender) throws Exception {
   		   return my.selectOne("member2.select2", gender);
   	   }
   	   public Member2VO select3(String mid) throws Exception {
   		   
   		   Member2VO vo = my.selectOne("member2.select3", mid);
   		   return vo;
   	   }
   	   public Member2VO select4(String mid) {
   		   return my.selectOne("member2.select1", mid);
   	   }
   
   	   
}
