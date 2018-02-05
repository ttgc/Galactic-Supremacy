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

package states.levels;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import exceptions.SpawnException;
import gameplay.Dialog;
import gameplay.ennemies.StarcupBoss;

public class Level_3 extends Level {
	private StarcupBoss boss;
	private Dialog initialDial;
	private Dialog finalDial;

	public Level_3() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		super.init(gc, sbg);
		limit = 0;
		try {
			boss = new StarcupBoss(400, 256, this);
		} catch (SpawnException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ennemies.add(boss);
		initialDial = new Dialog();
		initialDial.insertMsg("Escadron leader :\n\nNos radars indiquent quelque chose de non identitifie\nSoldat prenez garde...", 4000);
		initialDial.insertMsg("Transmission interrompue", 1000);
		initialDial.insertMsg("Inconnu :\n\nAeorjgejg kernger orejten trnteorgne\noehgerte... ternejghz", 1500);
		initialDial.insertMsg("Traducteur active", 1000);
		initialDial.insertMsg("Inconnu :\n\nJe suis le leader Starcup", 1000);
		initialDial.insertMsg("Starcup leader :\n\nVotre monde sera bientot le notre\nNous allons vous detruire !\nTon chemin se finit ici", 4000);
		finalDial = new Dialog();
		finalDial.insertMsg("Starcup leader :\n\nNooooooooooon", 1500);
		finalDial.insertMsg("Transmission entrante", 1000);
		finalDial.insertMsg("Escadron leader :\n\nSoldat\nVous avez battu ce monstre\nMais qu est ce que c etait", 3000);
		finalDial.insertMsg("Escadron leader :\n\nRentrez a la base\nNous avons besoin de votre rapport", 2000);
		initialDial.start();
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		super.render(gc, sbg, g);
		//boss.render(g);
		if (initialDial != null && initialDial.isStarted()) {
			initialDial.render(g);
		}
		if (finalDial.isStarted()) {
			finalDial.render(g);
		}
	}
	
	@Override
	public void keyPressed(int key, char c) {
		// TODO Auto-generated method stub
		if ((initialDial != null && initialDial.isStarted()) || finalDial.isStarted()) {
			return;
		}
		super.keyPressed(key, c);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub
		if (initialDial != null) {
			if (initialDial.isStarted()) {
				initialDial.update(delta);
				return;
			} else {
				initialDial = null;
				boss.initialize();
			}
		}
		if (finalDial.isStarted()) {
			finalDial.update(delta);
			return;
		}
		super.update(gc, sbg, delta);
		//boss.update(delta);
		if (boss.isIndestr() && !finalDial.isStarted()) {
			boss.loot(player);
			finalDial.start();
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 12;
	}

	@Override
	public boolean isSpawnable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void generateSpawn() throws SpawnException {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public void launch_endlevel() {
		// TODO Auto-generated method stub
		if (!boss.alive && !finalDial.isStarted()) {
			setfinished();
		}
	}

	@Override
	protected void initObstacle() {
		// TODO Auto-generated method stub
		return;
	}

}
