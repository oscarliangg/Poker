package coolcards.example;
import java.util.ArrayList;
import java.util.Collections;

public class Hand {
    ArrayList<Card> cards;
    ArrayList<Card> bestHand;
    int rank;
    int highCardValue;

    public Hand(){
        cards = new ArrayList<Card>();
    }

    public void AddCard(Card card){
        cards.add(card);
    }

    public void Sort(){
        Collections.sort(cards);
    }

    public void Evaluate(){
        bestHand = new ArrayList<Card>();

        //Straight Flush and Royal Flush: WORKING
        if(cards.size() >= 5) CheckForStraightFlush(0);
        if(cards.size() >= 6) CheckForStraightFlush(1);
        if(cards.size() == 7) CheckForStraightFlush(2);
        
        if(!bestHand.isEmpty()){
            Collections.sort(bestHand);
            highCardValue = bestHand.get(4).getValue();
            if(highCardValue != 14) rank = 2;
            else rank = 1;
            return;
            
        }

        //FourKind: WORKING
        nKindCheck(4);

        if(!bestHand.isEmpty()){
            rank = 3;
            return;
        }

        //FullHouse: WORKING
        FullHouse();
        if(!bestHand.isEmpty()){
            rank = 4;
            return;
        }
        


        //Flush: WORKING
        if(cards.size() >= 5) CheckForFlush(4);
        if(cards.size() >= 6) CheckForFlush(5);
        if(cards.size() == 7) CheckForFlush(6);

        if(!bestHand.isEmpty()){
            rank = 5;
            Collections.sort(bestHand);
            highCardValue = bestHand.get(4).getValue();
            return;
        }

        //Straight: WORKING
        if(cards.size() >= 5) CheckForStraight(0);
        if(cards.size() >= 6) CheckForStraight(1);
        if(cards.size() == 7) CheckForStraight(2);

        if(!bestHand.isEmpty()){
            rank = 6;
            Collections.sort(bestHand);
            highCardValue = bestHand.get(4).getValue();
            return;
        }
        
    
    }

    

    public void getHandValue(){
        for (int i = 0; i < bestHand.size(); i++){
            System.out.println(bestHand.get(i));
        }
        switch(rank){
            case 1: System.out.println("royalFlush"); break;
            case 2: System.out.println("straightFlush"); break;
            case 3: System.out.println("4Kind"); break;
            case 4: System.out.println("Full House"); break;
            case 5: System.out.println("flush"); break;
            case 6: System.out.println("straight"); break;
            default: System.out.println("unknown type 404"); break;
        }
        System.out.println("highCard: " + highCardValue);
    }

    public void nKindCheck(int n){
        for (int i = cards.size() - 1; i > -1; i--){
            Card randomCard = new Card(null, null, i);
            int checkCard = cards.get(i).getValue();
            int occurences = 0;
            ArrayList<Card> FourKindHand = new ArrayList<Card>();
            for (int j = cards.size() - 1; j > -1; j--){
                
                if (cards.get(j).getValue() == checkCard) {
                    occurences += 1;
                    FourKindHand.add(cards.get(j));
                    highCardValue = checkCard;
                }
                else{
                    randomCard = cards.get(j);
                    if (FourKindHand.size() < (5-n)) FourKindHand.add(randomCard);
                }
                // System.out.println("Check Card:" +  checkCard);
                // System.out.println("Check Card Get:" + cards.get(j).getValue());
            }  
            if (occurences == n){
                bestHand = FourKindHand;
                return;
            }
        }
    }

    public void FullHouse(){
        ArrayList<Card> FullHouseHand = new ArrayList<Card>();
        for (int i = cards.size() - 1; i > -1; i--){
            int checkCard = cards.get(i).getValue();
            int occurences = 0;
            for (int j = cards.size() - 1; j > -1; j--){
                
                if (cards.get(j).getValue() == checkCard) {
                    occurences += 1;
                    FullHouseHand.add(cards.get(j));
                    highCardValue = checkCard;
                }
            }  
            if (occurences == 3){
                break;
            }
        }
        
        for (int i = cards.size() - 1; i > -1; i--){
            int checkCard = cards.get(i).getValue();
            int occurences = 0;
            ArrayList<Card> part2 = new ArrayList<Card>();
            for (int j = cards.size() - 1; j > -1; j--){
                
                if (cards.get(j).getValue() == checkCard && checkCard != highCardValue) {
                    occurences += 1;
                    part2.add(cards.get(j));
                    highCardValue = checkCard;
                }
            }  
            if (occurences == 2){
                bestHand.addAll(part2);
                bestHand.addAll(FullHouseHand);
                return;
            }
        }
    }

    void CheckForStraightFlush(int i){
        ArrayList<Card> straightFlush = new ArrayList<Card>();
        String suitName = cards.get(i).getSuit();
        int startingValue = cards.get(i).getValue();
        straightFlush.add(cards.get(i));

        for (int j = i+1; j < i+5; j++) {
            if (startingValue + 1 == cards.get(j).getValue() && suitName.equals(cards.get(j).getSuit())){
                straightFlush.add(cards.get(j));
                startingValue++;
            }
            else{
                return;
            }
        }

        bestHand = straightFlush; return;
    }

    void CheckForFlush(int i){
        ArrayList<Card> flush = new ArrayList<Card>();
        String suitName = cards.get(i).getSuit();
        int occurences = 0;
        

        for (int j = cards.size() - 1; j > -1; j--)
        {
            if (suitName.equals(cards.get(j).getSuit()) && occurences < 5){
                flush.add(cards.get(j));
                occurences++;
            }
        }

        if (occurences == 5) bestHand = flush;
        else
            return;
    }

    void CheckForStraight(int i){
        ArrayList<Card> straight = new ArrayList<Card>();
        int startingValue = cards.get(i).getValue();
        straight.add(cards.get(i));

        for (int j = i+1; j < i+5; j++) {
            if (startingValue + 1 == cards.get(j).getValue()){
                straight.add(cards.get(j));
                startingValue++;
            }
            else{
                return;
            }
        }

        bestHand = straight; return;
    }

    @Override
    public String toString(){
        String contents = "";
        for (int i = 0; i < cards.size(); i++){
            contents += cards.get(i);
            if(i != cards.size() - 1)
                contents += ", ";
        }

        return contents;
    }
}