package SoftwareEngineer_HomeWork;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Address;
import javax.mail.Transport;
/**
 * 
 *	���࣬��������
 *	
 */

public class SendEngine {
	private static String crlf = System.getProperty("line.separator");
	private static String sep = File.separator;
	private static String smtpServer;
	private static String username;
	private static String passwd;// ָ�����ڷ��͵������û���������
	private static String text;// �ʼ�����
	private static String subject;// �ʼ�����
	private static Address[] destAddr;
	private static String filePath;// Ŀ�����䱣���ļ���λ��
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
	 * @return String smtpServer ����smtp��������ַ
	 */
	public String getSmtpServer() {
		return smtpServer;
	}
	/**
	 * 
	 * @param smtpServer
	 * ��������smtp��������ַ
	 */
	public void setSmtpServer(String smtpServer) {
		SendEngine.smtpServer = smtpServer;
	}
	/**
	 * 
	 * @return String username ��¼�û���
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 
	 * @param username
	 * ���������½�û���
	 */
	public void setUsername(String username) {
		SendEngine.username = username;
	}
	/**
	 * 
	 * @return String getPasswd ��ȡ����
	 */
	public String getPasswd() {
		return passwd;
	}
	/**
	 * 
	 * @param passwd
	 * ��������
	 */
	public void setPasswd(String passwd) {
		SendEngine.passwd = passwd;
	}
	/**
	 * 
	 * @return ��ȡ�ʼ�����
	 */
	public String getText() {
		return text;
	}
	/**
	 * 
	 * @param text
	 * �����ʼ�����
	 */
	public void setText(String text) {
		SendEngine.text = text;
	}
	/**
	 * @return ��ȡ�ʼ�����
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * 
	 * @param subject
	 * �����ʼ�����
	 */
	public void setSubject(String subject) {
		SendEngine.subject = subject;
	}
	
}
