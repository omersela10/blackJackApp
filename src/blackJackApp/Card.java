package blackJackApp;

public class Card {

    // Data Members
    private int value;
    private String iconPath;
    private boolean isAce;

    
    
    // Constructor
    public Card(int newValue, boolean isAceCard, String newIconPath) {

        this.value = newValue;
        this.isAce = isAceCard;
        this.iconPath = newIconPath;
    }

    // Getters:
	public int getValue() {
		return value;
	}

	public String getIconPath() {
		return iconPath;
	}
	
	public boolean isAce() {
		return isAce;
	}
	
	// Setters
	public void setValue(int anyValue) {
		this.value = value;
	}

	public void setIconPath(String anyIconPath) {
		this.iconPath = iconPath;
	}

	public void setAce(boolean isAceCard) {
		this.isAce = isAce;
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(o instanceof Card) {
			Card otherCard = (Card)o;
			return otherCard.getValue() == this.getValue();
		}
		
		return false;
	}

}
