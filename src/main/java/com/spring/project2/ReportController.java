package com.spring.project2;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReportController {

	@Autowired
	ReportDAO dao;
	
	@RequestMapping("targetchk")
	@ResponseBody
	public int targetchk(MessageVO messageVO) {
		int count = dao.tcheck(messageVO);
		System.out.println(count);
		return count;
	}
	
	@RequestMapping("reportinsert")
	@ResponseBody
	public void insert(ReportVO reportVO) {
		dao.create(reportVO);
	}
	
	@RequestMapping("reportcount")
	@ResponseBody
	public int reportcount() {
		int result = dao.rcount();
		return result;
	}
	
	@RequestMapping("reportpage")
	@ResponseBody
	public List<ReportVO> reportpage(String page) {
		int page2 = Integer.parseInt(page);
		int start = page2*6-5;
		int end = page2*6;
		ArrayList<Object> pagelist = new ArrayList<Object>();
		pagelist.add(start);
		pagelist.add(end);
		List<ReportVO> list = dao.read(pagelist);
		return list;
	}
}
