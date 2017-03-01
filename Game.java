import java.util.Arrays;

/**Class implements a game of crazy eights, using classes Card, MyCard,
 * Deck.java, HandActions, and Hand. Almost all code is in main method, except
 * for several helper methods and static variables. Game is modeled according to
 * assignment specifications pdf. Players are from 1-# of players rather than
 * starting from player 0-# of players -1 for more realistic output.
 * 
 * @author HowieZhao
 *
 */
public class Game {
	// static variables used by the game
	static Hand[] hands;
	static Deck deck;
	static Card[] pile;
	static String question = "What suit do you want the 8 you just played to be?";

	/*
	 * Method in which the game is run. Takes as input a single command line
	 * argument which determines the size of the deck as well as how many hands
	 * are dealt/are playing.
	 */
	public static void main(String[] args) {
		// parse argument
		int parsedInt = Integer.parseInt(args[0]);
		// ensure that command line argument input is valid, otherwise print
		// message and end program.
		if (parsedInt < 2 || parsedInt > 7) {
			System.out.println(
					"Please enter a command line argument of an integer between 2 and 7, inclusive and try again.");
			return;
		}
		// creates deck based on number of hands in game (input argument)
		else {
			if (parsedInt <= 5) {
				deck = new Deck();
			} else {
				deck = new Deck(2, 4);
			}
			// shuffle deck before game begins
			deck.shuffleDeck();
			// initialize hands[] hands with the number of hands given given by
			// the parsed input argument
			hands = new Hand[parsedInt];
			// initialize each hand with 8 cards.
			for (int i = 0; i < parsedInt; i++) {
				hands[i] = new Hand(new Card[8]);
			}
			// give one card to each hand until each hand has 8 cards by using
			// deck.pop()
			int j = 0;
			while (j < 8) {
				for (int i = 0; i < hands.length; i++) {
					hands[i].displayHand()[j] = deck.pop();
					// at after last hand has been given a card at index j,
					// increment j so new card will be given at index j+1 next
					// iteration of for loop. While loop will terminate when j
					// becomes 8
					if (i == hands.length - 1) {
						j++;
					}
				}
			}
			// top of discard pile will first be top card of deck after
			// distributing hands. If a joker is the first card, another card
			// will be drawn and will be assigned to pile instead
			pile = new Card[1];
			pile[0] = deck.pop();
			while (pile[0].getRank() == 1) {
				pile[0] = deck.pop();
			}
			System.out.println(
					"Cards have been dealt, and top card of deck (if joker, redrawn) has been added to discard pile.");
			System.out.println("Player's hands are displayed both before and after their turn.");
			System.out.println();
			// display all players' hands after dealing
			for (int i = 0; i < hands.length; i++) {
				printHand(i);
			}
			System.out.println();
			// index determines which hand is currently playing. % will be used
			// at the end of each turn in order to preserve circular order
			int index = 0;
			// game will continue until return is used
			while (true) {
				// print current hand using printHand method as well as well as
				// card on top of discard pile
				System.out.println("Player " + (index + 1) + "'s turn");
				printHand(index);
				System.out.println("Top of the discard pile is " + pile[0]);
				// checks if hand can play and if not, add cards to hand
				// until current hand can play a valid card, or if deck runs out
				// of cards
				while (canPlay(hands[index], pile) == false) {
					if (deck.numCards() != 0) {
						Card poppedCard = deck.pop();
						hands[index].getCard(new Card[] { poppedCard });
						System.out.println("Picked up " + poppedCard + " ");
					}
					// if deck becomes empty after drawing a card, print current
					// hand, allow hand to play it can, and then use deckEmpty
					// method to determine winner. Afterwards end program.
					if (deck.numCards() == 0) {
						printHand(index);
						Card playedCard = hands[index].playCard(pile);
						if (playedCard != null) {
							System.out.println(playedCard + " has been played.");
						}
						deckEmpty();
						return;
					}
				}
				// if card is played, store card played as playedCard so it can
				// be accessed later
				Card playedCard = hands[index].playCard(pile);
				System.out.println(playedCard + " has been played.");
				// check if current hand has no cards; if it does not,
				// current player/ hand has won
				if (hands[index].displayHand().length == 0) {
					System.out.println("Player " + (index + 1) + " has won.");
					return;
				}
				// create string nextPlayer, which determines who the next
				// player/hand is (only for output printing purposes)
				// if current player is last player, next player is first
				// player.
				String nextPlayer;
				if (index != hands.length - 1) {
					nextPlayer = "Player " + (index + 2);
				} else {
					nextPlayer = "Player " + 1;
				}
				// if two is played next player picks up two cards and has their
				// turn skipped. % is used to account for circular order of
				// players
				if (playedCard.getRank() == 2) {
					System.out.println(nextPlayer + " picks up two cards");
					System.out.println(nextPlayer + " misses their turn.");
					int nextIndex = (index + 1) % parsedInt;
					if (deck.numCards() > 2) {
						hands[nextIndex].getCard(new Card[] { deck.pop(), deck.pop() });
						printHand(index);
						index = (index + 2) % parsedInt;
						pile[0] = playedCard;
						System.out.println();
						continue;
					}
					// if only 2 cards left in deck, next player will pick
					// them both up, and deckEmpty will run to determine
					// winner, program will then end
					if (deck.numCards() == 2) {
						hands[nextIndex].getCard(new Card[] { deck.pop(), deck.pop() });
						printHand(index);
						deckEmpty();
						return;
					}
					// if only one card left, next player will take that one
					// card, pile will be updated and then deck empty will run
					// and program will end
					else {
						hands[nextIndex].getCard(new Card[] { deck.pop() });
						printHand(index);
						pile[0] = playedCard;
						deckEmpty();
						return;
					}
				}
				// if 4 is played, next player has their turn skipped, discard
				// pile is updated and current turn is over. The hand index is
				// adjusted accordingly to account for the turn skip (circular
				// order)
				if (playedCard.getRank() == 4) {
					System.out.println(nextPlayer + " misses their turn.");
					printHand(index);
					index = (index + 2) % parsedInt;
					pile[0] = playedCard;
					System.out.println();
					continue;
				}
				// if 8 is played, message is displayed and suit of played 8 is
				// set based off of message method logic.
				if (playedCard.getRank() == 8) {
					playedCard = new MyCard(8, hands[index].message(question));
				}
				// if joker is played, current card on pile is not updated, and
				// then turn ends
				if (playedCard.getRank() == 1) {
					printHand(index);
					index = (index + 1) % parsedInt;
					System.out.println();
					continue;
				}
				// pile is updated, current hand is printed and index is updated
				// at the end of turn.
				pile[0] = playedCard;

				printHand(index);
				index = (index + 1) % parsedInt;
				System.out.println();
			}
		}
	}

	/*
	 * takes as input hand object current hand and integer index and prints out
	 * the specified player's hand.
	 */
	private static void printHand(int index) {
		System.out.println("Player " + (index + 1) + "'s hand:");
		System.out.println(Arrays.toString(hands[index].displayHand()));
	}

	/*
	 * Takes as input specified hand object as well as card[] pile and returns
	 * true or false depending on whether or not the current hand has a valid
	 * card to play.
	 */
	private static boolean canPlay(Hand hand, Card[] pile) {
		for (int i = 0; i < hand.displayHand().length; i++) {
			if (hand.displayHand()[i].getSuit().equals(pile[pile.length - 1].getSuit())
					|| hand.displayHand()[i].getRank() == pile[pile.length - 1].getRank()
					|| hand.displayHand()[i].getRank() == 8 || hand.displayHand()[i].getRank() == 1) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Method is called when deck is empty, Method will print out all hands and
	 * check which hand has the lowest sum of ranks and printing that hand as
	 * the winner.
	 */
	private static void deckEmpty() {
		System.out.println();
		System.out.println("There are no more cards in the deck.");
		// display all players' hands.
		for (int i = 0; i < hands.length; i++) {
			printHand(i);
		}
		// Check which players' hand has the lowest sum of ranks
		int winnerHand = 0;
		int currentRank = 0;
		int lowestRank = 100000000;
		for (int i = 0; i < hands.length; i++) {
			for (int k = 0; k < hands[i].displayHand().length; k++) {
				// add current hand index i's card ranks
				currentRank += hands[i].displayHand()[k].getRank();
			}
			// check if current hand index i's sum of ranks is lower than
			// lowestRank,
			// if it is, replace lowestRank with current hand index's sum of
			// ranks,
			// and assign player/hand number to winnerHand.
			if (currentRank < lowestRank) {
				lowestRank = currentRank;
				winnerHand = (i + 1);
			}
			currentRank = 0;
		}
		// display which player won (lowest sum of ranks)
		System.out.println("Player " + winnerHand + " has won.");
		return;
	}
}
