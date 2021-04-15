package com.spring.project2;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChartController {
	
	@Autowired // 싱글톤으로 만든 주소를 넣기!(주입, injection)
	ChartDAO dao;

	@RequestMapping("chart5") // 연결할 주소
	@ResponseBody // views를 없애기 위한 ResponseBody
	public HashMap<String, Integer> select5(ChartVO chartVO) throws Exception {
		System.out.println(chartVO);

		List<ChartVO> list5 = dao.select5(chartVO); // 리스트 사용
		System.out.println(list5.size()); // 갯수 세보기
		
		int count1 = 0; // 실내 인원 수
		int count2 = 0; // 야외 인원 수
		
		for (int i = 0; i < list5.size(); i++) {
			if (list5.get(i).getPLACE() == 1) {
				count1++; // 가져온 값이 1이면 카운트1 증가
			} else {
				count2++; // 1이 아니면 카운트2 증가
			}
		}
		
		HashMap<String, Integer> map = new HashMap<String,Integer>(); // 키-값 지정을 위해 HashMap 사용
		
		map.put("실내", count1); // 실내: count1 & 야외: count2 키-값 지정
		map.put("야외", count2);
		
		return map; // 받아온 값을 사용하기 위해 리턴 사용
	}
	
	@RequestMapping("chart6") // 연결할 주소
	@ResponseBody // views를 없애기 위한 ResponseBody
	public HashMap<String, Integer> select6(ChartVO chartVO) throws Exception {
		System.out.println(chartVO);

		List<ChartVO> list6 = dao.select6(chartVO); // 리스트 사용
		System.out.println(list6.size()); // 갯수 세보기
		
		int countM1 = 0; // 연상을 선택한 남성
		int countM2 = 0; // 연하를 선택한 남성
		int countM3 = 0; // 동갑을 선택한 남성
		
		int countF1 = 0; // 연상을 선택한 여성
		int countF2 = 0; // 연하를 선택한 여성
		int countF3 = 0; // 동갑을 선택한 여성
		
		System.out.println(list6.get(0).getWAGE()); // Age의 첫번째 데이터 가져오는지 확인
		System.out.println(list6.get(0).getGENDER()); // 성별의 첫번째 데이터 가져오는지 확인
		
		for (int i = 0; i < list6.size(); i++) { // for문과 이중if문으로 1000명의 데이터 분류
			if (list6.get(i).getWAGE() == 1) { // 우선 선호 나이로 구분
				if (list6.get(i).getGENDER().equals("M")) { // 그 중 성별 구분
					countM1++;
				}else {
					countF1++;
				}
			}else if(list6.get(i).getWAGE() == 2){
				if (list6.get(i).getGENDER().equals("M")) {
					countM2++;
				}else {
					countF2++;
				}
			}else{
				if (list6.get(i).getGENDER().equals("M")) {
					countM3++;
				}else {
					countF3++;
				}
			}
		}
		
		HashMap<String, Integer> map = new HashMap<String,Integer>(); // 키-값 지정을 위해 HashMap 사용 
		
		map.put("maleOlder", countM1);
		map.put("maleYounger", countM2);
		map.put("maleSame", countM3);
		
		map.put("femaleOlder", countF1);
		map.put("femaleYounger", countF2);
		map.put("femaleSame", countF3);
		
		return map; // 받아온 값을 사용하기 위해 리턴 사용
	}
	

}