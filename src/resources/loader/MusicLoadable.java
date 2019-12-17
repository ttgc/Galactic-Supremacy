package resources.loader;

import java.io.InputStream;
import java.net.URL;
import java.util.function.Function;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public class MusicLoadable extends Music implements ResourceLoadable {

	public MusicLoadable(String ref) throws SlickException {
		super(ref);
		// TODO Auto-generated constructor stub
	}

	public MusicLoadable(URL ref) throws SlickException {
		super(ref);
		// TODO Auto-generated constructor stub
	}

	public MusicLoadable(InputStream arg0, String arg1) throws SlickException {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public MusicLoadable(URL arg0, boolean arg1) throws SlickException {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public MusicLoadable(String arg0, boolean arg1) throws SlickException {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	public static Function<String, ResourceLoadable> getInstantiator() {
		// TODO Auto-generated method stub
		return (String path) -> {
			try {
				return new MusicLoadable(path);
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
