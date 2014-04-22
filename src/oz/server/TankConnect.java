package oz.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import oz.bean.Tank;
import oz.tool.Oz;

public class TankConnect {
	
	
	
	private Socket socket;
//	private ObjectInputStream ois;
//	private ObjectOutputStream oos;
	private BufferedReader in;
	private PrintWriter out;
	
	private int id;
	
	

	public int getId() {
		return id;
	}

	public TankConnect(Socket socket,int id){
		this.socket = socket;
		String idString = ""+id;
		this.id = id;//为了和对应的坦克id相匹配
		try {
//			oos = new ObjectOutputStream(this.socket.getOutputStream());
//			ois = new ObjectInputStream(this.socket.getInputStream());
//			oos.writeObject(idString);
//			oos.flush();
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			
			out.println(idString);
			out.flush();
			
		} catch (IOException e) {
			System.out.println("TankConnect IO出错！");
			e.printStackTrace();
		}
	}
	
	public Tank call(){
		
		Tank tank = null;
		try {
//			 tank = (Tank)ois.readObject();
             String recvBuf = in.readLine();
             tank = Oz.getTank(recvBuf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tank;
	}
	
	
	public void send(ArrayList<Tank> tanks){
//			oos.reset();
//			oos.writeObject(tanks);
//			oos.flush();
			String sendBuf = Oz.tanksString(tanks);
			out.println(sendBuf);
			out.flush();
			
	}
	
	public void close(){
		try {
//			oos.close();
//			ois.close();
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("服务器关闭链接失败！");
			e.printStackTrace();
		}
		
	}
	
	
}
