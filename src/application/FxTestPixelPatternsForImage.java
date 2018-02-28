package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FxTestPixelPatternsForImage extends Application {
	
	WritableImage myWritableImage;
    PixelWriter myPixelWriter;
	int imgWidth = 200;
	int imgHeight = 150;

	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox layout = new VBox(10);
		

		patternOneWritableImage();
		//WritableImage wImg = null;
		ImageView imgView = new ImageView(myWritableImage);
		
		MenuBar menuBar = new MenuBar();
		Menu patternsMenu = new Menu("Πατέντες");
		
		MenuItem mnuItemPatternOne = new MenuItem("1η Πατέντα");
		mnuItemPatternOne.setOnAction(ae -> {patternOneWritableImage();imgView.setImage(myWritableImage);});
		patternsMenu.getItems().add(mnuItemPatternOne);
		

		MenuItem mnuItemPatternTwo = new MenuItem("2η Πατέντα");
		mnuItemPatternTwo.setOnAction(ae -> {patternTwoWritableImage();imgView.setImage(myWritableImage);});
		patternsMenu.getItems().add(mnuItemPatternTwo);		

		MenuItem mnuItemPatternThree = new MenuItem("3η Πατέντα");
		mnuItemPatternThree.setOnAction(ae -> {patternThreeWritableImage();imgView.setImage(myWritableImage);});
		patternsMenu.getItems().add(mnuItemPatternThree);

		MenuItem mnuItemPatternFour = new MenuItem("4η Πατέντα");
		mnuItemPatternFour.setOnAction(ae -> {patternFourWritableImage();imgView.setImage(myWritableImage);});
		patternsMenu.getItems().add(mnuItemPatternFour);
		
		MenuItem mnuItemPatternFive = new MenuItem("5η Πατέντα");
		mnuItemPatternFive.setOnAction(ae -> {patternFiveWritableImage();imgView.setImage(myWritableImage);});
		patternsMenu.getItems().add(mnuItemPatternFive);
		
		MenuItem mnuItemPatternSix = new MenuItem("6η Πατέντα");
		mnuItemPatternSix.setOnAction(ae -> {patternSixWritableImage();imgView.setImage(myWritableImage);});
		patternsMenu.getItems().add(mnuItemPatternSix);
		
		menuBar.getMenus().add(patternsMenu);
		
		
		layout.getChildren().addAll(menuBar, imgView);//, settingsView);
		
	    
		primaryStage.setTitle("Αλγόριθμοι Ζωγραφικής");
		primaryStage.setScene(new Scene(layout, imgWidth+100, imgHeight+100));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private void patternOneWritableImage(){        
		myWritableImage = new WritableImage(imgWidth, imgHeight);
		myPixelWriter = myWritableImage.getPixelWriter();

		for(int x = 0; x < imgWidth; x++){
			for(int y = 0; y < imgHeight; y++){
				if (x>y) {
					myPixelWriter.setColor(x, y, Color.BLACK);            		
				} else {
					myPixelWriter.setColor(x, y, Color.YELLOW);            		
				}
			}
		}
    }
	

	private void patternTwoWritableImage(){        
		myWritableImage = new WritableImage(imgWidth, imgHeight);
		myPixelWriter = myWritableImage.getPixelWriter();
		
		double ratio = imgWidth*1.0/imgHeight;
		for(int x = 0; x < imgWidth; x++){
			for(int y = 0; y < imgHeight; y++){
				if (x*1.0>y*ratio) {
					myPixelWriter.setColor(x, y, Color.BLACK);            		
				} else {
					myPixelWriter.setColor(x, y, Color.YELLOW);            		
				}
			}
		}
    }
	

	private void patternThreeWritableImage(){        
		myWritableImage = new WritableImage(imgWidth, imgHeight);
		myPixelWriter = myWritableImage.getPixelWriter();
		
		double ratio = (imgWidth*1.0/2.0)/imgHeight*1.0;
		for(int x = 0; x < imgWidth; x++){
			for(int y = 0; y < imgHeight; y++){
				if (x<imgWidth/2.0) {
					if (x*1.0>y*ratio) {
						myPixelWriter.setColor(x, y, Color.BLACK);            		
					} else {
						myPixelWriter.setColor(x, y, Color.YELLOW);            		
					}
				} else {
					if (x*1.0>(y*ratio)+(imgWidth/2.0)) {
						myPixelWriter.setColor(x, y, Color.BLACK);            		
					} else {
						myPixelWriter.setColor(x, y, Color.YELLOW);            		
					}
				}
			}
		}
	}
		
	private void patternFourWritableImage(){        
		myWritableImage = new WritableImage(imgWidth, imgHeight);
		myPixelWriter = myWritableImage.getPixelWriter();
		
		double ratio = (imgWidth*1.0/2.0)/imgHeight*1.0;
		for(int x = 0; x < imgWidth; x++){
			for(int y = 0; y < imgHeight; y++){
				if (x<imgWidth/2.0) {
					if (x*1.0>y*ratio) {
						myPixelWriter.setColor(x, y, Color.BLACK);            		
					} else {
						myPixelWriter.setColor(x, y, Color.YELLOW);            		
					}
				} else {
					if (x*1.0>(y*ratio)+(imgWidth/2.0)) {
						myPixelWriter.setColor(x, y, Color.YELLOW);            		
					} else {
						myPixelWriter.setColor(x, y, Color.BLACK);            		
					}
				}
			}
		}
    }

	private void patternFiveWritableImage(){        
		myWritableImage = new WritableImage(imgWidth, imgHeight);
		myPixelWriter = myWritableImage.getPixelWriter();
		
		double ratio = (imgWidth*1.0/2.0)/imgHeight*1.0;
		for(int x = 0; x < imgWidth; x++){
			for(int y = 0; y < imgHeight; y++){
				if (x<imgWidth/2.0) {
					if (x*1.0>y*ratio) {
						myPixelWriter.setColor(x, y, Color.BLACK);            		
					} else {
						myPixelWriter.setColor(x, y, Color.YELLOW);            		
					}
				} else {
					if ((y*ratio)<(imgWidth/2.0)-x+(imgWidth/2.0)) {
						myPixelWriter.setColor(x, y, Color.BLACK);            		
					} else {
						myPixelWriter.setColor(x, y, Color.YELLOW);            		
					}
				}
			}
		}
	}
	
	private void patternSixWritableImage(){        
		myWritableImage = new WritableImage(imgWidth, imgHeight);
		myPixelWriter = myWritableImage.getPixelWriter();
		
		double ratio = imgWidth*1.0/imgHeight*1.0;
		double halfSizeDif = (imgWidth-imgHeight)/2.0;
		for(int x = 0; x < imgWidth; x++){
			for(int y = 0; y < imgHeight; y++){
				if (x<imgWidth/2.0) {
					if (((imgWidth/2.0)-x)<y+halfSizeDif && (imgWidth/2.0)+x>y+halfSizeDif) {
						myPixelWriter.setColor(x, y, Color.BLACK);            		
					} else {
						myPixelWriter.setColor(x, y, Color.YELLOW);            		
					}
				} else {
					if (y+halfSizeDif>x-(imgWidth/2.0) && y+halfSizeDif<imgWidth-(x-(imgWidth/2.0))) {
						myPixelWriter.setColor(x, y, Color.BLACK);
					} else {
						myPixelWriter.setColor(x, y, Color.YELLOW);
					}
					/*
					if ((imgHeight/2.0)-x<y && (imgHeight/2.0)+x>y) {
						myPixelWriter.setColor(x, y, Color.YELLOW);            		
					} else {
						myPixelWriter.setColor(x, y, Color.BLACK);            		
					}
					*/
				}
			}
		}
	}
}
