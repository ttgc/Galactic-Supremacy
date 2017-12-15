import java.util.Vector;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class MapPath {
	private int total_level;
	private int avalaible_level;
	private Vector<Integer> xlev;
	private Vector<Integer> ylev;
	private int position;

	public MapPath(int xori, int yori) {
		// TODO Auto-generated constructor stub
		xlev = new Vector<Integer>();
		ylev = new Vector<Integer>();
		xlev.add(xori);
		ylev.add(yori);
		avalaible_level = 1;
		total_level = 1;
		position = 0;
	}
	
	public void add_level(int x, int y) {
		xlev.add(x);
		ylev.add(y);
		total_level++;
	}
	
	public void enable_next() {
		if (avalaible_level < total_level) {
			avalaible_level++;
		}
	}
	
	public int forward() {
		if (position < avalaible_level-1) {
			position++;
		}
		return position;
	}
	
	public int backward() {
		if (position > 0) {
			position--;
		}
		return position;
	}
	
	public void draw(Graphics g, Color line, Color av, Color notav, Image cursor) {
		Color old = g.getColor();
		float old_line = g.getLineWidth();
		g.setLineWidth(2);
		g.setColor(line);
		for (int i=0;i<total_level-1;i++) {
			g.drawLine(xlev.get(i), ylev.get(i), xlev.get(i+1), ylev.get(i+1));
		}
		g.setLineWidth(old_line);
		g.setColor(av);
		for (int i=0;i<avalaible_level;i++) {
			g.fillOval(xlev.get(i)-12, ylev.get(i)-12, 24, 24);
		}
		g.setColor(notav);
		for (int i=avalaible_level;i<total_level;i++) {
			g.fillOval(xlev.get(i)-12, ylev.get(i)-12, 24, 24);
		}
		cursor.drawCentered(xlev.get(position), ylev.get(position));
		g.setColor(old);
	}

	public int getTotal_level() {
		return total_level;
	}

	public void setTotal_level(int total_level) {
		if (total_level < this.total_level) {
			return;
		}
		this.total_level = total_level;
	}

	public int getAvalaible_level() {
		return avalaible_level;
	}

	public Vector<Integer> getXlev() {
		return xlev;
	}

	public Vector<Integer> getYlev() {
		return ylev;
	}

	public int getPosition() {
		return position;
	}

}
