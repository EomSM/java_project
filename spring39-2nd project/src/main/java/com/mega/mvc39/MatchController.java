package com.mega.mvc39;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MatchController {

	@Autowired
	MatchDAO dao;
	
	@RequestMapping("matchinsert.mega")
	public void insert(MatchVO matchVO) throws Exception{
		System.out.println("test===================" + matchVO);
		dao.create(matchVO);
	}
	
	@RequestMapping("login")
	public String login(MatchVO matchVO, HttpSession session) {
		// 넘어갈 페이지가 다르다는 점을 Spring에 알려줘야 함 : 반환값 있게: x void

		MatchVO vo = dao.login(matchVO);
		if (vo!= null) {
			System.out.println("해당 id/pw 있음");
			session.setAttribute("userId",vo.getMid());
			return "ok";
		} else {
			System.out.println("해당 id/pw 없음");
			return "no";
		}

	}
}
