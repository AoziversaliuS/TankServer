package oz.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server extends Thread{
	
	
	private  final int PORT;
	private  ServerSocket server;
	
	public static ArrayList<TankConnect> connect = new ArrayList<TankConnect>();
	
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
		while(true){
			
			synchronized (Server.LOCK) {
//				System.out.println("当前连接数为: "+connect.size());
				
			}
			
			
			try {
				Thread.sleep(15);
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
