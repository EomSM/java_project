package com.spring.project2;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController {

	@Autowired
	MemberDAO dao;

	// 회원가입 처리 메서드.
	@RequestMapping("memberinsert")
	public void insert(MemberVO memberVO) throws Exception {
		// date를 string으로 받는 vo에 먼저 넣기
		System.out.println("member insert test===================" + memberVO); // 입력값 확인용 출력
		dao.create(memberVO);
	}

	// 회원탈퇴 처리하는 메서드
	@RequestMapping("memberdelete")
	public void delete(MemberVO memberVO) throws Exception {
		System.out.println("member delete controller test===================" + memberVO.mid); // 입력값 넘어왔는지 확인.
		dao.delete(memberVO);
	}

	// 로그인처리하는 메서드
	@RequestMapping("memberlogin")
	public String login(MemberVO memberVO, HttpSession session) {

		MemberVO vo = dao.login(memberVO);
		if (vo != null) {
			System.out.println("해당 id/pw 있음");
			session.setAttribute("Mid", vo.getMid());
			return "memberlogin";
		} else {
			System.out.println("해당 id/pw 없음");
			return "no";
		}
	}

	// 개인 정보 확인을 위한 메서드. 수정되기 전 정보를 보여준다. 
	@RequestMapping("memberinfo")
	public void getMemberInfo(MemberVO memberVO, Model model) {
		System.out.println("my info controller test===================" + memberVO);
		MemberVO vo = dao.select(memberVO);
		model.addAttribute("vo", vo);
	}

	// 수정하기 위한 정보를 미리 보이는 처리
	@RequestMapping("memberupdate")
	public void updateMemberInfo(MemberVO memberVO, Model model) {
		System.out.println("member update controller test==================="  + memberVO);
		MemberVO vo = dao.select(memberVO);
		model.addAttribute("vo", vo);
	}

	// 수정할 내용이 vo로 넘어오면 수정 처리하는 메서드
	@RequestMapping("memberupdate2")
	public void completeMemberUpdate(MemberVO memberVO) throws Exception {
		dao.update(memberVO);
	}




}
