/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package project4;

/*****************************************************************
   Class CardList, A Linked list of playing cards
   May be placed in a file named CardList.java

   Note : This class can be used to create a 'hand' of cards
   Just Create another CardList object, and delete cards from
   'theDeck' and insert the into the new CardList object

******************************************************************/

class CardList {
  private Card firstcard = null;
  private int numcards=0;

  public  CardList(int num) {
    numcards = num;   //set numcards in the deck
    for (int i = 0; i < num; i++) {  // load the cards
      Card temp = new Card(i);
      if (firstcard != null) {
        temp.setNext(firstcard);
      }
      firstcard = temp;
    }
  }

  public Card getFirstCard() {
      return firstcard;
  }

  public Card deleteCard(int cardnum) {
    Card target, targetprevious;

    if (cardnum > numcards)
      return null;   // not enough cards to delete that one
    else
      numcards--;

    target = firstcard;
    targetprevious = null;
    while (cardnum-- > 0) {
        targetprevious = target;
        target = target.getNextCard();
        if (target == null) return null;  // error, card not found
    }
    if (targetprevious != null)
      targetprevious.setNext(target.getNextCard());
    else
      firstcard =  target.getNextCard();
    return target;
  }

  public void insertCard(Card target) {
    numcards++;
    if (firstcard != null)
      target.setNext(firstcard);
    else
      target.setNext(null);
    firstcard =  target;
  }

  public void shuffle() {
    for ( int i = 0; i < 300; i++) {
      int rand = (int)(Math.random() * 100) % numcards;
      Card temp = deleteCard(rand);
      if (temp != null) insertCard(temp);
    }  // end for loop
  }  
  // end shuffle
  
  public void clear(){
      Card temp=getFirstCard();
      while(temp!=null){
          deleteCard(0);
          temp=temp.getNextCard();
      }
  }


}    // end class CardList

