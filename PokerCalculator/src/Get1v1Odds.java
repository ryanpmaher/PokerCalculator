import java.util.ArrayList;

public class Get1v1Odds {
	ArrayList<Card> myHand;
	ArrayList<Card> oppHand;
	ArrayList<Card> board;
	
	Get1v1Odds(ArrayList<Card> myHand1, ArrayList<Card> oppHand1, ArrayList<Card> board1){
		myHand = myHand1;
		oppHand = oppHand1;
		board = board1;
	}

	public double getOdds() {
		Hand mine = new Hand();
		Hand opp = new Hand();

		double total = 0;
		double wins = 0;
		Deck d = new Deck();
		
		for(int i=0;i<myHand.size();i++) {
			mine.addCard(myHand.get(i));
			d.removeCard(myHand.get(i));
		}
		for(int i=0;i<oppHand.size();i++) {
			opp.addCard(oppHand.get(i));
			d.removeCard(oppHand.get(i));
		}
		
		for(int i = 0;i<board.size();i++) {
			opp.addCard(board.get(i));
			mine.addCard(board.get(i));
			d.removeCard(board.get(i));
		}
		
		for(int i = 1;i<53;i++) {
			if(d.inDeck(i)) {
				total++;
				Card c = d.removeCard(i);
				opp.addCard(c);
				mine.addCard(c);
				Long oppScore = opp.getBest();
				Long myScore = mine.getBest();
				

				if(Long.compare(myScore, oppScore)>0) {
					wins++;
				}
				opp.removeCard(c);
				mine.removeCard(c);
			}
		}
		return wins/total;
	}
	private void printHand(ArrayList<Card> choose5) {
		for (int i = 0; i < choose5.size(); i++) {
			System.out.print(choose5.get(i).getStr() + " ");
		}
		System.out.println();

	}
	public static void main(String[] args) {
		ArrayList<Card> mine = new ArrayList<Card>();
		ArrayList<Card> opp = new ArrayList<Card>();
		ArrayList<Card> board = new ArrayList<Card>();
		
		mine.add(new Card(14,4));
		mine.add(new Card(13,4));
		
		opp.add(new Card(6,1));
		opp.add(new Card(7,1));
		
		board.add(new Card(5,1));
		board.add(new Card(4,1));
		board.add(new Card(13,3));
		board.add(new Card(14,3));
		
		Get1v1Odds g = new Get1v1Odds(mine,opp,board);
		System.out.println("Odds to win: " + g.getOdds());
		

	}
}
