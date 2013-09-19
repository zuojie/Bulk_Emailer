package SoftwareEngineer_HomeWork;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/**
 * 
 * GUI�࣬���������࣬
 * ����Ӧ��ť�¼���������Ӧ�ķ�Ӧ
 */
public class GUI extends Frame {
	private static String crlf = System.getProperty("line.separator");
	private static SendEngine sendEng;
	private Panel borderP1, borderP11, borderP12;// ƽ��ʽ����
	private Panel flowL1, flowL11, flowL12, flowL13;// �в���
	private Panel gridL1, gridL11, gridL12;
	private Panel gridR, gridLU, gridLD;// ջ�񲼾�
	private static Color light_blue = new Color(193, 210, 240);
	private static Button bt_send, bt_generate;
	private static String bing = "http://cn.bing.com/search?q=";
	private static String yahoo = "http://www.yahoo.cn/s?src=8003&vendor=100101&source=yahoo_yhp_0706_search_button&q=";
	private static String baidu = "http://www.baidu.com/s?wd=";
	private static String google = "http://www.google.com.hk/#hl=zh-CN&source=hp&q=";

	Button bt_g, bt_baidu, bt_y, bt_bing;
	TextField key, tf_limit, tf_subj, tf_INF;// ����ȫ�ֱ���inner-class����
	public TextArea ta_state, ta_email;
	TextArea ta_text;
	public String mailMessage = "";// ��¼�ʼ�����

	public GUI(SendEngine sendEng, String title) {
		super(title);
		this.sendEng = sendEng;
	}
/**
 * ����������launchFrame()
 */
	public void lanuchFrame() {

		this.setLayout(new GridLayout(1, 2));
		gridL1 = new Panel();
		gridL11 = new Panel();
		gridL12 = new Panel();
		gridR = new Panel();
		flowL1 = new Panel();
		flowL11 = new Panel();
		flowL12 = new Panel();
		flowL13 = new Panel();
		borderP1 = new Panel();
		borderP11 = new Panel();
		borderP12 = new Panel();

		gridL1.setLayout(new GridLayout(2, 1));
		gridL11.setLayout(new GridLayout(4, 1));
		gridR.setLayout(new GridLayout(2, 1));
		flowL1.setLayout(new FlowLayout());
		flowL11.setLayout(new FlowLayout(FlowLayout.LEFT));
		flowL12.setLayout(new FlowLayout(FlowLayout.LEFT));
		flowL13.setLayout(new FlowLayout(FlowLayout.LEFT));
		borderP1.setLayout(new BorderLayout());
		borderP11.setLayout(new BorderLayout());
		borderP12.setLayout(new BorderLayout());
		
		key = new TextField("������Ĺؼ���...", 20);
		gridL11.add(key);
		// �ϲ���һ�в���
		tf_limit = new TextField("����������ȡ��URLҳ�����", 25);
		bt_g = new Button("Google");
		bt_baidu = new Button("Baidu");
		bt_y = new Button("Yahoo");
		bt_bing = new Button("Bing");
		List search_list = new List(4, false); 
		search_list.add("Google");
		search_list.add("baidu");
		search_list.add("yahoo");
		search_list.add("bing");
		
		/**
		 * Ϊ����������ť�����Ϣ��Ӧ
		 * 
		 */
		flowL1.add(tf_limit);
		//////////flowL1.add(search_list);/////////////////////��������δʹ�á�
		flowL1.add(bt_g);
		flowL1.add(bt_baidu);
		flowL1.add(bt_y);
		flowL1.add(bt_bing);
		gridL11.add(flowL1);
		// �ϰ벿�ڶ��в���
		
		tf_INF = new TextField("20", 15);
		Button btn_INF = new Button("ÿ�ַ����ʼ�������[0,40]֮��");
		btn_INF.setEnabled(false);
		flowL13.add(btn_INF);
		flowL13.add(tf_INF);
		gridL11.add(flowL13);
		//�ϰ벿�����в���
		
		Button bt_subj = new Button("�ʼ�����");
		bt_subj.setEnabled(false);
		tf_subj = new TextField("I'm not spam", 15);
		flowL11.add(bt_subj);
		flowL11.add(tf_subj);
		gridL11.add(flowL11);
		gridL1.add(gridL11);

		// �ϰ벿�����в���

		Button bt_text = new Button("�ʼ�����");
		bt_text.setEnabled(false);
		ta_text = new TextArea("Actually, I M..." + crlf + crlf + crlf
				+ "PS:��֤ô");
		ta_text.setBackground(light_blue);
		borderP1.add(bt_text, BorderLayout.NORTH);
		borderP1.add(ta_text, BorderLayout.CENTER);
		gridL1.add(borderP1);
		ta_text.addKeyListener(new KeyMonitor());// Ϊ�ʼ������ı�����Ӽ���
		// �°벿��һ�в���

		bt_generate = new Button("�����ʼ�");
		bt_send = new Button("�����ʼ�");
		bt_generate.setEnabled(false);
		bt_send.setEnabled(false);// �����ӷ��������������ɺͷ����ʼ�
		flowL12.add(bt_generate);
		flowL12.add(bt_send);
		borderP1.add(flowL12, BorderLayout.SOUTH);
		
		//�Բ��ְ�ť���������߳�
		new Thread(new Btn()).start();
		
		// �°벿�ڶ��в���

		// ����Ϊ��ߵĲ��ֹ�����

		Label emailAddr = new Label("ץȡ��Email��ַ");
		ta_email = new TextArea();
		ta_email.setBackground(Color.BLACK);
		ta_email.setForeground(Color.YELLOW);
		borderP11.add(emailAddr, BorderLayout.NORTH);
		borderP11.add(ta_email, BorderLayout.CENTER);
		gridR.add(borderP11);
		// �ϲ�����

		Label sendState = new Label("����״̬");
		ta_state = new TextArea();
		ta_state.setBackground(Color.BLACK);
		ta_state.setForeground(Color.YELLOW);
		borderP12.add(sendState, BorderLayout.NORTH);
		borderP12.add(ta_state, BorderLayout.CENTER);
		gridR.add(borderP12);
		// �²�����

		// ����Ϊ�Ұ벿����

		// ����Ϊ��Ӳ˵�
		MenuBar mb = new MenuBar();
		Menu login = new Menu("login");// �ļ��˵�
		Menu help = new Menu("help");
		MenuItem conn = new MenuItem("connect-to-server");
		MenuItem hel = new MenuItem("help");
		MenuItem about = new MenuItem("about");
		login.add(conn);
		help.add(hel);
		help.add(about);
		mb.add(login);
		mb.add(help);

		// Ϊ�˵�����һ���̴߳�����Ϣ����
		MultiThread mthd = new MultiThread(conn, hel, about);
		Thread thd = new Thread(mthd);
		thd.start();

		this.add(gridL1);
		this.add(gridR);
		this.setMenuBar(mb);

		this.setResizable(false);
		this.setVisible(true);
		pack();
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				System.exit(0);
			}
		});
		this.setLocation(200, 200);
	}

	/**
	 * ����ť��Ӧ���ڲ��࣬ʵ����Runable
	 * �࣬��Ϊ�̱߳�����
	 * 
	 */

	class Btn implements Runnable {
		public void run() {
			bt_g.addActionListener(new ActionListener()// Google
					{
						public void actionPerformed(ActionEvent e) {
							sendEng.spider = new Spider(sendEng);
							sendEng.spider.setKeyWord(key.getText().trim());
							try{
								sendEng.spider.setTimes(Integer.parseInt(tf_limit
									.getText().trim()));
							}catch(NumberFormatException nfe){
								ta_email.setText("������Ϸ�����ȡURL��������������" + crlf + crlf);
								return;
							}
							sendEng.spider.setStrURL(google);
							sendEng.spider.spiderMain();
						}
					});
			bt_baidu.addActionListener(new ActionListener()// baidu
					{
						public void actionPerformed(ActionEvent e) {
							sendEng.spider = new Spider(sendEng);
							sendEng.spider.setKeyWord(key.getText().trim());
							try{
								sendEng.spider.setTimes(Integer.parseInt(tf_limit
									.getText().trim()));
							}catch(NumberFormatException nfe){
								ta_email.setText("������Ϸ�����ȡURL��������������" + crlf + crlf);
								return;
							}
							sendEng.spider.setStrURL(baidu);
							sendEng.spider.spiderMain();
						}
					});
			bt_y.addActionListener(new ActionListener()// Yahoo
					{
						public void actionPerformed(ActionEvent e) {
							sendEng.spider = new Spider(sendEng);
							sendEng.spider.setKeyWord(key.getText().trim());
							try{
								sendEng.spider.setTimes(Integer.parseInt(tf_limit
									.getText().trim()));
							}catch(NumberFormatException nfe){
								ta_email.setText("������Ϸ�����ȡURL��������������" + crlf + crlf);
								return;
							}
							sendEng.spider.setStrURL(yahoo);
							sendEng.spider.spiderMain();
						}
					});
			bt_bing.addActionListener(new ActionListener()// Bing
					{
						public void actionPerformed(ActionEvent e) {
							sendEng.spider = new Spider(sendEng);
							sendEng.spider.setKeyWord(key.getText().trim());
							try{
								sendEng.spider.setTimes(Integer.parseInt(tf_limit
									.getText().trim()));
							}catch(NumberFormatException nfe){
								ta_email.setText("������Ϸ�����ȡURL��������������" + crlf + crlf);
								return;
							}
							sendEng.spider.setStrURL(bing);
							sendEng.spider.spiderMain();
						}
					});
			//�ʼ��༭��ť��Ӧ
			// Ϊ�ʼ���ذ�ť�����Ӧ
			bt_generate.addActionListener(new ActionListener()// ���������ʼ���ť��ȡ�����ĺ������Ѿ���������
					{
						public void actionPerformed(ActionEvent e) {
							sendEng.email.setSubject(tf_subj.getText().trim());
							sendEng.email.setINF(Integer.parseInt(tf_INF.getText()));
							if (sendEng.email.generate()) {
								ta_state.append("�ʼ����ɳɹ�" + crlf);
							} else {
								ta_state.append("�ʼ�����ʧ��" + crlf);
							}
						}
					});
			bt_send.addActionListener(new ActionListener() {// ���·����ʼ���ť����������ķ���
						public void actionPerformed(ActionEvent e) {
							if (sendEng.email.send()) {
								ta_state.append("�������" + crlf);
								ta_state.append("�����½������ӣ�������һ��" + crlf);
							} else {
								ta_state.append("�����쳣��ֹ" + crlf);
							}
							bt_send.setEnabled(false);
							bt_generate.setEnabled(false);// ����һ�ֺ����ٴη���
						}
					});
		}
	}

	/**
	 *��Ӧ�˵��ڲ��࣬����Runnable�ӿڣ�
	 *�������Ϊ�̱߳����� 
	 */
	class MultiThread implements Runnable {
		MenuItem conn, hel, about;
		public MultiThread(MenuItem conn, MenuItem hel, MenuItem about){
			this.conn = conn;
			this.hel = hel;
			this.about = about;
		}

		public void run() {
			invoke();
		}
/**
 * ��Ӧ����
 */
		public void invoke() {
			// Ϊ���ӷ������˵������Ϣ��Ӧ
			conn.addActionListener(new ActionListener()// Ϊ��conn���˵��������Ϣ��Ӧ
					{
						public void actionPerformed(ActionEvent e) {
							new Login().init();// ������¼�Ի���
						}
					});
			// Ϊ�����˵������Ϣ��Ӧ
			hel.addActionListener(new ActionListener()// Ϊhelp�˵��������Ϣ��Ӧ
					{
						public void actionPerformed(ActionEvent e) {
							final Frame help = new Frame();
							help.setTitle("help");
							Panel pan = new Panel();
							pan.setLayout(new BorderLayout());
							TextArea ta = new TextArea();
							Button bt_ok = new Button("ȷ��");

							ta.append("ʹ�ð���:" + crlf);
							ta.append("1: �༭�����ʼ�֮ǰ���ȵ�¼���������" + crlf);
							ta.append("2: �趨�������ؼ���֮��ѡ��Goolge������һ����������" + crlf);
							ta.append("3���ʼ���������ı༭��Ϻ󣬵�������ʼ������Ҳ�״̬���۲����ɽ��"
									+ crlf);
							ta.append("4���������󣬵�����ͼ���:" + crlf);
							ta.append("5: ���������������������̷���ʱ������������������ƣ�" + crlf
									+ "���Է�������ÿ��30s����һ����ÿ������30��" + crlf);
							// Ϊok�����Ӧ
							bt_ok.addActionListener(new ActionListener()
									{
										public void actionPerformed(
												ActionEvent e) {
											help.setVisible(false);
										}
									});

							pan.add(ta, BorderLayout.NORTH);
							pan.add(bt_ok, BorderLayout.CENTER);

							help.add(pan);
							help.setBackground(Color.GRAY);
							help.pack();
							help.setLocation(100, 150);
							help.setResizable(false);
							help.setVisible(true);

							help.addWindowListener(new WindowAdapter() {
								public void windowClosing(WindowEvent e) {
									help.setVisible(false);
								}
							});
						}
					});

			// Ϊabout�˵��������Ϣ��Ӧ
			about.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					final Frame about = new Frame();
					about.setTitle("about");
					TextArea ta = new TextArea("Author: Pzjay/Hongli/yuelei/Wangyang" + crlf
							+ "Version:beta 1.0");
					Panel pan = new Panel();
					pan.setLayout(new BorderLayout());
					pan.add(ta, BorderLayout.CENTER);
					about.add(pan);

					about.pack();
					about.setLocation(100, 150);
					about.setResizable(false);
					about.setVisible(true);

					about.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
							about.setVisible(false);
						}
					});
				}
			});
		}

	}

	/**
	 * 
	 *  �ı����¼��������ࣺ���»س������ı�����Ϣȡ���͵��ı���
	 */
	class KeyMonitor extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (KeyEvent.VK_ENTER == e.getKeyCode()) {
				mailMessage += ta_text.getText().trim() + crlf;// ȡ���ı����е��ı�
			}
		}

		public void actionPerformed(ActionEvent AE) {
			mailMessage += ta_text.getText().trim() + crlf;// ȡ���ı����е��ı���tirm��ȥ����β�Ŀո�

		}
	}
/**
 * 
 *�����¼GUI����
 */
	class Login extends Frame {
		Label username;
		Label passwd;
		Label stmpServer;
		TextField tf_name;
		TextField tf_pass;
		TextField tf_server;

		public void init() {
			setLayout(new FlowLayout());
			username = new Label("username");
			passwd = new Label("passwd");
			stmpServer = new Label("mail-server");
			tf_name = new TextField("xdtest@126.com", 15);
			tf_pass = new TextField("testTEST", 15);
			tf_server = new TextField("smtp.126.com", 15);
			tf_name.setBackground(new Color(216, 216, 166));
			tf_pass.setBackground(new Color(204, 232, 207));
			tf_server.setBackground(new Color(186, 216, 166));
			tf_pass.setEchoChar('*');
			Button btn_ok = new Button("OK");
			// ΪOK button��Ӽ���
			btn_ok.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)// ���¼�������Ӧ:ȡ���ı�������
				{
					sendEng.setUsername(tf_name.getText().toString().trim());
					sendEng.setPasswd(tf_pass.getText().toString().trim());
					sendEng
							.setSmtpServer(tf_server.getText().toString()
									.trim());
					sendEng.email = new Email(sendEng.getUsername(), sendEng
							.getPasswd(), sendEng.getSmtpServer(), mailMessage,
							sendEng.getSubject(), "I:\\��\\����ҵ\\file\\",
							sendEng);

					if (sendEng.email.conn()) {
						ta_state.setText("���ӷ������ɹ�" + crlf);
						bt_generate.setEnabled(true);
						bt_send.setEnabled(true);// ����¼�����������������
						setVisible(false);
					} else {
						ta_state.setText("���ӷ�����ʧ��" + crlf);
						setVisible(true);// ���Ӳ��ɹ�����¼�Ի���㲻�˳������û���������
					}
				}
			});
			add(username);
			add(tf_name);
			add(passwd);
			add(tf_pass);
			add(stmpServer);
			add(tf_server);
			add(btn_ok);
			pack();
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					setVisible(false);
				}
			});
			this.setLocation(300, 400);
			this.setResizable(false);
			setVisible(true);
		}

		public void paint(Graphics g) {
		}
	}
}
