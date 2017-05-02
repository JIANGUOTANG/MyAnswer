package com.example.jian.myanswer.baidong.daoti;
//采用，按顺序按行读取，保存的方式，整理试卷，以及题目答案的处理
//分离答案4种情况:
//	1)题目里都没答案.
//	2)题目里有答案.
//		2.1)答案在题目的最后面（题目与答案分开）     //按行按题号获取答案
//		2.2)答案混在题目里        //按
//			2.2.1)在题目的开头         				类型: 判断题,选择题       可有无括号下滑线
//			2.2.2)在题目第一行的最后面          		类型: 判断题,选择题       可有无括号，下滑线
//			2.2.3)在题目的(小、中、下滑线)中间里	类型: 选择题,填空题,判断题      匹配:  首 -(无)-> 尾  -(无)-> 中 -> 2.2.3
//			2.2.4)在题目最后补行                                         类型: 大题,选择题


import com.example.jian.myanswer.baidong.daoti.Remove;
import com.example.jian.myanswer.baidong.daoti.RemoveABCD;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dealt2 {

	//按块（大小）来处理：大标题->小标题
	//先获取每一大题，再分开进行不同的处理。
	//图片存储。
	private String data;
	Matcher matcher;
	String no_regex = "[0-9]{1,2}[.、．）)]{1,2}\\s?"; //匹配题号
	String torF_regex = "[(【（]\\s{0,3}[tTfF错对√×A-J]?\\s{0,3}[）】)][0-9]+[.、．）)]";  //匹配判断（括号开头）
	//private String title_no_regex = "[\\(（]?(\\s)?[a-jA-J错对√×]?(\\s)?[）\\)]?[0-9]{1,2}[.、．）\\)]{1,2}";
	private String title_no_regex = "\\s[0-9]{1,2}\\)?[.、．）]\\S";   //匹配小题的题号   除去开头的空格，从0位置的开始匹配题号
	private String title_no3_regex = "\\s[0-9]{1,2}[.、．)）]\\s?\\S";
	private String title_no2_regex = "[0-9]{1,2}\\)?[.。、．）)]";
	private String title_no4_regex = "\\s[\\(（]?\\s?[a-jA-J错对√×]?\\s?[）\\)]?[0-9]{1,2}[.、．)）]";
	//private String edge_select_answer_regex = "[(（[]?\\s?[A-DxTFa-d]{1,4}\\s?[)）]?]";

	//去掉头尾的空格，获取第一个值和最后一个值判断是否为[A-D]。若非 -> 再判断是否有括号的答案

	//题型存储
	private Map<String,List<String>> program;
	//分块的大题
	//private Map<String,String> block_program;
	//每一类题: 题号 题目
	private List<String> item_list;
	//每一小题: 1)题目  2)答案+解析  3)答对与否  4)评论 5)包含图片(地址)
	private String item; //先将题目和答案一并放到一起


	public void init(){  //每次完成需要进行一次刷新
		item = "";
		item_list = new ArrayList<String>();
		program = new HashMap<String,List<String>>();
	}

	public void initItem(){ //每次完成需要进行一次刷新
		item = "";
	}

	public void initItemList(){ //每次载入完一类题后，重新刷新
		item_list = new ArrayList<String>();
		//for(int i = 0; i < item_list.size(); i++)
		//	item_list.set(i,null);
	}

	public Dealt2(String data){
		this.data = data;
		deleteBlank();
		init();
	}

	//初始化读取数据

	//题目分离
	// 1)读取到大标题:  进行分块
	// 		1.1)读取小标题        
	// 2)读取不到大标题:
	//		2.1)直接将所有类型存储在一起   不分大题块
	//			2.2)读取到小题号     按小题号进行分
	//			2.3)读取不到小题号      按空行、行分题
	List<String> name = new ArrayList<String>(); //题目名
	List<String> content = new ArrayList<String>(); //各大题题目
	public void classify(){  //对题类分块
		String this_data = data;
		String big_title_regex = "\\s[一二三四五六七八九十]{1,2}[、.．]?[a-zA-Z0-9\u4e00-\u9fa5]+";  //匹配大题标题    除去开头的空格，从0位置的开始匹配标题
		String title_low_regex = "\\s\\w?[.、．]?[\u4e00-\u9fa5]+题";
		Matcher title_matcher = Pattern.compile(big_title_regex).matcher("  "+data);
		Matcher title_low_matcher = Pattern.compile(title_low_regex).matcher("  "+data);
		for(int i = 0; title_matcher.find(); i++){
			name.add(title_matcher.group().trim());
			content.add("");
			if(i>0){
				content.set(i-1,this_data.split(name.get(i))[0]);
			}
			this_data = this_data.split(name.get(i))[1];
		}
		int size = name.size();
		if(size > 0){    //如果获取到大标题，将其存储到数组列表中
			content.set(size-1,this_data.split(name.get(size-1))[0]);
			sub_Classify();
		}
		else{    //如果无大标题号，通过第二种正则表达式查找是否有符合的标题。
			for(int i = 0; title_low_matcher.find(); i++){
				name.add(title_low_matcher.group().trim());
				content.add("");
				if(i>0){
					content.set(i-1,this_data.split(name.get(i))[0]);
				}
				this_data = this_data.split(name.get(i))[1];
			}
			size = name.size();
			if(size > 0){
				content.set(size-1,this_data.split(name.get(size-1))[0]);
				sub_Classify();
			}
			else{ //如果无大标题，有小题题号
				otherProgram();
			}
		}
	}

	//按题号分小题
	public void sub_Classify(){
		//int now_no = 1;
		Matcher matcher;
		String temp_content = "";
		String title_no = "";
		String temp = "";
		String item_content = "";
		String[] items = new String[2];
		for(int i = 0; i < name.size(); i ++){
			temp_content = content.get(i);
			matcher = Pattern.compile(title_no_regex).matcher(temp_content);
			while(matcher.find()){
				temp = matcher.group();
				items = temp_content.split(matcher.group());
				//if(name.get(i).indexOf("选择")!=-1){
				//	items[0] = selectToSort(items[0]);
				//}
				item_content = items[0];
				if(items.length>1){
					temp_content = items[1];
					item_list.add(title_no+items[0]);
					title_no = temp;
				}
			}
			if(items.length>1)
				item_list.add(title_no+items[1]);
			item_list.remove(0);
			if(item_list.size()==0){
				String item = "";
				int now_no = 1;
				int item_no = 0;
				int index = -1;
				matcher = Pattern.compile(title_no2_regex).matcher(temp_content);
				while(matcher.find()){
					temp = matcher.group();
					if(index!=-1&&now_no==item_no){
						temp_content = temp_content.substring(index);
						item = temp_content.substring(0,temp_content.indexOf((temp)));
						if(index>0){
							item_list.add(item);
							now_no++;
						}
					}
					index = temp_content.indexOf(""+temp);
					if(temp.length()>1&&temp.charAt(1)<='9'&&temp.charAt(1)>='0')
						item_no = (temp.charAt(0)-48)*10+temp.charAt(1)-48;
					else
						item_no = temp.charAt(0)-48;
				}
				item_list.add(temp_content.substring(index));
			}
			program.put(name.get(i),item_list);
			initItemList();
		}
		allSelect();
	}

	//小题题号匹配
	//由复杂到简单，由大多数到少数

	public void title_no(){
		Matcher matcher;
		int count=1;
		int now_no;
		String temps[] = new String[2];
		String get_title_no="";
		String[] no_regexs={   //由严谨到宽松,由大多数到少数
				"\\s[0-9]{1,2}[.、．\\)）]",      //常用
				"[\\(][0-9]{1,2}[\\)]",
				"\\s[0-9]{1,2}[.、．\\)）]{1,2}",
				"\\s[\\(（](\\s)?[A-Ga-G](\\s)?[）\\)][0-9]{1,2}[.、．\\)）]",
				"[0-9]{1,2}[.、．\\)）]",
				"[\\[\\(（【](\\s)?[a-jA-JTFtf错对√×](\\s)?[\\]】\\)）]"
		};

		for(int i = 0; i < name.size(); i ++){
			String temp_content = content.get(i);
			now_no = 0;
			while(item_list==null){
				count++;
				matcher = Pattern.compile(no_regexs[count]).matcher(temp_content);
				while(matcher.find()){
					now_no++;
					get_title_no = matcher.group();
					temps = temp_content.split(get_title_no);
					if(temps.length>1)
						item_list.add(get_title_no+temps[0]);
				}
				if(count==no_regexs.length){
					break;
				}
			}
			if(temps.length>1)
				item_list.add(get_title_no+temps[1]);
			item_list.remove(0);
			program.put(name.get(i),item_list);
			initItemList();
		}
		//按题号1-n确认
	}

	//将选择题选项分开,并获取答案
	public void allSelect(){
		Set<String> name = program.keySet();
		List<String> temp_list;
		String temp = "";
		for(Iterator<String> iter = name.iterator(); iter.hasNext();){
			String n = (String) iter.next();
			if(n.indexOf("选择")!=-1){
				temp_list = program.get(n);
				for(int i=0;i<temp_list.size();i++){
					temp = selectToSort(temp_list.get(i));
					temp = moveSelectAnswer(temp);
					temp_list.set(i,temp);
				}
				travelAllItem(new RemoveABCD());
			}else if(n.indexOf("填空")!=-1){
				temp_list = program.get(n);
				for(int i=0;i<temp_list.size();i++){
					temp = moveBlankAnswer(temp_list.get(i));
					temp_list.set(i,temp);
				}
			}
		}
	}

	//不符合规范的题目保存到“其它”项中  按照按行读取的方式解析题目（有题号）
	public void otherProgram(){
		String[] number = {"一","二","三","四","五","六","七","八","九","十"};
		//List<String[]> item = new ArrayList<String[]>();

		StringBuilder now_item = new StringBuilder();
		//String no_regex = "[0-9]{1,2}[.、．）)]"; //匹配题号
		//String torF_regex = "[(【（]\\s{0,3}[tTfF错对√×A-J]?\\s{0,3}[）】)][0-9]+[.、．）)]";  //匹配判断（括号开头）
		int index_title = 1;
		//	int index_no = 0; //当前小题题号
		String name = "第一题";
		//	StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
		String line;
		String temp;
		Matcher matcher,matcher2;
		try {
			while((line = br.readLine()) != null ) {
				matcher = Pattern.compile(no_regex).matcher(line.trim());
				matcher2 = Pattern.compile(torF_regex).matcher(line.trim());
				if(matcher.find()){
					temp = matcher.group();
					if(line.indexOf(temp)<5){
						line = line.replace("　","");
					}
					if(line.indexOf(temp)==0){  //符合开头题号
						if(line.charAt(0)=='1'&&(line.charAt(1)>'9'||line.charAt(1)<'0')&&!item_list.isEmpty()){
							item = now_item.toString();
							item_list.add(item);
							if(index_title!=1)
								item_list.remove(0);
							if(item.indexOf("A")>5&&item.indexOf("A")<item.indexOf("B")-2&&item.indexOf("B")<item.indexOf("C")-2&&item.indexOf("C")<item.indexOf("D")-2){
								name = number[index_title-1]+"、选择题";
							}
							program.put(name, item_list);
							name = "第"+number[index_title]+"题";
							initItemList();
							index_title++;
						}
						if(now_item.length()!=0){
							item = now_item.toString();
							item_list.add(item);
							now_item = new StringBuilder();
							initItem();
						}
					}else if(matcher2.find()){
						String s = matcher2.group(0);
						if(line.indexOf(matcher2.group())==0){
							if(s.charAt(s.length()-2)=='1'&&(s.charAt(s.length()-3)>'9'||s.charAt(s.length()-3)<'0')&&!item_list.isEmpty()){
								item = now_item.toString();
								item_list.add(item);
								if(index_title!=1)
									item_list.remove(0);
								if(item.indexOf("A")>5&&item.indexOf("A")<item.indexOf("B")-2&&item.indexOf("B")<item.indexOf("C")-2&&item.indexOf("C")<item.indexOf("D")-2){
									name = number[index_title-1]+"、选择题";
								}
								program.put(name, item_list);
								name = "第"+number[index_title]+"题";
								initItemList();
								index_title++;
							}
							if(now_item.length()!=0){
								item = now_item.toString();
								item_list.add(item);
								now_item = new StringBuilder();
								initItem();
							}
						}
						//now_item.append(line+"\n");
					}

					now_item.append(line+"\n");
				}
				else{
					now_item.append(line+"\n");
				}
			}
			item = now_item.toString();
			item_list.add(item);
			item_list.remove(0);
			program.put(name, item_list);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//如果调用了otherPragram方法，对无确切大题题名判断是否选择题，用于分割选项/以及答案获取处理
	public void guessSelect(){

	}

	// 新建，并初始化存储题目的二维字符串数组
	public void store(){

	}

	//将选择题的选项按行排版
	public String selectToSort(String str){
		StringBuilder question = new StringBuilder();
		String select_item_regex = "\\S?[A-Ja-j]\\s{0,2}[、.）)]\\S+\\s"; //是否要添加\\s
		String select_item_low_regex = "\\s[A-Ja-j]\\S+\\s";
		String[] temp = new String[2];
		temp[0] = str;
		//StringBuilder question2 = new StringBuilder();
		Matcher item_matcher = Pattern.compile(select_item_regex).matcher(str+" ");
		while(item_matcher.find()){
			String temp_select = item_matcher.group().trim();
			if(temp_select.charAt(0) =='A' || temp_select.charAt(0) == 'a'||temp_select.charAt(1) =='A' || temp_select.charAt(1) == 'a'){
				question.append(str.substring(0,str.indexOf(temp_select)).trim());
				//	question2.append(question.toString());
			}

			question.append("\n  " + temp_select);
			temp =  temp[0].split(temp_select);

			//question2.append("\n " + temp[0]+temp_select);
			if(temp.length==2)
				temp[0] = temp[1];
		}
		if(question.length()==0){
			item_matcher = Pattern.compile(select_item_low_regex).matcher(str+" ");
			while(item_matcher.find()){
				String temp_select = item_matcher.group().trim();
				if(temp_select.charAt(0) =='A' || temp_select.charAt(0) == 'a'){
					question.append(str.substring(0,str.indexOf(temp_select)).trim());
				}
				question.append("\n  " + temp_select);
			}

		}
		int answer_index = str.lastIndexOf("答案");
		if(str.lastIndexOf("D")<answer_index||str.lastIndexOf("d")<answer_index){
			question.append("@@@" + str.substring(answer_index));
		}
		if(question.length()==0){
			return str;
		}
		return question.toString();
	}

	//将答案和题目分开
	public void splitQuestion_Answer(){
		//选择题
		//填空题
		//判断题
		//大题(应用题,简答题,综合题)
	}

	//排除相近的字母筛选为选项
	public void filte(String str){
		boolean s = (str.indexOf("A")-str.indexOf("B")<2);
		s = s&(str.indexOf("B")-str.indexOf("C")<2);
		if(s){

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

	//大题答案分离获取
	public String moveOtherAnswer(String str){
		String result = " ";
		String word_answer_regex = "[解答]||(证明)||(答案)[:：]";   //匹配大题答案  (1.在最开头的位置。2.在最后的位置 ) 在题目的结尾
		Matcher matcher = Pattern.compile(word_answer_regex).matcher(str);
		if(matcher.find()){
			String temp = matcher.group();
			result = str.replace(temp, "@@@"+temp);
		}
		return result;
	}

	// 将选择题目中的答案分离出来。
	public String moveSelectAnswer(String str){
		String select_answer_last = "[(（]\\s{0,3}([a-jA-J0]+)\\s{0,3}[）)][.。]?\\s";
		//	String select_answer_first = "[(（]\\s?[a-jA-J]+\\s?[）)]\\s?";
		String select_answer_trail = "\\s{0,}[a-jA-J]+\\s+";
		String select_answer_normal = "[(（_]\\s{0,5}[A-Ja-j]{1,5}\\s{0,4}[)）_]";  //匹配选择题答案 ()
		String temp = null;
		Matcher matcher;
		String[] select_regex = {select_answer_last,select_answer_normal,select_answer_trail};
		for(int i=0;i<select_regex.length+1;i++){
			if(i==select_regex.length){
				return str;
			}
			if(temp==null){
				matcher = Pattern.compile(select_regex[i]).matcher(str);
				if(matcher.find()){
					temp = matcher.group().trim();
					temp = str.replace(temp, "")+"@@@"+temp.replaceAll("[_(（）)【】]", "");
				}
			}else{
				return temp;
			}
		}
//		//第一行结束位置含括号：
//		matcher = Pattern.compile(select_answer_last).matcher(str);
//		if(matcher.find()){
//			temp = matcher.group().trim();
//			temp = str.replace(temp, "")+"@@@"+temp.replaceAll("[(（）)]", "");
//			if(true){
//				
//			}
//		}
//		//题目里面
//		matcher = Pattern.compile(select_answer_normal).matcher(str);
//		if(temp==null){
//			if(matcher.find()){
//				temp = matcher.group().trim();
//				temp = str.replace(temp, "")+"@@@"+temp.replaceAll("[_(（）)【】]", "");
//			}
//		}
//		//第一行最后，不含括号
//		matcher = Pattern.compile(select_answer_trail).matcher(str);
//		if(temp==null){
//			if(matcher.find()){
//				temp = matcher.group().trim();
//				temp = str.replace(temp, "")+"@@@"+temp.replaceAll("[_(（）)【】]", "");
//			}
//		}
//		//没答案
//		if(temp==null){
//			return str;
//		}

		return temp;
	}

	// 将填空题目中的答案分离出来,符合 1. ___答案__ || 2. 答案 || 3. ( 答案 )
	public String moveBlankAnswer(String strs) { // [补充]
		//String blank = "(\\(|\\（|[_]|\\s)([\u4e00-\u9fa5a-zA-Z0-9√×]+)([_]|\\s|\\)|\\）)"; // 填空题答案匹配
		String blank = "([(（_])\\s{0,5}([\u4e00-\u9fa5a-zA-Z0-9]+)\\s{0,5}([_)）])";
		String blank_normal = "([(（_])\\s{0,5}\\S+\\s{0,5}([_)）])";
		//分类，当为括号时用空格取代，当为下滑线时用下滑线取代

		String[] cond = { "和", "且", "或", "与" }; // 去掉该字符
		int condition = 0; // 答案 并列关系
		String temp; // 临时匹配的数据
		String question = ""; // 去除了答案的问题
		String answer = ""; // 获取的答案
		int index = 0; // 并列关系 插入位置
		boolean status = false;
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
			if(strs.indexOf("_")!=-1){
				index = (strs.indexOf("_")+strs.lastIndexOf("_"))*3/5;
			}
			strs = strs.substring(0, index) + cond[condition]
					+ strs.substring(index, strs.length());
		}

		//去掉含有多选的单选题答案
		question = strs;
		Dao.setFilequestion(question);
		Dao.setFileanswer(answer);
		return Dao.getFilequestion() + "@@@" + Dao.getFileanswer();
	}

	// 去除空行
	public void deleteBlank() {
		String[] datas = data.split("\n");
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < datas.length; i++) { // 去除空行
			if (datas[i].trim().equals("")) {
			} else {
				sb.append(datas[i].trim() + "\n");
			}
		}
		data = sb.toString();
	}

	//调用方法函数遍历处理所有值
	public void travelAllItem(Remove process){
		List<String>  items = new ArrayList<String>();
		String temp;
		for(String name:program.keySet()){
			items = program.get(name);
			if(!items.isEmpty()){
				for(int i=0;i<items.size();i++){
					temp = items.get(i);
					//对temp值进行处理（方法调用）
					process.deal(name,i,temp);
					temp = process.getResult();
					if(temp == ""){
						items.remove(i);
					}else{
						items.set(i, temp);
					}
				}
			}
		}
	}

	//按行遍历数据
	public static List<String> readDataByLine(String str){
		StringBuilder sb = new StringBuilder();
		//封装ByteArrayInputStream-->InputStreamReader-->BufferedReader  
		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(str.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
		String line;
		List<String> result = new ArrayList<String>();
		int index = 0;
		try {
			while((line = br.readLine())!=null){
				//sb.append(line+"@##@");
				result.add(line);
			}
			//System.out.println(sb.toString());
			br.close();
			//if(sb.length()>5)
			//result = sb.toString().substring(0, sb.toString().length()-5).split("@##@");
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if(result==null){
			//result = new String[1];
			//result[0] = str;
		}
		System.out.println();
		return result;//sb.toString().substring(0, sb.toString().length()-5).split("@##@");
	}

	//去除题号
	public String removeNo(String str){
		String temp = str;
		temp = "";
		return temp;
	}

	//除去选项，并添加间隔符  选择题专用
	//public void 

	public Map<String,List<String>> dealt(){
		Dealt2 dealt2 = new Dealt2(Dao.getData());
		if(Dao.getData()!=null){
			dealt2.classify();
		}
		return program;
	}


	//方法调用
	public static Map<String,List<String>> start2(String location){
		//设置读取路径
		Dao.setFile(location);
		//处理(解析word)文档
		new Word(Dao.getFile());
		//对题目解析，分离处理存储
		Dealt2 dealt2 = new Dealt2(Dao.getData());
		if(Dao.getData()!=null){
			dealt2.classify();
		}
		//获取 Map<String str0,List<String str1> list0> program 格式题目数据
		// String str0 : 题目名
		// List<String str1> list0 : 同一大题里的题目
		// String str1 : 单独一道题目
		return dealt2.program;
		//program = dealt2.dealt();
	}


	//get image

}