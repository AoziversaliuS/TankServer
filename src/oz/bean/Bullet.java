package oz.bean;

import java.io.Serializable;

import oz.type.DirKey;

public class Bullet implements Serializable{
	
	//�ӵ�����һ����
	
	private int x;
	private int y;
	private DirKey dir;
	private boolean alive = true;
	
	
	
	public Bullet(int x, int y, DirKey dir) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		
		this.alive = true;
	}
	
	
	
	
	public Bullet(int x, int y, DirKey dir, boolean alive) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.alive = alive;
	}




	public boolean isAlive() {
		return alive;
	}




	public void setAlive(boolean alive) {
		this.alive = alive;
	}




	public Bullet() {
	}
	
	
	public void active(int speed){
		
		if( dir==DirKey.Up ){
			this.y = this.y - speed;
		}
		else if( dir==DirKey.Down ){
			this.y = this.y + speed;
		}
		else if( dir==DirKey.Left ){
			this.x = this.x - speed;
		}
		else if( dir==DirKey.Right ){
			this.x = this.x + speed;
		}
		
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
	public DirKey getDir() {
		return dir;
	}
	public void setDir(DirKey dir) {
		this.dir = dir;
	}
	
	
	

}
