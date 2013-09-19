package SoftwareEngineer_HomeWork;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Address;
import javax.mail.Transport;
/**
 * 
 *	主类，软件入口类
 *	
 */

public class SendEngine {
	private static String crlf = System.getProperty("line.separator");
	private static String sep = File.separator;
	private static String smtpServer;
	private static String username;
	private static String passwd;// 指定用于发送的邮箱用户名和密码
	private static String text;// 邮件正文
	private static String subject;// 邮件主题
	private static Address[] destAddr;
	private static String filePath;// 目标邮箱保存文件的位置
	private static Transport sendEngine;
	public static GUI gui;
	public static Email email;
	public static Spider spider;
	public static void main(String[] ars){
		new SendEngine().go();
	}
	
	public void go(){
		gui = new GUI(this, "Bulk-Emailer V1.0");
		gui.lanuchFrame();
	}
	/**
	 * 
	 * @return String smtpServer 邮箱smtp服务器地址
	 */
	public String getSmtpServer() {
		return smtpServer;
	}
	/**
	 * 
	 * @param smtpServer
	 * 设置邮箱smtp服务器地址
	 */
	public void setSmtpServer(String smtpServer) {
		SendEngine.smtpServer = smtpServer;
	}
	/**
	 * 
	 * @return String username 登录用户名
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 
	 * @param username
	 * 设置邮箱登陆用户名
	 */
	public void setUsername(String username) {
		SendEngine.username = username;
	}
	/**
	 * 
	 * @return String getPasswd 获取密码
	 */
	public String getPasswd() {
		return passwd;
	}
	/**
	 * 
	 * @param passwd
	 * 设置密码
	 */
	public void setPasswd(String passwd) {
		SendEngine.passwd = passwd;
	}
	/**
	 * 
	 * @return 获取邮件正文
	 */
	public String getText() {
		return text;
	}
	/**
	 * 
	 * @param text
	 * 设置邮件正文
	 */
	public void setText(String text) {
		SendEngine.text = text;
	}
	/**
	 * @return 获取邮件主题
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * 
	 * @param subject
	 * 设置邮件主题
	 */
	public void setSubject(String subject) {
		SendEngine.subject = subject;
	}
	
}
