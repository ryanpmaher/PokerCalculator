import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Card {
	int value; //J=11  Q=12  K=13  A=14
	int suit;//1-Clubs  2-Diamonds  3-Hearts  4-Spades
	String str; //to get image from file
	BufferedImage img = null; //Image of card
	Card(int v, int s){
		//Assign Values
		value = v;
		suit = s;
		str = makeStr();
		
/*		//Load image
		String imageStr = "C:\\Users\\Ryan Maher\\Desktop\\NewWorkspace\\PokerCalculator\\Graphics\\Cards\\"+str+".png";
		try {
		    img = ImageIO.read(new File(imageStr));
		} catch (IOException e) {
			System.out.println(e.toString()+"\nError loading image from string: "+imageStr);
			
		}*/
	}
	Card(int id){
		suit = id%4;
		id-=suit;
		
		if(suit==0) {
			id-=4;
			suit =4;
		}
		value = id/4 +2;
		str = makeStr();
		
	}

	private String makeStr() {
		String suitS="";
		String valS;
		switch(this.suit) {
			case 1:
				suitS = "C";
				break;
			case 2:
				suitS = "D";
				break;
			case 3:
				suitS = "H";
				break;
			case 4:
				suitS = "S";
				break;
		}
		switch(this.value) {
			case 14:
				valS = "A";
				break;
			case 11:
				valS = "J";
				break;
			case 12:
				valS = "Q";
				break;
			case 13:
				valS = "K";
				break;
			default:
				valS = Integer.toString(this.value);
		}
		return valS+suitS;
	}
	public int getCardId() {
		return (value-2)*4 + suit;
	}
	public int getValue() {
		return value;
	}

	public int getSuit() {
		return suit;
	}

	public String getStr() {
		return str;
	}

	public BufferedImage getImg() {
		return img;
	}

}
