// A Java program for a Server 
import java.net.*;
import java.util.ArrayList;
import java.io.*; 
  
public class Server 
{ 
	ArrayList<ArrayList<Card>> opps = new ArrayList<ArrayList<Card>>();
	static ArrayList<Card> board = new ArrayList<Card>();
	static ArrayList<Card> mine = new ArrayList<Card>();
	
    //initialize socket and input stream 
    private Socket          socket   = null; 
    private ServerSocket    server   = null; 
    private DataInputStream in       =  null; 
  
    RangeOdds ro = null;
    
    // constructor with port 
    public Server(int port) 
    { 
        // starts server and waits for a connection 
        try
        { 
            server = new ServerSocket(port); 
            System.out.println("Server started"); 
  
            System.out.println("Waiting for a client ..."); 
  
            socket = server.accept(); 
            System.out.println("Client accepted"); 
  
            // takes input from the client socket 
            in = new DataInputStream( 
                new BufferedInputStream(socket.getInputStream())); 
            
            String line = ""; 
  
            // reads message from client until "Over" is sent 
            while (!line.equals("Over")) 
            { 
                try
                { 
                    line = in.readUTF(); 
                    System.out.println(line); 
                    if(line.equalsIgnoreCase("addmine")) {
                    	addMine();
                    } else if(line.equalsIgnoreCase("addboard")) {
                    	addBoard();
                    } else if(line.equalsIgnoreCase("addopphand")){
                    	addOppHand();
                    } else if(line.equalsIgnoreCase("getodds")) {
                    	getOdds();
                    }
  
                } 
                catch(IOException i) 
                { 
                    System.out.println(i); 
                } 
            } 
            System.out.println("Closing connection"); 
  
            // close connection 
            socket.close(); 
            in.close(); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
    } 
  
    private void getOdds() {
    	printHand(mine);
    	printHand(board);
    	printHand(opps.get(0));
    	if(mine.size()==2&&board!=null&&opps!=null) {
    		ro = new RangeOdds(mine,board,opps);
    		System.out.println("ODDS TO WIN: "+ ro.getRangeOdds());
    	}
	}

	private void addOppHand() {
    	ArrayList<Card> hnd = new ArrayList<Card>();
    	int cards = 0;
    	String line = "";
    	while(cards<2) {
    		try {
				line = in.readUTF();
			} catch (IOException e) {
				System.out.println("Error in AddMine of server from (line = in.LineUTF()");
				e.printStackTrace();
			}
    		int id = Integer.parseInt(line);
    		if(id<53&&id>0) {
    			Card c = new Card(id);
    			System.out.println(c.str);
    			hnd.add(c);
    			cards++;
    		} else {
    			System.out.println("Invalid Card ID");
    		}
    	}
    	System.out.println("Opp Hand Entered");	
    	opps.add(hnd);
	}

	private void addBoard() {
    	String line = "";
    	int id = 0;
    	while(!line.equalsIgnoreCase("doneboard")) {
    		try {
				line = in.readUTF();
			} catch (IOException e) {
				System.out.println("Error in AddMine of server from (line = in.LineUTF()");
				e.printStackTrace();
			}
    		try {
    			id=Integer.parseInt(line);
        		if(id<53&&id>0) {
        			Card c = new Card(id);
        			System.out.println(c.str);
        			board.add(c);
        		} else {
        			System.out.println("Invalid Card ID");
        		}
    		} catch (Exception e) {
    			System.out.println("Invalid Card ID error parsing");
    			id = 0;
    		}

    	}
    	System.out.println("Board Entered");		
	}

	private void addMine() {
    	int cards = 0;
    	String line = "";
    	while(cards<2) {
    		try {
				line = in.readUTF();
			} catch (IOException e) {
				System.out.println("Error in AddMine of server from (line = in.LineUTF()");
				e.printStackTrace();
			}
    		int id = Integer.parseInt(line);
    		if(id<53&&id>0) {
    			Card c = new Card(id);
    			System.out.println(c.str);
    			mine.add(c);
    			cards++;
    		} else {
    			System.out.println("Invalid Card ID");
    		}
    	}
    	System.out.println("My Hand Entered");
	}

	private void printHand(ArrayList<Card> choose5) {
		for (int i = 0; i < choose5.size(); i++) {
			System.out.print(choose5.get(i).getStr() + " ");
		}
		System.out.println();

	}
	public static void main(String args[]) 
    { 
        Server server = new Server(5000); 
    } 
} 