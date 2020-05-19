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
	public double processString (String myHandS[],String oppRangeS[], String boardS[]) {
		//FORMAT:
		//my Hand: [FIRST CARD ID] [SECOND CARD ID]
		//Example: [51][33]
		//oppRange: [Possible hand1][Possible hand2]...
		//Example:[9-10o][10-Jo]...
		//Board: [Card1][Card2][Card3]....
		Deck d = new Deck();
		ArrayList<Card> mine1 = new ArrayList<Card>();
		ArrayList<Card> board1 = new ArrayList<Card>();
		ArrayList<ArrayList<Card>> range = new ArrayList<ArrayList<Card>>();
		//Process My Hand:
		mine1.add(new Card(Integer.parseInt(myHandS[0])));
		mine1.add(new Card(Integer.parseInt(myHandS[1])));
		d.removeCard(new Card(Integer.parseInt(myHandS[0])));
		d.removeCard(new Card(Integer.parseInt(myHandS[1])));

		//Process Board
		for(int i =0;i<boardS.length;i++) {
			board1.add(new Card(Integer.parseInt(boardS[i])));
			d.removeCard(new Card(Integer.parseInt(boardS[i])));
		}
		
		//Process Range
		for(int i = 0;i<oppRangeS.length;i++) {
			int card1;
			int card2;
			String s = oppRangeS[i];
			int dash = s.indexOf('-');
			
			String c1 = s.substring(0,dash);
			String c2 = s.substring(dash+1);
			
			card1 = Integer.parseInt(c1);
			card2 = Integer.parseInt(c2);
			
			if(card1==card2) {
				for(int k = 1;k<4;k++) {
					Card crd1 = new Card(card1,k);
					if(d.inDeck(crd1)) {
						for(int j = k+1;j<5;j++) {
							Card crd2 = new Card(card1,j);
							if(d.inDeck(crd2)) {
								ArrayList<Card> opp = new ArrayList<Card>();
								opp.add(crd1);
								opp.add(crd2);
								printHand(opp);
								range.add(opp);
							}
						}
					}
				}
			} else if(card1>card2) {//SUITED
				for(int k = 1;k<5;k++) {
					Card crd1 = new Card(card1,k);
					Card crd2 = new Card(card2,k);
					if(d.inDeck(crd1)&&d.inDeck(crd2)) {
						ArrayList<Card> opp = new ArrayList<Card>();
						opp.add(crd1);
						opp.add(crd2);
						printHand(opp);
						range.add(opp);
					}
				}
			} else if(card2>card1) {
				for(int k = 1;k<5;k++) {
					Card crd1 = new Card(card1,k);
					if(d.inDeck(crd1)) {
						for(int j = 1;j<5;j++) {
							Card crd2 = new Card(card2,j);
							if(k!=j&&d.inDeck(crd2)) {
								ArrayList<Card> opp = new ArrayList<Card>();
								opp.add(crd1);
								opp.add(crd2);
								printHand(opp);
								range.add(opp);
							}
						}
					}
				}
			}
		}
		board = board1;
		mine = mine1;
		opps = range;

		return getRangeOdds();
	}
	public static void main(String[] args) {
		ArrayList<Card> opp1 = new ArrayList<Card>();
		ArrayList<Card> opp2 = new ArrayList<Card>();
		ArrayList<Card> mine1 = new ArrayList<Card>();
		ArrayList<Card> board1 = new ArrayList<Card>();
		ArrayList<ArrayList<Card>> f = new ArrayList<ArrayList<Card>>();

		mine1.add(new Card(8,3));
		mine1.add(new Card(8,2));
		
		opp1.add(new Card(10,3));
		opp1.add(new Card(11,4));
		
		opp2.add(new Card(13,1));
		opp2.add(new Card(12,1));
		

				
		board1.add(new Card(12,2));
		board1.add(new Card(6,1));
		board1.add(new Card(10,1));
		board1.add(new Card(8,1));
		f.add(opp1);
		f.add(opp2);
		RangeOdds r = new RangeOdds(mine1,board1,f);
		
		System.out.println("Odds to win: " + r.getRangeOdds());
		
		String[] mineArr = new String[2];
		mineArr[0] = ""+mine1.get(0).getCardId();
		mineArr[1] = ""+mine1.get(1).getCardId();
		
		String boardArr[] = new String[board1.size()];
		for(int i = 0;i<board1.size();i++) {
			boardArr[i] = ""+board1.get(i).getCardId();
		}
		
		String rangeArr[] = new String[f.size()];
		for(int i =0;i<f.size();i++) {
			rangeArr[i]=""+f.get(i).get(0).getValue()+"-"+f.get(i).get(1).getValue();
		}
		
		System.out.println("Odds to win (STRING): " + r.processString(mineArr, rangeArr, boardArr));


	}
	private void printHand(ArrayList<Card> choose5) {
		for (int i = 0; i < choose5.size(); i++) {
			System.out.print(choose5.get(i).getStr() + " ");
		}
		System.out.println();

	}
}
