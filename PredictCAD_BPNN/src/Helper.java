
public class Helper {
	public static double sigmoidFunct(double x) {
		return 1.0 / (1.0 + Math.pow(Math.E, -x));
	}
	
	public static double derivative_sigmoidFunct(double x) {
		return sigmoidFunct(x) * (1.0 - sigmoidFunct(x));
	}
	
}
