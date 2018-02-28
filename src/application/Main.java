package application;
	
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import gr.stolis.games.ImageMemoryUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	private static List<File> filesForTheGame;
	private static List<String> cardStatus;
	private static int[] currentlyActiveOpen = {-1,-1};

	private static final int RECT_WIDTH = 30;
	private static final int RECT_HEIGHT = 30;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
			initialize(root);
			Scene scene = new Scene(root,650,650);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("ΠΑΙΧΝΙΔΙ ΜΝΗΜΗΣ ΓΙΑ ΤΟΥΣ ΜΟΥΡΓΟΥΣ");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initialize(GridPane root) throws FileNotFoundException{
		int numOfCells = root.getRowConstraints().size() * root.getColumnConstraints().size();

//		File dir = new File("C:\\Users\\apostolos\\Desktop\\KARPENHSI STOMIO 2013");
//		File dir = new File("C:\\Users\\apostolos\\Pictures\\Γεννεθλια Ειρήνης 2017");
		File dir = new File("C:\\Users\\apostolos\\Pictures\\teams_logos");
//		File dir = new File("C:\\Users\\apostolos\\Pictures\\motogp");
		FileFilter filter = (file) -> { 
				return (file.getName().toLowerCase().endsWith("jpg") 
						|| file.getName().toLowerCase().endsWith("png")); 
				};
		File[] rndSelectedFiles = ImageMemoryUtils.getRandomListOfFiles(dir, numOfCells/2, true, filter);
		
		filesForTheGame = new ArrayList<File>(Arrays.asList(rndSelectedFiles));
		filesForTheGame.addAll(filesForTheGame);
		Collections.shuffle(filesForTheGame);
		cardStatus = new ArrayList<String>();
		for (int i=0; i<filesForTheGame.size(); i++) {
			cardStatus.add("Open");
		}
		
		for (int i = 0; i < root.getChildren().size(); i++) {
			Node child = root.getChildren().get(i);
			child.setDisable(true);
			System.out.println(child.getClass());
			if (child.getClass().getCanonicalName().equals("javafx.scene.image.ImageView")) {
				((ImageView)child).setImage(new Image("file:///" + filesForTheGame.get(i).getAbsolutePath(), true));
			}
		}
		
		Timeline timeline = new Timeline(new KeyFrame(
		        Duration.millis(4000),
		        ae -> closeAllCards(root)));
		timeline.play();
	}

	public static List<File> getImageFilesForTheGame() {
		return filesForTheGame;
	}
	
	public static List<String> getCardStatus() {
		return cardStatus;
	}
	
	public static int[] getcurrentlyActiveOpen() {
		return currentlyActiveOpen;
	}
	
	public static void setCurOpen(int i, int val) {
		currentlyActiveOpen[i] = val;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static WritableImage getCardsBackImage() {
		int width = 400;//(int)((ImageView)event.getSource()).getImage().getWidth();
		int height = 400;//(int)((ImageView)event.getSource()).getImage().getHeight();
		WritableImage newImage = new WritableImage(width, height);//250, 200);
		// Get the pixels data
		byte[] pixels = getPixelsData();
		writePattern(newImage, pixels);
		return newImage;
	}
	
	public static void closeAllCards(GridPane root){
		WritableImage newImage = getCardsBackImage();
		for (int i=0; i<filesForTheGame.size(); i++) {
			((ImageView)root.getChildren().get(i)).setImage(newImage);
			((ImageView)root.getChildren().get(i)).setDisable(false);
			cardStatus.set(i, "Close");
		}
	}
	
	public static void setStatusToCard(ImageView cardNode, String status){
		GridPane parent = (GridPane)cardNode.getParent();
		int index = parent.getChildren().indexOf(cardNode);
		if (status.equals("Close"))
			cardNode.setImage(getCardsBackImage());
		else
			cardNode.setImage(new Image("file:///" + filesForTheGame.get(index).getAbsolutePath(), true));
		cardStatus.set(index, status);
	}
	
	private static byte[] getPixelsData() {
		// Each pixel takes 3 bytes
		byte[] pixels = new byte[RECT_WIDTH * RECT_HEIGHT * 3];
		// Height to width ratio
		double ratio = 1.0 * RECT_HEIGHT/RECT_WIDTH;
		// Generate pixels data
		for (int y = 0; y < RECT_HEIGHT; y++) {
			for (int x = 0; x < RECT_WIDTH; x++) {
				int i = y * RECT_WIDTH * 3 + x * 3;
				if (x <= y/ratio) {
					pixels[i] = 0; // red -1 means 255 (-1 & 0xff = 255)
					pixels[i+1] = 0; // green = 0
					pixels[i+2] = 0; // blue = 0
				} else {
					pixels[i] = -1; // red = 0
					pixels[i+1] = -1; // Green 255
					pixels[i+2] = 0; // blue = 0
				}
			}
		}
		return pixels;
	}
	
	private static void writePattern(WritableImage newImage, byte[] pixels) {
		PixelWriter pixelWriter = newImage.getPixelWriter();
		// Our data is in BYTE_RGB format
		PixelFormat<ByteBuffer> pixelFormat = PixelFormat.getByteRgbInstance();
		int spacing = 5;
		int imageWidth = (int)newImage.getWidth();
		int imageHeight = (int)newImage.getHeight();
		// Roughly compute the number of rows and columns
		int rows = imageHeight/(RECT_HEIGHT + spacing);
		int columns = imageWidth/(RECT_WIDTH + spacing);

		// Write the pixels to the image
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				// Compute the current location inside the image where
				// the rectangular region to be written
				int xPos = x * (RECT_WIDTH + spacing);
				int yPos = y * (RECT_HEIGHT + spacing);
				// Write the pixels data at he current location
				// defined by xPos and yPos
				pixelWriter.setPixels(xPos, yPos,
				RECT_WIDTH, RECT_HEIGHT,
				pixelFormat,
				pixels, 0,
				RECT_WIDTH * 3);
			}
		}
	}
}
