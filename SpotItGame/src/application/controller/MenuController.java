package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MenuController {
	//instantiate action objects
	@FXML
	private AnchorPane mainPane;

    @FXML
    /**
     * method to switch to the leaderboard screen
     * @param event
     * @throws IOException
     */
    void switchLeaderboards(ActionEvent event) throws IOException{
    	URL url = new File("src/application/view/Leaderboard.fxml").toURI().toURL();
    	mainPane = FXMLLoader.load(url);
        Scene scene = new Scene(mainPane);// pane you are GOING TO show
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();// pane you are ON
        scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
    	
        window.setScene(scene);
        window.show();

    }

    @FXML
    /**
     * method that handles switching to the create screen
     * @param event
     * @throws IOException
     */
    void switchToCreate(ActionEvent event) throws IOException {
    	URL url = new File("src/application/view/CreateDeck.fxml").toURI().toURL();
    	mainPane = FXMLLoader.load(url);
        Scene scene = new Scene(mainPane);// pane you are GOING TO show
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();// pane you are ON
        scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
    	
        window.setScene(scene);
        window.show();
    }

    @FXML
    /**
     * method that handles switching to the game screen
     * @param event
     * @throws IOException
     */
    void switchToPlay(ActionEvent event) throws IOException{
    	URL url = new File("src/application/view/GameStart.fxml").toURI().toURL();
    	mainPane = FXMLLoader.load(url);
        Scene scene = new Scene(mainPane);// pane you are GOING TO show
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();// pane you are ON
        scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
    	
        window.setScene(scene);
        window.show();
    }
    

}
