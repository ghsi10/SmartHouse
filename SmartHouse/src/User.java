import java.util.ArrayList;

public class User {
	private String name;
	private ArrayList<Device> devices;
	public User(String name) {
		this.name=name;
		devices = new ArrayList<Device>();
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
	public String strGetDevices() {
		String tmp="";
		for (int i=0;i<devices.size()-1;i++)
			tmp+=devices.get(i).getName()+" ";
		tmp+=devices.get(devices.size()-1).getName();
		return tmp;
	}
	public Device getDevice(String d) {
		for (int i=0;i<devices.size();i++)
			if (devices.get(i).getName().compareTo(d)==0)
				return devices.get(i);
		return null;
	}
}
