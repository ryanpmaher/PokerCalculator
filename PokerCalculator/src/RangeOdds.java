import java.util.ArrayList;

public class RangeOdds {
	ArrayList<ArrayList<Card>> opps = new ArrayList<ArrayList<Card>>();
	static ArrayList<Card> board = new ArrayList<Card>();
	static ArrayList<Card> mine = new ArrayList<Card>();

	RangeOdds(ArrayList<Card> m, ArrayList<Card> b){
		mine = m;
		board = b;
	}
	
	RangeOdds(ArrayList<Card> m, ArrayList<Card> b, ArrayList<ArrayList<Card>> o){
		mine = m;
		board = b;
		opps = o;
	}
	
	public void addOppHand(Card c1, Card c2) {
		ArrayList<Card> opp = new ArrayList<Card>();
		opp.add(c1);
		opp.add(c2);
		opps.add(opp);
	}
	public void addOppHand(ArrayList<Card> opp) {
		opps.add(opp);
	}
	public double getRangeOdds() {
		double sum = 0;
		for(ArrayList<Card> oppHand : opps) {
			Get1v1Odds g = new Get1v1Odds(mine,oppHand,board);
			sum += g.getOdds();
		}
		return sum/(double)opps.size();
	}
	public double getEV(double odds, double pot, double call) {
		return (odds*pot)-((1-odds)*call);
	}
	public static void main(String[] args) {
		ArrayList<Card> opp1 = new ArrayList<Card>();
		ArrayList<Card> opp2 = new ArrayList<Card>();
		ArrayList<Card> mine1 = new ArrayList<Card>();
		ArrayList<Card> board1 = new ArrayList<Card>();
		ArrayList<ArrayList<Card>> f = new ArrayList<ArrayList<Card>>();

		mine1.add(new Card(8,3));
		mine1.add(new Card(8,2));
		
		opp1.add(new Card(14,1));
		opp1.add(new Card(5,1));
		
		

				
		board1.add(new Card(4,1));
		board1.add(new Card(6,3));
		board1.add(new Card(7,4));
		board1.add(new Card(12,1));
		f.add(opp1);
		
		RangeOdds r = new RangeOdds(mine1,board1,f);
		
		System.out.println("Odds to win: " + r.getRangeOdds());
		

	}
}
