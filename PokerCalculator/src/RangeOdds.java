import java.util.ArrayList;

public class RangeOdds {
	ArrayList<ArrayList<Card>> opps = new ArrayList<ArrayList<Card>>();
	static ArrayList<Card> board = new ArrayList<Card>();
	static ArrayList<Card> mine = new ArrayList<Card>();

	public void addOppHand(Card c1, Card c2) {
		ArrayList<Card> opp = new ArrayList<Card>();
		opp.add(c1);
		opp.add(c2);
		opps.add(opp);
	}
	public void addOppHand(ArrayList<Card> opp) {
		opps.add(opp);
	}
	public double rangeOdds() {
		double sum = 0;
		for(ArrayList<Card> oppHand : opps) {
			Get1v1Odds g = new Get1v1Odds(mine,oppHand,board);
			sum += g.getOdds();
		}
		return sum/(double)opps.size();
	}
	public static void main(String[] args) {
		ArrayList<Card> opp1 = new ArrayList<Card>();
		ArrayList<Card> opp2 = new ArrayList<Card>();
		
		mine.add(new Card(14,4));
		mine.add(new Card(12,4));
		
		opp1.add(new Card(6,1));
		opp1.add(new Card(7,2));
		
		opp2.add(new Card(13,1));
		opp2.add(new Card(14,2));
				
		board.add(new Card(6,2));
		board.add(new Card(4,1));
		board.add(new Card(11,3));
		
		RangeOdds r = new RangeOdds();
		r.addOppHand(opp1);
		r.addOppHand(opp2);
		System.out.println("Odds to win: " + r.rangeOdds());
		

	}
}
