package gr.stolis.games.memoryimages.view;

import gr.stolis.games.memoryimages.model.CardModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardView extends ImageView {
	private final CardModel cardModel;
	
	private static final double CARD_DEFAULT_WIDTH = 200.0;
	private static final double CARD_DEFAULT_HEIGHT = 150.0;
	
	Image closedImage;
	Image openedImage;

	public CardView(Image closedImage, Image openedImage, String cardName, boolean opened) {
		super(openedImage);
		
		this.setPreserveRatio(true);
		this.setFitWidth(CARD_DEFAULT_WIDTH);
		this.setFitHeight(CARD_DEFAULT_HEIGHT);
		this.setPickOnBounds(true);
		
		
		this.closedImage = closedImage;
		this.openedImage = openedImage;
		
		cardModel = new CardModel(cardName, opened);
		if (!opened)
			this.setImage(closedImage);
		
		cardModel.cardOpenProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.booleanValue())
					CardView.this.setImage(openedImage);
				else
					CardView.this.setImage(closedImage);
			}
		});
	}
	
	public CardView(Image closedImage, Image openedImage, CardModel cardModel) {
		super(openedImage);
		
		
		this.setPreserveRatio(true);
		this.setFitWidth(CARD_DEFAULT_WIDTH);
		this.setFitHeight(CARD_DEFAULT_HEIGHT);
		this.setPickOnBounds(true);
		this.closedImage = closedImage;
		this.openedImage = openedImage;
		
		
		this.cardModel = cardModel;
		if (!cardModel.getCardOpen())
			this.setImage(closedImage);
		
		this.cardModel.cardOpenProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.booleanValue())
					CardView.this.setImage(openedImage);
				else
					CardView.this.setImage(closedImage);
			}
		});
	}
	
	public final CardModel getCardModel() {
		return this.cardModel;
	}
	
	
}
