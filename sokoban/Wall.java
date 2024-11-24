package sokoban;
public class Wall extends MapObject {
	public Wall(Map host_map, Vec2 pos) {
		super(host_map, false, pos);
	}

	@Override
	String getSprite() {
		return "wall";
	}
}