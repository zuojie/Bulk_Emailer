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
 * GUI类，管理界面的类，
 * 并响应按钮事件，做出响应的反应
 */
public class GUI extends Frame {
	private static String crlf = System.getProperty("line.separator");
	private static SendEngine sendEng;
	private Panel borderP1, borderP11, borderP12;// 平板式布局
	private Panel flowL1, flowL11, flowL12, flowL13;// 行布局
	private Panel gridL1, gridL11, gridL12;
	private Panel gridR, gridLU, gridLD;// 栈格布局
	private static Color light_blue = new Color(193, 210, 240);
	private static Button bt_send, bt_generate;
	private static String bing = "http://cn.bing.com/search?q=";
	private static String yahoo = "http://www.yahoo.cn/s?src=8003&vendor=100101&source=yahoo_yhp_0706_search_button&q=";
	private static String baidu = "http://www.baidu.com/s?wd=";
	private static String google = "http://www.google.com.hk/#hl=zh-CN&source=hp&q=";

	Button bt_g, bt_baidu, bt_y, bt_bing;
	TextField key, tf_limit, tf_subj, tf_INF;// 放在全局便于inner-class调用
	public TextArea ta_state, ta_email;
	TextArea ta_text;
	public String mailMessage = "";// 记录邮件正文

	public GUI(SendEngine sendEng, String title) {
		super(title);
		this.sendEng = sendEng;
	}
/**
 * 主调函数：launchFrame()
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
		
		key = new TextField("键入你的关键词...", 20);
		gridL11.add(key);
		// 上部第一行布局
		tf_limit = new TextField("键入爬虫爬取的URL页面个数", 25);
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
		 * 为各个搜索按钮添加消息响应
		 * 
		 */
		flowL1.add(tf_limit);
		//////////flowL1.add(search_list);/////////////////////下拉条【未使用】
		flowL1.add(bt_g);
		flowL1.add(bt_baidu);
		flowL1.add(bt_y);
		flowL1.add(bt_bing);
		gridL11.add(flowL1);
		// 上半部第二行布局
		
		tf_INF = new TextField("20", 15);
		Button btn_INF = new Button("每轮发送邮件数量：[0,40]之间");
		btn_INF.setEnabled(false);
		flowL13.add(btn_INF);
		flowL13.add(tf_INF);
		gridL11.add(flowL13);
		//上半部第三行布局
		
		Button bt_subj = new Button("邮件主题");
		bt_subj.setEnabled(false);
		tf_subj = new TextField("I'm not spam", 15);
		flowL11.add(bt_subj);
		flowL11.add(tf_subj);
		gridL11.add(flowL11);
		gridL1.add(gridL11);

		// 上半部第四行布局

		Button bt_text = new Button("邮件正文");
		bt_text.setEnabled(false);
		ta_text = new TextArea("Actually, I M..." + crlf + crlf + crlf
				+ "PS:办证么");
		ta_text.setBackground(light_blue);
		borderP1.add(bt_text, BorderLayout.NORTH);
		borderP1.add(ta_text, BorderLayout.CENTER);
		gridL1.add(borderP1);
		ta_text.addKeyListener(new KeyMonitor());// 为邮件正文文本域添加监听
		// 下半部第一行布局

		bt_generate = new Button("生成邮件");
		bt_send = new Button("发送邮件");
		bt_generate.setEnabled(false);
		bt_send.setEnabled(false);// 不连接服务器不允许生成和发送邮件
		flowL12.add(bt_generate);
		flowL12.add(bt_send);
		borderP1.add(flowL12, BorderLayout.SOUTH);
		
		//对布局按钮启动监听线程
		new Thread(new Btn()).start();
		
		// 下半部第二行布局

		// 以上为左边的布局管理器

		Label emailAddr = new Label("抓取的Email地址");
		ta_email = new TextArea();
		ta_email.setBackground(Color.BLACK);
		ta_email.setForeground(Color.YELLOW);
		borderP11.add(emailAddr, BorderLayout.NORTH);
		borderP11.add(ta_email, BorderLayout.CENTER);
		gridR.add(borderP11);
		// 上部布局

		Label sendState = new Label("发送状态");
		ta_state = new TextArea();
		ta_state.setBackground(Color.BLACK);
		ta_state.setForeground(Color.YELLOW);
		borderP12.add(sendState, BorderLayout.NORTH);
		borderP12.add(ta_state, BorderLayout.CENTER);
		gridR.add(borderP12);
		// 下部布局

		// 以上为右半部布局

		// 以下为添加菜单
		MenuBar mb = new MenuBar();
		Menu login = new Menu("login");// 文件菜单
		Menu help = new Menu("help");
		MenuItem conn = new MenuItem("connect-to-server");
		MenuItem hel = new MenuItem("help");
		MenuItem about = new MenuItem("about");
		login.add(conn);
		help.add(hel);
		help.add(about);
		mb.add(login);
		mb.add(help);

		// 为菜单开启一个线程处理消息请求
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
	 * 处理按钮响应的内部类，实现了Runable
	 * 类，作为线程被创建
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
								ta_email.setText("请输入合法的爬取URL数量限制数量！" + crlf + crlf);
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
								ta_email.setText("请输入合法的爬取URL数量限制数量！" + crlf + crlf);
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
								ta_email.setText("请输入合法的爬取URL数量限制数量！" + crlf + crlf);
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
								ta_email.setText("请输入合法的爬取URL数量限制数量！" + crlf + crlf);
								return;
							}
							sendEng.spider.setStrURL(bing);
							sendEng.spider.spiderMain();
						}
					});
			//邮件编辑按钮响应
			// 为邮件相关按钮添加响应
			bt_generate.addActionListener(new ActionListener()// 摁下生成邮件按钮，取走正文和主题已经发送限制
					{
						public void actionPerformed(ActionEvent e) {
							sendEng.email.setSubject(tf_subj.getText().trim());
							sendEng.email.setINF(Integer.parseInt(tf_INF.getText()));
							if (sendEng.email.generate()) {
								ta_state.append("邮件生成成功" + crlf);
							} else {
								ta_state.append("邮件生成失败" + crlf);
							}
						}
					});
			bt_send.addActionListener(new ActionListener() {// 摁下发送邮件按钮，调用主类的发送
						public void actionPerformed(ActionEvent e) {
							if (sendEng.email.send()) {
								ta_state.append("发送完毕" + crlf);
								ta_state.append("请重新建立连接，发送下一轮" + crlf);
							} else {
								ta_state.append("发送异常终止" + crlf);
							}
							bt_send.setEnabled(false);
							bt_generate.setEnabled(false);// 发送一轮后不能再次发送
						}
					});
		}
	}

	/**
	 *响应菜单内部类，集成Runnable接口，
	 *其对象作为线程被创建 
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
 * 响应函数
 */
		public void invoke() {
			// 为连接服务器菜单添加消息响应
			conn.addActionListener(new ActionListener()// 为“conn”菜单项添加消息响应
					{
						public void actionPerformed(ActionEvent e) {
							new Login().init();// 启动登录对话框
						}
					});
			// 为帮助菜单添加消息响应
			hel.addActionListener(new ActionListener()// 为help菜单项添加消息响应
					{
						public void actionPerformed(ActionEvent e) {
							final Frame help = new Frame();
							help.setTitle("help");
							Panel pan = new Panel();
							pan.setLayout(new BorderLayout());
							TextArea ta = new TextArea();
							Button bt_ok = new Button("确定");

							ta.append("使用帮助:" + crlf);
							ta.append("1: 编辑发送邮件之前请先登录邮箱服务器" + crlf);
							ta.append("2: 设定好搜索关键词之后选择Goolge等其中一个进行搜索" + crlf);
							ta.append("3：邮件主题和正文编辑完毕后，点击生成邮件，在右侧状态栏观察生成结果"
									+ crlf);
							ta.append("4：生成无误，点击发送即可:" + crlf);
							ta.append("5: 由于邮箱服务器设置有最短发送时间间隔和最大发送量的限制，" + crlf
									+ "所以发送器会每隔30s发送一批，每批发送30封" + crlf);
							// 为ok添加响应
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

			// 为about菜单项添加消息响应
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
	 *  文本域事件监听器类：按下回车，将文本域消息取出送到文本区
	 */
	class KeyMonitor extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (KeyEvent.VK_ENTER == e.getKeyCode()) {
				mailMessage += ta_text.getText().trim() + crlf;// 取出文本域中的文本
			}
		}

		public void actionPerformed(ActionEvent AE) {
			mailMessage += ta_text.getText().trim() + crlf;// 取出文本域中的文本，tirm是去除收尾的空格

		}
	}
/**
 * 
 *管理登录GUI的类
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
			// 为OK button添加监听
			btn_ok.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)// 对事件做出反应:取出文本框内容
				{
					sendEng.setUsername(tf_name.getText().toString().trim());
					sendEng.setPasswd(tf_pass.getText().toString().trim());
					sendEng
							.setSmtpServer(tf_server.getText().toString()
									.trim());
					sendEng.email = new Email(sendEng.getUsername(), sendEng
							.getPasswd(), sendEng.getSmtpServer(), mailMessage,
							sendEng.getSubject(), "I:\\软工\\大作业\\file\\",
							sendEng);

					if (sendEng.email.conn()) {
						ta_state.setText("连接服务器成功" + crlf);
						bt_generate.setEnabled(true);
						bt_send.setEnabled(true);// 不登录邮箱服务器不允许发送
						setVisible(false);
					} else {
						ta_state.setText("连接服务器失败" + crlf);
						setVisible(true);// 连接不成功，登录对话框便不退出，让用户继续连接
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
