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
//0407 데이터베이스 이름을 오류 방지를 위해 +s 했을 때에 고쳐주는 부분은 여기만?

public MatchVO login(MatchVO matchVO) {
	return my.selectOne("matchs.login", matchVO); //1 row select
	//mapper파일의 namespace이름. 태그의 id와 일치해야.
	//<mapper namespace="//member//">
	//<select id="login">
}

//public void read(MatchVO matchVO) {
//my.read("matchs.read", matchVO);	
//}
//
//public void update() {
//
//}
//
//public void delete() {
//	
//}
	
	
}
