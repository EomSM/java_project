package com.spring.project2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

					//앞페이지에서 넘어온 데이터를 받아주어야 함. => Controller부분!
					//어노테이션 선언 , 주석이라는 뜻으로  자바코드에 의미 부여, 선언하는 역할 . 
@Controller         //이 경우 이 클래스는 컨트롤러로 쓰겠다./ 스프링은 어노테이션 설정만으로 싱글톤 생성 가능
public class TypeController {     //싱글톤 객체은는 인스턴스가 1개만 생성해야하는 경우에 사용되고 한번 생성되어 여러곳에서 쓰일 수 있는 객체
	@Autowired //필요한 의존 객
	TypeDAO dao;
	@RequestMapping("typeInsert")   //requestmapping , jsp에서 controller로 넘길때 일로 넘기세요 하는 url, 처리 끝나고 view의 위치에 대한 지정이기도 함.
	public void insert(TypeVO typeVO) throws Exception {
		dao.insert(typeVO);//위에서 
		System.out.println("호출!");
		System.out.println("컨트롤메서드");
		System.out.println("받은 id: " + typeVO.getTid());
		System.out.println("받은 음주값: " + typeVO.getDrinking());
		System.out.println("받은 흡연값: " + typeVO.getSmoking());
		System.out.println("받은 종교값: " + typeVO.getReligion());
		System.out.println("받은 키: " + typeVO.getHeight());
		System.out.println("받은 장소값: " + typeVO.getPlace());
		System.out.println("받은 연락값: " + typeVO.getContact());
		System.out.println("받은 나이값: " + typeVO.getWage());
		
	}
	@RequestMapping("typeSelect")
	public void select(TypeVO typeVO, Model model) throws Exception {
		
		TypeVO vo2 = dao.select(typeVO);
		
		System.out.println("받은 id: " + vo2.getTid());
		System.out.println("받은 원하는음주: " + vo2.getDrinking() + "==========================");
		System.out.println("받은 원하는흡연: " + vo2.getSmoking());
		System.out.println("받은 종교: " + vo2.getReligion());
		System.out.println("받은 키: " + vo2.getHeight());
		System.out.println("받은 장소: " + vo2.getPlace());
		System.out.println("받은 연락수단: " + vo2.getContact());
		System.out.println("받은 원하는나이: " + vo2.getWage());
		
		//views까지 검색된 정보를 가지고 가야한다.
		//model속성으로만 등록하면됨
		model.addAttribute("vo2", vo2);
		
	}
	
	@RequestMapping("typeUpdate")
	public void update(TypeVO typeVO) throws Exception {
		dao.update(typeVO);
	}
	@RequestMapping("typeDelete")
	public void delete(TypeVO bag) throws Exception {
		dao.delete(bag);
	}
	
}
