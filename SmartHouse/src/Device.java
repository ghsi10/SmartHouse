public interface Device {
	String getName();
	void Mode(boolean m);
	default void Value(double v){};
}
