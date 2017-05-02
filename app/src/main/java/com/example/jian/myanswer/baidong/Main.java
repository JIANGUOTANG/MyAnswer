package com.example.jian.myanswer.baidong;


import android.util.Log;

//查询答案方法的调用
public abstract class Main {

	private String[] answerAndExplain  = {"无","无"};
	Dealt dealt = new Dealt();

	public Main(){}
	//1）选择题查询答案，返回答案和解析
	public String[] selection(String str){
		// 将获取的问题:str 转换成url，访问的链接
		String url = GetAnswer.questionToURL(str);
		Log.i("steps:","step1:url:"+url);
		// 访问链接并下载页面内容
		String result = GetAnswer.downloadWeb(url);
		String answer = "无";
		//将下载下来的页面查找匹配的问题,获取url
		Log.i("urljian",result);
		GetAnswer.getQuestionUrl(result);
		if(Dao.isStatus()) {
			//访问匹配的问题的answerUrl，下载答案页面
			answer = GetAnswer.getAnswerUrlAndSelect();
			//设置答案
			Dao.setAnswer(GetAnswer.getAnswer(answer));
			//设置解析
			Dao.setExplain(GetAnswer.getExplain(answer));
		}
		else{
				GetBaiduWenKu.getSelectionAnswer();
		}
		if(Dao.getAnswer().length()<1){
			Dao.setAnswer("无");
		}
		if(Dao.getExplain().length()<1){
			Dao.setExplain("无");
		}
		answerAndExplain[0] = Dao.getAnswer();
		answerAndExplain[1] = Dao.getExplain();
		MainRespond(answerAndExplain[0],answerAndExplain[1]);
		return answerAndExplain;
	}

	//2）简答题，应用题，综合题等大题获取答案
	public void question(String str){
		Dao.setFilequestion(str);
		GetBaiduWenKu.getNonSelectionAnswer();

	}
   public abstract  void MainRespond(String answer,String explain);
}
