import java.util.Arrays;
import java.util.Random;

/**Class models a deck of cards, with 2 constructors: 1 creating a
 * standard deck of 52 cards with 2 jokers, and another taking input dictating
 * how many standard decks and how many jokers are in the created Deck. Methods
 * written allow the deck to be shuffled, obtaining the number of cards in the
 * deck, getting cards in the deck as an array, peeking at the top card, taking
 * the top card off the deck and returning it, as well as sorting the deck using
 * of the card compareTo method. Class has two attributes, a card[] containing
 * all deck cards, and deckNumber which represents the amount of cards in the
 * deck.
 * 
 * @author HowieZhao
 *
 */
public class Deck {
	protected Card[] deck;
	protected int deckNumber;

	/*
	 * creates a deck of 54 cards as described in the Cards problem (52 cards
	 * that are in the standard deck of cards plus two jokers)
	 */
	public Deck() {
		this.deck = new Card[54];
		createStandard();
		deck[52] = new MyCard("Joker", "None");
		deck[53] = new MyCard("Joker", "None");
		deckNumber += 2;
	}

	// helper method which creates a standard deck (iterates through each suit,
	// creating a new card for each rank
	// of the suit, then continuing onto the next suit)
	private void createStandard() {
		for (int i = 0; i < 4; i++) {
			for (int j = 2; j < 15; j++) {
				deck[deckNumber] = new MyCard(Card.RANKS[j], Card.SUITS[i]);
				deckNumber++;
			}
		}
	}

	/*
	 * create a deck with specified number of copies of the standard deck (52
	 * cards) and with the specified number of jokers
	 * 
	 * Both inputs must be non-negative
	 */
	public Deck(int numStandard, int numJokers) {
		this.deck = new Card[numStandard * 52 + numJokers];
		for (int i = 0; i < numStandard; i++) {
			createStandard();
		}
		for (int i = 0; i < numJokers; i++) {
			deck[deckNumber] = new MyCard("Joker", "None");
			deckNumber++;
		}
	}

	/* returns the number of cards in the deck */
	public int numCards() {
		return deckNumber;
	}

	/*
	 * returns all the cards in the deck without modifying the deck the ordering
	 * in the array is the order of the cards at this given time
	 */
	public Card[] getCards() {
		return this.deck;
	}

	/* return the top card of the deck without modifying the deck */
	public Card peek() {
		return deck[deckNumber - 1];
	}

	/* remove the top card from the deck and return it */
	public Card pop() {
		Card poppedCard = this.deck[deckNumber - 1];
		deckNumber--;
		Card[] newDeck = new Card[deckNumber];
		for (int i = 0; i < deckNumber; i++) {
			newDeck[i] = this.deck[i];
		}
		this.deck = newDeck;
		return poppedCard;
	}

	/* randomly shuffle the order of the cards in the deck */
	// randomizes the cards at each index in the deck Card array
	public void shuffleDeck() {
		Random rand = new Random();
		for (int i = deckNumber - 1; i >= 0; i--) {
			int randIndex = rand.nextInt(i + 1);
			Card temp = this.deck[randIndex];
			this.deck[randIndex] = this.deck[i];
			this.deck[i] = temp;
		}
	}

	/*
	 * sorts the deck so that cards are in the order specified in the Cards
	 * problem (low cards first)
	 */
	public void sortDeck() {
		Arrays.sort(this.deck);
	}

	// test cases
	public static void main(String[] args) {
		Deck test = new Deck(2, 0);
		System.out.println(Arrays.toString(test.deck));
		System.out.println(test.deckNumber);
		test.sortDeck();
		System.out.println(Arrays.toString(test.deck));
		test.shuffleDeck();
		System.out.println(Arrays.toString(test.deck));
		test.sortDeck();
		System.out.println(Arrays.toString(test.getCards()));

	}
}
