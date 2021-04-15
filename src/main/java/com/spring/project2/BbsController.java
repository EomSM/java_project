package com.spring.project2;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class BbsController {
	
	@Autowired
	BbsDAO dao;
	
	
	@ResponseBody  //데이터를 보내고 난 후 ajax success문을 수행하기 위해 다시 보냄
	@RequestMapping("bbsinsert")
	public void insert(BbsVO bbsVO){
		dao.create(bbsVO);
	}
	
	@RequestMapping("bbsselect")
	public void select(BbsVO bbsVO, Model model) throws Exception {
		BbsVO bbsvo = dao.read(bbsVO);
		model.addAttribute("bbsone", bbsvo );	
	}
	
	
	@RequestMapping("bbscount")
	@ResponseBody
	public int selectall() throws Exception {
		return dao.readall();
		
	}
	
	@RequestMapping("pagedata")
	@ResponseBody
	public List<BbsVO> selectpage(int page) throws Exception {
		int start = page*8-7;
		int end = page*8;
		ArrayList<Object> pagelist = new ArrayList<Object>();
		pagelist.add(start);
		pagelist.add(end);
		List<BbsVO> list = dao.readpage(pagelist);
		return list;
		
	}
	
	
	@RequestMapping("bbscontent")
	public BbsVO select(BbsVO bbsVO ) throws Exception {
		return dao.read(bbsVO);
	}
	
	@RequestMapping("bbsupdate")   //bbs 게시글 수정시 기존 데이터를 read해주는 컨트롤러
	public BbsVO set1(BbsVO bbsVO ) throws Exception {
		return dao.read(bbsVO);
	}
	
	@RequestMapping("bbsupdate2")   //bbs 게시글 수정 후 데이터 처리
	@ResponseBody
	public void set2(BbsVO bbsVO ) throws Exception {
		System.out.println(bbsVO.bcontent);
		System.out.println(bbsVO.btitle);
		System.out.println(bbsVO.bid);
		dao.update(bbsVO);
	}
	
	@RequestMapping("bbsdelete")
	public String delete(BbsVO bbsVO ) throws Exception {
		dao.delete(bbsVO);
		return "redirect:bbsall.jsp";  //뷰가 아닌 bbsall.jsp로 보내기위해 redirect. 그냥 return 스트링은 view를 보여줌 
	}
}