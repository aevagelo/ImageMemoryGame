package gr.stolis.games.memoryimages.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;

public class MemoryGameSettingsModel {

	public static int DIFFICULTY_LEVEL_EASY = 12;
	public static int DIFFICULTY_LEVEL_MEDIUM = 16;
	public static int DIFFICULTY_LEVEL_DIFFICULT = 24;

	public static int TIME_DELAY_LEVEL_EASY = 6;// in sseconds
	public static int TIME_DELAY_LEVEL_MEDIUM = 4;// in sseconds
	public static int TIME_DELAY_LEVEL_DIFFICULT = 2;// in sseconds
	
		
	private final IntegerProperty difficultyLevel = new SimpleIntegerProperty(this, "difficultyLevel", DIFFICULTY_LEVEL_EASY);
	private final IntegerProperty timeDelay = new SimpleIntegerProperty(this, "timeDelay", TIME_DELAY_LEVEL_EASY);
	private final StringProperty resourceFolder = new SimpleStringProperty(this, "resourceFolder", "imgs/fruits");
	
	
	public MemoryGameSettingsModel() {
		this(DIFFICULTY_LEVEL_EASY, TIME_DELAY_LEVEL_EASY, "imgs/fruits");
	}
	
	public MemoryGameSettingsModel(int difficultyLevel, int timeDelay, String resourceFolder) {
		this.difficultyLevel.set(difficultyLevel);
		this.timeDelay.set(timeDelay);
		this.resourceFolder.set(resourceFolder);
	}

	//-----------------------------------------
	
	public final int getDifficultyLevel() {
		return difficultyLevel.get();
	}

	public final int getTimeDelay() {
		return timeDelay.get();
	}

	public final String getResourceFolder() {
		return resourceFolder.get();
	}

	//-----------------------------------------
	
	public final IntegerProperty difficultyLevelProperty() {
		return difficultyLevel;
	}

	public final IntegerProperty timeDelayProperty() {
		return timeDelay;
	}
	
	public final StringProperty resourceFolderProperty() {
		return resourceFolder;
	}

	//-----------------------------------------
	
	public final void setDifficultyLevel(int difficultyLevel) {
		difficultyLevelProperty().set(difficultyLevel);
	}

	public final void setTimeDelay(int timeDelay) {
		timeDelayProperty().set(timeDelay);
	}
	
	public final void setResourceFolder(String resourceFolder) {
		resourceFolderProperty().set(resourceFolder);
	}
}
