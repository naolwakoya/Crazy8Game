import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.Random;


public class Crazy8Game{
  
  public static void main(String[] args){
    int[] marks = new int[]{0,0,0,0};
    boolean finish = true;
    int done=1000;
    while(finish){
      System.out.println();
      System.out.println("New round start!");
      System.out.println();
      //create the deck 
      Card[] deck = new Card[52];
      int index = 0;
      for(int r=2; r<=14; r+=1){
        for(int s=0; s<4; s+=1){
          deck[index++] = new Card(Card.SUITS[s], Card.RANKS[r]);
        }
      }
      
      // shuffle the deck 
      Random rnd = new Random();
      Card swap;
      for(int i = deck.length-1; i>=0; i=i-1){
        int pos = rnd.nextInt(i+1);
        swap = deck[pos];
        deck[pos] = deck[i];
        deck[i] = swap;
      }
      
      /* players in the game */
      Player[] players = new Player[4];
      players[0] = new MindTheEights( Arrays.copyOfRange(deck, 0, 5) );
      System.out.println("0 : " + Arrays.toString( Arrays.copyOfRange(deck, 0, 5)));
      players[1] = new DiscardHighPoints( Arrays.copyOfRange(deck, 5, 10) );
      System.out.println("1 : " + Arrays.toString( Arrays.copyOfRange(deck, 5, 10)));
      players[2] = new ExtraCards( Arrays.copyOfRange(deck, 10, 15) );
      System.out.println("2 : " + Arrays.toString( Arrays.copyOfRange(deck, 10, 15)));
      players[3] = new HamperLeader( Arrays.copyOfRange(deck, 15, 20) );
      System.out.println("3 : " + Arrays.toString( Arrays.copyOfRange(deck, 15, 20)));
      
      
      
     /* Player[] players = new Player[4];
       players[0] = new DiscardHighPoints( Arrays.copyOfRange(deck, 0, 5) );
       System.out.println("0 : " + Arrays.toString( Arrays.copyOfRange(deck, 0, 5)));
       players[1] = new DiscardHighPoints( Arrays.copyOfRange(deck, 5, 10) );
       System.out.println("1 : " + Arrays.toString( Arrays.copyOfRange(deck, 5, 10)));
       players[2] = new DiscardHighPoints( Arrays.copyOfRange(deck, 10, 15) );
       System.out.println("2 : " + Arrays.toString( Arrays.copyOfRange(deck, 10, 15)));
       players[3] = new DiscardHighPoints( Arrays.copyOfRange(deck, 15, 20) );
       System.out.println("3 : " + Arrays.toString( Arrays.copyOfRange(deck, 15, 20)));*/
      
      
      /* discard and draw piles */
      DiscardPile discardPile = new DiscardPile();
      Stack<Card> drawPile = new Stack<Card>();
      for(int i=15; i<deck.length; i++){
        drawPile.push(deck[i]);
      }
      
      System.out.println("draw pile is : " + Arrays.toString( Arrays.copyOfRange(deck, 15, deck.length) ));
      
      deck = null;
      
      boolean move;
      boolean win = false;
      int next = -1;
      int player = 0;    
      int a = 1; 
      
      ArrayList<Player> people = new ArrayList<Player>(Arrays.asList(players));
      discardPile.add( drawPile.pop() );
      
      
      while( !win ){
        player = (player + next)==players.length?0:((player + next)==-1?players.length-1:(player + next));
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Player's stats");
        for(int i=0; i<players.length; i++){
          System.out.println("Player " + i + " has " + people.get(i).sum() +" points left: " + people.get(i).cards());
        }
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("player " + player + " has cards :"+people.get(player).cards());
        if(!drawPile.empty()) {System.out.println("draw pile before    : " + Arrays.toString(drawPile.toArray()) );}
        System.out.println("discard pile before: " + discardPile.cards.toString() );
        
        move = people.get(player).play(discardPile, drawPile, people, next);
        if (discardPile.top().getRank() == 2){
          player = (player + next)==players.length?0:((player + next)==-1?players.length-1:(player + next));
          System.out.println();
          System.out.println("player "+player+" pick two card");
          System.out.println("player " + player + " has cards :"+people.get(player).cards());
          if(!drawPile.empty()) {System.out.println("draw pile before    : " + Arrays.toString(drawPile.toArray()) );}
          System.out.println("discard pile before: " + discardPile.cards.toString() );
          if(drawPile.empty()){
            System.out.println("after turn player " + player + " has cards :"+people.get(player).cards());
            if(!drawPile.empty()) {System.out.println("draw pile after    : " + Arrays.toString(drawPile.toArray()) );}
            System.out.println("discard pile after: " + discardPile.cards.toString() );
            break;
          }
          people.get(player).draw(drawPile);
          if(drawPile.empty()){
            System.out.println("after turn player " + player + " has cards :"+people.get(player).cards());
            if(!drawPile.empty()) {System.out.println("draw pile after    : " + Arrays.toString(drawPile.toArray()) );}
            System.out.println("discard pile after: " + discardPile.cards.toString() );
            break;
          }
          people.get(player).draw(drawPile);
          System.out.println("after turn player " + player + " has cards :"+people.get(player).cards());
          if(!drawPile.empty()) {System.out.println("draw pile after    : " + Arrays.toString(drawPile.toArray()) );}
          System.out.println("discard pile after: " + discardPile.cards.toString() );
          
        } 
        else if (discardPile.top().getRank() == 4){
          player = (player + next)==players.length?0:((player + next)==-1?players.length-1:(player + next));
          System.out.println("skip player "+player);
        } 
        else if (discardPile.top().getRank() == 7){
          next *= -1;
          System.out.println("reverse!");
        }
        else if( discardPile.top().getRank() == 8) {
          System.out.println("Got 8: "+discardPile.top());
          discardPile.ask(people.get(player).ask());
          System.out.println("player want change to: "+discardPile.top());
          
        }
        
        
        
        win = move;
        
        System.out.println("after turn player " + player + " has cards :"+people.get(player).cards());
        if(!drawPile.empty()) {System.out.println("draw pile after    : " + Arrays.toString(drawPile.toArray()) );}
        System.out.println("discard pile after: " + discardPile.cards.toString() );
        
      }
      System.out.println();
      System.out.println("This round finish");
      //if(true) {
      if(people.get(player).getSizeOfHand() == 0 ) {
        System.out.println("winner is player " + player);
        System.out.println("draw pile is : " + Arrays.toString(drawPile.toArray()));
        System.out.println("discard pile is: " + discardPile.cards.toString() );
        for(int i=0; i<players.length; i++){
          int temp;
          temp = people.get(i).sum();
          System.out.println("Player " + i + " has " + temp +" points left: " + people.get(i).cards());
          marks[player]+=temp;
        }
      }
      else {
        int[] temp = new int[]{0,0,0,0};
        int min = 0;
        int minP = people.get(0).sum();
        System.out.println("drawPile empty");
        System.out.println("draw pile is : " + Arrays.toString(drawPile.toArray()));
        System.out.println("discard pile is: " + discardPile.cards.toString() );
        for(int i=0; i<players.length; i++){
          temp[i] = people.get(i).sum();
          System.out.println("Player " + i + " has " + temp[i] +" points left: " + people.get(i).cards());
        }
        for(int j = 0; j < players.length; j++){
          if(temp[j]<minP){
            min = j;
            minP = temp[j];
          }
        }
        System.out.println("winner is player " + min);
        for(int k=0; k<players.length; k++){
          marks[min]+=temp[k];
        }
        
      }
      System.out.println("Now players' points are:" + Arrays.toString(marks));
      for(int l=0; l<players.length; l++){
        if(marks[l]>=done){
          System.out.println();
          System.out.println("Champion is player " + l);
          finish = false;
          break;
        }
      }
    }
  }
}

/*import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.Stack;
 import java.util.Random;
 
 public class Crazy8Game{
 
 public static void main(String[] args){
 
 Card[] deck = new Card[52];
 int index = 0;
 for(int r=2; r<=14; r+=1){
 for(int s=0; s<4; s+=1){
 deck[index++] = new Card(Card.SUITS[s], Card.RANKS[r]);
 }
 }
 
 Random rnd = new Random();
 Card swap;
 for(int i = deck.length-1; i>=0; i=i-1){
 int pos = rnd.nextInt(i+1);
 swap = deck[pos];
 deck[pos] = deck[i];
 deck[i] = swap;
 }  
 
 Player[] players = new Player[3];
 players[0] = new ExtraCards( Arrays.copyOfRange(deck, 0, 5) );
 System.out.println("0 : " + Arrays.toString( Arrays.copyOfRange(deck, 0, 5))); 
 players[1] = new HamperLeader( Arrays.copyOfRange(deck, 5, 10) );
 System.out.println("0 : " + Arrays.toString( Arrays.copyOfRange(deck, 5, 10))); 
 players[2] = new BadPlayer( Arrays.copyOfRange(deck, 10, 15) );
 System.out.println("0 : " + Arrays.toString( Arrays.copyOfRange(deck, 10, 15))); 
 
 
 DiscardPile discardPile = new DiscardPile();
 Stack<Card> drawPile = new Stack<Card>();
 for(int i=15; i<deck.length; i++){
 drawPile.push(deck[i]);
 }
 
 System.out.println("draw pile is : " + Arrays.toString( Arrays.copyOfRange(deck, 15, deck.length) ));
 
 deck = null;  
 
 boolean win = false;
 int player = -1;    // start game play with player 0
 
 ArrayList<Player> people = new ArrayList<Player>(Arrays.asList(players));
 discardPile.add( drawPile.pop() );
 
 while( !win ){
 player = (player + 1) % players.length;
 System.out.println("player " + player);
 //System.out.println("draw pile    : " + drawPile.peek() );
 //System.out.println("discard pile : " + discardPile.top() );
 
 win = people.get(player).play(discardPile, drawPile, people,1);
 
 //System.out.println("draw pile   : " + drawPile.peek() );
 System.out.println("discard pile : " + discardPile.top() );
 
 }
 System.out.println("winner is player " + player);
 
 }
 }*/