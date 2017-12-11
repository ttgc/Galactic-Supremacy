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

public class DoubleCanon extends Canon {
	private static final long serialVersionUID = 6689655347857095546L;

	public DoubleCanon(int dur, float cd) {
		super(dur, cd);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Shoot[] shoot(double x, double y) {
		// TODO Auto-generated method stub
		tick();
		tick();
		Shoot[] result = {new Shoot(x-4, y, 90, true), new Shoot(x+4, y, 90, true)};
		return result;
	}

}
