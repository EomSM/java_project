package com.mega.mvc39;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MatchController {

	@Autowired
	MatchDAO dao;

	@RequestMapping("login")
	public String login(MatchVO matchVO, HttpSession session) {

		MatchVO vo = dao.login(matchVO);
		if (vo != null) {
			System.out.println("해당 id/pw 있음");
			session.setAttribute("userId", vo.getMid());
			return "ok";
		} else {
			System.out.println("해당 id/pw 없음");
			return "no";
		}

	}
	
	// MATCHS 테이블에 입력
	@RequestMapping("matchinsert") 
	public void insert(MatchVO matchVO) throws Exception {
		System.out.println(matchVO.getMbti() + "=====================**");
		System.out.println("match insert test===================" + matchVO);
		dao.create(matchVO);
	}

	// MATCHS 업데이트를 위한 정보 확인
	@RequestMapping("match_info")
	public void getMatchInfo(MatchVO matchVO, Model model) {
		MatchVO vo = dao.select(matchVO);
		model.addAttribute("vo", vo);
	}

	// MATCHS 수정하기 위한 정보를 미리 보이는 처리
	@RequestMapping("matchupdate")
	public void updateMatchInfo(MatchVO matchVO, Model model) {
		System.out.println("match update controller test==========="  + matchVO);
		MatchVO vo = dao.select(matchVO);
		System.out.println(vo);
		model.addAttribute("vo", vo);
	}


	// MATCHS 업데이트 수정 처리
	@RequestMapping("matchupdate2")
	public void updateMatch(MatchVO matchVO) throws Exception {
		dao.update(matchVO);
	}

}
