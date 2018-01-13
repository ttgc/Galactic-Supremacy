package gameplay.shop;
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


public class ShopManager {
	private String[] name;
	private String[] description;
	private int[] prices;
	private boolean[] avalaible;
	private static boolean[] bought;
	private static boolean[] onlyonetime;
	private int current;
	private int total;

	public ShopManager(int[] id_avalaible) {
		// TODO Auto-generated constructor stub
		int nbr = 5;
		name = new String[nbr];
		description = new String[nbr];
		prices = new int[nbr];
		avalaible = new boolean[nbr];
		initData();
		total = 0;
		for (int i=0;i<avalaible.length;i++) {
			avalaible[i] = false;
		}		
		for (int i=0;i<id_avalaible.length;i++) {
			avalaible[id_avalaible[i]] = true;
			total++;
		}
		current = 0;
		
	}
	
	public int swapleft() {
		current--;
		if (current == -1) {
			current = total-1;
		}
		return current;
	}
	
	public int swapright() {
		current++;
		if (current == total) {
			current = 0;
		}
		return current;
	}
	
	public boolean buy(Player customer) {
		int count = 0;
		int getid = 0;
		for (int i=0;i<avalaible.length;i++) {
			if (avalaible[i]) {
				count++;
				if (count == current+1) {
					getid = i;
					break;
				}
			}
		}
		if (!(bought[getid] && onlyonetime[getid])) {
			if (customer.pay(prices[getid])) {
				bought[getid] = true;
				return true;
			}
		}
		return false;
	}
	
	public String getDescription() {
		int count = 0;
		int getid = 0;
		for (int i=0;i<avalaible.length;i++) {
			if (avalaible[i]) {
				count++;
				if (count == current+1) {
					getid = i;
					break;
				}
			}
		}
		return description[getid];
	}
	
	public int getPrice() {
		int count = 0;
		int getid = 0;
		for (int i=0;i<avalaible.length;i++) {
			if (avalaible[i]) {
				count++;
				if (count == current+1) {
					getid = i;
					break;
				}
			}
		}
		return prices[getid];
	}
	
	public boolean isBought() {
		int count = 0;
		int getid = 0;
		for (int i=0;i<avalaible.length;i++) {
			if (avalaible[i]) {
				count++;
				if (count == current+1) {
					getid = i;
					break;
				}
			}
		}
		return bought[getid];
	}
	
	public boolean isOnlyonetime() {
		int count = 0;
		int getid = 0;
		for (int i=0;i<avalaible.length;i++) {
			if (avalaible[i]) {
				count++;
				if (count == current+1) {
					getid = i;
					break;
				}
			}
		}
		return onlyonetime[getid];
	}
	
	public String getName() {
		int count = 0;
		int getid = 0;
		for (int i=0;i<avalaible.length;i++) {
			if (avalaible[i]) {
				count++;
				if (count == current+1) {
					getid = i;
					break;
				}
			}
		}
		return name[getid];
	}
	
	public int getID() {
		int count = 0;
		int getid = 0;
		for (int i=0;i<avalaible.length;i++) {
			if (avalaible[i]) {
				count++;
				if (count == current+1) {
					getid = i;
					break;
				}
			}
		}
		return getid;
	}
	
	private void initData() {
		//item 0 = Double Canon
		name[0] = "Double Canon";
		description[0] = "Un canon qui tire deux lasers paralleles en meme temps";
		prices[0] = 4000;
		//item 1 = Triple Canon
		name[1] = "Triple Canon";
		description[1] = "Un canon qui tire trois lasers en meme temps";
		prices[1] = 6000;
		//item 2 = Penta Canon
		name[2] = "Penta Canon";
		description[2] = "Un canon qui tire cinq lasers en meme temps";
		prices[2] = 10000;
		//item 3 = Bouclier protecteur
		name[3] = "Bouclier protecteur";
		description[3] = "Un bouclier qui protege votre vaisseau de 500 points de degats";
		prices[3] = 500;
		//item 4 = Basic Canon
		name[4] = "Canon Standard";
		description[4] = "Un canon standard qui tire un seul laser";
		prices[4] = 200;

	}
	
	public static ShopSave export_data() {
		ShopSave sav = new ShopSave();
		sav.save();
		return sav;
	}
	
	public static void import_data() {
		ShopSave sav = ShopSave.load();
		if (sav == null) {
			return;
		}
		bought = sav.getBougth();
		onlyonetime = sav.getOnlyonetime();
	}
	
	public static void initShops() {
		int nbr = 5;
		bought = new boolean[nbr];
		onlyonetime = new boolean[nbr];
		for (int i=0;i<nbr;i++) {
			bought[i] = false;
			onlyonetime[i] = false;
		}
		onlyonetime[0] = true;
		onlyonetime[1] = true;
		onlyonetime[2] = true;
	}
	
	public static void resetBought(int id) {
		bought[id] = false;
	}

	public static boolean[] getBought() {
		return bought.clone();
	}

	public static boolean[] getOnlyonetime() {
		return onlyonetime.clone();
	}

}
