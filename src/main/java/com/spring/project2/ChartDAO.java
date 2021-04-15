package com.spring.project2;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChartDAO {
	
	// myBatis싱글톤 주소를 주입!
	@Autowired
	SqlSessionTemplate my;
	
	public List<ChartVO> select5(ChartVO chartVO) {
		
		// myBatis에게 시키기
		return my.selectList("chart.list5", chartVO);

	}
	
	public List<ChartVO> select6(ChartVO chartVO) {

		return my.selectList("chart.list6", chartVO);

	}

}
