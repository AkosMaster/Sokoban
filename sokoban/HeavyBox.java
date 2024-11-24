package sokoban;
public class HeavyBox extends MapObject {
	public HeavyBox(Map host_map, Vec2 pos) {
		super(host_map, true, pos);
	}

	@Override
	String getSprite() {
		return "hbox";
	}

	@Override
	public boolean try_push(Vec2 direction, int depth) {

		if (depth > 1) {
			return false;
		}
		try {
			MapObject objectInFront = map.getObjectAt(position.add(direction));
			if (objectInFront == null) {
				
				setPosition(position.add(direction));
				return true;
			}
		} catch (Exception e) { // don't push out of bounds
			return false;
		}
		return false;
	}
}