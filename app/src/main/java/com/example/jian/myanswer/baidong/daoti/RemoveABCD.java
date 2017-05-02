package com.example.jian.myanswer.baidong.daoti;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class RemoveABCD implements Remove{
	String result;
	String regex = "[A-Ja-j]\\s{0,2}[、.）)]";
	@Override
	public void deal(String name,int no,String value) {
		// TODO 自动生成的方法存根
		result = value;
		if(name.indexOf("选择")!=-1){
			Matcher matcher;
			StringBuilder sb = new StringBuilder();
			List<String> temp = Dealt2.readDataByLine(value);
			String t = "";
			for(int i=0;i<temp.size();i++){
				if(i==0)
					sb.append(temp.get(0));
				t = temp.get(i).trim();
				matcher = Pattern.compile(regex).matcher(t);
				if(matcher.find()){
					if(t.indexOf(matcher.group(0))<2)
						sb.append("\n"+t.replace(matcher.group(),"@##@"));
				}
			}
			result =sb.toString();
		}
	}

	@Override
	public String getResult() {
		// TODO 自动生成的方法存根
		return result;
	}

}
