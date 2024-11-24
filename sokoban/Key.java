package sokoban;
public class Key extends MapObject {
	public Key(Map host_map, Vec2 pos) {
		super(host_map, true, pos);
	}

	@Override
	String getSprite() {
		return "key";
	}

	@Override
	public boolean try_push(Vec2 direction, int depth) {

		try {
			MapObject objectInFront = map.getObjectAt(position.add(direction));
			if (objectInFront != null && objectInFront instanceof Door) {
				// delete door and itself
				map.setObjectAt(position.add(direction), null);
				map.setObjectAt(position, null);
				return true;
			}
		} catch (Exception e) { // out of bounds

		}

		return super.try_push(direction, depth);
	}
}