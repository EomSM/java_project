package com.spring.project2;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageDAO {

	// myBatis싱글톤 주소를 주입!
	@Autowired
	SqlSessionTemplate my;

	public void create(MessageVO bag) throws Exception { // 쪽지 보내기
		// myBatis에 시키기
		my.insert("message.messageSend", bag);
	}
	
	public void delete(MessageVO bag) throws Exception { // 삭제
		// myBatis에 시키기
		my.delete("message.messageDelete", bag);
	}

	public List<MessageVO> select(MessageVO messageVO) { // 보낸 쪽지

		return my.selectList("message.list", messageVO);

	}

	public List<MessageVO> select2(MessageVO messageVO) { // 받은 쪽지

		return my.selectList("message.list2", messageVO);

	}
	
	public List<MessageVO> select3(MessageVO messageVO) { // 보낸 쪽지 - 검색

		return my.selectList("message.messageSearch", messageVO);

	}

}
