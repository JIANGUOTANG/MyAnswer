package com.example.jian.myanswer.baidong.daoti;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveRepeat implements Remove {
	String result;

	@Override
	public void deal(String name, int no, String value) {
		// TODO 自动生成的方法存根
		//处理异常数据
		Matcher matcher = Pattern.compile("[0-9]{1,2}").matcher(value);
		int n = -1;
		if(matcher.find()){
			n = Integer.parseInt(matcher.group());
			if(no != n+1&&no != n+2){
				result = "";
			}else if(value.trim().indexOf(matcher.group(0))>3){
				result = "";
			}
		}
	}

	@Override
	public String getResult() {
		// TODO 自动生成的方法存根
		return result;
	}

}
