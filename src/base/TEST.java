package base;

public class TEST {

	public TEST() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			m();
			System.out.println("1");
		
		}catch(RuntimeException ex){
			System.out.println("2");
		}
		catch(Exception ex){
			System.out.println(3);
		}
	}
	static void m() throws Exception{
		try{
			String s = "5.6";
			Integer.parseInt(s);
//		}catch(NumberFormatException ex){
//			System.out.println(ex instanceof Exception);
//			throw ex;
//		}
		}catch(RuntimeException ex){
			System.out.println(5);
		}
	}
	public static void foo (int i)throws Exception{
		if(i == 0){
			throw new Exception();
		}
	}

}
