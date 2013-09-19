package SoftwareEngineer_HomeWork;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Spider类，用于进行数据收集
 */
public class Spider {
	/**
	 * Spider匹配URL的正则式
	 */
	private final static Pattern urlPat = Pattern.compile(
			"http://(\\w|\\.|/|-)+\\.?" +
			"(edu|gov|cc|info|html|com|cn|org|php|aspx|asp|jsp|net|\\d+)"); 
	/**
	 * Spider匹配Email Address的正则式
	 */
	private final static Pattern emailPat = Pattern.compile(
			"[\\w]+@[[\\w]*\\.[\\w]*]+");//涵盖xx@mail.com,对于xx[at]mail.com, xx#mail.com, xx at mail.com暂不考虑
	private static int EmailCount = 0;//统计邮箱地址个数
	private final static String sep = File.separator;
	private final static String crlf = System.getProperty("line.separator"); 
	private static Set<String> urls = new HashSet<String>();
	private static List<String> urlsQ = new ArrayList<String>();
	private static FileWriter fwURL = null;
	private static FileWriter fwEmail = null;
	private static BufferedWriter bwEmail = null;
	private static BufferedWriter bwURL = null;
	static SendEngine sendEng;//持有主类消息，用于显示抓取状态
	private static String strURL;
	private static int times;
	private static String keyWord;
	/**
	 * 
	 * @param keyWord 设置搜索关键字
	 */
	public void setKeyWord(String keyWord) {
		Spider.keyWord = keyWord;
	}
	/**
	 * 
	 * @param times 设置爬取页面次数
	 */
	public void setTimes(int times) {
		Spider.times = times;
	}
	/**
	 * 
	 * @param strURL 设置初始URL
	 */
	public void setStrURL(String strURL) {
		Spider.strURL = strURL;
	}
	/**
	 * 
	 * @param sendEng 构造函数，初始化sendEngine对象的引用
	 */
	public Spider(SendEngine sendEng){
		this.sendEng = sendEng;
	}
	/**
	 * Spider类主函数，类从此开始执行
	 */
	public void spiderMain() {
		try {
			fwURL = new FileWriter("I:" + sep + "软工" + sep +
					"大作业" + sep + "file" + sep + "saveURLs.addr");
			fwEmail = new FileWriter("I:" + sep + "软工" + sep +
					"大作业" + sep + "file" + sep + "saveEmail.addr");
		} catch (IOException e) {
			e.printStackTrace();
		}
		bwURL = new BufferedWriter(fwURL);
		bwEmail = new BufferedWriter(fwEmail);
		strURL += keyWord + "%20请留下邮箱";//空格的ASCII码对应的16进制值
		run(times, strURL);
	}
	/**
	 * 工作函数
	 * @param times 爬取页面个数限制
	 * @param initURL 初始URL
	 */
	void run(int times, String initURL){
		long beg = new Date().getTime();
		urls.add(initURL);
		urlsQ.add(initURL);
		/**
		 * 测试邮箱///////////////////////////////////////////////////////////////////////////////////////////
		 */
					try {
						bwEmail.append("testse@sina.com" + crlf);
						bwEmail.append("xdtest@126.com" + crlf);
						bwEmail.append("592505547@qq.com" + crlf);
						bwEmail.append("testSE@yahoo.cn" + crlf);
						bwEmail.append("1021360629@qq.com" + crlf);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		///////////////////////////////////////////////////////////////////////////////////////////////////
			
		
		int i = 0;
		while(urlsQ.size() > 0 && i ++ < times){// 获取文本域中的文本
			initURL = urlsQ.get(0);
			urlsQ.remove(initURL);
			parseHtml(initURL);
		}
		long end = new Date().getTime();
		try {
			bwURL.append(crlf + "共抓取: " + urls.size() + "个URL" + crlf);
			bwURL.append("用时: " + (end - beg) + "ms");
			sendEng.gui.ta_email.append("抓取完毕" + crlf);
			sendEng.gui.ta_email.append(crlf + "共抓取: " + EmailCount + "个Email" + crlf);
			sendEng.gui.ta_email.append("用时: " + (end - beg) + "ms");
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {//不关闭流就不会写入到文件，还要注意关闭顺序
			if(null != bwEmail)  bwEmail.close();
			if(null != bwURL) bwURL.close();
			if(null != fwURL) fwURL.close();
			if(null != fwEmail) fwEmail.close();
		} catch (IOException e) {
			System.out.println("文件操作失败!");
		}
	}
	
	/**
	 * 下载HTML页面方法
	 * @param strURL 获取strURL的页面内容
	 */
	public void parseHtml(String strURL){
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try {
			URL url = new URL(strURL);// 根据得到的文本新建链接
			URLConnection urlCon = url.openConnection();// 获取一个URLConnection对象
			is = urlCon.getInputStream();// 字节流读入
			isr = new InputStreamReader(is);// 包装成字符流
			br = new BufferedReader(isr);
			String buf;
			Matcher mat = null;
			while (null != (buf = br.readLine()))// 按行读取
			{
				getURL(mat, buf);
				getEmail(mat, buf);
			}
		} catch (Exception ex) {
			sendEng.gui.ta_email.append("无效URL链接" + crlf);
		}finally{
			try {
				if(null != is) is.close();
				if(null != br) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * @param mat 匹配器
	 * @param buf 解析的字符串
	 * 抓取URL方法
	 */
	public void getURL(Matcher mat, String buf){
		mat = urlPat.matcher(buf);
		String urlTp;
		while(mat.find()){
			urlTp = mat.group().trim();
			if(!urls.contains(urlTp) && !urlTp.contains("baidu") && 
					!urlTp.contains("bing") && !urlTp.contains("w3")
					&& !urlTp.contains("37ct")){//在百度站内打转的URL舍弃
				urls.add(urlTp);
				urlsQ.add(urlTp);
				try {
					bwURL.append(urlTp + crlf);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 
	 * @param mat 匹配模式
	 * @param buf 等待解析的字符串
	 * 抓取Email Address方法。
	 * 对于重复抓两次的email地址是由于email地址上启用了发送邮件的超链：
	 * <a href="mailto:loveyun_210@hotmail.com">loveyun_210@hotmail.com</a></div> 
	 * 网页源码中同一个email出现了两次所致。
	 */
	public void getEmail(Matcher mat, String buf){
		mat = emailPat.matcher(buf);
		while(mat.find()){
			++ EmailCount;
			try {
				bwEmail.append(mat.group().trim() + crlf);
				sendEng.gui.ta_email.append(mat.group() + crlf);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
