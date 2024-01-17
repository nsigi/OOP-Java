package myfirstpackage;

public class MySecondClass{
	private int x, y;
	public MySecondClass(){
		x = 1;
		y = 1;
	}
	public void setX(int newX){
		x = newX;
	}
	public void setY(int newY){
		y = newY;
	}
	public int getx(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int sum(){
		return x + y;
	}
}