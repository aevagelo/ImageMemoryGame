package gr.stolis.games.memoryimages.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

import gr.stolis.games.memoryimages.model.CardModel;
import gr.stolis.games.memoryimages.model.MemoryGameMainModel;
import gr.stolis.games.memoryimages.model.MemoryGameSettingsModel;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class MemoryGameMainView extends GridPane {
	private MemoryGameMainModel gameModel;
	//private MemoryGameSettingsModel settings;
	public int numOfColumns, numOfLines;
	
	private Media clockSound = new Media(getClass().getClassLoader()
			.getResource("media/sounds/Ticking_Clock-KevanGC.mp3").toExternalForm());
	public MediaPlayer mediaPlayer = new MediaPlayer(clockSound);
	
	private static final int RECT_WIDTH = 21;//25;
	private static final int RECT_HEIGHT = 21;//25;

	public MemoryGameMainView(MemoryGameSettingsModel settings) throws IOException, URISyntaxException {
		super();
		//this.settings = settings;
		setVgap(10.0);
		setHgap(10.0);
		int numOfCards = settings.getDifficultyLevel();
		File imgDir = null;
		Enumeration<URL> en=getClass().getClassLoader().getResources(settings.getResourceFolder());//animals//fruits//flags
		if (en.hasMoreElements()) {
		    URL imgDirUrl = en.nextElement();
		    imgDir = new File(imgDirUrl.toURI());
		}
		if (imgDir == null) {
			throw new FileNotFoundException();
		}
		gameModel = new MemoryGameMainModel(imgDir, numOfCards);
		gameModel.deactivateAllCards();
		getGridDimensions(numOfCards);
		int counter = 0;
		Image closedImage = getCardsBackImage2(Color.BLACK, Color.YELLOW, 200, 150);
//		Image closedImage = getCardsBackImage(Color.BLACK, Color.YELLOW, 200, 150);//getCardsBackImage();
		//Image openedImage = getCardsBackImage((byte)35, (byte)82, (byte)21);
		RowConstraints rowCons = new RowConstraints();
		rowCons.setValignment(VPos.CENTER);
		rowCons.setMaxHeight(150.0);
		rowCons.setPrefHeight(150.0);
		rowCons.setMinHeight(150.0);
		for (int i = 0; i < numOfLines; i++) {
			this.getRowConstraints().add(rowCons);
			for (int j = 0; j < numOfColumns; j++) {
				if (i == 0) {
					ColumnConstraints colCons = new ColumnConstraints();
					colCons.setHalignment(HPos.CENTER);
					colCons.setMaxWidth(200.0);
					colCons.setPrefWidth(200.0);
					colCons.setMinWidth(200.0);
					this.getColumnConstraints().add(colCons);
				}				

				CardModel tmpModel = gameModel.getCards().get(counter);
				Image openedImage = new Image(new FileInputStream(new File(tmpModel.getCardName())));
				CardView card = new CardView(closedImage, openedImage, tmpModel);
				card.setOnMouseClicked(new EventHandler<Event>() {
					@Override
					public void handle(Event event) {
						if (card.getCardModel().getCardActive()) {
							//CardModel tmpModel = ((CardView)event.getSource())).getCardModel();
							int selectedCardIndex = gameModel.getCards().indexOf(tmpModel);
							System.out.println(gameModel.getCards().indexOf(tmpModel));
							
							gameModel.selectCardToOpen(selectedCardIndex);

							//this doesn't work as i expected
//							if (!card.getCardModel().getCardActive()) {
//								card.removeEventHandler(MouseEvent.MOUSE_CLICKED, card.getOnMouseClicked());
//							}
						} else {
							System.out.println("OMG, i am still having this listener.");
						}
					}
				});
				
				this.add(card, j, i);
				
				counter++;
			}
		}
		
		mediaPlayer.stop();
		mediaPlayer.seek(Duration.ZERO);
//		mediaPlayer.setStopTime(new Duration(settings.getTimeDelay() * 1000.0));
		mediaPlayer.setVolume(0.3);
		mediaPlayer.play();
		
		//add one more line for the the counter
		Label lblCounter = new Label(String.valueOf(settings.getTimeDelay()));
		lblCounter.setFont(new Font(74.0));
		this.add(lblCounter, 0, numOfLines);
		Timeline timelineCounter = new Timeline();
		timelineCounter.setCycleCount(settings.getTimeDelay());
		KeyFrame countDownKeyFrame = new KeyFrame(Duration.seconds(1.0), 
				ae -> {
					int val = Integer.parseInt(lblCounter.getText())-1;
					lblCounter.setText(String.valueOf(val));
					if(val == 0) {
			        	gameModel.closeAllCards();
			        	gameModel.activateAllCards();
			        	mediaPlayer.stop();
			        	lblCounter.setVisible(false);
					}
				});
		timelineCounter.getKeyFrames().add(countDownKeyFrame);
		timelineCounter.play();
		
//		Timeline timeline = new Timeline(new KeyFrame(
//		        Duration.millis( settings.getTimeDelay() * 1000),
//		        ae -> {
//		        	gameModel.closeAllCards();
//		        	gameModel.activateAllCards();
//		        	mediaPlayer.stop();
//		        	lblCounter.setVisible(false);
//		        }));
//		timeline.play();
	}
	
//	public MemoryGameMainView() {
//		new MemoryGameMainView(lbl.getText());
//	}
	
	public static WritableImage getCardsBackImage() {
		return getCardsBackImage((byte)-1, (byte)-1, (byte)0);
	}
	
	public static WritableImage getCardsBackImage(byte r, byte g, byte b) {
		int width = 200;//(int)((ImageView)event.getSource()).getImage().getWidth();
		int height = 150;//(int)((ImageView)event.getSource()).getImage().getHeight();
		WritableImage newImage = new WritableImage(width, height);//250, 200);
		// Get the pixels data
		byte[] pixels = getPixelsData(r, g , b);
		writePattern(newImage, pixels);
		return newImage;
	}
	
	public static WritableImage getCardsBackImage(Color c1, Color c2, int width, int height) {
		WritableImage newImage = new WritableImage(width, height);
	    PixelWriter myPixelWriter = newImage.getPixelWriter();
	    
	    double ratio = width / (double)height;
	    for(int x = 0; x < width; x++){
	        for(int y = 0; y < height; y++){
	        	if (x>y*ratio) {
	                myPixelWriter.setColor(x, y, c1);            		
	        	} else {
	                myPixelWriter.setColor(x, y, c2);            		
	        	}
	        }
	    }
	    return newImage;
	}
	
	public static WritableImage getCardsBackImage2(Color c1, Color c2, int width, int height) {
		WritableImage newImage = new WritableImage(width, height);
	    PixelWriter myPixelWriter = newImage.getPixelWriter();
	    
	    double ratio = (width/2.0) / (double)height;
	    for(int x = 0; x < width; x++){
	        for(int y = 0; y < height; y++){
	        	if (x<width/2.0) {
					if (x*1.0>y*ratio) {
						myPixelWriter.setColor(x, y, c1);            		
					} else {
						myPixelWriter.setColor(x, y, c2);            		
					}
				} else {
					if (x*1.0>(y*ratio)+(width/2.0)) {
						myPixelWriter.setColor(x, y, c2);            		
					} else {
						myPixelWriter.setColor(x, y, c1);            		
					}
				}
	        }
	    }
	    return newImage;
	}
	
	private static byte[] getPixelsData() {
		byte r, g , b;
		return getPixelsData(r=-1, g=-1, b=0);
	}
	
	private static byte[] getPixelsData(byte r, byte g, byte b) {
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
					pixels[i] = r;//-1; // red = 0
					pixels[i+1] = g;//-1; // Green 255
					pixels[i+2] = b;//0; // blue = 0
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
	
	private void getGridDimensions(int numOfCards) {
        int tmpCols = 0;

        numOfColumns = 0;
        numOfLines = 0;

        do {
        	tmpCols+=2;
        	if (numOfCards%tmpCols==0) {
        		numOfColumns = tmpCols;
        		numOfLines = numOfCards/tmpCols;
        	}
        } while (tmpCols<(numOfCards/tmpCols));

        //always keep minimum for rows
        if (numOfLines>numOfColumns) {
        	int tmp = numOfLines;                           
        	numOfLines = numOfColumns;
        	numOfColumns = tmp;
        }
	}
	
//	public double populateWidth() {
//		double totalWidth = 0;
//		for (int i = 0; i < this.getColumnConstraints().size(); i++) {
//			totalWidth += 250 + 10;
//		}
//		return totalWidth;
//	}
}
