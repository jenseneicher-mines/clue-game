package clueGame;

public class Solution {
	public Card person;
	public Card room;
	public Card weapon;
	public Solution(Card person, Card room, Card weapon) {
		super();
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
	public Solution() {
		super();
	}
	@Override
	public String toString() {
		return person.getCardName() + ", " + room.getCardName() + ", " + weapon.getCardName();
	}
	
}
