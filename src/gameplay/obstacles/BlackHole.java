/*******************************************************************************
	Galactic Supremacy, Shoot'em up game
	Copyright (C) 2017, 2018  PIOT Thomas
		
	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.
		
	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.
	
	You should have received a copy of the GNU General Public License
	along with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package gameplay.obstacles;

import org.newdawn.slick.Graphics;

import basics.RoundHitbox;
import gameplay.Shoot;
import gameplay.ennemies.Ennemy;
import gameplay.player.Ship;
import states.levels.Level;

public class BlackHole {
	private HoleState state;
	private BlackHole link;
	private double x;
	private double y;
	private RoundHitbox hitbox;

	public BlackHole(double x, double y) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		link = null;
		state = HoleState.Closed;
		hitbox = new RoundHitbox(32);
		hitbox.update(x, y);
	}
	
	public BlackHole(double x, double y, BlackHole link, boolean bidirectional) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this.link = link;
		link.link = this;
		if (bidirectional) {
			state = HoleState.Open;
			link.state = HoleState.Open;
		} else {
			state = HoleState.Entrance;
			link.state = HoleState.Exit;
		}
		hitbox = new RoundHitbox(32);
		hitbox.update(x, y);
	}
	
	public void render(Graphics g) {
		if (state.equals(HoleState.Closed)) {
			Level.getRessources()[6].drawCentered((float) x, (float) y);
		} else {
			Level.getRessources()[5].drawCentered((float) x, (float) y);
		}
	}
	
	public void update(int delta) {
		y++;
		hitbox.update(x, y);
	}
	
	public void linkto(BlackHole link, boolean bidirectional) {
		this.link = link;
		link.link = this;
		if (bidirectional) {
			state = HoleState.Open;
			link.state = HoleState.Open;
		} else {
			state = HoleState.Entrance;
			link.state = HoleState.Exit;
		}
	}
	
	public void unlink() {
		link.link = null;
		link.state = HoleState.Closed;
		state = HoleState.Closed;
		link = null;
	}
	
	public boolean teleport(Ship ship) {
		if (hitbox.check_collision(new RoundHitbox(ship.getHitbox())) && (state.equals(HoleState.Entrance) || state.equals(HoleState.Open))) {
			ship.setX(link.x);
			ship.setY(link.y);
			return true;
		}
		return false;
	}
	
	public boolean teleport(Ennemy mob) {
		if (hitbox.check_collision(mob.getRhitbox()) && (state.equals(HoleState.Entrance) || state.equals(HoleState.Open))) {
			mob.setX(link.x);
			mob.setY(link.y);
			return true;
		}
		return false;
	}
	
	public boolean teleport(Shoot shoot) {
		if (hitbox.check_collision_point(shoot.getX(), shoot.getY()) && (state.equals(HoleState.Entrance) || state.equals(HoleState.Open))) {
			shoot.setX(link.x);
			shoot.setY(link.y);
			return true;
		}
		return false;
	}

}
