package com.example.jian.myanswer.baidong;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public  class Get {
     String url = "";
     String answerurl = "";
     int i = 0;

    public  String sendGet(String url) // 截取的网页所有内容
    {
        String result = ""; // 定义一个字符串用来存储网页内容
        try {
            System.out.println(url);
            result = okGet(url);
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
            if (i < 0) {   //报错递归重发
                try {
                    Thread.sleep(2000);
                    sendGet(url);
                    i++;
                } catch (InterruptedException e1) {
                    // TODO 自动生成的 catch 块
                    e1.printStackTrace();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        try {
            result = new String(result.getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
     String result = "";

	public  interface  GetReapond{
		void respond();
	}
	private  GetReapond getReapond;

	public  void setGetReapond(GetReapond getReapond) {
		this.getReapond = getReapond;
	}
    OkHttpClient okHttpClient;
	private   String okGet(final String url) {
         okHttpClient = new OkHttpClient();
        okHttpClient.retryOnConnectionFailure();
        Request.Builder builder = new Request.Builder().url(url);
        builder.addHeader("Connection", "close");
        Request request = builder.build();
        Call mCall = okHttpClient.newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                okGet(url);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null != response.cacheResponse()) {
                    result = response.networkResponse().toString();
                  //  Log.i("jian", "cache---" + result);
                } else {
                  result =  response.body().string();
                    //result = response.networkResponse().body().toString();
                 //   Log.i("jian", "cache---" + result);
                }
                if(!result.equals("")) {
                    if(!getURL(result).equals("")){
                 sendGetGbk(getURL(result));}

                }
               else{
                    Dao.setAnswer("没找到答案");
                    Dao.setAnswer("没找到解析");
                }

            }
        });
        return result;
    }
   private  String getgbkResult ="";
    public  String sendGetGbk(String url) // 截取的网页所有内容
    {

    //    String result = ""; // 定义一个字符串用来存储网页内容

        OkHttpClient okHttpClient  = new OkHttpClient();

        Request.Builder builder = new Request.Builder().url(url);
        builder.addHeader("Connection", "close");
        Request request = builder.build();
        Call call= okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null != response.cacheResponse()) {
                    getgbkResult = response.networkResponse().toString();
                    //  Log.i("jian", "cache---" + result);
                } else {
                    getgbkResult =  response.body().string();
                    //result = response.networkResponse().body().toString();
                    //   Log.i("jian", "cache---" + result);

                }
                Dao.setAnswer(getAnswer(getgbkResult));
                Dao.setExplain(explain(getgbkResult));
                getReapond.respond();
            }
        });

        return getgbkResult;

    }

    public  String getURL(String result) { // 从网页中获取答案(url)地址
        System.out.println(result + "ff");
        Pattern pattern = Pattern
                .compile("http://www.ppkao.com/shiti/[0-9]+/"); // 获取url
        // 定义一个matcher用来做匹配
        Matcher matcher = pattern.matcher(result);
        if (matcher.find()) {
            url = matcher.group();

            //url = matcher.group().substring(6, url.length() - 1);
            url = "http://user.ppkao.com/mnkc/shiti/?id="
                    + url.substring(27, url.length() - 1);// 转换url为有答案的url

        } else {
            //
            System.out.println("错啦");
            url = "";
        }
        url = toURLString(url);
        System.out.println("jian"+url);
        return url;
    }

    //答案获取
    public  String getAnswer(String result) {
        String answer = "竟然没答案...w(ﾟДﾟ)w";
        System.out.println(result + "ying");
        Pattern pattern = Pattern.compile("(参考答案：+(\\w))|(试题答案</i>.*<span>.*</span>)");
        Matcher matcher = pattern.matcher(result);
        if (matcher.find())
            answer = matcher.group();
        return answer;
    }

    //问题转换为url访问地址
    public  String questionToURL(String str) {

        String url = "http://s.ppkao.com/cse/search?q=" + str + "&s=7348154799869824824";
        url = toURLString(url);
        return url;
    }

    public  String toURLString(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                try {
                    sb.append(URLEncoder.encode(String.valueOf(c), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    //查找答案解析
    public  String explain(String result) {
        String explain = "Σ( ° △ °|||)︴阿~没解析,根本搞不懂阿";
        Pattern pattern = Pattern.compile("(?<=clearfix\"><b></b>)[\\s\\S]+?(?=</div>)");
        Matcher matcher = pattern.matcher(result);
        if (matcher.find()) {
//            explain = matcher.group();
//            int end = explain.indexOf("</div>");
            explain = matcher.group();
        }
        return explain;
    }

    public  void question(String str) { //遍历选择题

    }

    public  void questions(String[] str) {
        for (String s : str) {
            url = questionToURL(s); //访问问题url
            sendGet(url);
            answerurl = getURL(sendGet(url)); //访问答案url
            String answer = getAnswer(answerurl); //答案
            String explain = explain(answerurl);  //解析
            System.out.println("answer:" + answer + ",explain:" + explain);
        }
    }


}