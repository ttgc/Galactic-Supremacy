package resources.loader;

import java.io.InputStream;
import java.net.URL;
import java.util.function.Function;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class SoundLoadable extends Sound implements ResourceLoadable {

	public SoundLoadable(URL arg0) throws SlickException {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public SoundLoadable(String arg0) throws SlickException {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public SoundLoadable(InputStream arg0, String arg1) throws SlickException {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	public static Function<String, ResourceLoadable> getInstantiator() {
		// TODO Auto-generated method stub
		return (String path) -> {
			try {
				return new SoundLoadable(path);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				return new EmptyResource(e);
			}
		};
	}

	@Override
	public boolean hasFailed() {
		// TODO Auto-generated method stub
		return false;
	}

}
