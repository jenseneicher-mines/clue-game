package clueGame;

public class Card {
	
	private String cardName;
	private CardType type;
	
	public Card(String name, CardType type) {
		this.cardName = name;
		this.type = type;
	}

	public CardType getType() {
		return type;
	}
	
	public String getCardName() {
		return cardName;
	}
	
//&& this.type == otherCard.type
	public boolean equals( Card otherCard ) {
		if ( this.cardName.equals(otherCard.getCardName()) && this.type.equals(otherCard.getType())  ) {
			return true;
		} 
		else {
			return false;
		}
	}
	
	

}
