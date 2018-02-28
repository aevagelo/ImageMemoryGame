package gr.stolis.games.memoryimages.model;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import gr.stolis.games.ImageMemoryUtils;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MemoryGameMainModel {
	private final IntegerProperty firstCardOpen = new SimpleIntegerProperty(-1);
	private final IntegerProperty secondCardOpen = new SimpleIntegerProperty(-1);
	
	private final ReadOnlyIntegerWrapper numOfMistakes = new ReadOnlyIntegerWrapper(this, "numOfMistakes", 0);
	
	ListProperty<CardModel> cards = null;
	
	public MemoryGameMainModel(File dir, int numOfCards) throws IllegalArgumentException{
		this(getCardsFromDirectory(dir, numOfCards));
	}
	
	public MemoryGameMainModel(ArrayList<CardModel> cardsList) throws IllegalArgumentException{
		int numOfCards = cardsList.size();
		if (numOfCards%2 == 1) {
			throw new IllegalArgumentException("Number of cards can be only an even number");
		}
		
		ObservableList<CardModel> observableList = FXCollections.observableArrayList(cardsList);
		cards = new SimpleListProperty<CardModel>(observableList);
		
		for (int i = 0; i < numOfCards; i++) {
			String val = String.valueOf(i);
			if (i>numOfCards/2)
				val = String.valueOf(i - (numOfCards/2));
//			cards.add(new CardModel(val, false));
		}
		FXCollections.shuffle(cards);
		for (int i = 0; i < cards.size(); i++) {
			System.out.println(cards.get(i).getCardName());
		}
	}
	
	public ObservableList<CardModel> getCards() {
		return cards.get();
	}

	public final int getFirstCardOpen() {
		return firstCardOpen.get();
	}
	
	public final int getSecondCardOpen() {
		return secondCardOpen.get();
	}
	
	public final int getNumOfMistakes() {
		return numOfMistakes.get();
	}
	
	//-------------------------------------------------------------------

	public final IntegerProperty firstCardOpenProperty() {
		return firstCardOpen;
	}
	
	public final IntegerProperty secondCardOpenProperty() {
		return secondCardOpen;
	}
	
	public final ReadOnlyIntegerProperty numOfMistakesProperty() {
		return numOfMistakes.getReadOnlyProperty();
	}
	
	//-------------------------------------------------------------------
	
	public final void setFirstCardOpen(int firstCardOpenValue) {
		this.firstCardOpenProperty().set(firstCardOpenValue);
	}
	
	public final void setSecondCardOpen(int secondCardOpenValue){
		this.secondCardOpenProperty().set(secondCardOpenValue);
	}
	
	private static ArrayList<CardModel> getCardsFromDirectory(File dir, int numOfCards) throws IllegalArgumentException{
		if (numOfCards%2 == 1) {
			throw new IllegalArgumentException("Number of cards can be only an even number");
		}
		FileFilter filter = (file) -> { 
				return (file.getName().toLowerCase().endsWith("jpg") 
						|| file.getName().toLowerCase().endsWith("png")); 
				};
		File[] rndSelectedFiles = ImageMemoryUtils.getRandomListOfFiles(dir, numOfCards/2, true, filter);
		ArrayList<CardModel> cardsList = new ArrayList<CardModel>();
		
		for (int i = 0; i < rndSelectedFiles.length; i++) {
			cardsList.add(new CardModel(rndSelectedFiles[i].getAbsolutePath(), true));
			cardsList.add(new CardModel(rndSelectedFiles[i].getAbsolutePath(), true));
		}
		return cardsList;
	}
	
	public void selectCardToOpen(int cardIndex){
		CardModel cardModel = this.getCards().get(cardIndex);
		if (!cardModel.getCardActive()) {
			System.out.println("this card is inactive");
			return;
		}
		if (firstCardOpen.get()<0 && secondCardOpen.get()<0) {
			firstCardOpen.set(cardIndex);
			cardModel.setCardOpen(true);
		} else if (firstCardOpen.get()>=0 && secondCardOpen.get()<0) {
			if(firstCardOpen.get() == cardIndex) {
				System.out.println("you select an already open card");
				return;
			}
			if (cardModel.getCardName().equals(getCards().get(getFirstCardOpen()).getCardName())) {//cards matched
				cardModel.setCardOpen(true);
				cardModel.setCardActive(false);
				getCards().get(getFirstCardOpen()).setCardOpen(true);
				getCards().get(getFirstCardOpen()).setCardActive(false);
				this.setFirstCardOpen(-1);
				this.setSecondCardOpen(-1);
				if (isGameFinished()) {
					System.out.printf("===================== YEA Mate you finished the Game (mistakes: %d) =======================\r\n", numOfMistakes.get());
				}
			} else {//cards doesNOT match, increase mistake counter
				cardModel.setCardOpen(true);
				this.setSecondCardOpen(cardIndex);
				numOfMistakes.set(numOfMistakes.get() + 1);
				System.out.printf("You ve made a mistake, total num of mistakes %d \r\n", numOfMistakes.get());
			}
		} else if (firstCardOpen.get()>=0 && secondCardOpen.get()>=0) {
			getCards().get(getFirstCardOpen()).setCardOpen(false);
			getCards().get(getSecondCardOpen()).setCardOpen(false);
			cardModel.setCardOpen(true);
			this.setFirstCardOpen(cardIndex);
			this.setSecondCardOpen(-1);
		}
	}
	
	public boolean isGameFinished() {
//		boolean isFinished = true;
		for(CardModel card : getCards()) {
			if (card.getCardActive()) {
				return false;
			}
		}
		return true;
	}
	
	public void closeAllCards(){
		/*
		for (Iterator iterator = cards.iterator(); iterator.hasNext();) {
			CardModel cardModel = (CardModel) iterator.next();
			cardModel.setCardOpen(false);
		}
		*/
		cards.stream().forEach(card -> card.setCardOpen(false));
	}
	
	public void activateDeactivateAllCards(boolean activate) {
		/*
		for (Iterator iterator = cards.iterator(); iterator.hasNext();) {
			CardModel cardModel = (CardModel) iterator.next();
			cardModel.setCardActive(activate);
		}
		*/
		
		cards.stream().forEach(card -> card.setCardActive(activate));
	}

	public void activateAllCards() {
		activateDeactivateAllCards(true);
	}
	
	public void deactivateAllCards() {
		activateDeactivateAllCards(false);
	}

	public void startChronometer() {
		
	}
	
	public void stoptChronometer() {
		
	}
}
