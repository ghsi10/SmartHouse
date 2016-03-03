
public class AirConditioner implements Device {
	public static final String name = "Lamp";
	private boolean mode;
	private double value;
	@Override
	public void Mode(boolean m) {
		mode=m;
	}
	@Override
	public void Value(double v) {
		value=v;
	}
}
