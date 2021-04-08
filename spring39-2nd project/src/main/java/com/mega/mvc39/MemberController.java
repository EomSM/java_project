package com.mega.mvc39;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController {

	@Autowired
	MemberDAO dao;

	@RequestMapping("memberinsert.mega")
	public void insert(MemberVO vo) throws Exception {
		// date를 string으로 받는 vo에 먼저 넣기
		System.out.println("test----------------------");
		System.out.println("test===================" + vo);
		dao.create(vo);
	}

	@RequestMapping("memberdelete")
	public void delete(MemberVO memberVO) throws Exception {
		System.out.println("controller test===================" + memberVO.mid);
		dao.delete(memberVO);
	}

	@RequestMapping("memberlogin")
	public String login(MemberVO memberVO, HttpSession session) {
		// 넘어갈 페이지가 다르다는 점을 Spring에 알려줘야 함 : 반환값 있게: x void

		MemberVO vo = dao.login(memberVO);
		if (vo != null) {
			System.out.println("해당 id/pw 있음");
			session.setAttribute("Mid", vo.getMid());
			return "memberlogin";
			// return "redirect:main.jsp";
		} else {
			System.out.println("해당 id/pw 없음");
			return "no";
		}
	}

	@RequestMapping("my_info") // 개인정보내용확인
	public void select(MemberVO memberVO) {
		System.out.println("controller test===================" + memberVO);
		dao.read(memberVO);
	}

//	@RequestMapping("memberfind") // 아이디로 검색해서 가입시 입력한 이메일로 재설정 메일 보내기
//	public void selectEmail(MemberVO memberVO) {
//		System.out.println("controller test===================" + memberVO.mid);
//		dao.read(memberVO.email);
//	}

	@RequestMapping("memberupdate")
	public void update(MemberVO memberVO) throws Exception {
		dao.update(memberVO);
	}

}
