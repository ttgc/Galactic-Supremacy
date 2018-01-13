package gameplay.powerup;
import gameplay.player.Player;

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

public class PowerupCooldown extends Powerup {
	private boolean down;

	public PowerupCooldown(double x, double y, boolean malus) {
		super(x, y);
		// TODO Auto-generated constructor stub
		down = malus;
	}

	@Override
	public void transfer(Player target) {
		// TODO Auto-generated method stub
		if (down) {
			target.getShip().getCanon().setCooldown(target.getShip().getCanon().getCooldown()*2);
		} else {
			target.getShip().getCanon().setCooldown(target.getShip().getCanon().getCooldown()/2);
		}

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}

}
