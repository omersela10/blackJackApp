package blackJackApp;
import java.util.*;

public class Hand {
	
	// Data Members
	private List<Card> cards;
	private int betMoney;
	private int sumOfCardsWithAce;
	private int sumOfCards;
	private boolean canSplit = false;
	
	private HandState firstChoice;
	private HandState secondChoice;
	private HandState afterSplit;
	private HandState endOfRoundHand;
	
	private HandState theState;
	
	// Constructor
	public Hand(int newBetMoney){
		
		this.initializeHand(newBetMoney);
	}

	private void initializeHand(int newBetMoney) {
		
		// Create 2 cards
		this.cards = new ArrayList<Card>();
		Card firstCard = CreateCard.genarateCard();
		Card secondCard = CreateCard.genarateCard();
		
		this.cards.add(firstCard);
		this.cards.add(secondCard);
		
		// Set Money
		this.betMoney = newBetMoney;
		
		// Update sum of cards
		this.setSumOfCard();
		this.setSumOfCardsWithAce();
		
		// Check if can split
		this.canSplit = firstCard.equals(secondCard);
		this.InitializeStates();
	}
	
	// Set the States
	private void InitializeStates() {
		
		this.afterSplit = new AfterSplitState(this);
		this.firstChoice = new FirstChoiceState(this);
		this.secondChoice = new SecondChoiceState(this);
		this.endOfRoundHand = new EndHandRoundState(this);
	
		this.theState = this.getFirstChoice();
	}

	public HandState getFirstChoice() {
		return firstChoice;
	}

	public void setFirstChoice(HandState firstChoice) {
		this.firstChoice = firstChoice;
	}

	public HandState getSecondChoice() {
		return secondChoice;
	}

	public void setSecondChoice(HandState secondChoice) {
		this.secondChoice = secondChoice;
	}

	public HandState getAfterSplit() {
		return afterSplit;
	}

	public void setAfterSplit(HandState afterSplit) {
		this.afterSplit = afterSplit;
	}

	public HandState getEndOfRoundHand() {
		return endOfRoundHand;
	}

	public void setEndOfRoundHand(HandState endOfRoundHand) {
		this.endOfRoundHand = endOfRoundHand;
	}

	// Getters
	public List<Card> getCards() {
		return this.cards;
	}

	public int getBetMoney() {
		return this.betMoney;
	}
	
	public int getSumOfCardsWithAce() {
		return sumOfCardsWithAce;
	}
	
	public int getSumOfCards() {
		return sumOfCards;
	}
	
	public HandState getState() {
		return this.theState;
	}
	
	public boolean canSplit() {
		return this.canSplit;
	}
	
	// Setters
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public void setBetMoney(int anyBetMoney) {
		this.betMoney = anyBetMoney;
	}

	public void setHandState(HandState anyHandState) {
		this.theState = anyHandState;
	}
	
	private void setSumOfCardsWithAce() {
		
		this.sumOfCardsWithAce = 0;
		
		boolean seenAce = false;
		// Sum all the cards, include case of Ace
		for(Card anyCard: this.cards) {
			
			this.sumOfCardsWithAce += anyCard.getValue();
			
			if(anyCard.isAce() == true && seenAce == false) {
				
				this.sumOfCardsWithAce += 10;
				seenAce = true;
			}
		   
		}
	}

	private void setSumOfCard() {
		
		this.sumOfCards = 0;
		
		// Sum all the cards, ignore case of Ace
		for(Card anyCard: this.cards) {
			this.sumOfCards += anyCard.getValue();
		}
	}
	
	// Get one more card, set the sum respectively.
	public void getMoreCard() {
		
		Card newCard = CreateCard.genarateCard();
		this.cards.add(newCard);
		
		this.setSumOfCard();
		this.setSumOfCardsWithAce();
	}
	
}
