import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by chufuze on 2017-08-14.
 */
public class DiscardHighPoints extends Player {
  public DiscardHighPoints(Card[] cards){this.hand = new ArrayList<Card>(Arrays.asList(cards));}
  public boolean play(DiscardPile       discardPile,
                      Stack<Card> drawPile,
                      ArrayList<Player> players, int next){
    if(drawPile.empty()){return true;}
    int maxPoint=-1;
    int maxIndex = -1;
    ArrayList<Card> out = new ArrayList<Card>();
    for(int i=0; i<getSizeOfHand(); i++){
      if((hand.get(i).getRank()==14?1:(hand.get(i).getRank()>10?10:(hand.get(i).getRank()==8?50:((hand.get(i).getRank()==2 || hand.get(i).getRank()==4)?25:(hand.get(i).getRank()==7?20:(hand.get(i).getRank())))))) > maxPoint){
        maxPoint = (hand.get(i).getRank()==14?1:(hand.get(i).getRank()>10?10:(hand.get(i).getRank()==8?50:((hand.get(i).getRank()==2 || hand.get(i).getRank()==4)?25:(hand.get(i).getRank()==7?20:(hand.get(i).getRank()))))));
        maxIndex = i;
      }
    }
    boolean tester = true;
    for(int i=0; i<getSizeOfHand(); i++){
      if((discardPile.top().getRank() == hand.get(maxIndex).getRank()) || (discardPile.top().getSuit().equals(hand.get(maxIndex).getSuit()))){
        discardPile.add(hand.remove(maxIndex));
        tester = false;
        break;
      }
    }
    if(tester) {
      for (int j = 0; j < getSizeOfHand(); j++) {
        if (hand.get(j).getSuit().equals(hand.get(maxIndex)) && (discardPile.top().getRank() == hand.get(j).getRank())) {
          discardPile.add(hand.remove(j));
          tester = false;
          break;
        }
      }
    }
    if(tester){
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
        return true;
      }
      discardPile.add(out.remove(0));
      hand.addAll(out);
      
    }
    if( this.hand.size() == 0 ){return true;}
    return false;
  }
}
