package SoftwareEngineer_HomeWork;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
/**
 *邮件类 
 */
public class Email {
	/**
	 * 每次发送邮件数量
	 */
	private static int INF = 30;
	private static Session session;
	private static Properties props;
	private static String sep = File.separator;
	private static MimeMessage msg;
	private static InternetAddress sourceAddr;
	private static String smtpServer = "smtp.163.com";// 使用 的邮件服务提供商, 缺省是163邮箱
	private static String username;
	private static String passwd;// 指定用于发送的邮箱用户名和密码
	private static String text;// 邮件正文
	private static String subject;// 邮件主题
	private static Address[] destAddr;
	private static String filePath;// 目标邮箱保存文件的位置
	private static Transport sendEngine;
	static SendEngine sendEng;// 持有主类的引用，用于显示发送状态
	public static List<String> emailList = new ArrayList<String>();
	static int COUNT = 1;// 静态变量，记录重连了多少次服务器

	private static MimeBodyPart attach;// 附件
	private static String attPath;// 附件路径

	private static String crlf = System.getProperty("line.separator");

	/**
	 * 
	 * @param username
	 *            用户名
	 * @param passwd
	 *            密码
	 * @param text
	 *            邮件正文
	 * @param subject
	 *            邮件主题
	 * @param filePath
	 *            目标邮箱地址路径
	 */
	public Email(String username, String passwd, String server, String text,
			String subject, String filePath, SendEngine sendEng) {
		this.username = username;
		this.passwd = passwd;
		this.smtpServer = server;
		this.text = text;
		this.filePath = filePath;
		this.subject = subject;
		this.sendEng = sendEng;
	}

	/**
	 * 
	 * @param username
	 *            用户名
	 * @param passwd
	 *            密码
	 * @param text
	 *            邮件正文
	 * @param subject
	 *            邮件主题
	 * @param filePath
	 *            目标邮箱地址路径
	 * @param attPath
	 *            附件路径
	 */
	public Email(String username, String passwd, String server, String text,
			String subject, String filePath, SendEngine sendEng, String attPath) {
		this(username, passwd, server, text, subject, filePath, sendEng);
		this.attPath = attPath;
	}
/**
 * 
 * @return 获得邮件发送数量限制
 */
	public static int getINF() {
		return INF;
	}
/**
 * 
 * @param inf 设置邮件发送数量限制
 */
	public static void setINF(int inf) {
		INF = inf;
	}
	/**
	 * 
	 * @param text 设置邮件正文
	 */
	public void setText(String text) {
		Email.text = text;
	}
/**
 * 
 * @param subject 设置邮件主题
 */
	public void setSubject(String subject) {
		Email.subject = subject;
	}

	/**
	 * 初始化邮件编辑环境方法
	 */
	public void init() {
		props = new Properties();
		props.put("mail.smtp.starttls.enable", "false");
		props.setProperty("mail.smtp.auth", "true");// tell the program that the
													// mail server adopts the
													// authentification model
		props.setProperty("mail.transport.protocol", "smtp");
		session = Session.getInstance(props);
		msg = new MimeMessage(session);
	}

	/**
	 * 连接邮件服务器方法
	 */
	public boolean conn() {
		init();
		try {
			sendEngine = session.getTransport();
			sendEngine.connect(smtpServer, 25, username, passwd);
		} catch (NoSuchProviderException e) {// 向GUI的对象的发送状态输入框输出连接失败警告
			sendEng.gui.ta_state.setText("连接邮件服务器失败，请检查用户名和密码..." + crlf);
			return false;
		} catch (MessagingException e) {
			return false;
		}
		return true;
	}

	/**
	 * 邮件正文编辑方法
	 */
	public boolean generate() {
		try {
			sourceAddr = new InternetAddress(username);
			msg.setSubject(subject);
			msg.setFrom(sourceAddr);
			msg.setText(text);
		} catch (MessagingException e) {
			return false;
		}
		return true;
	}

	/**
	 * 邮件发送方法
	 */
	public boolean send() {
		String buf = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filePath + "saveEmail.addr"));
			buf = null;
			destAddr = new InternetAddress[INF + 1];

		} catch (FileNotFoundException e) {
			return false;
		}
		if (1 == COUNT ++) {// 第一次调用，初始化保存邮箱地址的ArrayList
			try {
				while (null != (buf = br.readLine())) {
					emailList.add(buf);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		int cnt = 0;
		try {
			while (!emailList.isEmpty()) {
				try {
					buf = emailList.get(0);
					Thread.sleep(1500);
					sendEng.gui.ta_state.append("发送给 " + buf + crlf);
					emailList.remove(0);
					destAddr[cnt] = new InternetAddress(buf);
					++ cnt;
					if (cnt > INF) {
						sendEngine.sendMessage(msg, destAddr);
						return true;// 清零重新准备发送
					}
				}catch(SendFailedException sfe){
					cnt = 0;
					sendEng.gui.ta_state.append("出现非法邮箱地址,本轮发送失败，继续发送下轮" + crlf + crlf);
					continue;
				}catch (AddressException ae) {
					continue;
				}catch (MessagingException e) {
					sendEng.gui.ta_state.append("由于您一次性发送数量过多，连接被重置，" + 
							crlf + "请重新设置每次发送个数" +  crlf + crlf);
					return false;
				} 
			}
		} catch (InterruptedException e) {
			System.out.println("发送异常终止");
		} finally {
			try {
				if (null != br)
					br.close();
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
}
