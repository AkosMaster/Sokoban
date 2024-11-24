package sokoban;
import java.io.*;

public class MapObject implements Serializable {
	boolean pushable;
	Map map;
	Vec2 position;
	public MapObject(Map host_map, boolean can_push, Vec2 pos) throws IllegalStateException {
		this.map = host_map;
		this.pushable = can_push;
		this.position = pos.copy();
		if (map.getObjectAt(pos) != null) {
			throw new IllegalStateException("Position already occupied by other MapObject");
		}
		map.setObjectAt(pos, this);
	}

	public boolean is_pushable() {
		return pushable;
	}

	public Vec2 getPosition() {
		return position;
	}

	public void setPosition(Vec2 newPos) throws IllegalStateException {
		if (map.getObjectAt(newPos) != null) {
			throw new IllegalStateException("Position already occupied by other MapObject");
		}
		map.setObjectAt(position, null);
		map.setObjectAt(newPos, this);
		position = newPos.copy();
	}

	public boolean try_push(Vec2 direction, int depth) {
		if (!is_pushable()) {
			return false;
		}

		try {
			MapObject objectInFront = map.getObjectAt(position.add(direction));

			if (objectInFront == null || objectInFront.try_push(direction, depth+1)) {
				
				setPosition(position.add(direction));
				
				return true;
			}
		} catch (Exception e) { // don't push out of bounds
			return false;
		}
		return false;
	}

	String getSprite() {
		return "error";
	}
}