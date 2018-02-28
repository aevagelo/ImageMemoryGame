package gr.stolis.games.memoryimages.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CardModel{
	private final StringProperty cardName = new SimpleStringProperty(this, "cardName");
	private final BooleanProperty cardOpen = new SimpleBooleanProperty(this, "cardOpen");
	private final BooleanProperty cardActive = new SimpleBooleanProperty(this, "cardActive");

	public CardModel(String cardNameValue, boolean cardOpenValue, boolean cardActiveValue) {
		this.cardName.set(cardNameValue);
		this.cardOpen.set(cardOpenValue);
		this.cardActive.set(cardActiveValue);
	}

	public CardModel(String cardNameValue, boolean cardOpenValue) {
		this(cardNameValue, cardOpenValue, true);
	}
	
	public final String getCardName() {
		return cardName.get();
	}

	public final boolean getCardOpen() {
		return cardOpen.get();
	}
	
	public final boolean getCardActive() {
		return cardActive.get();
	}
	
	public final StringProperty cardNameProperty() {
		return cardName;
	}
	
	public final BooleanProperty cardOpenProperty() {
		return cardOpen;
	}
	
	public final BooleanProperty cardActiveProperty() {
		return cardActive;
	}
	
	public final void setCardName(String cardNameValue) {
		this.cardNameProperty().set(cardNameValue);
	}
	
	public final void setCardOpen(boolean cardOpenValue){
		this.cardOpenProperty().set(cardOpenValue);
	}
	
	public final void setCardActive(boolean cardActiveValue){
		this.cardActiveProperty().set(cardActiveValue);
	}
	
	public void toggle() {
		if (this.getCardActive()) {
			if (this.getCardOpen())
				this.setCardOpen(false);
			else
				this.setCardOpen(true);
		}
	}
}
