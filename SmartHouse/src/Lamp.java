
public class Lamp implements Device {
	public static final String name = "Lamp";
	private boolean mode;
	@Override
	public void Mode(boolean m) {
		System.out.println(m);
		mode=m;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return name+" "+mode;
	}

}
