package sokoban;
import java.io.*;

public class Vec2 implements Serializable {
	public int x;
	public int y;
	public Vec2(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vec2 add(Vec2 v) {
		return new Vec2(x + v.x, y + v.y);
	}
	public Vec2 copy() {
		return new Vec2(x,y);
	}
}