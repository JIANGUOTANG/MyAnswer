package com.example.jian.myanswer.util;

import com.example.jian.myanswer.baidong.daoti.Dealt;
import com.example.jian.myanswer.baidong.daoti.Dealt2;
import com.example.jian.myanswer.baidong.daoti.Remove;
import com.example.jian.myanswer.baidong.daoti.RemoveABCD;
import com.example.jian.myanswer.bean.program.Contents;
import com.example.jian.myanswer.bean.program.Item;
import com.example.jian.myanswer.bean.program.Items;
import com.example.jian.myanswer.bean.program.Program;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
public class ImportProgramUtil {
	public static String path;
	private static Map<String, List<String>> program;
    public static String name;
	// 输出结果
	public static Items p(String name) {
		List<String> list;int i = 0;
		list = program.get(name);
		List<Item> item = new ArrayList<>();
		int type = 0;
		if (name.contains("单选") || name.contains("选择")) {
			type = ProgramManager.SINGLECHOICE;

		} else if (name.contains("多选") || name.contains("双选")) {
			type = ProgramManager.MUTIPLECHOICE;

		}else if(name.contains("填空")){
			type = ProgramManager.GAPFILLING;
		}else{
			type = ProgramManager.OPERATING;
		}
		item = choice(list);
//		item = getChoiceItem(list);
		Items items2 = new Items(type, 1, item);

		System.out.println(program.keySet());
		Remove remove = new RemoveABCD();
		if(list!=null&&!list.isEmpty()){
			for(Iterator<String> iterator = list.iterator();iterator.hasNext();){
				i++;
				// String str = iterator.next();
				System.out.println(name+"_第"+i+"题:"+iterator.next());

//		 getItem(iterator.next());
			}
		}

		return items2;
	}
	//选择题

	private static List<Item> choice(List<String> list){
		List<Item> items = new ArrayList<>();
		if (list != null && !list.isEmpty()) {
			for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
				// System.out.println(name+"_第"+i+"题:"+iterator.next());
				// System.out.println(question);
				String str = iterator.next();

				// System.out.println(questionName + "op" +
				// option+"answer"+answer);
				Item item = new Item(str, "", "", "", "");
				items.add(item);
			}
		}
		return items;
	}
//	private static List<Item> getChoiceItem(List<String> list) {
//		List<Item> items = new ArrayList<>();
//		if (list != null && !list.isEmpty()) {
//			for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
//				String question = iterator.next();
//				// System.out.println(name+"_第"+i+"题:"+iterator.next());
//				// System.out.println(question);
//				String select_item_regex = "[\\s\\S]+((?=答案)|(?=正确))";
//				String select_answer_regex = "(?=答案).+";
//				Pattern pattern = Pattern.compile(select_item_regex);
//				Matcher matcher = pattern.matcher(question);
//				matcher.find();
//				String question2 = matcher.group();
//				pattern = pattern.compile(select_answer_regex);
//				matcher = pattern.matcher(question);
//				matcher.find();
//				String questionName;
//				String answer;
//				String option = "";
//				String str1[] = question2.split("\n");
//				String str2 = matcher.group();
//				pattern = pattern.compile("[A-J]+");
//				matcher = pattern.matcher(str2);
//				matcher.find();
//				answer = matcher.group(0);
//				questionName = str1[0];
//				for (int i = 1, j = str1.length; i < j; i++) {
//					option = option + str1[i];
//					if (i != j - 1) {
//						option = option + "@#@";
//					}
//					// System.out.println(str1[i]);
//				}
//				// System.out.println(questionName + "op" +
//				// option+"answer"+answer);
//				Item item = new Item(questionName, option, "", "", answer, "");
//				items.add(item);
//			}
//		}
//		return items;
//
//	}

	// 输出结果
	public static Program  p() {
		int i = 0;
		List<Items> items = new ArrayList<>();
		for (String str : program.keySet()) {
			i++;
			Items items2 =p(str);
			items.add(items2);
		}
		Contents content = new Contents(items);
		Program program = new Program("now", "jianguotang", name, content);
		return program;
	}
	// 按行读取文档
	public static void start() {
		Dealt dealt = new Dealt();
		dealt.makeUp();
		// dealt.select();
		// dealt.cutAndStore();
		dealt.print("单项选择题");
	}
	// //按块读取文档
	// public static void start2(String location){
	// //设置读取路径
	// Dao.setFile(location);
	// //处理(解析word)文档
	// new Word(Dao.getFile());
	// //对题目解析，分离处理存储
	// Dealt2 dealt2 = new Dealt2(Dao.getData());
	// if(Dao.getData()!=null){
	// dealt2.classify();
	// }
	// //获取 Map<String str0,List<String str1> list0> program 格式题目数据
	// // String str0 : 题目名
	// // List<String str1> list0 : 同一大题里的题目
	// // String str1 : 单独一道题目
	// program = dealt2.dealt();
	// }
	//

	public static void testImage() {
		String savePathName = "C:\\Users\\Administrator\\Desktop\\image\\";
		String inputFileName = "C:\\Users\\Administrator\\Desktop\\学位操作系统试卷2.docx";
		try {
			// new Word().getImageFromDocx(inputFileName, savePathName);
		} catch (Exception e) {
			// // TODO 自动生成的 catch 块
			// e.printStackTrace();
		}
		// //System.out.println("result:::::"+result);
	}
		public static Program  creatProgram(){
		// program =
		// Dealt2.start2("C:\\Users\\Administrator\\Desktop\\学位操作系统试卷2.docx");
		program = Dealt2.start2(path);
		// p("一、 多选题");
		Program program2 = p();
		Gson gson = new Gson();
		String json = gson.toJson(program2);
		// testImage();
		return program2;

	}
	public void other() {
		// Dao.setFile("C:\\Users\\Administrator\\Desktop\\学位操作系统试卷2.doc");
		// start();
		// 获取 Map<String str0,List<String str1> list0> program 格式题目数据
		// String str0 : 题目名
		// List<String str1> list0 : 同一大题里的题目
		// String str1 : 单独一道题目

	}

}
