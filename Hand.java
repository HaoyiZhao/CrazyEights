import java.util.Arrays;

/** Class models a hand of cards, with sole attribute being a card array. Only
 * constructor takes as input a card array and assigns it to card array
 * attribute hand. Methods of class include playing a card from hand, adding
 * cards to hand, displaying and sorting hand, and printing out a message/
 * changing the suit if an eight is played(method determines which suit is ideal
 * for the switch).
 * 
 * @author HowieZhao
 *
 */
public class Hand implements HandActions {
	protected Card[] hand;

	public static final boolean simpleLogic = true;

	/*
	 * Constructor takes as input card array and creates Hand attribute hand.
	 */
	public Hand(Card[] cards) {
		this.hand = cards;
	}

	// Assume top of the pile is the final index of Card[] pile. Takes as input
	// Card[] pile and returns card played by hand
	// if possible. Card played will also be subtracted from hand (hand will be
	// updated through the creation of a new card[]).
	// Returns null if no card can be played by hand.
	@Override
	public Card playCard(Card[] pile) {
		for (int i = 0; i < hand.length; i++) {
			if (hand[i].getSuit().equals(pile[pile.length - 1].getSuit())
					|| hand[i].getRank() == pile[pile.length - 1].getRank() || hand[i].getRank() == 8
					|| hand[i].getRank() == 1) {
				// played card is stored in variable so it can be returned after
				// updating hand.
				Card playedCard = hand[i];
				// update hand using card[] newHand
				Card[] newHand = new Card[hand.length - 1];
				for (int k = 0; k < newHand.length; k++) {
					if (k >= i) {
						newHand[k] = hand[k + 1];
					} else {
						newHand[k] = hand[k];
					}
				}
				// set newHand to hand to finish hand update
				hand = newHand;
				return playedCard;
			}
		}
		return null;
	}

	/*
	 * Method takes as input a card[] and returns new hand with card[] added to
	 * old hand.
	 */
	@Override
	public Card[] getCard(Card[] cards) {
		// newHand length will be the sum of the old hand length and the card[]
		// length
		Card[] newHand = new Card[this.hand.length + cards.length];
		// add old cards to newHand
		for (int i = 0; i < this.hand.length; i++) {
			newHand[i] = this.hand[i];
		}
		// starting from index following last card of old hand, add input card[]
		// cards to hand
		for (int i = this.hand.length; i < newHand.length; i++) {
			newHand[i] = cards[i - this.hand.length];
		}
		// udpate current hand with newHand
		this.hand = newHand;
		return null;
	}

	/*
	 * returns current hand card[] (attribute)
	 */
	@Override
	public Card[] displayHand() {
		return this.hand;
	}

	/*
	 * method sorts hand object based on compareTo method of MyCard/card.
	 * (non-Javadoc)
	 * 
	 * @see HandActions#sortHand()
	 */
	@Override
	public Card[] sortHand() {
		Arrays.sort(this.hand);
		return this.hand;
	}

	/*
	 * Method takes as input a non-null string and then returns a suit which
	 * would be beneficial for the current Hand to have as the top of the pile.
	 * 
	 */
	@Override
	public String message(String question) {
		System.out.println(question);
		// counts how many of each suit is in the hand and then returns the suit
		// with the highest number of cards of the suit in the Hand's hand
		// Card[]
		int diamondCounter = 0;
		int clubCounter = 0;
		int heartCounter = 0;
		int spadeCounter = 0;
		int highestCounter = 0;
		String highest = "";
		for (int i = 0; i < hand.length; i++) {
			if (hand[i].getSuit() == "Diamonds") {
				diamondCounter++;
				// check if suit counter is higher than highestCounter every
				// time it is incremented. If higher, replace string highest,
				// with highestCounter suit and set highestCounter to
				// suitCounter
				if (diamondCounter > highestCounter) {
					highest = "Diamonds";
					highestCounter = diamondCounter;
				}
			} else if (hand[i].getSuit() == "Clubs") {
				clubCounter++;
				if (clubCounter > highestCounter) {
					highest = "Clubs";
					highestCounter = clubCounter;
				}
			} else if (hand[i].getSuit() == "Hearts") {
				heartCounter++;
				if (heartCounter > highestCounter) {
					highest = "Hearts";
					highestCounter = heartCounter;
				}
			} else if (hand[i].getSuit() == "Spades") {
				spadeCounter++;
				if (spadeCounter > highestCounter) {
					highest = "Spades";
					highestCounter = spadeCounter;
				}
			} else {
				continue;
			}
		}
		System.out.println(highest);
		return highest;

	}

	// tests
	public static void main(String[] args) {
		Card c1 = new MyCard(5, "Diamonds");
		Card c2 = new MyCard(2, "Spades");
		Card c3 = new MyCard(14, "Hearts");
		Hand test = new Hand(new Card[] { c1, c2 });
		System.out.println(Arrays.toString(test.hand));
		test.getCard(new Card[] { new MyCard(4, "Hearts") });
		test.getCard(new Card[] { new MyCard(5, "Hearts") });
		System.out.println(Arrays.toString(test.hand));
		test.sortHand();
		System.out.println(Arrays.toString(test.hand));
		Card playedCard = test.playCard(new Card[] { c3 });
		System.out.println(playedCard);
		test.message("Which suit would you like to change to?");
		System.out.println(playedCard);
		System.out.println(Arrays.toString(test.hand));
		test.playCard(new Card[] { new MyCard(4, "Clubs") });
		System.out.println(Arrays.toString(test.hand));

	}

}