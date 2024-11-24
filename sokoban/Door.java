package sokoban;
public class Door extends MapObject {
	public Door(Map host_map, Vec2 pos) {
		super(host_map, false, pos);
	}

	@Override
	String getSprite() {
		return "door";
	}
}