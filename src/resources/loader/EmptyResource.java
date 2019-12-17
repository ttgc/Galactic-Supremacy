package resources.loader;

import org.newdawn.slick.SlickException;

public class EmptyResource implements ResourceLoadable {
	private SlickException exception;

	public EmptyResource(SlickException exception) {
		// TODO Auto-generated constructor stub
		this.exception = exception;
	}
	
	public void throwException() throws SlickException {
		throw exception;
	}

	@Override
	public boolean hasFailed() {
		// TODO Auto-generated method stub
		return true;
	}

}
