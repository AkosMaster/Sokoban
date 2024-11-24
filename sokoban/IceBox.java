package sokoban;
public class IceBox extends MapObject {
	public IceBox(Map host_map, Vec2 pos) {
		super(host_map, true, pos);
	}

	@Override
	String getSprite() {
		return "icebox";
	}

	void slide(Vec2 direction) {
		try {
			while (map.getObjectAt(position.add(direction)) == null) {
				setPosition(position.add(direction));
			}
		} catch (Exception e) {
			return;
		}
	}

	@Override
	public boolean try_push(Vec2 direction, int depth) {
		try {
			MapObject objectInFront = map.getObjectAt(position.add(direction));
			if (objectInFront == null) {
				
				slide(direction);
				return true;
			} else {
				return super.try_push(direction, depth);
			}
		} catch (Exception e) { // don't push out of bounds
			return false;
		}
	}
}