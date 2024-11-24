package sokoban;
import java.io.*;

public class Box extends MapObject implements Serializable {
	public Box(Map host_map, Vec2 pos) {
		super(host_map, true, pos);
	}

	@Override
	String getSprite() {
		return "box1";
	}
}