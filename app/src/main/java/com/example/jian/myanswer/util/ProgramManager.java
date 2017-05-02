package com.example.jian.myanswer.util;

import android.util.Log;

import com.example.jian.myanswer.baidong.Main;
import com.example.jian.myanswer.bean.JsonProGram;
import com.example.jian.myanswer.bean.program.Contents;
import com.example.jian.myanswer.bean.program.Item;
import com.example.jian.myanswer.bean.program.Items;
import com.example.jian.myanswer.bean.program.Program;
import com.example.jian.myanswer.greendao.DaoSession;
import com.example.jian.myanswer.greendao.JsonProGramDao;
import com.example.jian.myanswer.word.App;
import com.google.gson.Gson;

import org.greenrobot.greendao.query.Query;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.jian.myanswer.util.ImportProgramUtil.name;

/**
 * Created by jian on 2017/4/15.
 */
public class ProgramManager {
    private  Program program;
    private Contents content;
    private List<Items> items;

    public List<Items> getItems() {
        return items;
    }

    public static final int SINGLECHOICE = 1;//单选题
    public static final int MUTIPLECHOICE = 2;//多选题
    public static final int CHECKING= 3;//判断题
    public static final int GAPFILLING = 4;//填空题
    public static final int OPERATING = 5;//操作题，大题，只有题目和答案，没选项，通常是无法在手机上完成答题的
    //更新题目，选中了答案，或答题后就会需要更新,或者网上的答案
    public ProgramManager() {

    }
    public void init(Program program){
        this.program = program;
        content = this.program .getContent();
        items = content.getItems();
    }
    public  void updateItem(int location, int position, String answer){
        Items item = this.items.get(location);
        List<Item> item1 = item.getItem();//一道大题的全部小题
        Item item2 = item1.get(position);//被修改的那道小题
        item2.setAnswer(answer);//更新了答案
    }
    /**
     *
     * @param location 第几道大题
     * @param position 第几题
     * @param answer 答案
     */
    public  void updateAnswerFromNet(int location,int position,String answer){
        Items item = this.items.get(location);
        List<Item> item1 = item.getItem();//一道大题的全部小题
        Item item2 = item1.get(position);//被修改的那道小题
        item2.setAnswerFromNet(answer);//更新了网上查找的答案
    }
    public void createProgram(String title,String time,String author){
        content = new Contents(items);
        program = new Program(time,author,title,content);
        Gson gson = new Gson();
        String json = gson.toJson(program);//把整个program生成json格式
    }
    /**
     *
     * @param item  一道题（大题）
     * @param type  题目类型
     * @param location 第几道题（大题）
     */
    public void addItem(int type,int location,List<Item> item){
        Items item1 = new Items(type,location,item);
        items.add(item1);
    }
    private static DaoSession daoSession;
    private JsonProGramDao jsonProGramDao;
    /**
     * 根据试卷名称来查找试卷
     * @param name
     *
     * @return
     */
    JsonProGram jsonProGram;
    public Program getProgram(String  name){
        daoSession = App.getDaoSession();
        jsonProGramDao = daoSession.getJsonProGramDao();
        Query<JsonProGram> build = jsonProGramDao.queryBuilder().where(JsonProGramDao.Properties.Name.eq(name)).build();
        List<JsonProGram> list = build.list();
        Program program = null;
        if(list.size()>0){
            jsonProGram = list.get(0);
            Gson gson = new Gson();
            //把json转化为试题类
            program=gson.fromJson(jsonProGram.getProgramJson(),Program.class);
           //初始化其他数据
            this.program = program;
            content = this.program .getContent();
            items = content.getItems();
        }

       return program;
    }
    public void saveProgram(){
        Gson gson = new Gson();
        String s = gson.toJson(program);
        Log.i("jian",s);
        if(jsonProGram==null){
            jsonProGram = new JsonProGram();
            jsonProGram.setProgramJson(s);
            jsonProGram.setName(program.getTitle());
            jsonProGramDao.insert(jsonProGram);
        }else{
            jsonProGram.setProgramJson(s);
            jsonProGramDao.update(jsonProGram);
        }
    }
    //查找试卷的答案
    public void searchAnswer(){
        for(Items items1:items){
            switch (items1.getType()){
                case SINGLECHOICE:
                    List<Item> item = items1.getItem();
                    for(Item item1:item){
                        if (item1.getAnswer().equals("")){
                            new GetSelectAnswerThread(item1.getQuestion(),item1).start();
                        }
                    }
                    break;
                case MUTIPLECHOICE:
                    List<Item> item2 = items1.getItem();
                    for(Item item1:item2){
                        if (item1.getAnswer().equals("")){
                            new GetSelectAnswerThread(item1.getQuestion(),item1).start();
                        }
                    }
                    break;
                case CHECKING:

                    break;
                case GAPFILLING:
                    List<Item> item3 = items1.getItem();
                    for(Item item1:item3){
                    }
                    break;
                case OPERATING:
                    List<Item> item4 = items1.getItem();
                    for(Item item1:item4){
                    }
                    break;
            }
        }
    }

    //查找选择题的答案
    class GetSelectAnswerThread extends Thread {
        private volatile Thread t;
        private Item  item;
        public GetSelectAnswerThread(String questionName,Item item) {
           this.item = item;
        }
        public void run() {
            try {
                //选择题的
                Main mainSelect = new Main() {
                    @Override
                    public void MainRespond(String answer, String explain) {
                        String regex = "[A-Ha-h]+";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(answer);
                        if(matcher.find()){
                            item.setAnswer(matcher.group(0));
                        }
                        item.setAnalyze(explain);
                    }
                };
                mainSelect.selection(name);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        private void mStop(){
            t = null;
        }
        public void start () {
//            if (t == null) {
            t = new Thread (this);
            t.start ();
//            }else{
//                interrupt();
//                t.start ();
//
//            }
        }
        public void interrupt(){
            if(!t.isInterrupted()){
                t.interrupt();
            }
        }
    }
    //查找其他题的答案
    class GetAnswerThread extends Thread {
        private volatile Thread t;

        public GetAnswerThread(String questionName, Thread t) {
            this.t = t;
        }
        public void run() {
            try {
//                mainSelect.selection(name);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        private void mStop(){
            t = null;
        }
        public void start () {
//            if (t == null) {
            t = new Thread (this);
            t.start ();
//            }else{
//                interrupt();
//                t.start ();
//
//            }
        }
        public void interrupt(){
            if(!t.isInterrupted()){
                t.interrupt();
            }
        }
    }
}
