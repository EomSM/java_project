package com.spring.project2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MessageController {

	@Autowired // 싱글톤으로 만든 주소를 넣기!(주입, injection)
	MessageDAO dao;

	@RequestMapping("messageSend") // 쪽지 보내기
	public void insert(MessageVO messageVO) throws Exception {
		
		System.out.println(messageVO); // VO 확인해보기

		dao.create(messageVO); // insert기능 사용

	}
	@RequestMapping("messageSend2") // 수신자 read
	public void read(MessageVO messageVO, Model model) throws Exception {
		
		System.out.println(messageVO); // VO 확인해보기
		model.addAttribute("vo", messageVO) ;
		
	}
	
	@RequestMapping("messageDelete") // 쪽지 삭제
	public String delete(MessageVO messageVO) throws Exception {
		
		System.out.println(messageVO);

		dao.delete(messageVO); // delete기능 사용
		
		// redirect를 사용하여 서버가 클라이언트에게 재요청하게 함.
		return "redirect:messageListSend.jsp";
	}
	
	@RequestMapping("messageSearch") // 보낸 쪽지 - 수신자 검색
	public void select3(MessageVO messageVO, Model model) throws Exception {
		
		System.out.println(messageVO); // VO 확인해보기

		List<MessageVO> list3 = dao.select3(messageVO); // select기능을 사용한 뒤 list에 쌓기
		System.out.println(list3.size()); // 리스트 갯수 세보기
		
		// 검색결과를 담기위해 views까지 데이터를 넘김.
		model.addAttribute("messageSearch", list3);
	}
	
	@RequestMapping("listSend") // 보낸 쪽지함
	@ResponseBody // views를 없애기 위한 ResponseBody
	public  List<MessageVO> select(MessageVO messageVO) throws Exception {
		
		System.out.println(messageVO);
		
		List<MessageVO> list = dao.select(messageVO); // select기능을 사용한 뒤 list에 쌓기
		System.out.println(list.size()); // 리스트 갯수 세보기
		
		return list; // 받아온 값을 사용하기 위해 리턴 사용
	}

	@RequestMapping("messageListReceive") // 받은 쪽지함
	public void select2(MessageVO messageVO, Model model) throws Exception {
		System.out.println(messageVO);

		List<MessageVO> list2 = dao.select2(messageVO); // select기능을 사용한 뒤 list에 쌓기
		System.out.println(list2.size()); // 리스트 갯수 세보기
		
		// 검색결과를 담기위해 views까지 데이터를 넘김.
		model.addAttribute("list2", list2);
	}

}
