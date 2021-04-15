package com.spring.project2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MatchingController {
@Autowired
TypeDAO tdao;
@Autowired
Member2DAO mdao;
@Autowired
MatchDAO m2dao;
@Autowired
MatchingService7 ms;


@RequestMapping("matching")
public void matching(Model model, HttpSession session) throws Exception {
	String mid = (String)session.getAttribute("Mid");
	System.out.println(mid);
	HashMap<String, Double> map = new HashMap<String, Double>(); 
	map = ms.RecN(mid);
	System.out.println("컨트롤러에 map값:  " + map);
	ArrayList<Double> point = new ArrayList<Double>();
	point.addAll(map.values());
	Collections.sort(point);
	if (point.size() == 3) {
		System.out.println(point.get(2));
		
		ArrayList<String> list = new ArrayList<String>();
		
		list.addAll(map.keySet());
		System.out.println(list.get(2));
		TypeVO tvo = tdao.select2(list.get(2));
		Member2VO mvo = mdao.select3(list.get(2));
		MatchVO mcvo = m2dao.select1(list.get(2));
		model.addAttribute("tvo",tvo);
		model.addAttribute("mvo",mvo);
		model.addAttribute("mcvo",mcvo);
		model.addAttribute("point1",point.get(2).intValue());
	} else if (point.size() == 2) {
	System.out.println(point.get(1));
	
	ArrayList<String> list = new ArrayList<String>();
	
	list.addAll(map.keySet());
	System.out.println(list.get(1));
	TypeVO tvo = tdao.select2(list.get(1));
	Member2VO mvo = mdao.select3(list.get(1));
	MatchVO mcvo = m2dao.select1(list.get(1));
	model.addAttribute("tvo",tvo);
	model.addAttribute("mvo",mvo);
	model.addAttribute("mcvo",mcvo);
	model.addAttribute("point1",point.get(1).intValue());
	} else if (point.size() == 1) {
		System.out.println(point.get(0));
		
		ArrayList<String> list = new ArrayList<String>();
		
		list.addAll(map.keySet());
		System.out.println(list.get(0));
		TypeVO tvo = tdao.select2(list.get(0));
		Member2VO mvo = mdao.select3(list.get(0));
		MatchVO mcvo = m2dao.select1(list.get(0));
		model.addAttribute("tvo",tvo);
		model.addAttribute("mvo",mvo);
		model.addAttribute("mcvo",mcvo);
		model.addAttribute("point1",point.get(0).intValue());
	} else {
		System.out.println(point.get(2));
	}
}
@RequestMapping("matching2")
public void matching2(Model model, HttpSession session) throws Exception {
	String mid = (String)session.getAttribute("Mid");
	System.out.println(mid);	
	HashMap<String, Double> map = new HashMap<String, Double>(); 
	map = ms.RecN(mid);
	System.out.println("컨트롤러에 map값:  " + map);
	
	ArrayList<Double> point = new ArrayList<Double>();
	
	point.addAll(map.values());
	Collections.sort(point);
		if (point.size() == 3) {
			System.out.println(point.get(1));
			ArrayList<String> list = new ArrayList<String>();
			
			list.addAll(map.keySet());
			System.out.println(list.get(1));
			TypeVO tvo = tdao.select2(list.get(1));
			Member2VO mvo = mdao.select3(list.get(1));
			MatchVO mcvo = m2dao.select1(list.get(1));
			model.addAttribute("tvo",tvo);
			model.addAttribute("mvo",mvo);
			model.addAttribute("mcvo",mcvo);
			model.addAttribute("point2",point.get(1).intValue());
		}else if(point.size() == 2) {
			System.out.println(point.get(0));
			ArrayList<String> list = new ArrayList<String>();
			
			list.addAll(map.keySet());
			System.out.println(list.get(0));
			TypeVO tvo = tdao.select2(list.get(0));
			Member2VO mvo = mdao.select3(list.get(0));
			MatchVO mcvo = m2dao.select1(list.get(0));
			model.addAttribute("tvo",tvo);
			model.addAttribute("mvo",mvo);
			model.addAttribute("mcvo",mcvo);
			model.addAttribute("point2",point.get(0).intValue());
		}	else if(point.size() == 1) {
			System.out.println(point.get(2));
			
		}
	}
@RequestMapping("matching3")
public void matching3(Model model, HttpSession session) throws Exception {
	String mid = (String)session.getAttribute("Mid");
	System.out.println(mid);
	HashMap<String, Double> map = new HashMap<String, Double>(); 
	map = ms.RecN(mid);
	System.out.println("컨트롤러에 map값:  " + map);
	ArrayList<Double> point = new ArrayList<Double>();
	
	point.addAll(map.values());
	Collections.sort(point);
	if (point.size() == 3) {
		System.out.println(point.get(0));
		ArrayList<String> list = new ArrayList<String>();
		
		list.addAll(map.keySet());
		System.out.println(list.get(0));
		TypeVO tvo = tdao.select2(list.get(0));
		Member2VO mvo = mdao.select3(list.get(0));
		MatchVO mcvo = m2dao.select1(list.get(0));
		model.addAttribute("tvo",tvo);
		model.addAttribute("mvo",mvo);
		model.addAttribute("mcvo",mcvo);
		model.addAttribute("point2",point.get(0).intValue());
	}else if(point.size() == 2) {
		System.out.println(point.get(2));
	
	}	else if(point.size() == 1) {
		System.out.println(point.get(2));
		
		}
	}
}
