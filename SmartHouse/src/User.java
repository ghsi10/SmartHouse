import java.util.ArrayList;

public class User {
	private String name;
	private ArrayList<Device> devices;
	public User(String name) {
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public ArrayList<Device> getDevices() {
		return devices;
	}
	public void addDevice(Device d) {
		devices.add(d);
	}
}
