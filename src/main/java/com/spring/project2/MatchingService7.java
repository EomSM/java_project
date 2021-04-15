package com.spring.project2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component //싱글톤
public class MatchingService7 {
	@Autowired
	TypeDAO tdao;  //싱글톤호출
	@Autowired	
	Member2DAO mdao;	//싱글톤
	@Autowired
	MatchDAO mcdao;	//싱글톤
	Double point = 0.0;  //매칭점수를 준비해둔다.


	public HashMap<String, Double> MbtiMatchInfp1(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("infp")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("enfj");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드
	public HashMap<String, Double> MbtiMatchInfp2(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("infp")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("entj");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(tvo);
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					System.out.println(tvo.getPlace());
					System.out.println(list.get(i).getPlace());
					System.out.println(tvo.getPlace() == list.get(i).getPlace());
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드	
	public HashMap<String, Double> MbtiMatchInfj1(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("infj")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("enfp");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드
	public HashMap<String, Double> MbtiMatchInfj2(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("infj")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("entp");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드	
	public HashMap<String, Double> MbtiMatchEnfp1(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("enfp")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("infj");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드
	public HashMap<String, Double> MbtiMatchEnfp2(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("enfp")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("intj");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드	
	public HashMap<String, Double> MbtiMatchEnfj1(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("enfj")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("infp");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드
	public HashMap<String, Double> MbtiMatchEnfj2(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("enfj")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("entj");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드	
	public HashMap<String, Double> MbtiMatchIntj1(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("intj")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("enfp");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드
	public HashMap<String, Double> MbtiMatchIntj2(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("intj")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("entp");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드	
	public HashMap<String, Double> MbtiMatchEntj1(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("entj")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("infp");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드
	public HashMap<String, Double> MbtiMatchEntj2(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("intp")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("entj");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드	
	public HashMap<String, Double> MbtiMatchIntp1(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("intp")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("entj");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드
	public HashMap<String, Double> MbtiMatchIntp2(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("intp")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("estj");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드	
	public HashMap<String, Double> MbtiMatchIsfp1(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("isfp")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("enfj");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드
	public HashMap<String, Double> MbtiMatchIsfp2(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("isfp")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("esfj");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드		
	public HashMap<String, Double> MbtiMatchIsfp3(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("isfp")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("estj");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드			
	public HashMap<String, Double> MbtiMatchEsfp1(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("esfp")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("isfj");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드
	public HashMap<String, Double> MbtiMatchEsfp2(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("esfp")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("istj");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드		
	public HashMap<String, Double> MbtiMatchIstp1(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("istp")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("esfj");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드
	public HashMap<String, Double> MbtiMatchIstp2(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("istp")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("estj");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드		
	public HashMap<String, Double> MbtiMatchEstp1(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo);//데이터 잘 넘어왔는지 확인.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("estp")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("isfj");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드
	public HashMap<String, Double> MbtiMatchEstp2(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("estp")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("istj");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드		
	public HashMap<String, Double> MbtiMatchIsfj1(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("isfj")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("esfp");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드
	public HashMap<String, Double> MbtiMatchIsfj2(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("isfj")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("estp");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드		
	public HashMap<String, Double> MbtiMatchEsfj1(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("esfj")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("isfp");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드
	public HashMap<String, Double> MbtiMatchEsfj2(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("esfj")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("istp");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드		
	public HashMap<String, Double> MbtiMatchIstj1(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("istj")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("esfp");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드
	public HashMap<String, Double> MbtiMatchIstj2(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("istj")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("estp");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드		
	public HashMap<String, Double> MbtiMatchEstj1(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("estj")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("intp");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드
	public HashMap<String, Double> MbtiMatchEstj2(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("estj")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("isfp");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드		
	public HashMap<String, Double> MbtiMatchEstj3(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("estj")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("istp");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드		
	public HashMap<String, Double> MbtiMatchEntp1(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("entp")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("infj");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드
	public HashMap<String, Double> MbtiMatchEntp2(String mid) throws Exception {
		//반환값 HashMap 준비해둔다. 회원id=매칭점수 방식으로 데이터를 넣을 것이므롤 <String, Integer>형식으로 한다.   
		//컨트롤러에 넘어가는 데이터는 mid이므로 여기서 변수를 미리 입력값으로 지정해 받을 준비를 한다. 
		HashMap<String, Double> map = new HashMap<String, Double>(); //반환받을 해쉬맵을 생성한다.
		Member2VO vo = mdao.select4(mid);  //member2테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 vo에 넣는다. 
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		System.out.println(vo.getGender()); //vo 데이터 잘 넘어왔는지 확인
		System.out.println(mvo.getMbti()); //mvo로 데이터 잘 넘어왔는지 확인
		String tid = mid;          //idealType테이블의 경우 id의 칼럼명이 mid가 아닌 tid로 지정되어 있기에  tid로 지정한다.
		TypeVO tvo = tdao.select2(tid); // idealType테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 tvo에 넣는다.
		System.out.println(tvo.getHeight());//데이터 잘 넘어왔는지 확인.
		if (mvo.getMbti().equals("entp")) { //회원의 mbti가 infp일때 조건문을 실행한다. 여기서 나중에 else if로 다른 mbti일때도 실행할 수 있게 해준다.
			List<MatchVO> list = mcdao.select2("intj");//mbti 매칭에서 제일 중요한 것은 mbti이므로 잘맞는건 enfj와 entp이다.
			//mbti를 입력하고 그것에 맞는 vo를 list에 추가하는 방식으로 mapper와 dao를 구성한다(selectList)
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) { //mbti가 enfj인 회원들의 데이터를 순서대로 가져오기 위해 for문 사용
				System.out.println(list.get(i));//제대로 가져왔는지 확인
				Member2VO vo2 = mdao.select3(list.get(i).getMid()); //member테이블에서 id를 입력해 id가 있는 vo를 찾아오는 sql문 사용
				//>dao호출(selectOne)
				//for문이 돌아갈때마다 다른 회원의 vo를 호출
				System.out.println(vo2);//vo 잘 가져왔는지 확인
				System.out.println("나의 성 : " + vo.getGender() + " ,너의 성:" + vo2.getGender()); //성별 비교 전에 잘 가져왔는지 확인
				if (vo.getGender() == vo2.getGender()) { 
					point = 0.0;   //두 회원의 성별이 같으면 매칭점수를 0으로 한다. >>매칭 안 되게
					System.out.println("성별 같을 떄: " + point);
				} else { //두 회원의 성별이 다를 때 위의 mbti로 인한 점수를 바탕으로 감점이나 가산점을 주는 알고리즘 부여
					point = 90.0; // mbti궁합이 좋고, 서로의 성별이 다를 때 점수를 90점 부여한다.
					if (tvo.getDrinking() != 6) { //6은 상대방의 음주성향 상관없음이므로 감점x/ 6이 아닐때 감점 가능
						int a = Math.abs(tvo.getDrinking() - list.get(i).getDrink()); //내가 원하는 성향과 상대방의 성향을 비교한다.
						point = point - 3*a;  //Math.abs()함수로 위의 차이를 절대값으로 가져오고 그것에 비례해서 감점
					}
					System.out.println("성별 다르고 음주 연산: " + point); 
					if (tvo.getSmoking() == 3) { //상대방이 흡연하든 말든 상관 없을 경우 값이 3이고, 이  경우 감점x

					}else if (tvo.getSmoking() != list.get(i).getSmoke()) {
						point = point - 10; // 흡연여부가 상관없지 않고, 상대방의 흡연여부와 내가 원하는 바가 다를 경우 10점 감점  
					}
					System.out.println("흡연 연산: " +point);
					if(tvo.getReligion() == 5) {  //상대방이 어떤 종교를 갖든  상관 없을 경우 값이 5이고, 이  경우 감점x

					}else if (tvo.getReligion() != list.get(i).getBelieve()) {
						point = point - 5; // 상대방의 종교와 내 이상형 정보가 다를 때 감점 
					}
					System.out.println("종교연산: " + point);
					if(list.get(i).getTall() >= 185) { //상대방의 키가 185 이상일떄는
						if (tvo.getHeight() == 185) { // 나의 이상형 키가 185이상일때(값 185)만 가산점 
							point = point + 3.01; //소수점은 우선순위 가산점 같은 +3가산점 중에서도 .01이 하, .1이 상 중복을 최대한 피하기 위한 가산점 나중에 캐슬링해줄 것
						}
					}else if (list.get(i).getTall() >= 180) {  //상대방의 키가 180~185일때 
						if (tvo.getHeight() == 180) {     //나의 이상형 키가 180~185(값 180)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 175) { //상대방의 키가 175~180일때 
						if (tvo.getHeight() == 175) {		//나의 이상형 키가 175~180(값 175)만 가산점
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 170) {  //위와 같은 구조
						if (tvo.getHeight() == 170) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 165) {
						if (tvo.getHeight() == 165) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 160) {
						if (tvo.getHeight() == 160) {
							point = point + 3.01;
						}
					}else if (list.get(i).getTall() >= 150) {  
						if (tvo.getHeight() == 150) {
							point = point + 3.01;
						}
					}else {									//상대방의 키가 150이 안될 때
						if (tvo.getHeight() == 145) {      //내가 원하는 키가 150 이하일때만 가산점
							point = point + 3.01;
						}
					}
					System.out.println("키연산: " + point);
					if (tvo.getPlace() == list.get(i).getPlace()) {  //나의 선호 데이트 장소와 상대방의 선호 데이트 장소가 같을 때 가산점
						point = point + 2.2;  // 2점 가산점 주는 부분 중 추가 가산을 소수점으로 표현 .2가 상/ .02가 하

					}
					System.out.println("선호 장소 연산: " + point);
					if (tvo.getContact() == list.get(i).getContact()) {//나의 선호 연락 방식과 상대방의 선호 연락 방식이 같을 때 가산점
						point = point + 2.02;
					}
					System.out.println("선호 연락방식 연산: " + point);
					if(tvo.getWage() == 1) { //내가 원하는 사람이 연상일때  내가 태어난 연도가 상대방이 태어난 연도보다  클때만 가산점
						if (vo.getBirthdate() > vo2.getBirthdate()) {
							point = point + 2.2; 
						}
					}else if (tvo.getWage() == 2) {  //내가 원하는 사람이 연하일때  내가 태어난 연도가 상대방이 태어난 연도보다  작을때만 가산점
						if (vo.getBirthdate() < vo2.getBirthdate()) {
							point = point + 2.2;
						}
					}else { //내가 원하는 사람이 동갑일때  내가 태어난 연도가 상대방이 태어난 연도랑 같을 때만 가산점
						if (vo.getBirthdate() == vo2.getBirthdate()) {
							point = point + 3.1;
						}
					}
					System.out.println("나이궁합연산: " + point);
				}//성별
				System.out.println("결과 연산: " + point);
				map.put(list.get(i).getMid(), point);//위에서 계산한 점수와 그 점수가 나오는 상대의 아이디를 HashMap에 넣는다.
			}//for
		}//myMbti
		return map; // 반환값은 위에서 준비한 HashMap 변수
	}//메서드			
	public HashMap<String, Double> RecN (String mid) throws Exception {
		HashMap<String, Double> map5 = new HashMap<String, Double>();
		MatchVO mvo = mcdao.select1(mid);	//matchs테이블에서 id를 입력해 그 아이디가 있는 VO를 전부 가져와 mvo에 넣는다.
		HashMap<String, Double> map1 = new HashMap<String, Double>();
		HashMap<String, Double> map2 = new HashMap<String, Double>(); //미리 map생성
		HashMap<String, Double> map3 = new HashMap<String, Double>();//map1,2 받아올 map 생성
		if (mvo.getMbti().equals("infp")) {//나의 mbti의 유형과 궁합이 맞는 사람들의 아이디와 점수를 데려오기 위해 위에서 만든 메소드 호출
			map1 = MbtiMatchInfp1(mid);    //mbti유형이 16개이므로 if절 16개
			map2 = MbtiMatchInfp2(mid);
		}else if(mvo.getMbti().equals("infj")) {
			map1 = MbtiMatchInfj1(mid);
			map2 = MbtiMatchInfj2(mid);

		}else if(mvo.getMbti().equals("intp")) {
			map1 = MbtiMatchIntp1(mid);
			map2 = MbtiMatchIntp2(mid);

		}else if(mvo.getMbti().equals("intj")) {
			map1 = MbtiMatchIntj1(mid);
			map2 = MbtiMatchIntj2(mid);

		}else if(mvo.getMbti().equals("enfj")) {
			map1 = MbtiMatchEnfj1(mid);
			map2 = MbtiMatchEnfj2(mid);

		}else if(mvo.getMbti().equals("enfp")) {
			map1 = MbtiMatchEnfp1(mid);
			map2 = MbtiMatchEnfp2(mid);

		}else if(mvo.getMbti().equals("esfj")) {
			map1 = MbtiMatchEsfj1(mid);
			map2 = MbtiMatchEsfj2(mid);

		}else if(mvo.getMbti().equals("esfp")) {
			map1 = MbtiMatchEsfp1(mid);
			map2 = MbtiMatchEsfp2(mid);

		}else if(mvo.getMbti().equals("estj")) {
			map1 = MbtiMatchEstj1(mid);          //이 경우와 isfp의 경우 최고궁합 유형이 하나 더 있으므로 
			map2 = MbtiMatchEstj2(mid);			//이때만 새로 생성하고 메소드를 호출하여 데이터를 받아온다.
			HashMap<String, Double> map4 = new HashMap<String, Double>();
			map4 = MbtiMatchEstj3(mid);          
			map3.putAll(map4);                 //이런 경우가 16가지 경우의 수 중 2가지 밖에 없으므로
			//cpu처리를 줄이기 위해  두 유형에서만 따로 처리해준다.
		}else if(mvo.getMbti().equals("estp")) {
			map1 = MbtiMatchEstp1(mid);
			map2 = MbtiMatchEstp2(mid);
		}else if(mvo.getMbti().equals("entj")) {
			map1 = MbtiMatchEntj1(mid);
			map2 = MbtiMatchEntj2(mid);
		}else if(mvo.getMbti().equals("entp")) {
			map1 = MbtiMatchEntp1(mid);
			map2 = MbtiMatchEntp2(mid);
		}else if(mvo.getMbti().equals("isfp")) {
			map1 = MbtiMatchIsfp1(mid);
			map2 = MbtiMatchIsfp2(mid);
			HashMap<String, Double> map4 = new HashMap<String, Double>();
			map4 = MbtiMatchEstj3(mid);
			map3.putAll(map4);
		}else if(mvo.getMbti().equals("isfj")) {
			map1 = MbtiMatchIsfj1(mid);
			map2 = MbtiMatchIsfj2(mid);
		}else if(mvo.getMbti().equals("istj")) {
			map1 = MbtiMatchIstj1(mid);
			map2 = MbtiMatchIstj2(mid);
		}else if(mvo.getMbti().equals("istp")) {
			map1 = MbtiMatchIstp1(mid);
			map2 = MbtiMatchIstp2(mid);
		}
		map3.putAll(map1);   //위에서 만들어 놓은 map3에 조건문을 거쳐 나온 map1, map2를 모두 넣어준다.
		map3.putAll(map2);	 //if안에서 코드 작성시 모든 if절에 이 코드를 추가해야 하므로 여기서 추가해준다.
		System.out.println("서비스 메서드1 결과: " + map3);//확인용
	
		/*
		 * Set<Entry<String, Double>> set = map3.entrySet(); Iterator<Entry<String,
		 * Double>> it = set.iterator();
		 * 
		 * // HashMap에 포함된 key, value 값을 호출 한다. while (it.hasNext()) {
		 * HashMap.Entry<String, Double> e = (HashMap.Entry<String, Double>)it.next();
		 * System.out.println("이름 : " + e.getKey() + ", 점수 : " + e.getValue()); }
		 */
		ArrayList<Double> list = new ArrayList<>(); //점수를 순서대로 정렬할 ArrayList준비
		Double[] list2 = new Double[map3.size()]; 
		
		System.out.println(list2.length);
		list.addAll(map3.values());              
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println("리스트: " + list.get(i));
		}
		Collections.sort(list);
		for (int i = 0; i < list.size(); i++) {
			System.out.println("리스트2: " + list.get(i));
		}	
		int listSize = list.size();
		if(list.size() == 3) {
			System.out.println("1:" + list.get(listSize - 1) + ", 2:" + list.get(listSize - 2));
			System.out.println(list.get(listSize - 1) == list.get(listSize - 2));
			System.out.println(list.get(listSize - 1).compareTo(list.get(listSize - 2)) == 0);
			if (list.get(listSize - 1).compareTo(list.get(listSize - 2)) == 0) { 
				//double은 == 연산자 안됨 ! .compareTo(다른 double값) == 0으로 비교해야함!!!!! 
				//compareTo함수는 비교대상이 동일한 값일 때 : 0, 비교 대상이 작은 경우 : -1	, 비교 대상이 큰 경우 : 1 리턴	
				map3.replace(getKey(map3, list.get(listSize - 2)), list.get(listSize - 1) - 0.001);
				list.set(listSize - 2, list.get(listSize - 1) - 0.001);
				
				if (list.get(listSize - 1).compareTo(list.get(listSize - 3)) == 0) {
					map3.replace(getKey(map3, list.get(listSize - 3)), list.get(listSize - 1)- 0.002);
					list.set(listSize - 3, list.get(listSize - 1)- 0.002);
					
				}
			}else if(list.get(listSize - 2).compareTo(list.get(listSize - 3)) == 0) {
				map3.replace(getKey(map3, list.get(listSize - 3)), list.get(listSize - 1) - 0.02);
				list.set(listSize - 3, list.get(listSize - 1)- 0.02);
			}
			
			System.out.println("1번: " + list.get(listSize - 1) + ", 2번: " + list.get(listSize - 2) + ", 3번: " + list.get(listSize - 3));
			String no1 = getKey(map3, list.get(listSize - 1));
			String no2 = getKey(map3, list.get(listSize - 2));
			String no3 = getKey(map3, list.get(listSize - 3));
			System.out.println(no1 + "," + no2 + "," + no3);
			map5.put(no1, list.get(listSize - 1));
			map5.put(no2, list.get(listSize - 2));
			map5.put(no3, list.get(listSize - 3));
			/*
			 * while (no1.equals(no2)) { for (int i = 3; i < list.size() - i - 1; i++) { no2
			 * = getKey(map3, list.get(listSize - i)); no3 = getKey(map3, list.get(listSize
			 * - i - 1)); } }
			 */
			/*
			 * if (no1.equals(no2)) { for (int i = 3; i < list.size() - i - 1; i++) { no2 =
			 * getKey(map3, list.get(listSize - i)); no3 = getKey(map3, list.get(listSize -
			 * i - 1)); } if (no2.eq) {
			 * 
			 * } }else if (no1.) {
			 * 
			 * }
			 */
	
			map5.put(no1, list.get(listSize - 1));
			map5.put(no2, list.get(listSize - 2));
			map5.put(no3, list.get(listSize - 3));
			} else if (list.size() == 2) {
				System.out.println("1:" + list.get(listSize - 1) + ", 2:" + list.get(listSize - 2));
				System.out.println(list.get(listSize - 1) == list.get(listSize - 2));
				System.out.println(list.get(listSize - 1).compareTo(list.get(listSize - 2)) == 0);
				if (list.get(listSize - 1).compareTo(list.get(listSize - 2)) == 0) { 
					//double은 == 연산자 안됨 ! .compareTo(다른 double값) == 0으로 비교해야함!!!!! 
					//compareTo함수는 비교대상이 동일한 값일 때 : 0, 비교 대상이 작은 경우 : -1	, 비교 대상이 큰 경우 : 1 리턴	
					map3.replace(getKey(map3, list.get(listSize - 2)), list.get(listSize - 1) - 0.001);
					list.set(listSize - 2, list.get(listSize - 1) - 0.001);
					
					
				}
				
				System.out.println("1번: " + list.get(listSize - 1) + ", 2번: " + list.get(listSize - 2));
				String no1 = getKey(map3, list.get(listSize - 1));
				String no2 = getKey(map3, list.get(listSize - 2));
				
				System.out.println(no1 + "," + no2);
				map5.put(no1, list.get(listSize - 1));
				map5.put(no2, list.get(listSize - 2));
				
			} else if (list.size() == 1){
				System.out.println("1:" + list.get(listSize - 1));
				
				
			
				
				System.out.println("1번: " + list.get(listSize - 1));
				String no1 = getKey(map3, list.get(listSize - 1));
				
				
				System.out.println(no1);
				map5.put(no1, list.get(listSize - 1));
				
			}
		return map5;

	}
	public static String getKey(HashMap<String, Double> m, double value) {

		/*for (int i = 0; i < m.keySet().size(); i++) {

			if (m.get(m.keySet()) == value) {

			}
		}*/

		for(String o: m.keySet()) {

			if(m.get(o) == value) { 

				return o; 
			} 
		}

		return null;
	}
	
}
