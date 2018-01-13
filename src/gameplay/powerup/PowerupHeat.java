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

public class PowerupHeat extends Powerup {
	private boolean malus;

	public PowerupHeat(double x, double y, boolean malus) {
		super(x, y);
		// TODO Auto-generated constructor stub
		this.malus = malus;
	}

	@Override
	public void transfer(Player target) {
		// TODO Auto-generated method stub
		int amount;
		if (malus) {
			amount = target.getShip().getCanon().getHeat()+10;
			if (amount > 100) {
				amount = 100;
			}
		} else {
			amount = target.getShip().getCanon().getHeat()-10;
			if (amount < 0) {
				amount = 0;
			}
		}
		target.getShip().getCanon().setHeat(amount);

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 4;
	}

}
