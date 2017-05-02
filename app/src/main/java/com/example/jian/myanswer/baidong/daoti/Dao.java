package com.example.jian.myanswer.baidong.daoti;

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
	private static String id;    //ppkao地址的url后缀编号
	private static String searchquestion;    //搜索出，并去掉特殊字符的问题
	private static String filelaterquestion; //文档中截取，并去掉特殊字符的问题
	private static List<byte[]> image;

	public static List<byte[]> getImage() {
		return image;
	}

	public static void setImage(List<byte[]> image) {
		Dao.image = image;
	}

	public static String getSearchquestion() {
		return searchquestion;
	}

	public static void setSearchquestion(String searchquestion) {
		Dao.searchquestion = searchquestion;
	}

	public static String getFilelaterquestion() {
		return filelaterquestion;
	}

	public static void setFilelaterquestion(String filelaterquestion) {
		Dao.filelaterquestion = filelaterquestion;
	}

	public static String getId() {
		return id;
	}

	public static void setId(String id) {
		Dao.id = id;
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

	public static void setProgram(Map<String, String[][]> program2) {
		Dao.program = program2;
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
