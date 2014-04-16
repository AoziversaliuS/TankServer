package oz.server;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import oz.bean.Tank;

public class Server extends Thread{
	
	
	private  final int PORT;
	private  ServerSocket server;
	public static int clientNum;
	public static final String LOCK = "LOCK";
	
	
	public Server(int port){
		this.PORT = port;
		try {
			 server = new ServerSocket(PORT);
			 System.out.println("TankServer启动！");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		while(true){
			try {
				
				
				Socket client = server.accept();
				synchronized (LOCK) {
					clientNum = clientNum + 1;
				}
				System.out.println("已链接新客户端 "+clientNum+" ip="+client.getInetAddress()+"/  端口="+client.getPort());
				new ClientThread(client).start();
				
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		
	}
	
	public static void main(String[] args) {
		
		Server server = new Server(9090);
		server.start();
	}


}
