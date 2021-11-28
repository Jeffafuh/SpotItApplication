package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import application.model.Card;
import application.model.Deck;
import application.model.Symbol;
import application.model.loadedImage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class CreateDeckController {
	
	@FXML
    private ScrollPane displayPane;

    @FXML
    private Button generateButton;
    
    @FXML
    private Label errorText;
    
    @FXML
    private Label ImagesPerCard;
    
    @FXML
    private Label VisualStyle;
    
    @FXML
    private ChoiceBox<String> symbolSelect;
    
    @FXML
    private AnchorPane mainPane;
    
    private double minSize;
    
    
    @FXML
    public void initialize()
    {
    	symbolSelect.getItems().add("2 (Default 3 Cards)");
    	symbolSelect.getItems().add("3 (Default 7 Cards)");
    	symbolSelect.getItems().add("4 (Default 13 Cards)");
    	symbolSelect.getItems().add("5 (Default 21 Cards)");
    	symbolSelect.getItems().add("6 (Default 31 Cards)");
    	symbolSelect.getItems().add("8 (Default 57 Cards)");
    	symbolSelect.getItems().add("9 (Default 73 Cards)");
    	symbolSelect.getItems().add("10 (Default 91 Cards)");
    	symbolSelect.getSelectionModel().select(0);
    }
    
    public int getNumSymbols()
    {
    	String s = symbolSelect.getValue();
    	int a = Integer.parseInt(s.substring(0, s.indexOf(' ')));
    	return a;
    }

    @FXML
    void generateDeck(ActionEvent event) throws IOException {
		int N = getNumSymbols()-1;
		minSize = (-25.0/7.0) * N + 67;
		Deck d = new Deck();
		d.constructNewDeck(N);
		Pane p = new Pane();
		
		p.setPrefSize(1050, (d.getDeckSize()+1)/3 * 350);
		displayPane.setContent(p);
		
		int i = 0, j = 0;
		while(!d.isDeckEmpty())
		{
			Pane toAdd = new Pane();
			displayCard(d.pop(), toAdd);
			toAdd.setTranslateX(350*i);
			toAdd.setTranslateY(350*(j/3));
			p.getChildren().add(toAdd);
			i = (i+1)%3;
			j++;
		}
    }
    
    public void displayCard(Card c, Pane p)
    {
    	Circle circle = new Circle(175);
    	circle.setTranslateX(175);
    	circle.setTranslateY(175);
    	circle.setFill(Color.WHITE);
    	circle.setStroke(Color.BLACK);
    	
    	ArrayList<Symbol> s = c.getSymbolList();
    	for(Symbol symb : s)
    	{
    		ImageView i = new ImageView();
 
    		try {
        		loadedImage img = new loadedImage(symb.getSymbol().getPath());
        		i.setImage(img);
        	}
    		catch(Exception e) { e.printStackTrace(); }
    		
    		double randSize = (Math.random()*(60-minSize)+minSize);
        	
        	i.setPreserveRatio(true);
        	i.setRotate(Math.random()*360);
    		i.setFitHeight(randSize);
        	i.setFitWidth(randSize);
        	
        	int counter = 0;
        	boolean intersects;
        	do {
        		intersects = false;
	        	double[] point = randPoint(175);
	        	i.setTranslateX(point[0]);
	        	i.setTranslateY(point[1]);
	        	
	        	for(Node n : p.getChildren())
	        	{
	        		if(i.getBoundsInParent().intersects(n.getBoundsInParent()))
	        		{
	        			intersects = true;
	        		}
	        	}
	        	counter++;
        	}while(intersects && counter < 1000);
        	
        	p.getChildren().add(i);
    	}
    	
    	p.getChildren().add(0, circle);
    }
    
    public double[] randPoint(double r)
    {
    	double[] point = new double[2];
    	double angle = Math.random() * 2 * Math.PI;
    	double hyp = Math.sqrt(Math.random()) * (19.0/26.0) * r;
    	point[0] = (45.0/52.0) * r+(Math.cos(angle) * hyp);
    	point[1] = (45.0/52.0) * r+(Math.sin(angle) * hyp);
    	return point;
    }
    
    @FXML
    void switchToMenu(ActionEvent event) throws IOException {
    	URL url = new File("src/application/view/Menu.fxml").toURI().toURL();
    	mainPane = FXMLLoader.load(url);
        Scene scene = new Scene(mainPane);// pane you are GOING TO show
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();// pane you are ON
        scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
    	
        window.setScene(scene);
        window.show();
    }
    
}
