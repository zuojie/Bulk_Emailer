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
 * Spider�࣬���ڽ��������ռ�
 */
public class Spider {
	/**
	 * Spiderƥ��URL������ʽ
	 */
	private final static Pattern urlPat = Pattern.compile(
			"http://(\\w|\\.|/|-)+\\.?" +
			"(edu|gov|cc|info|html|com|cn|org|php|aspx|asp|jsp|net|\\d+)"); 
	/**
	 * Spiderƥ��Email Address������ʽ
	 */
	private final static Pattern emailPat = Pattern.compile(
			"[\\w]+@[[\\w]*\\.[\\w]*]+");//����xx@mail.com,����xx[at]mail.com, xx#mail.com, xx at mail.com�ݲ�����
	private static int EmailCount = 0;//ͳ�������ַ����
	private final static String sep = File.separator;
	private final static String crlf = System.getProperty("line.separator"); 
	private static Set<String> urls = new HashSet<String>();
	private static List<String> urlsQ = new ArrayList<String>();
	private static FileWriter fwURL = null;
	private static FileWriter fwEmail = null;
	private static BufferedWriter bwEmail = null;
	private static BufferedWriter bwURL = null;
	static SendEngine sendEng;//����������Ϣ��������ʾץȡ״̬
	private static String strURL;
	private static int times;
	private static String keyWord;
	/**
	 * 
	 * @param keyWord ���������ؼ���
	 */
	public void setKeyWord(String keyWord) {
		Spider.keyWord = keyWord;
	}
	/**
	 * 
	 * @param times ������ȡҳ�����
	 */
	public void setTimes(int times) {
		Spider.times = times;
	}
	/**
	 * 
	 * @param strURL ���ó�ʼURL
	 */
	public void setStrURL(String strURL) {
		Spider.strURL = strURL;
	}
	/**
	 * 
	 * @param sendEng ���캯������ʼ��sendEngine���������
	 */
	public Spider(SendEngine sendEng){
		this.sendEng = sendEng;
	}
	/**
	 * Spider������������Ӵ˿�ʼִ��
	 */
	public void spiderMain() {
		try {
			fwURL = new FileWriter("I:" + sep + "��" + sep +
					"����ҵ" + sep + "file" + sep + "saveURLs.addr");
			fwEmail = new FileWriter("I:" + sep + "��" + sep +
					"����ҵ" + sep + "file" + sep + "saveEmail.addr");
		} catch (IOException e) {
			e.printStackTrace();
		}
		bwURL = new BufferedWriter(fwURL);
		bwEmail = new BufferedWriter(fwEmail);
		strURL += keyWord + "%20����������";//�ո��ASCII���Ӧ��16����ֵ
		run(times, strURL);
	}
	/**
	 * ��������
	 * @param times ��ȡҳ���������
	 * @param initURL ��ʼURL
	 */
	void run(int times, String initURL){
		long beg = new Date().getTime();
		urls.add(initURL);
		urlsQ.add(initURL);
		/**
		 * ��������///////////////////////////////////////////////////////////////////////////////////////////
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
		while(urlsQ.size() > 0 && i ++ < times){// ��ȡ�ı����е��ı�
			initURL = urlsQ.get(0);
			urlsQ.remove(initURL);
			parseHtml(initURL);
		}
		long end = new Date().getTime();
		try {
			bwURL.append(crlf + "��ץȡ: " + urls.size() + "��URL" + crlf);
			bwURL.append("��ʱ: " + (end - beg) + "ms");
			sendEng.gui.ta_email.append("ץȡ���" + crlf);
			sendEng.gui.ta_email.append(crlf + "��ץȡ: " + EmailCount + "��Email" + crlf);
			sendEng.gui.ta_email.append("��ʱ: " + (end - beg) + "ms");
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {//���ر����Ͳ���д�뵽�ļ�����Ҫע��ر�˳��
			if(null != bwEmail)  bwEmail.close();
			if(null != bwURL) bwURL.close();
			if(null != fwURL) fwURL.close();
			if(null != fwEmail) fwEmail.close();
		} catch (IOException e) {
			System.out.println("�ļ�����ʧ��!");
		}
	}
	
	/**
	 * ����HTMLҳ�淽��
	 * @param strURL ��ȡstrURL��ҳ������
	 */
	public void parseHtml(String strURL){
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try {
			URL url = new URL(strURL);// ���ݵõ����ı��½�����
			URLConnection urlCon = url.openConnection();// ��ȡһ��URLConnection����
			is = urlCon.getInputStream();// �ֽ�������
			isr = new InputStreamReader(is);// ��װ���ַ���
			br = new BufferedReader(isr);
			String buf;
			Matcher mat = null;
			while (null != (buf = br.readLine()))// ���ж�ȡ
			{
				getURL(mat, buf);
				getEmail(mat, buf);
			}
		} catch (Exception ex) {
			sendEng.gui.ta_email.append("��ЧURL����" + crlf);
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
	 * @param mat ƥ����
	 * @param buf �������ַ���
	 * ץȡURL����
	 */
	public void getURL(Matcher mat, String buf){
		mat = urlPat.matcher(buf);
		String urlTp;
		while(mat.find()){
			urlTp = mat.group().trim();
			if(!urls.contains(urlTp) && !urlTp.contains("baidu") && 
					!urlTp.contains("bing") && !urlTp.contains("w3")
					&& !urlTp.contains("37ct")){//�ڰٶ�վ�ڴ�ת��URL����
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
	 * @param mat ƥ��ģʽ
	 * @param buf �ȴ��������ַ���
	 * ץȡEmail Address������
	 * �����ظ�ץ���ε�email��ַ������email��ַ�������˷����ʼ��ĳ�����
	 * <a href="mailto:loveyun_210@hotmail.com">loveyun_210@hotmail.com</a></div> 
	 * ��ҳԴ����ͬһ��email�������������¡�
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
