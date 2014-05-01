package oz.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import oz.bean.Tank;


public class Server extends Thread{
	
	
	private  final int PORT;
	private  ServerSocket server;
	
	public static ArrayList<TankConnect> connects = new ArrayList<TankConnect>();
	public static ArrayList<Tank> tanks = new ArrayList<Tank>();
	
	public static final String LOCK="LOCK"; 
	
	public static ClientAccept clientAccept;
	
	private boolean close = false;
	
	public Server(int port){
		this.PORT = port;
		try {
			
			 server = new ServerSocket(PORT);
			 clientAccept =  new ClientAccept(server);
			 clientAccept.start();
			 System.out.println("TankServer������");
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		final long SLEEP_TIME = 5;
		while(!close){
			
			long start = System.currentTimeMillis();
			synchronized (Server.LOCK) {
				int exitId = -1;
				for(TankConnect tc:connects){
					tanks.add(tc.call());
				}
				
				serverLogic(tanks);
				
				//����exitId
				for(Tank tank:tanks){
					if( tank.getClientMessage()==Tank.M_EXIT_REQUEST ){
						tank.setClientMessage(Tank.M_EXIT_PERMIT);
						//һֻ֡����һ���ͻ����˳�
						exitId = tank.getId();
						break;
					}
				}
				
				for(TankConnect tc:connects){
					tc.send(tanks);
				}
				
				//�ر���ĳ���˳��ͻ��˵�����
				for(int i=0;i<connects.size();i++){
					if( connects.get(i).getId()==exitId ){
						connects.get(i).close();
						connects.remove(i);
						System.out.println("һ���ͻ����˳���");
						break;
					}
				}
				
				
				tanks.clear();
				
			}
			
			
			long costTime = System.currentTimeMillis() - start;
//			System.out.println("��������ʱ��"+costTime);
			try {
				if( SLEEP_TIME-costTime>0 ){
					Thread.sleep(SLEEP_TIME-costTime);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	private final int MAX_ROUND_COUNT=3;
	private int roundNum = 0;
	private int roundCount=MAX_ROUND_COUNT;
	private void serverLogic(ArrayList<Tank> tanks) {
		int survivorNum=0;
		int serverMsg = -1;
		if(tanks.size()>0){
			serverMsg = tanks.get(0).getServerMsg();
		}
		if( serverMsg==Tank.S_ROUND_SWITCHING ){
			for(Tank t:tanks){
				t.setServerMsg(Tank.S_ROUND_SWITCHING);
				t.setRoundCount(roundCount);
				t.setRoundNum(roundNum);
			}
			roundCount--;
			if( roundCount<0 ){
				for(Tank t:tanks){
					t.reset();
				}
			}
		}
		else if( serverMsg==Tank.S_PLAYING ){
			for(Tank t:tanks){
				if(!t.isDeadFinish()){
					survivorNum++;
					if(survivorNum>1){
						break;
					}
				}
			}
			if( survivorNum<=1 && tanks.size()>2 ){
				roundNum++;//������һ�غ�
				//���ü�����
				roundCount=MAX_ROUND_COUNT;
				
				for(Tank t:tanks){
						t.setServerMsg(Tank.S_ROUND_SWITCHING);
						if( !t.isDeadFinish() ){
							t.setType(Tank.BLACK_TANK);
						}
						else if( t.getType()!=Tank.OZ_TANK ){
							t.setType(Tank.GENERAL);
						}
				}
			}
		}
			
	}
	
	
	public void close(){
		close = true;
	}
	
	
	public static void main(String[] args) {
		InetAddress addr = null;
		String ip = null;
		int port;
//		String portString = JOptionPane.showInputDialog(null, "������������˿� :");
//
//		
//		if(portString!=null ){
//			port = Integer.parseInt(portString);
//		}
//		else{
//			port = 9090;
//		}
		port = 9090;
		try {
			addr = InetAddress.getLocalHost();
			ip=addr.getHostAddress().toString();
			System.out.println("ip:"+ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		Server server = new Server(port);
		server.start();
		Object[] options ={ "�˳�", "������" }; 
		JOptionPane.showOptionDialog(null,  "������ip:"+ip+"   �˿�:"+port, "[�´�����] ̹�˴�ս�����",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		clientAccept.close();
		server.close();
		System.out.println("�������ѹرգ�");
		System.exit(0);
		
		
	}


}
