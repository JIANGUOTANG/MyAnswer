package com.example.jian.myanswer.baidong.daoti;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class Word {
	private static String data;
	Matcher matcher;
	List<byte[]> image = new ArrayList<byte[]>();

	public Word(){}

	//判断文件类型 =》选择相应读取方法
	public Word(String filename){
		File file = new File(filename);
		String name = filename.substring(filename.length()-3,filename.length());
		int namesindex = filename.lastIndexOf("\\");
		String names = filename.substring(namesindex+1, filename.length()-4);
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
		Dao.setImage(image);
		readData(data);
		data = data.replaceAll("　", "");
		Dao.setData(data);
	}

	//读取word文档的文本信息
	public void docFile(File file){
		WordExtractor extractor = null;
		data = "";
		try {
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			HWPFDocument document = new HWPFDocument(fis);
			//List<String> resulttype = new ArrayList<String>();
			for(Picture picture: document.getPicturesTable().getAllPictures()){
				if(picture.getContent().length!=0){
					image.add(picture.getContent());
					//	String type = picture.getMimeType();
					//	type = type.substring(type.lastIndexOf("/")+1);
					//	resulttype.add(type);
				}
			}
			extractor = new WordExtractor(document);
			String[] fileData = extractor.getParagraphText();
			for(int i = 0;i<fileData.length;i++){
				if(fileData[i] != null){
					data+=fileData[i];
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//读取word文档的文本信息
	public void docxFile(File file){
		data = "";
		XWPFWordExtractor extractor = null;
		String fileData = null;
		try {
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			XWPFDocument document = new XWPFDocument(fis);
			extractor = new XWPFWordExtractor(document);
			CTDocument1 s = document.getDocument();
			String documentData = s.toString();    //dom格式的数据
			List<String> imageIndex = new ArrayList<String>();
			String[] pic_text = documentData.split("<pic:pic");
			String[] image_text = documentData.split("v:imagedata");
			String temp = "";
			for(String value:pic_text){
				temp = value.substring(value.lastIndexOf("<w:t>"),value.lastIndexOf("</w:t>"));
				imageIndex.add(temp);
				System.out.println("pic_length:"+pic_text.length+",temp::::::"+temp);
			}
			for(String value2:image_text){
				if(value2.lastIndexOf("</w:t>")!=-1)
					temp = value2.substring(value2.lastIndexOf("<w:t>"),value2.lastIndexOf("</w:t>"));
				imageIndex.add(temp);
				System.out.println("image_length:"+image_text.length+",temp2::::::"+temp);
			}

//			for(int i=0;i<10;i++){
//				int index = -1;
//				if((index = documentData.indexOf("pic:pic"))!=-1||(index = documentData.indexOf("v:imagedata"))!=-1){
//					String temp = documentData.substring(0,index);
//					imageIndex.add(temp.substring(temp.lastIndexOf("<w:t>"),temp.lastIndexOf("</w:t>")));
//				}
//			}

			for(String img:imageIndex){
				System.out.println("img_resource:::::"+img);
			}

			//System.out.println("s2:::::::::"+documentData);    //结果文档   文字: <wt>   图片: <pic:pic></pic:pic>   <imagedata></imagedata>
			//自动生成序号（1，2，3，4，5。。。   A,B,C....）     <w:numPr> <w:ilvl w:val="1"/> <w:numId w:val="1"/> </w:numPr>

			for (XWPFPictureData picture : document.getAllPictures()) {
				image.add(picture.getData());
			}
			fileData = extractor.getText();
			data = fileData;
		} catch (Exception e) {
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
			}
			br.close();
			data = sb.toString();
			br.close();
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
			//File f = new File(name,"utf-8");
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
	//获取doc,dox文档中的图片
	static final int BUFFER = 2048;
	public String getImageFromDocx(String docxfile,String destDir){
		String docxname = docxfile.substring(docxfile.lastIndexOf("\\")-1);
		try{
        	String inputFilename = docxfile;
            String unZipPathname = destDir+docxname.substring(0,docxname.length()-5)+"_";
            ZipFile zipFile = new ZipFile(inputFilename);
            Enumeration<?> enu = zipFile.entries();
            while(enu.hasMoreElements()){
                ZipEntry zipEntry = (ZipEntry)enu.nextElement();
                if(zipEntry.isDirectory()){
                //	if(zipEntry.getName().equals("word/media/")){
                		new File(unZipPathname+zipEntry.getName()).mkdirs();
                //	}
                	continue;
                }
                BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(zipEntry));
              //  if(zipEntry.getName().indexOf("word/media/")!=-1){
                File file = new File(unZipPathname+zipEntry.getName());
                File parent = file.getParentFile();
                if(parent != null && !parent.exists()){
                	parent.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos,BUFFER);
                int count;
                byte[] array = new byte[BUFFER];
                while((count = bis.read(array, 0, BUFFER))!=-1){
                	bos.write(array, 0, BUFFER);
                }
                bos.flush();
                bos.close();
                bis.close();
              //  }
            }
            return unZipPathname+"word/media";
		}catch(Exception e){
        	e.printStackTrace();
        	return null;
        }
	}

	//从doc文档中获取图片
//	public List<byte[]> getImageFromDoc(String path,String savepath)throws Exception{
//		FileInputStream fis = new FileInputStream(new File(path).getAbsolutePath());
//		HWPFDocument hdocument = new HWPFDocument(fis);
//		List<byte[]> result = new ArrayList<byte[]>();
//		List<String> resulttype = new ArrayList<String>();
//		for(Picture picture: hdocument.getPicturesTable().getAllPictures()){
//			if(picture.getContent().length!=0){
//				result.add(picture.getContent());
//				String type = picture.getMimeType();
//				type = type.substring(type.lastIndexOf("/")+1);
//				resulttype.add(type);
//			}
//		}
//
//		for(int i=0;i<result.size();i++){
//			String type = resulttype.get(i);
//			ByteArrayInputStream bis = new ByteArrayInputStream(result.get(i));
//			Iterator<?> readers = ImageIO.getImageReadersByFormatName(type);
//			ImageReader reader = (ImageReader) readers.next();
//			Object source = bis;
//			ImageInputStream iis = ImageIO.createImageInputStream(source);
//			reader.setInput(iis, true);
//			ImageReadParam param = reader.getDefaultReadParam();
//			Image image = reader.read(0, param);
//			BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
//			Graphics2D g2 = bufferedImage.createGraphics();
//			g2.drawImage(image, null, null);
//			File imageFile = new File(savepath+"image"+i+"."+type);
//			ImageIO.write(bufferedImage, type, imageFile);
//		}
//
//		return null;
//	}

	//按行读取数据  and read image
	public static void readData(String str){
		StringBuilder sb = new StringBuilder();
		//封装ByteArrayInputStream-->InputStreamReader-->BufferedReader
		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(str.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
		String line;
		try {
			while ( (line = br.readLine()) != null ) {
				if(line.indexOf("")!=-1){
					line = line.replaceAll(" ","<<image>>");
				}
				sb.append(line+"   \n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		data = sb.toString();
	}

	//获取数据
	public static String getData(){
		return data;
	}

}
