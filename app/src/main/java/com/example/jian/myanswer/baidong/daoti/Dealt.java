package com.example.jian.myanswer.baidong.daoti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dealt {
	// 4种情况:
	//	1)题目里都没答案.
	//	2)题目里都有答案.
	//	3)题目的最后面有答案（题目与答案分开）.
	//	4)部分题目里有答案

	private String pattern = "^(\\S){3,}\\s{1,}A|a\\S{1,}\\s{1,}B|b\\S{1,}\\s{1,}C|c\\S{1,}\\s{1,}|(D|d\\S{1,}$)"; // 选择题
	private String[] patterns = {// 单项选择题,多项选择题,填空题,判断题
			"(\\S{5,}\\s{1,}(A|a|B|b|C|c|D|d)){3,4}",
			"(\\S{5,}\\s{1,}(A|a|B|b|C|c|D|d)){4,}", "" };
	private String[] title = { "单项选择题", "多项选择题", "填空题", "判断题", "简答题", "应用题",
			"计算题", "证明题" }; // 其它题
	private int sign = 0; // 存储类型 , 标记当前存储位置:0,1,2,3(题目，答案，解析...)
	private Map<String, String[][]> program;
	private String[][] item;
	private List<String> timu;
	Matcher matcher;

	// 排版标准化，分类，并存储（默认）
	public void makeUp() {
		program = new HashMap<String, String[][]>();
		initItem();
		try {
			String[] data = Dao.getData().split("\n"); // 按行分割获取数据到data数组中
			String str = null; // 读取文件
			String name = null; // 题目名
			String beforeName = null; // 当前大题题目名
			int num = 0; // 当前所在题数
			int n = 0; // 对比位置
			boolean status = false; // 是否遍历到大题题名
			boolean TorF; // 判断是否为判读题
			boolean subtitle;// 判断是否为子标题
			boolean no; // 判断编号

			for (int k = 1; k < data.length; k++) {// 遍历所有数据
				str = data[k];
				if (data[k].length() > 1) { // 数据是否为空
					for (int i = 0; i < title.length; i++) { // 获取大题的题名
						if (str.indexOf(title[i]) >= 0) {
							if (name != null)
								beforeName = name;
							name = title[i];
							status = true;
							break;
						}
					}
					if (name != null) { // 如果找到大题的题名
						if (status) { // 遍历到大题名，存储到map中
							if (item[0][0] != null) {
								// System.out.println("name:"+beforeName+", item:");
								// for(String str2:item[0]){
								// if(str2!=null)
								// System.out.println(str2);
								// }
								program.put(beforeName, item);
								// String[][] s = program.get(beforeName);
								initItem();
							}
							num = 1;
							status = false;
						} else if (!status) { // 对所获取行数据进行处理
							if (str.length() > 1) {
								matcher = Pattern.compile("^[0-9]+").matcher(
										str.substring(0, 2));// 题号获取
							}
							while (matcher.find()) { // 匹配的题号
								n = Integer.parseInt(matcher.group());
							}
							TorF = (str.indexOf("（") == 0);
							subtitle = !(str.indexOf("）") == 1);
							no = (n == num);
							if ((no && subtitle) || (TorF && name == title[3])) { // 筛选含题号的题目行
								String[] answer = moveBlankAnswer(str).split(
										"@");
								item[0][num - 1] = answer[0];
								if(answer.length>1)
									item[1][num - 1] = answer[1];
								num++;
								sign = 0;
							} else if (str.indexOf("答") == 0 // 筛选出答案，并存储
									|| str.indexOf("解") == 0
									|| str.indexOf("证明") == 0) {
								item[1][num - 1] = str; // 答案行
								sign = 1;

							} else { // 筛选 补充题目的扩充行
								String s = item[sign][num - 2];
								if (s == null)
									s = "";
								// if(str.indexOf("B")>str.indexOf("A"))
								item[sign][num - 2] = s + str;
								// System.out.println(item[sign][num - 2]);
								if (str.indexOf(0) < 9) {
									// 还有其他的选择题，调用其他处理方法，如：没有题号，大题题名不在title内
									// [补充]
								}
							}
						}
					}
					if (k + 1 == data.length) {
						program.put(name, item);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 新建，并初始化存储题目的二维字符串数组
	public void initItem() {
		item = new String[5][100];
		// item[0] = new String[100];
		for (int i = 0; i < 5; i++) { // 0:题目,1:答案,2:解析,3:结果正误(分数),4:我的答案
			item[i] = new String[100];
			for (int j = 0; j < 100; j++) {
				item[i][j] = null;
			}
		}

		timu = new ArrayList<String>();

	}

	// 去除空行
	public void deleteBlank() {
		String[] data = Dao.getData().split("\n");
		StringBuffer sb = new StringBuffer("");
		for (int i = 1; i < data.length; i++) { // 去除空行
			if (data[i].trim().equals("")) {
			} else {
				sb.append(data[i].trim() + "\n");
			}
		}
	}

	// 通过正则匹配将题目内的小题分割，并存储到数组中
	public void dealt() {
		String pattern1 = "[0-9]+(\\、|\\.)\\s((A|a)\\S\\s(B|b)\\S\\s(C|c)\\S\\s|(D|d)\\S\\s)"; // 单项选择题，有小括号，3～4个选项
		String pattern2 = "[0-9]+(\\、|\\.)\\s((([A-Ka-k])\\S\\s){4,8})"; // 多项选择题，可选项大于4。
		String pattern3 = "[0-9]+(\\、|\\.)\\s";// 填空题 ，存在下滑线，没选项
		String pattern4 = "";// 判断题，有小括号，没选项
		String pattern5 = "";// 简答题，应用题，计算题，证明题。
		String pattern6 = ""; // 答案，解析。匹配答案2字.（解，答，证明。。。）
	}

	// 选择题排版标准化,仍需修改为正则表达式-》达到所有不可见符号都可匹配替换为换行
	public void select() {
		String[][] selects = program.get("单项选择题");
		//String[][] selects2 = program.get("多项选择题");
		if (selects!=null)
			for (int i = 0; i < selects[0].length; i++) {
				String[] d = selects[0][i].split("\t");
				for(int j=0;j<d.length;j++){
					if(d[j].length()>1)
						System.out.println(d[j]);
					d[j] += "\n";
				}
			}
	}

	// 题目存储
	public void putItem() {
		program = new HashMap<String, String[][]>();
		for (int i = 0; i < title.length; i++) {
			// program.put(title[1], "");
		}
		Dao.setProgram(program);
	}

	// 题目位置排序
	public static int[] sort(int[] numbers) {
		int temp = 0;
		int size = numbers.length;
		for (int i = 0; i < size - 1; i++) {
			for (int j = 0; j < size - 1 - i; j++) {
				if (numbers[j] > numbers[j + 1]) // 交换两数位置
				{
					temp = numbers[j];
					numbers[j] = numbers[j + 1];
					numbers[j + 1] = temp;
				}
			}
		}
		return numbers;
	}

	// 查找题目位置，并排序
	public int locate(String txt) {
		String data = Dao.getData();
		int[] index = { 0, 0, 0, 0, 0, 0, 0 };// 题目位置
		for (int i = 0; i < title.length; i++) {
			index[i] = txt.indexOf(title[i]);
			int[] str = sort(index);
			if (index[i] != 0) {
				// txt.substring(beginIndex, endIndex)
				switch (i) {
				}
			}
		}
		matcher = Pattern.compile(pattern).matcher(txt);
		while (matcher.find()) {
			System.out.println(title[0] + ":" + matcher.group());
		}
		return 1;
	}

	// 问题和答案和解析分开存储导入数组
	public void answer() { // [补充]
		// 设置sign，0：为题目； 1：为答案； 2：为解析。
		sign = 1;
	}

	// 将填空题,或者选择题目中的答案分离出来,符合 1. ___答案__ || 2. 答案 || 3. ( 答案 )
	public String moveBlankAnswer(String strs) { // [补充]
		//String blank = "(\\(|\\（|[_]|\\s)([\u4e00-\u9fa5a-zA-Z0-9√×]+)([_]|\\s|\\)|\\）)"; // 填空题答案匹配
		String blank = "(\\(|\\（|[_])\\s{0,5}([\u4e00-\u9fa5a-zA-Z0-9√×]+)\\s{0,5}([_]|\\)|\\）)";
		String select = "(\\(|\\（|[_])\\s{0,5}([a-jA-J0]+)\\s{0,5}([_]|\\)|\\）)";
		//分类，当为括号时用空格取代，当为下滑线时用下滑线取代

		String[] cond = { "和", "且", "或", "与" }; // 去掉该字符
		int condition = 0; // 答案 并列关系
		String temp; // 临时匹配的数据
		String question = ""; // 去除了答案的问题
		String answer = ""; // 获取的答案
		int index = 0; // 并列关系 插入位置
		boolean status = false;
		//	if (strs.indexOf("A") < 5 || strs.indexOf("C") < 5) {
		//	}
		//	else {
		// 获取填空题答案，并去掉填空题题目中的答案。
		matcher = Pattern.compile(blank).matcher(strs); // 匹配答案
		while (matcher.find()) {
			temp = matcher.group();
			if (temp.length() < 4) // 筛选是否含并列关系
				for (int i = 0; i < cond.length; i++)
					if (temp.indexOf(cond[i]) > 0) {
						status = true;
						condition = i;
					}
			if (temp.length() > 1 && temp.indexOf(cond[condition]) < 0) {
				temp = temp.substring(1, temp.length() - 1);
				temp += " ";
				answer += temp;
			}
		}
		if(answer.split(" ").length>4){
			answer = "";
		}
		if(strs.indexOf("（")>-1){
			if(strs.indexOf("（")==strs.lastIndexOf("（")){
				strs = strs.replaceAll(blank, "  "); // （去掉）替换掉答案
			}
			else{
				strs = strs.replaceFirst(blank, "");
			}
		}
		else
			strs = strs.replaceAll(blank, "___"); // （去掉）替换掉答案
		if (status) { // 将消除的并列关系字 condition 加回到问题中去
			//matcher = Pattern.compile("[_]+|\\s+").matcher(strs);
			//while (matcher.find()) { // 获取位置
			//index = (matcher.end() + matcher.start()) / 2;
			if(strs.indexOf("_")!=-1){
				index = (strs.indexOf("_")+strs.lastIndexOf("_"))*3/5;
			}
			//}
			// 添加关系字
			//System.out.println("now:::"+strs);
			strs = strs.substring(0, index) + cond[condition]
					+ strs.substring(index, strs.length());
		}

		//去掉含有多选的单选题答案

		question = strs;
		Dao.setFilequestion(question);
		Dao.setFileanswer(answer);
		//	}
		return Dao.getFilequestion() + "@" + Dao.getFileanswer();
	}

	// 获取选择题答案，并去掉选择题题目中的答案。
	public void moveSelectAnswer() {
		String select = "[\\（\\(]\\s{0,4}[a-jA-J]+\\s{0,4}[\\)\\）]"; // 匹配选择题答案
	}

	// 获取判断题答案，并去掉判断题题目中的答案。
	public void moveTFAnswer() {
		String TF = "[\\（\\(]\\s{0,4}[tTfF\\√\\×]+\\s{0,4}[\\)\\）]";// 匹配判断题答案
	}

	// 题目与答案分隔开，分隔开的答案分离并获取
	public void getAnswer() {
		String pattern1 = "答案";
	}

	// 查看题目是否有答案，没有答案则调用Crawling包内的函数获取答案
	public void addAnswer() { // [补充]

	}

	// 对题目答案进行分割后的保存
	public void cutAndStore() {
		String[][] content;
		int index;
		for (String key : program.keySet()) {
			content = program.get(key);
			index = 0;
			for (String value : content[0]) {
				if (value != null) {
					moveBlankAnswer(value);
					content[0][index] = Dao.getFilequestion();
					content[1][index] = Dao.getFileanswer();
				}
				index++;
			}
			program.put(key, content);
		}
	}

	//将长下划线，改成1条下划线，将多空格的括号，改成单空格的括号
	public String delete(String str,int type){
		String s = "";
		switch(type){
			case 0://选择题
				s = str.replaceAll("\\（[A-Ja-j\\s*]+\\）","（ ）");
				s = s.replaceAll("[_]+","（ ）");
				break;
			case 1:
				s = s.replaceAll("[_]+","___");
				break;
			case 2:
				break;
		}

		return s;
	}



	// 打印结果到console
	public void print(String name) {
		String s1 = "";
		try {
			if (program.containsKey(name)) {
				String[][] str = program.get(name);
				// for (String strs : program.keySet()) {
				// System.out.println(strs);
				// }
				for (int i = 0; i < str[0].length; i++)
					if (str[0][i] != null) {
						s1 = delete(str[0][i],0).replaceAll("\n","");
						Dealt2 d2 = new Dealt2(Dao.getData());
						s1 = d2.selectToSort(s1);
						System.out.print(s1+"\n:::答案:");
						System.out.println(str[1][i]);
					}
				System.out.println(str[0].length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
