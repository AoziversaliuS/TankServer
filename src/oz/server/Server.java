package oz.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import oz.bean.Tank;


public class Server extends Thread{
	
	
	private  final int PORT;
	private  ServerSocket server;
	
	public static ArrayList<TankConnect> connects = new ArrayList<TankConnect>();
	public static ArrayList<Tank> tanks = new ArrayList<Tank>();
	
	public static final String LOCK="LOCK"; 
	
	
	public Server(int port){
		this.PORT = port;
		try {
			
			 server = new ServerSocket(PORT);
			 new ClientAccept(server).start();
			 System.out.println("TankServer启动！");
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		final long SLEEP_TIME = 5;
		while(true){
			
			long start = System.currentTimeMillis();
			synchronized (Server.LOCK) {
				int exitId = -1;
				for(TankConnect tc:connects){
					tanks.add(tc.call());
				}
				
				//设置exitId
				for(Tank tank:tanks){
					if( tank.getClientMessage()==Tank.M_EXIT_REQUEST ){
						tank.setClientMessage(Tank.M_EXIT_PERMIT);
						//一帧只允许一个客户端退出
						exitId = tank.getId();
						break;
					}
				}
				
				for(TankConnect tc:connects){
					tc.send(tanks);
				}
				
				//关闭与某个退出客户端的链接
				for(int i=0;i<connects.size();i++){
					if( connects.get(i).getId()==exitId ){
						connects.get(i).close();
						connects.remove(i);
						break;
					}
				}
				
				
				tanks.clear();
				
			}
			
			
			long costTime = System.currentTimeMillis() - start;
			try {
				if( SLEEP_TIME-costTime>0 ){
					Thread.sleep(SLEEP_TIME-costTime);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		
		Server server = new Server(9090);
		server.start();
	}


}
