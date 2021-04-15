package com.spring.project2;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportDAO {

	@Autowired
	SqlSessionTemplate my;
	
	public int tcheck(MessageVO messageVO) {
		int result = my.selectOne("report.selectcount", messageVO);
		System.out.println(result);
		return result;
	}
	
	public void create(ReportVO reportVO) {
		my.insert("report.insert", reportVO);
	}
	
	public int rcount() {
		int result = my.selectOne("report.selectRcount");
		return result;
	}
	
	public List<ReportVO> read(ArrayList<Object> pagelist) {
		List<ReportVO> list= my.selectList("report.select",pagelist);
		return list;
	}
}
