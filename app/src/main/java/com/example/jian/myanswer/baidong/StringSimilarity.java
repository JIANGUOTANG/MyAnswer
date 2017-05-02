package com.example.jian.myanswer.baidong;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class StringSimilarity {

	private static String regex="[`_~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

	public static float compare(String str1, String str2) {
		//定义差异数
		int step = 0;
		// 计算两个字符串的长度。
		int len1 = str1.length();
		int len2 = str2.length();
		// 建立数组，比字符长度大一个空间
		int[][] dif = new int[len1 + 1][len2 + 1];
		// 赋初值。
		for (int a = 0; a <= len1; a++) {
			dif[a][0] = a;
		}
		for (int a = 0; a <= len2; a++) {
			dif[0][a] = a;
		}
		// 计算两个字符是否一样，计算左上的值
		int temp;
		for (int i = 1; i <= len1; i++) {
			for (int j = 1; j <= len2; j++) {
				if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
					temp = 0;
				} else {
					temp = 1;
				}
				// 取三个值中最小的
				dif[i][j] = min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1,
						dif[i - 1][j] + 1);
			}
		}
		// 取数组右下角的值，同样不同位置代表不同字符串的比较
		step =  dif[len1][len2];
		// 计算相似度
		float similarity = 1 - (float) dif[len1][len2]
				/ Math.max(str1.length(), str2.length());

		return similarity;
	}

	// 得到最小值
	private static int min(int... is) {
		int min = Integer.MAX_VALUE;
		for (int i : is) {
			if (min > i) {
				min = i;
			}
		}
		return min;
	}

	//字符串方法匹配
	public static float compareString(){
		float percentage = 0;
		String str1 = Dao.getFilequestion();
		String str2 = Dao.getSearchquestion();
		Log.i("steps0:","step0:getstr1"+str1+",str2:"+str2);
		str1 = str1.replaceAll(regex, "").trim();
		int index = str2.indexOf("<em>");
		String temp = str2.substring(index+3,str2.length()-1).replaceAll("</{0,1}em>|</{0,1}br>|\\s+", "");
		str2 = temp.replaceAll(regex, "").trim();
		int str1len = str1.length();
		int str2len = str2.length();
		if(str1len>str2len){
			str1 = str1.substring(0,str2len);
		}else{
			str2 = str2.substring(0,str1len);
		}
		percentage = compare(str1, str2);
		Log.i("steps0:","step0:getstr2"+str1+",str2:"+str2);
		return percentage;
	}

	//字符串方法匹配模糊版
	public static float compareString2(){
		String str1 = Dao.getFilequestion();
		String str2 = Dao.getSearchquestion();
		String temp = "";
		int index = str2.indexOf("<em>");
		Document doc = Jsoup.parse(str2);
		Elements ele = doc.getElementsByTag("em");
		for(Element e:ele){
			temp += e.text();
		}
		str2 = temp;
		float percentage = compare(str1, str2);
		return percentage;
	}




	//将挖出的问题，与文档中的问题进行匹配
	public static void main(String[] args) {
	//	doresult("操作系统为保证未经文件所有者授权则任何其他用户不得使用该文件的解决办法是",
		//		"<em>操作系统为保证未经文件</em>拥有<em>者授权</em>,<em>任何其他用户不</em>能<em>使用该</em>文件所提供的解决方法是( )。");

	}
}
