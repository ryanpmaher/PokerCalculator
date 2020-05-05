import java.util.HashMap; 
import java.util.Map; 
public class Deck {
	HashMap<Integer, Card> deck = new HashMap<>();
	Deck(){
		addAll52();
	}
	public Card removeCard(Card c) {
		int cardId = c.getCardId();
		if(deck.containsKey(cardId)) {
			return deck.remove(cardId);
		} else {
			System.out.println("Card not found in deck ("+c.getStr()+")");
			return null;
		}
	}
	public Card removeCard(int cardId) {
		if(deck.containsKey(cardId)) {
			return deck.remove(cardId);
		} else {
			System.out.println("Card not found in deck");
			return null;
		}
	}
	public int getDeckSize() {
		return deck.size();
	}
	public boolean inDeck(Card c) {
		return deck.containsKey(c.getCardId());
	}
	public boolean inDeck(int c) {
		return deck.containsKey(c);
	}
	private void addAll52() {
		//adds all cards to deck
		int counter = 1;
		for(int value = 2; value<15;value++) {
			for(int suit = 1; suit<5; suit++) {
				deck.put(counter, new Card(value,suit));
				counter++;
			}
		}
	}
}
