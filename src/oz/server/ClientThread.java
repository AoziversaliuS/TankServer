package oz.server;

import java.net.Socket;

public class ClientThread extends Thread {
	
	
	
	private Socket socket;
	
	public ClientThread(Socket socket){
		this.socket = socket;
	}

	@Override
	public void run() {
		System.out.println("¿Í»§¶Ë¶Ë¿Ú: "+socket.getPort());
	}
    
}
