public interface Device {
	void Mode(boolean m);
	default void Value(double v){};
}
