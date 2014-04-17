package oz.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import oz.bean.Tank;

public class TankConnect {
	
	
	
	Socket socket;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	
	public TankConnect(Socket socket,int id){
		this.socket = socket;
		String idString = ""+id;
		
		try {
			oos = new ObjectOutputStream(this.socket.getOutputStream());
			ois = new ObjectInputStream(this.socket.getInputStream());
			
			oos.writeObject(idString);
			oos.flush();
		} catch (IOException e) {
			System.out.println("TankConnect IO³ö´í£¡");
			e.printStackTrace();
		}
	}
	
	public Tank call(){
		
		return null;
	}
	
	
	public void send(){
		
	}
	
	
}
