package com.example.jian.myanswer.baidong;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Dao {
	private static String file; //文件名
	private static String data;  //文档读取所有的哦数据
	private static Map<String,String[][]> program;  //所有题  [0][] 题目  ; [1][] 答案; [2][] 解析 ; [3][] 正误;
	private static String answer;  //最终的答案
	private static String explain; //最终的解析
	private static String filequestion;  //文档的问题
	private static String fileanswer;    //文档的答案
	private static String searchquestion;    //搜索所得，并去掉特殊字符的问题
	private static double nowPercentage = 0;
	private static List<String> mutiAnswer = new ArrayList<>(); //多个答案存储
	private static List<String> mutiExplain = new ArrayList<>(); //多个问题存储

	public static List<String> getMutiAnswer() {
		return mutiAnswer;
	}

	public static void setMutiAnswer(List<String> mutiAnswer) {
		Dao.mutiAnswer = mutiAnswer;
	}

	public static List<String> getMutiExplain() {
		return mutiExplain;
	}

	public static void setMutiExplain(List<String> mutiExplain) {
		Dao.mutiExplain = mutiExplain;
	}

	public static double getNowPercentage() {
		return nowPercentage;
	}

	public static void setNowPercentage(double nowPercentage) {
		Dao.nowPercentage = nowPercentage;
	}

	public static boolean isStatus() {
		return status;
	}

	public static void setStatus(boolean status) {
		Dao.status = status;
	}

	private static boolean status = true; //爬取状态（有问题为：false,直接跳出）


	public static String getSearchquestion() {
		return searchquestion;
	}

	public static void setSearchquestion(String searchquestion) {
		Dao.searchquestion = searchquestion;
	}

	public static String getFilequestion() {
		return filequestion;
	}

	public static void setFilequestion(String filequestion) {
		Dao.filequestion = filequestion;
	}

	public static String getFileanswer() {
		return fileanswer;
	}

	public static void setFileanswer(String fileanswer) {
		Dao.fileanswer = fileanswer;
	}

	public static String getAnswer() {
		return answer;
	}

	public static void setAnswer(String answer) {
		Dao.answer = answer;
	}

	public static String getExplain() {
		return explain;
	}

	public static void setExplain(String explain) {
		Dao.explain = explain;
	}

	public static Map<String, String[][]> getProgram() {
		return program;
	}

	public static void setProgram(Map<String, String[][]> program) {
		Dao.program = program;
	}

	public static String getFile() {
		return file;
	}

	public static String getData() {
		return data;
	}

	public static void setData(String data1) {
		data = data1;
	}

	public static void setFile(String file1) {
		file = file1;
	}


}
