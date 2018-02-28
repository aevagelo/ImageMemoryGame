package gr.stolis.games.memoryimages.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;

import gr.stolis.games.memoryimages.model.MemoryGameSettingsModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MemoryGameSettingsView extends VBox {

	private final MemoryGameSettingsModel settingsModel;
	
	Label lblForLevelSettings = new Label("Επιλέξτε Επίπεδο Δυσκολίας");
	Label lblForTimeSettings = new Label("Επιλέξτε Χρόνο Εμφάνισης των Εικόνων");
	Label lblForThemeResourceSettings = new Label("Επιλέξτε Θέμα Εικόνων");
	
	final ToggleGroup groupForLevel = new ToggleGroup();
	final ToggleGroup groupForTime = new ToggleGroup();
	final ToggleGroup groupForResourceFolder = new ToggleGroup();
	
	RadioButton rbLevelEasy = new RadioButton("Εύκολο (12 Κάρτες)");
	RadioButton rbLevelMedium = new RadioButton("Μεσαίο (16 Κάρτες)");
	RadioButton rbLevelDifficult = new RadioButton("Δύσκολο (24 Κάρτες)");
	RadioButton rbTimeEasy = new RadioButton(String.valueOf(MemoryGameSettingsModel.TIME_DELAY_LEVEL_EASY) + " δευτερόλεπτα");
	RadioButton rbTimeDifficult = new RadioButton(String.valueOf(MemoryGameSettingsModel.TIME_DELAY_LEVEL_DIFFICULT) + " δευτερόλεπτα");
	

	GridPane gridForLevelSettings = new GridPane();
	GridPane gridForTimeSettings = new GridPane();
	GridPane gridForThemeSettings = new GridPane();
	
	//not needed at the moment
	Button saveBtn = new Button("Αποθήκευση");
	Button closeBtn = new Button("Κλείσιμο");
	
	public MemoryGameSettingsView(MemoryGameSettingsModel model) {
		super();
		this.settingsModel = model;
		initLayout();
		initData();
		bindViewToModel();
	}
	
	public MemoryGameSettingsView(MemoryGameSettingsModel model, double spacing) {
		super(spacing);
		this.settingsModel = model;
		initLayout();
		initData();
		bindViewToModel();
	}
	
	private void initLayout() {
		gridForLevelSettings.setVgap(10.0);
		gridForLevelSettings.setStyle("-fx-border-color: grey;   -fx-border-width: 2px;   -fx-padding: 10;   -fx-spacing: 8;");
		gridForLevelSettings.add(rbLevelEasy, 0, 0);
		gridForLevelSettings.add(rbLevelMedium, 0, 1);
		gridForLevelSettings.add(rbLevelDifficult, 0, 2);
		rbLevelEasy.setToggleGroup(groupForLevel);
		rbLevelMedium.setToggleGroup(groupForLevel);
		rbLevelDifficult.setToggleGroup(groupForLevel);
		
		gridForTimeSettings.setVgap(10.0);
		gridForTimeSettings.setStyle("-fx-border-color: grey;   -fx-border-width: 2px;   -fx-padding: 10;   -fx-spacing: 8;");
		gridForTimeSettings.add(rbTimeEasy, 0, 0);
		gridForTimeSettings.add(rbTimeDifficult, 0, 1);
		rbTimeEasy.setToggleGroup(groupForTime);
		rbTimeDifficult.setToggleGroup(groupForTime);
		
		gridForThemeSettings.setVgap(10.0);
		gridForThemeSettings.setStyle("-fx-border-color: grey;   -fx-border-width: 2px;   -fx-padding: 10;   -fx-spacing: 8;");
		Enumeration<URL> en;
		try {
			en = getClass().getClassLoader().getResources("imgs");
			if (en.hasMoreElements()) {
			    URL imgDirUrl = en.nextElement();
			    File imgDir = new File(imgDirUrl.toURI());
			    File[] themeDirs = imgDir.listFiles();
			    for (int i = 0; i < themeDirs.length; i++) {
			    	RadioButton rbTmpForTheme = new RadioButton(themeDirs[i].getName());
			    	rbTmpForTheme.setUserData("imgs/" + themeDirs[i].getName());
			    	gridForThemeSettings.add(rbTmpForTheme, 0, i);
			    	rbTmpForTheme.setToggleGroup(groupForResourceFolder);
				}
			}
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.getChildren().addAll(	lblForLevelSettings, gridForLevelSettings, 
									lblForTimeSettings, gridForTimeSettings,
									lblForThemeResourceSettings, gridForThemeSettings);
		
	}
	
	private void initData() {
		rbLevelEasy.setUserData(MemoryGameSettingsModel.DIFFICULTY_LEVEL_EASY);
		rbLevelMedium.setUserData(MemoryGameSettingsModel.DIFFICULTY_LEVEL_MEDIUM);
		rbLevelDifficult.setUserData(MemoryGameSettingsModel.DIFFICULTY_LEVEL_DIFFICULT);
		
		rbTimeEasy.setUserData(MemoryGameSettingsModel.TIME_DELAY_LEVEL_EASY);
		rbTimeDifficult.setUserData(MemoryGameSettingsModel.TIME_DELAY_LEVEL_DIFFICULT);
		
		if (this.settingsModel.getDifficultyLevel() == MemoryGameSettingsModel.DIFFICULTY_LEVEL_EASY) {
			rbLevelEasy.setSelected(true);
		} else if (this.settingsModel.getDifficultyLevel() == MemoryGameSettingsModel.DIFFICULTY_LEVEL_MEDIUM) {
			rbLevelMedium.setSelected(true);
		} else if (this.settingsModel.getDifficultyLevel() == MemoryGameSettingsModel.DIFFICULTY_LEVEL_DIFFICULT) {
			rbLevelDifficult.setSelected(true);
		}
		
		if (this.settingsModel.getTimeDelay() == MemoryGameSettingsModel.TIME_DELAY_LEVEL_EASY) {
			rbTimeEasy.setSelected(true);
		} else if (this.settingsModel.getTimeDelay() == MemoryGameSettingsModel.TIME_DELAY_LEVEL_DIFFICULT) {
			rbTimeDifficult.setSelected(true);
		}
		
		gridForThemeSettings.getChildren().iterator().forEachRemaining(node -> {
			if (node.getClass().getName().equals("javafx.scene.control.RadioButton")) {
				if (((RadioButton)node).getUserData().toString().equals(this.settingsModel.getResourceFolder())) {
					((RadioButton)node).setSelected(true);
					return;
				}
			}
		});
		
		System.out.println("level: " + groupForLevel.getSelectedToggle().getUserData().toString());
	}
	
	public void bindViewToModel() {
		//do it using anonymous inner class
		groupForLevel.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				settingsModel.difficultyLevelProperty().set((int) ((Toggle)newValue).getUserData());
				System.out.println("Hi : " + settingsModel.difficultyLevelProperty().get());
			}
		});
		
		//do it using anonymous lambda expression
	    groupForTime.selectedToggleProperty().addListener(
		(ObservableValue<? extends Toggle> ov, Toggle old_toggle,
				 Toggle new_toggle) -> {
					 settingsModel.setTimeDelay((int) new_toggle.getUserData());
					 System.out.println(settingsModel.timeDelayProperty().get());
    	});
	    
	    //do it using anonymous lambda expression
	    groupForResourceFolder.selectedToggleProperty().addListener(
		(ObservableValue<? extends Toggle> ov, Toggle old_toggle,
				 Toggle new_toggle) -> {
					 settingsModel.setResourceFolder(new_toggle.getUserData().toString());
    	});
	}
}
