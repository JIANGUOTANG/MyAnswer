package com.example.jian.myanswer.baidong;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;


public class Word {

	private static String data;
	private static StringBuffer datas;
	Matcher matcher;

	public Word(){}

	//判断文件类型 =》选择相应读取方法
	public Word(String filename){
		File file = new File(filename);
		String name = filename.substring(filename.length()-3,filename.length());
		int namesindex = filename.lastIndexOf("\\");
		String names = filename.substring(namesindex+1, filename.length()-4);
		System.out.println(names);
		switch(name){ //根据后缀名，判断文件类型
			case "doc":
				docFile(file);
				exchangeTxt(names);
				break;
			case "ocx":
				docxFile(file);
				names = filename.substring(0, names.length()-2);
				exchangeTxt(names);
				break;
			case "txt":
				txtFile(file);
				break;
		}
		Dao.setData(data);
	}

	//读取word文档的文本信息
	public void docFile(File file){
		WordExtractor extractor = null;
		data = "数据为空！ㄟ( ▔, ▔ )ㄏ";
		try {
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			HWPFDocument document = new HWPFDocument(fis);
			extractor = new WordExtractor(document);
			String[] fileData = extractor.getParagraphText();
			for(int i = 0;i<fileData.length;i++){
				if(fileData[i] != null){
					//		System.out.println(fileData[i]);
					data+=fileData[i];
					//datas.append(fileData[i]);
					//data=datas.toString();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	//读取word文档的文本信息
	public void docxFile(File file){
		data = "数据为空！ㄟ( ▔, ▔ )ㄏ";
		XWPFWordExtractor extractor = null;
		String fileData = null;
		try {
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			XWPFDocument document = new XWPFDocument(fis);
			extractor = new XWPFWordExtractor(document);
			fileData = extractor.getText();
			//System.out.println(fileData);
			data = fileData;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	//读取text文本格式信息
	public void txtFile(File file){
		try {
			StringBuffer sb= new StringBuffer("");
			BufferedReader br=new BufferedReader(
					new InputStreamReader(new FileInputStream(file),"UTF-8")
			);
			String str = null;
			while((str = br.readLine()) != null) {
				sb.append(str+"\n");
				//  System.out.println(str);
			}
			br.close();
			// reader.close();
			data = sb.toString();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}


	//转换为text文本格式信息
	public void exchangeTxt(String name) {
		try {
			StringBuffer sb = new StringBuffer(data);
			FileWriter writer = new FileWriter(name+".txt");
			BufferedWriter bw = new BufferedWriter(writer);
			bw.write(sb.toString());

			bw.close();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//转换为text文本格式信息
	public void exchangeTxt(String name,String data) {
		try {
			StringBuffer sb = new StringBuffer(data);
			File f = new File(name,"utf-8");
			//FileWriter writer = new FileWriter(name+".txt");
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(name+".txt"),"utf-8"
					)
			);
			bw.write(sb.toString());
			bw.close();
			//writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	//获取数据
	public static String getData(){
		return data;
	}


}
