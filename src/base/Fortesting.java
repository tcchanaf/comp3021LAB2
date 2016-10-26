package base;

public class Fortesting {

	public Fortesting() {
		// TODO Auto-generated constructor stub
	}
	
	public void fn(int count){
		count++;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Fortesting lol = new Fortesting();
		int count = 1;
		lol.fn(count);
		System.out.println(count);;
	}

}
