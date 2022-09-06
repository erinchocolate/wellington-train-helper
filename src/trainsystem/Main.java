package trainsystem;

public class Main{
	
	public static void main(String[] args) {
		TrainSystem t = new TrainSystem();
		System.out.println(t.getTripByStation("Box-Hill", "Wellington", 1600));
	}

}
