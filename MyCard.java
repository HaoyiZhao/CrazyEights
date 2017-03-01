/**Class models actual cards, inheriting certain constants and methods
 * from its parent class, Card.java. Methods available in class include
 * overriden CompareTo, getRank as an integer, getRankString, and getSuit.
 * ToString method is inherited from Card.java. Two constructors: both taking a
 * card's rank and suit as input, with one taking the card's rank as an integer,
 * while the other takes the rank as a String.
 * 
 * @author HowieZhao
 *
 */
public class MyCard extends Card {
	// made attributes protected as stated by the specifications.
	protected String rank;
	protected String suit;

	// create new MyCard, with input rank(String) and suit assigned to card's
	// attributes
	public MyCard(String rank, String suit) {
		this.rank = rank;
		this.suit = suit;
	}

	// create new MyCard, with input rank(integer) and suit assigned to card's
	// attributes.
	public MyCard(int rank, String suit) {
		this.suit = suit;
		if (rank == 1) {
			this.rank = "Joker";
		} else if (rank == 11) {
			this.rank = "Jack";
		} else if (rank == 12) {
			this.rank = "Queen";
		} else if (rank == 13) {
			this.rank = "King";
		} else if (rank == 14) {
			this.rank = "Ace";
		} else {
			this.rank = Integer.toString(rank);
		}
	}

	// override compareTo method of object (forced by Card.java). First compares
	// suit ("none" suit of joker highest)
	// then compares card rank if suits are equal.
	@Override
	public int compareTo(Card o) {
		if (this.getSuit() != o.getSuit()) {
			if (this.getSuit() == "None") {
				return 1;
			}
			if (o.getSuit() == "None") {
				return -1;
			}
			if (this.getSuit() == "Spades") {
				return 1;
			}
			if (o.getSuit() == "Spades") {
				return -1;
			}
			if (this.getSuit() == "Hearts") {
				return 1;
			}
			if (o.getSuit() == "Hearts") {
				return -1;
			}
			if (this.getSuit() == "Clubs") {
				return 1;
			}
			if (o.getSuit() == "Clubs") {
				return -1;
			}
		}
		return this.getRank() - o.getRank();
	}

	// method returns rank of current card object as an integer converted from
	// its string attribute rank.
	@Override
	public int getRank() {
		if (this.rank == "Joker") {
			return 1;
		} else if (this.rank == "Jack") {
			return 11;
		} else if (this.rank == "Queen") {
			return 12;
		} else if (this.rank == "King") {
			return 13;
		} else if (this.rank == "Ace") {
			return 14;
		} else {
			return Integer.parseInt(this.rank);
		}
	}

	// returns current card's rank as its original String using its attribute
	// rank.
	@Override
	public String getRankString() {

		return this.rank;
	}

	// returns current card's suit as a string from it's attribute suit.
	@Override
	public String getSuit() {
		return this.suit;
	}

	// test cases
	public static void main(String[] args) {
		Card c = new MyCard("2", "Diamonds");
		Card d = new MyCard(14, "Diamonds");
		System.out.println(c);
		System.out.println(c.compareTo(d));
	}

}
