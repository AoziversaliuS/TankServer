package oz.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ClientAccept extends Thread{
	
	ServerSocket server;
	
	private static int ClientId;
	
	public ClientAccept(ServerSocket server){
		this.server = server;
//		System.out.println("ClientAccept����");
	}
	
	
	@Override
	public void run() {
		while(true){
			try {
				
					System.out.println("�ȴ�����");
					Socket client = server.accept();
					ClientId++;
					synchronized (Server.LOCK) {
						Server.connect.add(new TankConnect(client,ClientId));
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
