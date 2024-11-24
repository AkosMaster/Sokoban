package sokoban;
import java.awt.event.*; 
import java.awt.*; 
import javax.swing.*; 
import java.io.*;
import java.util.*;
import java.lang.Exception;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class MapPanel extends JPanel implements KeyListener {

	int tilesize = 35;

	HashMap<String, BufferedImage> sprites;

	void loadSprites() {
		sprites = new HashMap<String, BufferedImage>();
		try {
			sprites.put("bg", ImageIO.read(new File("sprites/bg2.png")));
			sprites.put("wall", ImageIO.read(new File("sprites/wall.png")));                
        	sprites.put("box1", ImageIO.read(new File("sprites/box1.png")));
        	sprites.put("error", ImageIO.read(new File("sprites/error.png")));
        	sprites.put("player", ImageIO.read(new File("sprites/player3.png")));
        	sprites.put("hbox", ImageIO.read(new File("sprites/hbox.png")));
            sprites.put("goal", ImageIO.read(new File("sprites/goal2.png")));
            sprites.put("goalborder", ImageIO.read(new File("sprites/goalborder.png")));
            sprites.put("cursor", ImageIO.read(new File("sprites/cursor.png")));
            sprites.put("icebox", ImageIO.read(new File("sprites/icebox.png")));
            sprites.put("key", ImageIO.read(new File("sprites/key.png")));
            sprites.put("door", ImageIO.read(new File("sprites/door.png")));
       	} catch (IOException ex) {
            System.out.println("could not load sprites");
       	}
	}

    Map map = null;

	public MapPanel(int tilesize) {
        this.tilesize = tilesize;
		loadSprites();       	
        addKeyListener(this);
	}

    void setMap(Map map) {
        this.map = map;
        this.setSize(tilesize * map.getWidth(), tilesize * map.getHeight());
    }

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (map == null) {
            return;
        }

        for (int x = 0; x < map.getWidth(); x++) {
        	for (int y = 0; y < map.getHeight(); y++) {
        		MapObject obj = map.getObjectAt(new Vec2(x,y));
        		String sprite = "bg";
        		if (obj != null) {
        			sprite = obj.getSprite();
        		}

                boolean needsBorder = false;
                if (map.isGoal(new Vec2(x,y))) {
                    if (obj == null) {
                        sprite = "goal";
                    } else {
                        needsBorder = true;
                    }
                }


        		g.drawImage(sprites.get(sprite), x*tilesize, y*tilesize, tilesize, tilesize, this);
                if (needsBorder) {
                    g.drawImage(sprites.get("goalborder"), x*tilesize, y*tilesize, tilesize, tilesize, this);
                }

                if (editPos.x == x && editPos.y == y && devMode) {
                    g.drawImage(sprites.get("cursor"), x*tilesize, y*tilesize, tilesize, tilesize, this);
                }
        	}
        }    
    }

    Vec2 editPos = new Vec2(0,0);
    boolean devMode = false;

    public boolean isDevMode() {
        return devMode;
    }

    // java % is remainder, wont work well for negative numbers
    int modulus(int a, int b) {
        return ((a%b) + b) % b;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            devMode = !devMode;
        }
        if (!devMode) {
            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                editPos.y = modulus((editPos.y-1), map.getHeight());
                break;
            case KeyEvent.VK_DOWN:
                editPos.y = modulus((editPos.y + 1), map.getHeight());
                break;
            case KeyEvent.VK_LEFT:
                editPos.x = modulus((editPos.x - 1), map.getWidth());
                break;
            case KeyEvent.VK_RIGHT:
                editPos.x = modulus((editPos.x + 1), map.getWidth());
                break;
            case KeyEvent.VK_O:
                try {
                    map.saveToFile();
                    System.out.println("map saved");
                } catch (Exception ex) {
                    System.out.println("Error while saving map: " + ex);
                }
                break;

            case KeyEvent.VK_0:
                map.setObjectAt(editPos, null);
                break;
            case KeyEvent.VK_1:
                map.setObjectAt(editPos, new Wall(map, editPos));
                break;
            case KeyEvent.VK_2:
                map.setObjectAt(editPos, new Box(map, editPos));
                break;
            case KeyEvent.VK_3:
                map.setObjectAt(editPos, new HeavyBox(map, editPos));
                break;
            case KeyEvent.VK_4:
                map.setObjectAt(editPos, new IceBox(map, editPos));
                break;
            case KeyEvent.VK_5:
                map.setObjectAt(editPos, new Door(map, editPos));
                break;
            case KeyEvent.VK_6:
                map.setObjectAt(editPos, new Key(map, editPos));
                break;
            case KeyEvent.VK_7:
                map.setGoal(editPos, !map.isGoal(editPos));
                break;
        }
    }
    public void keyReleased(KeyEvent e) {
        
    }

    public void keyTyped(KeyEvent e) {
        
    }
}








