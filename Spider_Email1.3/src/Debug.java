import java.awt.*;
import java.awt.event.*;
public class Debug extends Frame
{
	//�����ĸ����ֹ���������
	//public static final Color k = Color.red;
	public static void main(String[] args){
		Debug debug = new Debug();
		Frame frm = new Frame();
		Button btn = new Button("oK");
		Button btncan = new Button("cancle");
		Thread thd = new Thread(debug.new mt(btncan));
		frm.add(btn);
		frm.add(btncan);
		frm.setVisible(true);
		frm.pack();
	}
	class mt implements Runnable{

		Button btn;
		public mt(Button btn){
			this.btn = btn;
		}
		@Override
		public void run() {
			btn.addActionListener(new ActionListener() {// ���·����ʼ���ť����������ķ���
				public void actionPerformed(ActionEvent e) {
System.out.println("����");
					Frame frm = new Frame("����");
					frm.add(btn);
					frm.setVisible(true);
					frm.pack();
				}
			});
		}
		
	}
}