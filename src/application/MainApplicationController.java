package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class MainApplicationController {
	
	
	@FXML
	protected void handleOnMouseClicked(MouseEvent event) throws FileNotFoundException {
		if (event.getSource().getClass().equals(ImageView.class)) {
			GridPane parent = (GridPane)((ImageView)event.getSource()).getParent();
			int indexOfImg = parent.getChildren().indexOf(event.getSource());
			System.out.println(indexOfImg);
			
			if (Main.getCardStatus().get(indexOfImg).equals("Matched")) {
				System.out.println("This card is already matched");
				return;
			}
			int[] curOpen = Main.getcurrentlyActiveOpen();
			if (curOpen[0]>-1 && curOpen[1]>-1) {//σημαινει πως άνοιξε δύο λάθος, κλειστα και ανοιξε αυτό που ζήτησε
				Main.setStatusToCard((ImageView)parent.getChildren().get(curOpen[0]), "Close");
				Main.setStatusToCard((ImageView)parent.getChildren().get(curOpen[1]), "Close");
				//if (Main.getCardStatus().get(indexOfImg).equals("Close")) {
				Main.setStatusToCard((ImageView)event.getSource(), "Open");
				Main.setCurOpen(0, indexOfImg);
				Main.setCurOpen(1, -1);
				//}
			} else if (curOpen[0]==-1 && curOpen[1]==-1) {
				Main.setStatusToCard((ImageView)event.getSource(), "Open");
				Main.setCurOpen(0, indexOfImg);
			} else if (curOpen[0]>-1 && curOpen[1]==-1){
				//an patithei to idio image agnoise to patima
				if(curOpen[0]==indexOfImg){
					return;
				}
				
				Main.setStatusToCard((ImageView)event.getSource(), "Open");
				if (Main.getImageFilesForTheGame().get(curOpen[0]).getName().equals(Main.getImageFilesForTheGame().get(indexOfImg).getName())) {//if it is matched 
					Main.setStatusToCard((ImageView)event.getSource(), "Matched");
					Main.setStatusToCard((ImageView)parent.getChildren().get(curOpen[0]), "Matched");
					Main.setCurOpen(0, -1);
				} else {
					Main.setCurOpen(1,indexOfImg);
				}
			}
//				int width = 400;//(int)((ImageView)event.getSource()).getImage().getWidth();
//				int height = 400;//(int)((ImageView)event.getSource()).getImage().getHeight();
//				WritableImage newImage = new WritableImage(width, height);//250, 200);
//				// Get the pixels data
//				byte[] pixels = getPixelsData();
//				this.writePattern(newImage, pixels);
//				((ImageView)event.getSource()).setImage(newImage);
				
				
				/*
				//((ImageView)event.getSource()).setImage(null);
				Circle circle = new Circle(30, 30, 30);
				circle.setFill(Color.YELLOW);
				ImagePattern imagePattern = new ImagePattern(image);
				circle.setFill(imagePattern);
				((ImageView)event.getSource()).setImage(image);
				*/
		}
	}
	
	
}
