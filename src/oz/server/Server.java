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
			 System.out.println("TankServer∆Ù∂Ø£°");
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		while(true){
			
			synchronized (Server.LOCK) {
				
				for(TankConnect tc:connects){
					tanks.add(tc.call());
				}
				
				for(TankConnect tc:connects){
					tc.send(tanks);
				}
				
				tanks.clear();
				
			}
			
			
			try {
				Thread.sleep(8);
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
