package oz.server;

import java.net.Socket;
import java.util.ArrayList;

import oz.bean.Tank;

public class ClientThread extends Thread {
	
	public static int maxClientNum;//当前的客户端数量
	
	public static final String ACCEPTING = "accept";
	public static final String SENDING = "send";
	public static final String RESET = "reset";
	
	private static int acceptNum;
	private static int sendNum;
	private static ArrayList<Tank> tanks = new ArrayList<Tank>();
	
	private boolean acceptStart = true;
	private boolean sendStart = true;
	public static boolean finish = false;
	
	private Socket socket;
	
	public ClientThread(Socket socket){
		this.socket = socket;
		maxClientNum++;
	}

	@Override
	public void run() {
		
		while(true){
			
			if( acceptStart ){
				acceptStart = false;
				if( acceptNum<maxClientNum ){
					//接收客户端的坦克资料
					
					synchronized (ACCEPTING) {
						acceptNum++;
					}
				}
			}
			
			if( sendStart ){
				if( acceptNum==maxClientNum ){
					sendStart = false;
					
					//发送tanks给对应的客户端。
					synchronized (SENDING) {
						sendNum++;
					}
				}
			}
			
			if( sendNum==maxClientNum ){
				synchronized (RESET) {
					if( finish==false ){
						tanks.clear();//清空队列
						acceptNum = 0;
						sendNum = 0;
						finish = true;
					}
				}
				acceptStart = true;
				sendStart = true;
			}
			
			
			
			
			tanks.add(new Tank());
			System.out.println("坦克数量:"+tanks.size());
		}

	}
    
}
