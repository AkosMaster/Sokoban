package sokoban;
import java.awt.event.*; 
import java.awt.*; 
import javax.swing.*; 
import java.io.*;
import java.util.*;
import java.lang.Exception;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Map implements Serializable {

	MapObject[][] objects;
	boolean[][] goals;
	int width;
	int height;
	transient MapPanel panel;
	Player player;

	void setPlayer(Player player) {
		this.player = player;
	}
	Player getPlayer() {
		return player;
	}
	int getWidth() {
		return width;
	}
	int getHeight() {
		return height;
	}
	boolean isGoal(Vec2 pos) {
		return goals[pos.x][pos.y];
	}
	void setGoal(Vec2 pos, boolean is_goal) {
		goals[pos.x][pos.y] = is_goal;
	}

	boolean isWin() {
		boolean hasGoal = false;
		boolean win = true;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (goals[x][y]) {
					hasGoal = true;
					MapObject obj = getObjectAt(new Vec2(x,y));
					if (obj == null || (obj instanceof Player)) {
						win = false;
					}
				}
			}
		}
		return hasGoal && win && !panel.isDevMode();
	}

	MapPanel getPanel() {
		return panel;
	}
	void setPanel(MapPanel panel) {
		this.panel = panel;
	}

	public Map(int width, int height, MapPanel panel) {
		this.width = width;
		this.height = height;
		this.objects = new MapObject[width][height];
		this.goals = new boolean[width][height];
		this.panel = panel;
	}

	public MapObject getObjectAt(Vec2 pos) throws IndexOutOfBoundsException {
		if (pos.x < 0 || pos.y < 0 || pos.x > width || pos.y > height) {
			throw new IndexOutOfBoundsException("Coordinates outside map boundary");
		}

		return objects[pos.x][pos.y];
	}
	public void setObjectAt(Vec2 pos, MapObject obj) {
		objects[pos.x][pos.y] = obj;
	}

    public void saveToFile() throws FileNotFoundException, IOException {

    	String name = JOptionPane.showInputDialog("Map name: ");

    	FileOutputStream fos = new FileOutputStream("maps/" + name);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
    }

    static public Map loadFromFile(String name) throws FileNotFoundException, IOException, ClassNotFoundException {
    	//String name = JOptionPane.showInputDialog("Map name: ");

    	FileInputStream fis = new FileInputStream("maps/" + name);
        ObjectInputStream iis = new ObjectInputStream(fis);
        Map map = (Map) iis.readObject();

        return map;
    }
}








