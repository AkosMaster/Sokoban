package sokoban;
import java.awt.event.*; 
import java.awt.*; 
import javax.swing.*;
import java.io.*;
import java.util.*;

/** A program főosztálya. */
public class Main {

	/** A "maps" mappában lévő fájlok listája, ezekből lehet választani a főmenüben. (és "új pálya" opció) */
	static ArrayList<String> getMapList() {
		ArrayList<String> maps = new ArrayList<>();

		maps.add("<create new map>");

		File mapdir = new File("maps");
      	File[] mapfiles = mapdir.listFiles();

      	for (File file : mapfiles) {
      		maps.add(file.getName());
      	}
      	return maps;
	}

	/** Felhoz egy popup ablakot, ami a játékostól egy pálya választását kéri, és ennek a nevét visszaadja. Ez a játék főmenüje. */
	static String mapSelector() {
		JPanel menu = new JPanel();

		ArrayList<String> maps = getMapList();

		JComboBox<String> mapSelector = new JComboBox<String>(maps.toArray(new String[0]));

		menu.add(mapSelector);
		
		int res =  JOptionPane.showConfirmDialog(null, menu, "Main Menu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (res != JOptionPane.OK_OPTION) {
			System.exit(0);
		}

		return mapSelector.getSelectedItem().toString();
	}

	/** Megkéri a játékost hogy válasszon egy pályát, majd ezt be is tölti és visszaadja. Ha a játékos új pályát akar létrehozni,
		akkor megkérdezi a méretét, és ekkora pályát hoz létre. */
	static Map selectMap() throws FileNotFoundException, IOException, ClassNotFoundException {
		String mapFile = mapSelector();
		Map map;

		if (mapFile.equals("<create new map>")) {

			String sizeStr = JOptionPane.showInputDialog("Map size: ");
			try {
				int size = Integer.parseInt(sizeStr);
				map = new Map(size,size, null);
			} catch (NumberFormatException e) {
				map = new Map(9,9, null);
			}

			Player p = new Player(map, new Vec2(0,0));
		} else {
			map = Map.loadFromFile(mapFile);
		}
		return map;
	}

	/** A kirajzolt csempék mérete. */
	static int tilesize = 700/12;

	/** Egy megadott pályához létrehoz egy ablakot, amely ezt kirajzolja, és kezeli a bemeneket. */
	static JFrame createGameFrame(Map map) {

		JFrame frame = new JFrame("sokoban");
		
		frame.setSize(map.getWidth()*tilesize + 15,map.getHeight()*tilesize + 35);
		MapPanel mapPanel = new MapPanel(tilesize);

		mapPanel.setFocusable(true);
		mapPanel.setVisible(true);
		
		frame.add(mapPanel);
		frame.show();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		map.setPanel(mapPanel);
		mapPanel.setMap(map);

		mapPanel.addKeyListener(map.getPlayer());
		return frame;
	}

	/** Kiírja a "nyertél" üzenetet egy popup ablakban. */
	static void winPopup() {
        JOptionPane.showMessageDialog(null, "You win!", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
    }

    /** main metódus, elindítja a főmenüt, betölti a választott pályát, majd vár amíg a játék véget nem ér.
    	Ekkor kiírja a "nyertél" üzenetet, majd újból a főmenübe léptet.*/
	public static void main(String[] args) throws InterruptedException {

		while (true) {
			Map map;
			JFrame frame;

			// próbáljunk meg választani egy pályát, és hozzuk létre az ablakot ha sikerül. Ha baj van, próbáljuk újra.
			try {
				map = selectMap();
				frame = createGameFrame( map );
            } catch (Exception e) {
            	JOptionPane.showMessageDialog(null, "An error occurred while loading your map.", "Error!", JOptionPane.INFORMATION_MESSAGE);
            	continue;
            }

            /** Várjunk amíg nem nyer a játékos. */
			synchronized(map) {
				map.wait();
			}

			/** Juhú, nyertünk! */
            winPopup();

            /** dobjuk ki a frame-et, és kezdődhet minden újra */
        	frame.setVisible(false);
        	frame.dispose();
        }
	}
}