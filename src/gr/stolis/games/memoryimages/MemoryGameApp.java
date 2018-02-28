package gr.stolis.games.memoryimages;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

import gr.stolis.games.memoryimages.model.MemoryGameSettingsModel;
import gr.stolis.games.memoryimages.view.MemoryGameMainVBoxView;
import gr.stolis.games.memoryimages.view.MemoryGameMainView;
import gr.stolis.games.memoryimages.view.MemoryGameSettingsView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MemoryGameApp extends Application {
	public static MemoryGameSettingsModel gameSettings = new MemoryGameSettingsModel();
	static Stage popUpStage = new Stage();
	
	@Override
	public void start(Stage stage) throws Exception {
		
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(1.0, 2.0, 1.0, 2.0));
		
		MenuBar menuBar = new MenuBar();
		Menu gameMenu = new Menu("Παιχνίδι");
		MenuItem mnuItemNewGame = new MenuItem("Νέο Παιχνίδι");
		mnuItemNewGame.setOnAction(ae -> startNewGame(layout));
		gameMenu.getItems().add(mnuItemNewGame);
		MenuItem mnuItemSettings = new MenuItem("Ρυθμίσεις");
		mnuItemSettings.setOnAction(ae -> {
			popUpStage.setX(stage.getX()+5);
			popUpStage.setY(stage.getY()+60.0);
			popUpStage.showAndWait();
		});
		gameMenu.getItems().add(mnuItemSettings);
		MenuItem mnuItemRecord = new MenuItem("Ρεκόρ");
//		mnuItemRecord.setOnAction(ae -> popUpStage.showAndWait());
		gameMenu.getItems().add(mnuItemRecord);
		MenuItem mnuItemExit = new MenuItem("Έξοδος");
		mnuItemExit.setOnAction(ae -> Platform.exit());
		gameMenu.getItems().add(mnuItemExit);
		menuBar.getMenus().add(gameMenu);
				
		
		MemoryGameSettingsView settingsView = new MemoryGameSettingsView(gameSettings);
		initPopUpStage(settingsView);
		
		layout.getChildren().addAll(menuBar);//, settingsView);
		
	    System.out.println(gameSettings.getDifficultyLevel());
	    
		stage.setTitle("Παιχνίδι Μνήμης για τους Μούργους");
	    stage.setScene(new Scene(layout, 850, 700));
	    stage.minWidthProperty().bind(stage.getScene().widthProperty());
	    stage.show();
	}
	
	private void startNewGame(Pane pane) {
//		MemoryGameMainView memoryCardView;
//		try {
//			memoryCardView = new MemoryGameMainView(gameSettings);
		MemoryGameMainVBoxView memoryCardView;
		try {
			memoryCardView = new MemoryGameMainVBoxView(gameSettings);
			for (Node child : pane.getChildren()) {
				if (child.getClass().getName().equals(memoryCardView.getClass().getName())) {
					pane.getChildren().remove(child);
//					((MemoryGameMainView)child).mediaPlayer.stop();
					((MemoryGameMainVBoxView)child).mediaPlayer.stop();
					child = null;
					break;
				}
			}
			pane.getChildren().add(memoryCardView);
			double totalWidth = 0;
			for (int i = 0; i < memoryCardView.mainGridPane.getColumnConstraints().size(); i++) {
				totalWidth += 200 + 10;
			}
			pane.getScene().getWindow().setWidth(totalWidth+5);
		} catch (IOException | URISyntaxException e) {
			System.out.println("Πρόβλημα στην Έναρξη του παιχνιδιού");
		}
	}
	
	public static void initPopUpStage(Pane pane){
		Scene scene2 = new Scene(pane, 200, 430);
		popUpStage.setScene(scene2);
		popUpStage.initModality(Modality.APPLICATION_MODAL);
		popUpStage.setTitle("Ρυθμίσεις Παιχνιδιού");
	}
	
	public static void main(String[] args) { launch(); }
}
