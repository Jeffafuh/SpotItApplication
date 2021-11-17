package application.controller;

import java.io.FileInputStream;

import application.model.Symbol;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameController {

    @FXML
    private ImageView testImage;

    public void initialize()
    {
    	Symbol s = Symbol.getRandSymbol();
    	try {
    		Image i = new Image(new FileInputStream(s.getSymbol().getPath()));
    		testImage.setImage(i);
    		testImage.setRotate(45);
    	}
    	catch(Exception e) { e.printStackTrace(); }
    }
}
