package gr.stolis.games.memoryimages.view;

import gr.stolis.games.memoryimages.model.CardModel;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;

public class CardLabelView extends Label {
	CardModel card;

	public CardLabelView(String text) {
		super(text);
		card = new CardModel(text, true);
		card.cardOpenProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				String text = (newValue.booleanValue()) ? card.getCardName() : "Closed" ;
				CardLabelView.this.setText(text);
			}
		});
	}

	public CardLabelView(String text, boolean open) {
		super(text); 
		card = new CardModel(text, open);
		if (!open)
			setText("Closed");
		card.cardOpenProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				String text = (newValue.booleanValue()) ? card.getCardName() : "Closed" ;
				CardLabelView.this.setText(text);
			}
		});
	}
	
	public void toggle() {
		if (card.getCardOpen())
			card.setCardOpen(false);
		else
			card.setCardOpen(true);
		
	}
}
