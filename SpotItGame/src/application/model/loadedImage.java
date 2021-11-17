package application.model;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.scene.image.Image;

public class loadedImage extends Image{
	
	private String path;

	public loadedImage(String url) throws IOException{
		super(new FileInputStream(url));
		path = url;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
