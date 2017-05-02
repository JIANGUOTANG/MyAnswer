package com.example.jian.myanswer.baidong;

/**
 * Created by Administrator on 2017/3/9 0009.
 */


import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
    从库当中获取选择题，以及大题的答案解析。
 */


public class GetBaiduWenKu {
    private static String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) ";
    private static String url = ""; //当前匹配问题的文档地址
    private static List<String> urls = new ArrayList<String>();   //文档内容所有的请求头地址
    private static String tag = "";
    private static String lasttag = "";
    private static int len = 0;

    // type 处理类型(整型):
    // (1).跳转百度高考页面获取答案。
    // (2).不进行任何函数调用，直接返回无答案
    private static boolean type = false;

    public static String getBaiduQuestionUrl() {  //获取百度文库问题的url地址
        String question = Dao.getFilequestion();
        lasttag = question.substring(question.length()-5,question.length()-1);
        len =question.length();
        if (question.length() > 38)
            question = question.substring(0, 37);
        Dao.setFilequestion(question);
        try {
            question = new String(question.getBytes("gb2312"), "gb2312");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String surl = "http://wenku.baidu.com/search?word=" + question
                + "BF&lm=0&org=0";
//        try {
//            Document doc = Jsoup.connect(surl).userAgent("").get();
//            Elements ele = doc.getElementsByTag("dl"); // 最外层
//
//            String temp = "";  //临时值
//            int s = 5;   //获取个数
//            double percentage = 0.0;
//            double maxpercentage = 0.0;
//            for(Element e:ele){
//                temp = e.select("p.summary").html();
//                temp = temp.substring(temp.indexOf("<em>"));
//                Dao.setSearchquestion(temp);
//                //Log.i("whatthefuck","百度000："+StringSimilarity.compareString()+":::::"+e.html());
//                if(e.text().contains("百度高考")&&StringSimilarity.compareString()>0.50){
//                    type = 1;
//                    //Log.i("whatthefuck","百度111："+StringSimilarity.compareString()+",,,,"+temp);
//                    url = e.select("a[href]").attr("href");
//                    return e.select("a[href]").attr("href");
//                }else if((percentage = StringSimilarity.compareString())>maxpercentage){
//                    maxpercentage = percentage;
//                    tag = e.select("p.summary em").first().text(); //获取要匹配的题目标题
//                    url = e.select("a[href]").attr("href"); //url跳转地址
//                    Log.i("whatthefuck","百度2："+temp);
//                }
//                if(--s<0){  //最多获取5次
//                    break;
//                }
//            }
//            if(maxpercentage<0.5){  //匹配度小于0.5
//                type = 3;
//                Log.i("whatthefuck","百度3："+temp);
//                return "p<0.5,无准确答案";
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return surl;
    }

    //获取百度选择题url
    public static String getSelectionUrl(String surl,String contain){
        try {
            Document doc = Jsoup.connect(surl).userAgent("").get();
            Elements ele = doc.getElementsByTag("dl"); // 最外层

            String temp = "";  //临时值
            int s = 5;   //获取个数
            double percentage = 0.0;
            double maxpercentage = 0.0;
            for(Element e:ele){
                temp = e.select("p.summary").html();
                temp = temp.substring(temp.indexOf("<em>"));
                Dao.setSearchquestion(temp);
                int titleindex = e.html().indexOf("title");
                String title =e.html().substring(titleindex+7,titleindex+10);
                if(title.equals("ppt")){
                    s++;
                }
                else if(e.text().contains(contain)&&StringSimilarity.compareString()>0.50){
                    type = true;
                    url = e.select("a[href]").attr("href");
                    return e.select("a[href]").attr("href");
                }else if((percentage = StringSimilarity.compareString())>maxpercentage){
                    maxpercentage = percentage;
                    tag = e.select("p.summary em").first().text(); //获取要匹配的题目标题
                    url = e.select("a[href]").attr("href"); //url跳转地址
                }
                if(--s<0){  //最多获取5次
                    break;
                }
            }
            if(maxpercentage<0.5){  //匹配度小于0.5
                type = false;
                return "p<0.5,无准确答案";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static void mutiProcess() throws InterruptedException, ExecutionException, TimeoutException {  //启动线程
        Future<String> future;
        final ExecutorService executorService = Executors.newFixedThreadPool(1);
        Callable<String> call = new Callable<String>() {
            @Override
            public String call() throws Exception {
                getBaiduQuestionUrl();
                return null;
            }
        };
        try {
            future = executorService.submit(call);
            future.get(8, TimeUnit.SECONDS);
        } catch (TimeoutException ex) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            future = executorService.submit(call);
            future.get(8, TimeUnit.SECONDS);
            executorService.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    public static List<String> getBaiduDocumentUrl(String url) { //获取百度文库文档的请求头链接
        String answer = "";
        String dataurl = "";
        int index = 0;
        String temp = "";
        try {
            if (url != "") {
                Document doc = Jsoup.connect(url).get();
                String all = doc.html();
                Log.i("ffffffffffff","all000"+all.substring(10000));
                int jsonindex = all.indexOf("json");
                all = all.substring(jsonindex, jsonindex+15000);
                Log.i("ffffffffffff","all11111"+all);
                if(all.indexOf("]")!=-1)
                    all = all.substring(0, all.indexOf("]"));
                while((index = all.indexOf("http"))!=-1){
                    all = all.substring(index,all.length());
                    int locate = all.indexOf("}");
                    temp = all.substring(0, locate-3);
                    urls.add(temp);
                    Log.i("ffffffffffff","all2222"+temp);
                    all = all.substring(locate, all.length());
                }
                //	all = all.substring(all.indexOf("htmlUrls"), all.length() - 1);
                //	all = all.substring(all.indexOf("http"), all.length() - 1);
                //	all = all.substring(0, all.indexOf("}") - 3);
                StringBuilder sb = new StringBuilder();
                String tempurl = "";
                int size = urls.size();
                for(int i = 0; i < size; i++){
                    tempurl = urls.get(i);
                    for (char a : tempurl.toCharArray()) {
                        if (a != 92) {
                            sb.append(a);
                        }
                    }
                    dataurl = sb.toString();
                    urls.set(i,dataurl);
                    sb = new StringBuilder();
                    //Thread.sleep(1000);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urls;
    }

    public static String getTest(String url) { // 获取试卷的内容
        BufferedReader in = null;
        String result = "";
        HttpURLConnection urlConnection;
        try {
            URL myURL = new URL(url);
            urlConnection = (HttpURLConnection) myURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection
                    .setRequestProperty(
                            "User-Agent",
                            "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
            urlConnection.setUseCaches(false);
            urlConnection.setDoInput(true);
           // urlConnection.setDoOutput(true);
            urlConnection.connect();
            in = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream(), "gb2312"));
            // 用来临时存储抓取到的每一行的数据
            String line;
            while ((line = in.readLine()) != null) {
                // 遍历抓取到的每一行并将其存储到result里面
                result += line + "\n";
            }
            result = decode(result);
            StringBuilder sb = new StringBuilder();

            //加工处理数据
            Matcher matcher; //= Pattern.compile("c\":\"[\u4e00-\u9fa5a-zA-Z0-9_，：。？→（）*-；、]+\"").matcher(result);
            matcher = Pattern.compile("(c\":\"[\u4e00-\u9fa5a-zA-Z0-9_，：。？→（）*-；、]+\")").matcher(result);
            while (matcher.find()) {
                //System.out.println();
                sb.append(matcher.group().substring(3));
            }
            result = sb.toString().replaceAll("\"", " ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

    public static String decode(String unicodeStr) { // 编码解析中文
        if (unicodeStr == null) {
            return null;
        }
        StringBuffer retBuf = new StringBuffer();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5)
                        && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
                        .charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(
                                unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                else
                    retBuf.append(unicodeStr.charAt(i));
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }

    public static String deal(String s){  //处理分行
        String str = s;
        int lastindex = 0;
        int index = 0; //开始标记位置
        int index2 = 0; //结束标记位置
        String temp = ""; //截取值
        String regex = "";
        StringBuilder sb = new StringBuilder(); //存储题目
        Matcher matcher = Pattern.compile("[0-9]+\\s{0,2}、").matcher(str);
        while(matcher.find()){
            temp = matcher.group();
            index = (str.substring(index, str.length())).indexOf(temp)+lastindex;
            if(lastindex==0){
                sb.append(str.substring(0,index)+"\n");
            }else{
                if(65<str.charAt(lastindex-1)&&str.charAt(lastindex-1)<122){
                    sb.append(str.substring(lastindex,index));
                }
                else{
                    sb.append(str.substring(lastindex,index)+"\n");
                }
            }
            lastindex = index;
        }
        sb.append(str.substring(lastindex, str.length()));
        return sb.toString();
    }

    public static void answer(){  //返回单个问题文本答案
        String answer = "无";
        try {
            Document doc = Jsoup.connect(url).userAgent("").get();
            String data = doc.html();
            data = data.substring(12000,data.length());
            Elements ele = doc.select("div.exam-answer-content p");
            if(!ele.isEmpty())
                answer = ele.first().text();
            String temp;
            temp = data.substring(data.indexOf((Dao.getFilequestion().substring(2,5))));
            temp = temp.substring(temp.indexOf(answer));
            temp = temp.substring(1,temp.indexOf("<"));
            Dao.setAnswer(answer+temp);
            int index = -1;
            if((index = data.indexOf("解析"))!=-1){
                data.substring(index);
                Dao.setExplain(doc.select("div.exam-analysis p").text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void answer2(){  //返回一个文档中的答案 （大题，非选择题）
        String answer = "";
        String doc = Dao.getData();
        int answerindex = -1;
        Matcher matcher = Pattern.compile("[\u4e00-\u9fa5a-zA-Z0-9]+").matcher(lasttag);
        if(matcher.find())
            lasttag = matcher.group();
        int index = doc.indexOf(lasttag);
        if(index!=-1)
            doc = doc.substring(index);
        Log.i("fffffffff","tag:"+lasttag+"url:"+url);
        if(index!=-1){
            Matcher matcher2 = Pattern.compile("答|解|证明").matcher(answer);
            answer = doc.substring(0, doc.length());
            if(matcher2.find()){
                if((answerindex = answer.indexOf(matcher2.group()))!=-1) {
                    answer = answer.substring(answerindex,answer.length());
                    matcher = Pattern.compile("[0-9一二三四五六七八]+[、.][\u4e00-\u9fa5a-zA-Z]").matcher(answer);
                    if(matcher.find())
                        answer = answer.substring(0,answer.indexOf(matcher.group())-2);
                }
                Log.i("fffffffff","lastanswer-1:"+answer);
            }else{
                answer = doc.substring(doc.indexOf(lasttag)+3);
                matcher = Pattern.compile("[0-9一二三四五六七八]+[、.][\u4e00-\u9fa5aa-zA-Z]").matcher(answer);
                Log.i("fffffffff","lastanswer0:"+answer);
                if(matcher.find())
                    answer = answer.substring(2,answer.indexOf(matcher.group())-2);
                Log.i("fffffffff","lastanswer:"+answer);
            }
        }
        Dao.setAnswer(answer);
        Log.i("fffffffff","answer:"+answer);
    }


    public static void getSelectionAnswer(){   //调用整个挖取过程
        getSelectionUrl(getBaiduQuestionUrl(),"百度高考");
        if(type){
            answer();
        }
    }

    //大题答案获取，文档抓取解析
    public static String getNonSelectionAnswer(){
        getSelectionUrl(getBaiduQuestionUrl(),"");
        StringBuilder data = new StringBuilder();
        String datas = "";
        //获取js数据地址
        if(url!="") {
            getBaiduDocumentUrl(url);
            for (String url : urls)  //获取所有js数据
                data.append(getTest(url));
            datas = data.toString();
            datas = deal(datas);
            datas = datas.replaceAll(" ", "");
            Dao.setData(datas);
            answer2();
        }
        return null;
    }

}
