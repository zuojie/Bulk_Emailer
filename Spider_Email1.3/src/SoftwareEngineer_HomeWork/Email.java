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
 *�ʼ��� 
 */
public class Email {
	/**
	 * ÿ�η����ʼ�����
	 */
	private static int INF = 30;
	private static Session session;
	private static Properties props;
	private static String sep = File.separator;
	private static MimeMessage msg;
	private static InternetAddress sourceAddr;
	private static String smtpServer = "smtp.163.com";// ʹ�� ���ʼ������ṩ��, ȱʡ��163����
	private static String username;
	private static String passwd;// ָ�����ڷ��͵������û���������
	private static String text;// �ʼ�����
	private static String subject;// �ʼ�����
	private static Address[] destAddr;
	private static String filePath;// Ŀ�����䱣���ļ���λ��
	private static Transport sendEngine;
	static SendEngine sendEng;// ������������ã�������ʾ����״̬
	public static List<String> emailList = new ArrayList<String>();
	static int COUNT = 1;// ��̬��������¼�����˶��ٴη�����

	private static MimeBodyPart attach;// ����
	private static String attPath;// ����·��

	private static String crlf = System.getProperty("line.separator");

	/**
	 * 
	 * @param username
	 *            �û���
	 * @param passwd
	 *            ����
	 * @param text
	 *            �ʼ�����
	 * @param subject
	 *            �ʼ�����
	 * @param filePath
	 *            Ŀ�������ַ·��
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
	 *            �û���
	 * @param passwd
	 *            ����
	 * @param text
	 *            �ʼ�����
	 * @param subject
	 *            �ʼ�����
	 * @param filePath
	 *            Ŀ�������ַ·��
	 * @param attPath
	 *            ����·��
	 */
	public Email(String username, String passwd, String server, String text,
			String subject, String filePath, SendEngine sendEng, String attPath) {
		this(username, passwd, server, text, subject, filePath, sendEng);
		this.attPath = attPath;
	}
/**
 * 
 * @return ����ʼ�������������
 */
	public static int getINF() {
		return INF;
	}
/**
 * 
 * @param inf �����ʼ�������������
 */
	public static void setINF(int inf) {
		INF = inf;
	}
	/**
	 * 
	 * @param text �����ʼ�����
	 */
	public void setText(String text) {
		Email.text = text;
	}
/**
 * 
 * @param subject �����ʼ�����
 */
	public void setSubject(String subject) {
		Email.subject = subject;
	}

	/**
	 * ��ʼ���ʼ��༭��������
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
	 * �����ʼ�����������
	 */
	public boolean conn() {
		init();
		try {
			sendEngine = session.getTransport();
			sendEngine.connect(smtpServer, 25, username, passwd);
		} catch (NoSuchProviderException e) {// ��GUI�Ķ���ķ���״̬������������ʧ�ܾ���
			sendEng.gui.ta_state.setText("�����ʼ�������ʧ�ܣ������û���������..." + crlf);
			return false;
		} catch (MessagingException e) {
			return false;
		}
		return true;
	}

	/**
	 * �ʼ����ı༭����
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
	 * �ʼ����ͷ���
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
		if (1 == COUNT ++) {// ��һ�ε��ã���ʼ�����������ַ��ArrayList
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
					sendEng.gui.ta_state.append("���͸� " + buf + crlf);
					emailList.remove(0);
					destAddr[cnt] = new InternetAddress(buf);
					++ cnt;
					if (cnt > INF) {
						sendEngine.sendMessage(msg, destAddr);
						return true;// ��������׼������
					}
				}catch(SendFailedException sfe){
					cnt = 0;
					sendEng.gui.ta_state.append("���ַǷ������ַ,���ַ���ʧ�ܣ�������������" + crlf + crlf);
					continue;
				}catch (AddressException ae) {
					continue;
				}catch (MessagingException e) {
					sendEng.gui.ta_state.append("������һ���Է����������࣬���ӱ����ã�" + 
							crlf + "����������ÿ�η��͸���" +  crlf + crlf);
					return false;
				} 
			}
		} catch (InterruptedException e) {
			System.out.println("�����쳣��ֹ");
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
