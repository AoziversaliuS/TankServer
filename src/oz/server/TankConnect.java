package oz.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import oz.bean.Tank;

public class TankConnect {
	
	
	
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private int id;
	
	

	public int getId() {
		return id;
	}

	public TankConnect(Socket socket,int id){
		this.socket = socket;
		String idString = ""+id;
		this.id = id;//Ϊ�˺Ͷ�Ӧ��̹��id��ƥ��
		try {
			oos = new ObjectOutputStream(this.socket.getOutputStream());
			ois = new ObjectInputStream(this.socket.getInputStream());
			
			oos.writeObject(idString);
			oos.flush();
			
		} catch (IOException e) {
			System.out.println("TankConnect IO����");
			e.printStackTrace();
		}
	}
	
	public Tank call(){
		
		Tank tank = null;
		try {
			 tank = (Tank)ois.readObject();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return tank;
	}
	
	
	public void send(ArrayList<Tank> tanks){
		try {
			oos.reset();
			oos.writeObject(tanks);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			oos.close();
			ois.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("�������ر�����ʧ�ܣ�");
			e.printStackTrace();
		}
		
	}
	
	
}
