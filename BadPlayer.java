import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;

public class BadPlayer extends Player{
  
  public BadPlayer(Card[] cards){this.hand = new ArrayList<Card>(Arrays.asList(cards));}
  
  /* play a card */ 
  public boolean play(DiscardPile       discardPile, 
                      Stack<Card>       drawPile, 
                      ArrayList<Player> players,int next)
  {
    if(drawPile.empty()){return true;}
    ArrayList<Card> out = new ArrayList<Card>();
    for(int i=0; i<hand.size(); i++){
      if(hand.get(i).getRank() == discardPile.top().getRank() || hand.get(i).getSuit().equals(discardPile.top().getSuit())){
        out.add(hand.remove(i));
      }
    }
    while(out.size()==0 && !drawPile.empty()){
      hand.add(drawPile.pop());
      for(int i=0; i<hand.size(); i++){
        if(hand.get(i).getRank() == discardPile.top().getRank() || hand.get(i).getSuit().equals(discardPile.top().getSuit())){
          out.add(hand.remove(i));
        }
      }
    }
    
    if(out.size()==0){
      System.out.println(Arrays.toString(hand.toArray()));
      return true;
    }
    discardPile.add(out.remove(0));
    hand.addAll(out);
    if( this.hand.size() == 0 ){
      System.out.println(Arrays.toString(hand.toArray()));
      return true;
    }
    return false;
  }
  
  //$57.94  
  
  
}