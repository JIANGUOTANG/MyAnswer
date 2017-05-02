package com.example.jian.myanswer.baidong;


import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/3/2 0002.
 */


/*
    从网络中获取答案(仅获取选择题，以及大题) ，暂不支持填空题，判断题等
 */

public class GetAnswer {

    static String questionurl = "";
    private static String url = "";
    private static int i = 0;
    private static Matcher matcher;
    private static String em = "";//获取题目标记<em>xxx</em>
    private static String item;
    private static int count = 5; //重复次数

    //第一步：将问题转换为url访问地址
    public static String questionToURL(String str){
        str = str.replaceAll(" ","");
        int strlen = str.length();
        if(str.charAt(0)<='9') {
            if(str.charAt(1)<='9')
                str = str.substring(3,strlen);
            else
                str = str.substring(2,strlen);
        }
        Dao.setFilequestion(str);
        String url = "http://s.ppkao.com/cse/search?q="+str+"&s=7348154799869824824";
        url = toURLString(url);
        return url;

    }

    //第二�?:进行url访问，并下载整个网页内容
    public static String downloadWeb(String url) //截取的网页所有内�?
    {
        String result = ""; // 定义�?个字符串用来存储网页内容
        BufferedReader in = null; // 定义�?个缓冲字符输入流
        InputStream is = null;
        try {

            URL realUrl = new URL(url);
            // 初始化一个链接到那个url的连�?
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();

            //设置请求方法
            connection.setDoInput(true); //允许输入流，即允许下载
            connection.setDoOutput(true); //允许输出流，即允许上传
            connection.setUseCaches(true); //不使用缓冲
            connection.setRequestMethod("GET"); //使用get请求
            // 获取输入流
            is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            in = new BufferedReader(isr);
            // 用来临时存储抓取到的每一行的数据
            String line;
            while ((line = in.readLine()) != null) {
                // 遍历抓取到的每一行并将其存储到result里面
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发�?�GET请求出现异常�?" + e);
            e.printStackTrace();
        }
        finally { // 使用finally来关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        try {
            result  = new String(result.getBytes(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            if(count>0){
                count--;
                downloadWeb(url);
            }
        }
        return result;
    }


    //第二步: 下载页面 doc模式
    public static String downloadWeb2(String url) // 截取的网页所有内�?
    {
        String result = ""; // 定义�?个字符串用来存储网页内容
        try {
            Document doc = Jsoup.connect(url).header("Accept-Encoding", "gzip, deflate").timeout(5000).userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)").get();
            result = doc.html().replaceAll("\n","");
            int index = 0;
            if(result.length()>3000)
                index = 3000;
            if(result.length()>5000)
                result = result.substring(index,result.length()-2000);
            else
                result = result.substring(index,result.length()-1);
        } catch (IOException e) {
            e.printStackTrace();
            if(i>0){
                i--;
                downloadWeb2(url);
            }
            System.out.println("页面加载异常");
        }
        return result;
    }


    //第三步:挖出匹配的题目和对应的答案题目url地址    result:下载下来的页�?
    public static void getQuestionUrl(String result) {

        String[][] questionUrl = new String[3][2];
        String url="";
        Document  doc = Jsoup.parse(result);
        //获取问题
        Elements ele = doc.select("div.result-abstract");

        //获取问题链接跳转的url
        Elements ele2 = doc.select("a[href].result");
        double p = 0;
        int i = 0;

        for(Element e:ele) {
            if(i==3)
                break;
            questionUrl[i][0] = e.html();
            i++;
        }
        i = 0;
        for(Element e:ele2) {
            if(i==3)
                break;
            questionUrl[i][1] = e.attr("href");
            i++;
        }
        String temp = "";  //临时存储匹配的问题
        double ptemp = 0; //临时匹配占比

        //在调�? StringSimilarity.result()前，�?先设置Dao中的问题变量�?
        for(int j=0;j<i;j++) {
            temp = questionUrl[j][0];
            Dao.setSearchquestion(temp);
            p = StringSimilarity.compareString();
            if (p > ptemp){
                ptemp = p;
            }
            if(p >= Const.percentage){
                em = temp.substring(temp.indexOf("<em>")+4,temp.indexOf("</em>"));
                url = questionUrl[j][1];
                break;
            }
        }
        //如果概率低于指定值，则调用百度查询
        if(p<Const.percentage){
            Dao.setStatus(false);
            System.out.println("调用百度文库查询");
        }else {
            questionurl = url;
            if (questionurl.length() < 8) {
                Dao.setStatus(false);
                Dao.setAnswer("无");
                Dao.setExplain("无");
            } else {
                Dao.setStatus(true);
            }
            Dao.setNowPercentage(p);
        }
    }

    //pp考获取答案解析
    //第四步，获取选项 和 答案地址url 并跳转到答案页，并获取页面内容
    public static String getAnswerUrlAndSelect() {
        String url = "";
        String select = "";
        int index = 0;
        questionurl = toURLString(questionurl);
        long stime = System.currentTimeMillis();
        long lasttime = 0;
        String result = downloadWeb2(questionurl);
        lasttime = System.currentTimeMillis();
        Log.i("timeuseaaaaa:::",questionurl+"    :["+((double)(lasttime-stime)/1000));
        stime = lasttime;
        matcher = Pattern.compile("[a-zA-Z0-9\\u4e00-\\u9fa5]+").matcher(em);
        if (matcher.find())
            em = matcher.group();
        index = result.indexOf(em); //题目开始的位置
        Log.i("getanswer","index"+index);
        Log.i("getanswer","result"+result);
       // if(result.indexOf("查看答案")!=-1){
            if (index != -1) {
                select = result.substring(index, result.length()-1);
                url = select.substring(select.indexOf("http"), select.length() - 1);
                if(url.indexOf(">")!=-1)
                    url = url.substring(0, url.indexOf(">") - 1);
                //int A = select.indexOf("A");
                matcher = Pattern.compile(">[a-gA-G][.。.)]?\\S+").matcher(select);
                if(matcher.find()){
                    select = select.substring(select.indexOf(matcher.group()), select.length() - 1);
                    select = select.substring(0, select.indexOf("</p>"));
                }
            }
            url = url.substring(0, url.lastIndexOf("/") + 2) + url.substring(url.indexOf("id"), url.length());
            item = select+"<br>";
//        }
        lasttime = System.currentTimeMillis();
        Log.i("timeusebbbbb:::",":"+((double)(lasttime-stime)/1000));
        stime = lasttime;
        result = downloadWeb2(url);

        Log.i("getanswer","result"+result);
        lasttime = System.currentTimeMillis();
        Log.i("timeuseccccc:::",url+"   :["+((double)(lasttime-stime)/1000));
        stime = lasttime;
        int emindex;
        if((emindex = result.indexOf(em))==-1)
            emindex = 0;
        result = result.substring(emindex,result.length()-1);
        return result;
    }

    //第五步:答案获取
    public static String getAnswer(String result){
        String answer="无";
        if(result!=" ") {
            matcher = Pattern.compile("答案：((\\w)|[a-zA-Z0-9\\u4e00-\\u9fa5]+)").matcher(result);
            if (matcher.find())
                answer = matcher.group();
            // String[] items = item.split("<br>");
            String temp = "";
            matcher = Pattern.compile("[A-J]").matcher(answer);
            if (matcher.find()) {
                temp = matcher.group();
                if(item.indexOf(temp)!=-1)
                    answer = item.substring(item.indexOf(temp), item.length() - 1);
                int s = answer.indexOf("<br");
                if (s > 0) {
                    answer = answer.substring(0, s);
                }
            }else{
                Document doc = Jsoup.parse(result);
                Elements ele = doc.getElementsByTag("span");
                answer = ele.first().text();
            }
        }
        int llen = answer.lastIndexOf("<b");
        if(llen!=-1){
            answer = answer.substring(0,llen-1);
        }
        answer = answer.replaceAll("</?(div||a||p||br||strong)>","");
        Log.i("GetAnswer",answer);
        return answer;
    }

    //第六步:查找答案解析
    public static String getExplain(String result){
        String explain = "无";
        int len ;
        String r = result;
        if((len = result.indexOf("解析"))!=-1){
            r = result.substring(len,result.length()-1);
        }
        if(r!="") {
            Document doc = Jsoup.parse(r);
            Elements ele = doc.getElementsByTag("span");
            explain = ele.first().text();
            int startindex = r.indexOf("<b></b>");
            if(startindex!=-1) {
                r = r.substring(startindex,r.length()-1);
                int last = r.indexOf("div");
                Log.i("dong","innnnn:"+last+":::::"+r);
                r = r.substring(7,last-2);
            }else{
                r = explain;
                Log.i("dong","outerrr:"+explain);
            }
        }
        explain = r.replaceAll("(</?p>)|(</?br>)|(/?span)","");  //去除tag符
        return explain;
    }

    //转换字符格式
    public static String toURLString(String s) {
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

}

