package oz.bean;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import oz.type.DirKey;

public class Tank implements Serializable{
	
	public static final int  WIDTH = 40;
	public static final int HEIGHT = 40;
	
	public static final int M_DEGFAULT=0,M_EXIT_REQUEST=1,M_EXIT_PERMIT=2;
	private int clientMessage = Tank.M_DEGFAULT;
	
	private int x;
	private int y;

	private DirKey lastDir = DirKey.Up;

	
	private int     id;
	private String  name;
	private boolean alive;
	private int     hp;
	
	
	
	
	public Tank(Point p, int id,String name) {

		this.x = p.x;
		this.y = p.y;
		this.id = id;
		this.name = name;

		this.clientMessage = Tank.M_DEGFAULT;
		this.alive = true;
		this.hp = 100;
		this.lastDir = DirKey.Up;
	}
	
	


	public Tank() {
	}

	
	public Tank(int clientMessage, int x, int y, DirKey lastDir, int id,
			String name, boolean alive, int hp) {
		super();
		this.clientMessage = clientMessage;
		this.x = x;
		this.y = y;
		this.lastDir = lastDir;
		this.id = id;
		this.name = name;
		this.alive = alive;
		this.hp = hp;
	}




	public void active(DirKey key,int speed,int screenWidth,int screenHeight){
		final int OFFSET = 25;
		if( key!=DirKey.Else ){
			//保存坦克方向,画图时用
			lastDir = key;
		}
		
		if( key==DirKey.Up ){
			if( this.y>OFFSET ){
				this.y = this.y - speed;
			}
			
		}
		else if( key==DirKey.Down ){
			if( this.y<screenHeight-HEIGHT ){
				this.y = this.y + speed;
			}
		}
		else if( key==DirKey.Left ){
			if( this.x>0 ){
				this.x = this.x - speed;
			}
		}
		else if( key==DirKey.Right ){
			if( this.x<screenWidth-WIDTH ){
				this.x = this.x + speed;
			}
		}
	}
	
	
	private Point getCenter(){
		return new Point(x+WIDTH/2, y+HEIGHT/2);
	}
	
	
	
	public void hit(ArrayList<Bullet> bullets){
		for(Bullet b:bullets){
			//子弹活着的时候才有杀伤力
			if( b.isAlive() ){
				if(b.getX()>x && b.getX()<x+WIDTH && b.getY()>y && b.getY()<y+HEIGHT ){
					//暂时设置成 碰到子弹就死亡
					b.setAlive(false);
					this.setAlive(false);
					break;
				}
			}
			
		}
	}
	


	
	
	
	


	public DirKey getLastDir() {
		return lastDir;
	}




	public void setLastDir(DirKey lastDir) {
		this.lastDir = lastDir;
	}





	
	
	
	
	
	
	
	
	
	
	
	public int getClientMessage() {
		return clientMessage;
	}


	public void setClientMessage(int clientMessage) {
		this.clientMessage = clientMessage;
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public boolean isAlive() {
		return alive;
	}


	public void setAlive(boolean alive) {
		this.alive = alive;
	}


	public int getHp() {
		return hp;
	}


	public void setHp(int hp) {
		this.hp = hp;
	}


	@Override
	public String toString() {
		return "Tank [clientMessage=" + clientMessage + ", x=" + x + ", y=" + y
				+ ", id=" + id + ", name=" + name + ", alive=" + alive
				+ ", hp=" + hp + "]";
	}


	
	
	
	

}
