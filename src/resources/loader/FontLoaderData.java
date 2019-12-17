package resources.loader;

import java.io.Serializable;

public class FontLoaderData implements Serializable {
	private static final long serialVersionUID = 5541471902795994957L;
	public String path;
	public int size;
	public boolean bold;
	public boolean italic;
	
	public FontLoaderData(String path, int size, boolean bold, boolean italic) {
		this.path = path;
		this.size = size;
		this.bold = bold;
		this.italic = italic;
	}

}
