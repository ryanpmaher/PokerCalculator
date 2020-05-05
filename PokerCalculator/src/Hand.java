import java.util.ArrayList;
import java.lang.Object;

public class Hand {
	ArrayList<Card> hand = new ArrayList<Card>();// list of available cards
	Long rank = (long) 0; // best 5 card score
	ArrayList<Card> current5;
	ArrayList<Card> final5;

	// add card to hand
	public void addCard(Card c) {
		hand.add(c);
	}
	public void removeCard(Card c) {
		for(int i =0;i<hand.size();i++) {
			if(hand.get(i).getCardId()==c.getCardId()) {
				hand.remove(i);
				i=hand.size();
			}
		}
	}

	// Numerical hand strength
	public  Long getBest() {
		rank = (long)0;
		for (int i = 0; i < 7; i++) {
			Card c1 = hand.remove(i);
			for (int j =i; j < 6; j++) {
				Card c2 = hand.remove(j);
				current5  = (ArrayList<Card>) hand.clone();
				sortHand();
				Long hS = handScore();
				if(hS!=null&&Long.compare(rank, hS)<0){
					rank = hS.longValue();
					final5 = (ArrayList<Card>) current5.clone();
				}
				
				hand.add(j, c2);
			}
			hand.add(i, c1);
		}
		return rank;
	}

	private void sortHand() {
		for(int i =0;i<5;i++) {
			int min = current5.get(0).getValue();
			int index = 0;
			for(int j =1;j<5-i;j++) {
				if(current5.get(j).getValue()<min) {
					min = current5.get(j).getValue();
					index = j;
				}
			}
			Card c = current5.remove(index);
			current5.add(5-i-1,c);
		}
		
	}

	private Long handScore() {
		// key for most significant hex digit:
		// straight flush ---9
		// four of a kind ---8
		// full house ---7
		// flush ---
		// straight ---5
		// three of a kind ---4
		// two pair ---3
		// pair ---2
		// highcard ---1

		// check straight flush
		if (checkFlush(current5)&&checkStraight(current5)) {
			return Long.decode("0x9"+getHandString(current5));
		} else if(checkFour(current5)) {
			//check four of a kind
			return Long.decode("0x8"+getHandString(current5));
		} else if(checkHouse(current5)) {
			//check full house
			if(current5.get(2).getValue()!=current5.get(1).getValue()) {
				current5.add(current5.remove(0));
				current5.add(current5.remove(0));
			}
			return Long.decode("0x7"+getHandString(current5));
		} else if (checkFlush(current5)) {
			return Long.decode("0x6"+getHandString(current5));
		} else if (checkStraight(current5)) {
			return Long.decode("0x5"+getHandString(current5));
		}
		long kind3 = checkKind3(current5);
		if(Long.compare(kind3, 0)!=0) {
			return kind3;
		}
		long pair2 = checkPair2(current5);
		if(Long.compare(pair2, 0)!=0) {
			return pair2;
		}
		long pair = checkPair(current5);
		if(Long.compare(pair, 0)!=0) {
			return pair;
		}
		
		return Long.decode("0x1"+getHandString(current5));
	}
	private long checkPair(ArrayList<Card> choose5) {
		for(int i=0;i<4;i++) {
			if(choose5.get(i).getValue()==choose5.get(i+1).getValue()) {
				choose5.add(0,choose5.remove(i+1));
				choose5.add(0,choose5.remove(i+1));
				return Long.decode("0x2"+getHandString(choose5));
			}
		}
		return 0;
	}

	private long checkPair2(ArrayList<Card> choose5) {
		if(choose5.get(0).getValue()==choose5.get(1).getValue()&&choose5.get(2).getValue()==choose5.get(3).getValue()) {
			return Long.decode("0x3"+getHandString(choose5));
		} else if(choose5.get(1).getValue()==choose5.get(2).getValue()&&choose5.get(3).getValue()==choose5.get(4).getValue()) {
			Card c = choose5.remove(0);
			choose5.add(4,c);
			return Long.decode("0x3"+getHandString(choose5));
		} else if(choose5.get(0).getValue()==choose5.get(1).getValue()&&choose5.get(3).getValue()==choose5.get(4).getValue()) {
			Card c = choose5.remove(2);
			choose5.add(4,c);
			return Long.decode("0x3"+getHandString(choose5));
		}
		return 0;
	}

	private long checkKind3(ArrayList<Card> choose5) {
		if(choose5.get(0).getValue()==choose5.get(1).getValue()&&choose5.get(1).getValue()==choose5.get(2).getValue()) {
			return Long.decode("0x4"+getHandString(choose5));
		} else if(choose5.get(3).getValue()==choose5.get(2).getValue()&&choose5.get(1).getValue()==choose5.get(2).getValue()) {
			Card c = choose5.remove(0);
			choose5.add(3,c);
			return Long.decode("0x4"+getHandString(choose5));
		} else if(choose5.get(3).getValue()==choose5.get(2).getValue()&&choose5.get(4).getValue()==choose5.get(3).getValue()) {
			Card c = choose5.remove(0);
			Card c2 = choose5.remove(0);
			choose5.add(c);
			choose5.add(c2);
			return Long.decode("0x4"+getHandString(choose5));
		}
		return 0;
	}

	private boolean checkHouse(ArrayList<Card> choose5) {
		return (choose5.get(0).getValue()==choose5.get(1).getValue()&&choose5.get(3).getValue()==choose5.get(4).getValue())
				&&(choose5.get(1).getValue()==choose5.get(2).getValue()||choose5.get(3).getValue()==choose5.get(2).getValue());
	}

	private String getHandString(ArrayList<Card> choose5) {
		String ret="";
		for(int i =0;i<choose5.size();i++) {
			ret+=Integer.toHexString(choose5.get(i).getValue());
		}
		return ret;
	}

	private boolean checkFour(ArrayList<Card> choose5) {
		return (choose5.get(1).getValue()==choose5.get(2).getValue()&&choose5.get(2).getValue()==choose5.get(3).getValue())
				&&(choose5.get(0).getValue()==choose5.get(2).getValue()||choose5.get(4).getValue()==choose5.get(2).getValue());
	}

	private boolean checkStraight(ArrayList<Card> choose5) {
		boolean ret = false;
		//check for straight -- not ace low
		if(choose5.get(1).getValue()==choose5.get(0).getValue()-1&&
				choose5.get(2).getValue()==choose5.get(0).getValue()-2&&
				choose5.get(3).getValue()==choose5.get(0).getValue()-3&&
				choose5.get(4).getValue()==choose5.get(0).getValue()-4) {
			ret = true;
		} else if(choose5.get(0).getValue()==14&&choose5.get(1).getValue()==5 
				&&choose5.get(2).getValue()==4&&choose5.get(3).getValue()==3
				&&choose5.get(4).getValue()==2) {
				choose5.add(choose5.remove(0));
				//checked wheel straight
			ret = true;
		}
		return ret;
	}
	private boolean checkFlush(ArrayList<Card> choose5) {
		int suit = choose5.get(0).getSuit();
		return choose5.get(1).getSuit()==suit&&choose5.get(2).getSuit()==suit
				&&choose5.get(3).getSuit()==suit&&choose5.get(4).getSuit()==suit;
	}

	private void printHand(ArrayList<Card> choose5) {
		for (int i = 0; i < choose5.size(); i++) {
			System.out.print(choose5.get(i).getStr() + " ");
		}
		System.out.println();

	}
/*	public static void main(String[] args) {
		Hand h = new Hand();
		h.addCard(new Card(13,2));
		h.addCard(new Card(12,2));
		h.addCard(new Card(11,2));
		h.addCard(new Card(10,2));
		h.addCard(new Card(9,2));
		h.addCard(new Card(4,2));
		h.addCard(new Card(3,2));

		System.out.println(Long.toHexString(h.getBest()));
	}
*/}
