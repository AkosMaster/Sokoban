package sokoban;
import java.awt.event.*;

public class Player extends MapObject implements KeyListener {
	public Player(Map host_map, Vec2 pos) {
		super(host_map, true, pos);
		host_map.setPlayer(this);
	}

	@Override
	String getSprite() {
		return "player";
	}

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		switch(keyCode) {
			case KeyEvent.VK_W:
				this.try_push(new Vec2(0,-1), 0);
				break;
			case KeyEvent.VK_S:
				this.try_push(new Vec2(0,1), 0);
				break;
			case KeyEvent.VK_A:
				this.try_push(new Vec2(-1,0), 0);
				break;
			case KeyEvent.VK_D:
				this.try_push(new Vec2(1,0), 0);
				break;
		}
		map.getPanel().repaint();

		synchronized(map) {
			if (map.isWin()) {
				map.notifyAll();
			}
		}
    }

    public void keyReleased(KeyEvent e) {
    	
    }

    public void keyTyped(KeyEvent e) {
    	
    }
}