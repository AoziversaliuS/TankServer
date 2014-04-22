package oz.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ClientAccept extends Thread{
	
	ServerSocket server;
	
	private static int ClientId;
	
	private boolean close = false;
	
	public ClientAccept(ServerSocket server){
		this.server = server;
//		System.out.println("ClientAccept启动");
	}
	
	
	public void close(){
		close = true;
	}
	
	@Override
	public void run() {
		while(!close){
			try {
				
					
					Socket client = server.accept();
					System.out.println("等待连接");
					synchronized (Server.LOCK) {
						ClientId++;
						Server.connects.add(new TankConnect(client,ClientId));
					}
					Thread.sleep(500);
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
