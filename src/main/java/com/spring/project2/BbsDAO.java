package com.spring.project2;


import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class BbsDAO {
	
	@Autowired
	SqlSessionTemplate my;
	
	
	public void create(BbsVO bbsVO){
		 my.insert("bbs.insert", bbsVO);
	}
	
	
	public BbsVO read(BbsVO bbsVO) {
		return my.selectOne("bbs.select", bbsVO);
	}
	
	
	public int readall() {
		return my.selectOne("bbs.selectall");
	}
	
	public List<BbsVO> readpage(ArrayList<Object> pagelist) {
		List<BbsVO> list = my.selectList("bbs.selectpage", pagelist);
		return list;
	}
	
	public void update(BbsVO bbsVO) {
		my.update("bbs.update", bbsVO);
	}
	
	public void delete(BbsVO bbsVO) {
		my.delete("bbs.delete", bbsVO);
	}
}