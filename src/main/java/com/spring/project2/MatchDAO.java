package com.spring.project2;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MatchDAO {

@Autowired
SqlSessionTemplate my;

//선민님 코드(create, login, select,update)

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

//광희님 코드(select1, select2)
public MatchVO select1(String mid) {
	return my.selectOne("matchs.select1", mid);
}

public List<MatchVO> select2(String mbti) {
	List<MatchVO> list = my.selectList("matchs.select2", mbti);	
	return list;
}


	
	
}
