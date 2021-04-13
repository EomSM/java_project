package com.mega.mvc39;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MatchDAO {

@Autowired
SqlSessionTemplate my;



public void create(MatchVO matchVO) {
	my.insert("matchs.insert", matchVO);
}

public MatchVO login(MatchVO matchVO) {
	return my.selectOne("matchs.login", matchVO); //1 row select
	//mapper파일의 namespace이름. 태그의 id와 일치해야.
	//<mapper namespace="//member//">
	//<select id="login">
}

//입력값: 개인id, 처리: 개인id에 해당하는 row를 가지고 온다., 반환값: row를 넣은 vo
public MatchVO select(MatchVO matchVO) {
	MatchVO vo = my.selectOne("matchs.select", matchVO);
	return vo;
}

public void update(MatchVO matchVO) throws Exception {
	my.update("matchs.update", matchVO);
}
//public void delete() {
//	
//}
	
	
}
