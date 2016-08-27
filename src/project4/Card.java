/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package project4;

import java.awt.Image;

/*****************************************************************
   Class Card, the derived class each card is one object of type Card
   May be placed in a file named Card.java
******************************************************************/

class Card extends Link {
  private Image cardimage;
  private int num,cardValue;
  private boolean aceValue;

  public Card (int cardnum) {
    cardimage = Project4.load_picture("images/gbCard" + cardnum + ".gif");
    // code ASSUMES there is an images sub-dir in your project folder
    num=cardnum;
    aceValue=true;
    if (cardimage == null) {
      System.out.println("Error - image failed to load: images/gbCard" + cardnum + ".gif");
      System.exit(-1);
    }
  }
  public Card getNextCard() {
    return (Card)next;
  }
  public Image getCardImage() {
    return cardimage;
  }
  
  public int getNum(){
      return num;
  }
  public void setCardNum(){
      cardValue=1;
  }
  public int getCardNum(boolean aceVal){
      //int cardValue;
      //1-9 CLUBS
      if(num==1 || num==2 || num==3 || num==4 || num==5 || num==6 || num==7 || num==8){
          cardValue=num+1;
      }
      //10sCLUBS
      else if(num==11 || num==12   || num==10 || num==9){
          cardValue=10;
      }
      //ACES
      else if(num==0 ||num==13 || num==26 || num==39){
          if(aceVal==false){
          cardValue=11;
          }
          else{
              cardValue=1;
          }
      }
      //1=10 DIAMONDS
      else if(num==14 || num ==15 || num==16 || num==17 || num==18 || num==19 || num==20 || num==21){
          cardValue=num-10-2;
      }
        //10S DIAMONDS
      else if(num==22 || num==23 || num==24 || num==25){
          cardValue=10;
      }
      //1-9 HEARTS
      else if(num==27 || num==28 || num==29 || num==30 ||  num==31 || num== 32 || num==33 || num==34){
          cardValue=num-25;
      }
      //10s HEARTS
      else if(num==35 || num==36 || num==37 || num==38){
          cardValue=10;
      }
      //1-9 SPADES
      else if(num==40 || num==41 || num==42 || num==43 || num==44 || num==45 || num==46 || num==47){
          cardValue=num-38;
          
      }
      //10s SPADES 
      else if(num== 48 || num==49 || num==50 || num==51){
          cardValue=10;
          
      }
      else
          cardValue=-1;

      return cardValue;
  }
 
  
}  //end class Card
