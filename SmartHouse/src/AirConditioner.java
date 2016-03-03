
public class AirConditioner implements Device {
	public static final String name = "AirConditioner";
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
	@Override
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return name+" "+mode+" "+value;
	}
}
