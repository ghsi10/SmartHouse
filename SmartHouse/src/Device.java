public class Device {
	private String name;
	private boolean mode;
	private int value;
	public Device (String name) {
		this.name=name;
		value=0;
		mode=false;
	}
	public String getName() {
		return name;
	}
	public boolean getMode() {
		return mode;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value=value;
	}
}
