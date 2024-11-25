package junittest;
import java.io.*;
import java.lang.Exception;
import java.lang.*;
import java.util.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class SokobanTest {

	sokoban.Map map;
	sokoban.MapPanel panel;

	sokoban.MapObject wall;
	sokoban.MapObject box;
	sokoban.MapObject hbox;
	sokoban.MapObject icebox;
	sokoban.MapObject key;

	@BeforeEach
	public void setUp() throws FileNotFoundException, IOException, ClassNotFoundException {
		map = sokoban.Map.loadFromFile("demo");

		wall = map.getObjectAt(new sokoban.Vec2(1, 4));
		box = map.getObjectAt(new sokoban.Vec2(2, 4));
		hbox = map.getObjectAt(new sokoban.Vec2(3, 4));
		icebox = map.getObjectAt(new sokoban.Vec2(4, 4));
		key = map.getObjectAt(new sokoban.Vec2(6, 4));
	}
	@Test
	public void map_getObjectTest() {
		Assert.assertTrue(map.getObjectAt(new sokoban.Vec2(1, 4)) instanceof sokoban.Wall);
		Assert.assertTrue(map.getObjectAt(new sokoban.Vec2(2, 4)) instanceof sokoban.Box);
		Assert.assertTrue(map.getObjectAt(new sokoban.Vec2(3, 4)) instanceof sokoban.HeavyBox);
		Assert.assertTrue(map.getObjectAt(new sokoban.Vec2(4, 4)) instanceof sokoban.IceBox);
		Assert.assertTrue(map.getObjectAt(new sokoban.Vec2(5, 4)) instanceof sokoban.Door);
		Assert.assertTrue(map.getObjectAt(new sokoban.Vec2(6, 4)) instanceof sokoban.Key);
		Assert.assertTrue(map.getObjectAt(new sokoban.Vec2(7, 4)) == null);

		Assert.assertThrows(IndexOutOfBoundsException.class, () -> map.getObjectAt(new sokoban.Vec2(100, 0)));
		Assert.assertThrows(IndexOutOfBoundsException.class, () -> map.getObjectAt(new sokoban.Vec2(-1, -1)));
	}
	@Test
	public void map_createObjectTest() {
		Assert.assertThrows(IndexOutOfBoundsException.class, () -> map.setObjectAt(new sokoban.Vec2(100, 1), null));

		new sokoban.Wall(map, new sokoban.Vec2(1,1));
		Assert.assertTrue(map.getObjectAt(new sokoban.Vec2(1, 1)) instanceof sokoban.Wall);

		map.setObjectAt(new sokoban.Vec2(1,1), null);
		Assert.assertTrue(map.getObjectAt(new sokoban.Vec2(1, 1)) == null);
	}

	@Test
	public void map_winTest() {
		Assert.assertFalse(map.isWin());

		new sokoban.Box(map, new sokoban.Vec2(7, 4));

		Assert.assertTrue(map.isWin());
	}

	@Test
	public void wall_pushTest() {
		Assert.assertFalse(wall.try_push(new sokoban.Vec2(1,0), 0));
		Assert.assertTrue(wall.getPosition().x == 1 && wall.getPosition().y == 4);
		Assert.assertFalse(wall.try_push(new sokoban.Vec2(0,1), 0));
		Assert.assertTrue(wall.getPosition().x == 1 && wall.getPosition().y == 4);
	}

	@Test
	public void box_pushTest() {
		Assert.assertFalse(box.try_push(new sokoban.Vec2(1,0), 0));
		Assert.assertTrue(box.getPosition().x == 2 && box.getPosition().y == 4);
		Assert.assertTrue(box.try_push(new sokoban.Vec2(0,1), 0));
		Assert.assertTrue(box.getPosition().x == 2 && box.getPosition().y == 5);
	}

	@Test
	public void hbox_pushTest() {
		Assert.assertFalse(hbox.try_push(new sokoban.Vec2(1,0), 0));
		Assert.assertTrue(hbox.getPosition().x == 3 && hbox.getPosition().y == 4);
		Assert.assertTrue(hbox.try_push(new sokoban.Vec2(0,1), 0));
		Assert.assertTrue(hbox.getPosition().x == 3 && hbox.getPosition().y == 5);
	}

	@Test
	public void icebox_pushTest() {
		Assert.assertFalse(icebox.try_push(new sokoban.Vec2(-1,0), 0));
		Assert.assertTrue(icebox.getPosition().x == 4 && icebox.getPosition().y == 4);
		Assert.assertTrue(icebox.try_push(new sokoban.Vec2(0,1), 0));
		Assert.assertTrue(icebox.getPosition().x == 4 && icebox.getPosition().y == 8);
	}

	@Test
	public void key_and_door_test() {
		Assert.assertTrue(key.try_push(new sokoban.Vec2(-1,0), 0));
		Assert.assertEquals(map.getObjectAt(new sokoban.Vec2(5, 4)), null);
		Assert.assertEquals(map.getObjectAt(new sokoban.Vec2(6, 4)), null);
	}

	@Test
	public void mapPanelTest() throws IOException {
		panel = new sokoban.MapPanel(10);

		panel.setMap(map);
		map.setPanel(panel);
	}

	@Test
	public void mapListTest() {
		ArrayList<String> list = sokoban.Map.getMapList();
		Assert.assertTrue(list.contains("demo"));
	}
}




