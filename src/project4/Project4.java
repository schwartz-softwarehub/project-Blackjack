/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.Array;

public class Project4 extends JFrame implements ActionListener {

    private static int winxpos = 0, winypos = 0;      // place window here

    private JButton shuffleButton, exitButton, newButton;
    private JButton hitButton, stayButton, splitButton, replay;
    private JButton playButton;
    private JButton bet100, bet500, bet1000;
    private static CardList theDeck = null;
    private static CardList hand = null;
    private static CardList dealer = null;
    private static CardList splitList = null;
    private static boolean startGame;
    private static boolean show, hAce, splitBoolean, splitShift, splitEnd;
    private static boolean canSplit;
    private String str, splitString, handString;
    private int handTotal, dealerTotal, splitTotal;
    private int moneyTotal, moneyBet, chip100,chipSplit;
    private boolean betting, handOver;
    ////////// three Linked Lists three lines of code above //////////
    /////////Poker Chip rectangle----------------
    private static Rectangle chipRect;
    ImageIcon chipImage = new ImageIcon("images/chip2.jpg");
    private JButton chipButton;

    private JPanel northPanel;
    private MyPanel centerPanel;
    private JPanel bottomPanel;
    private static JFrame myFrame = null;

    ////////////              MAIN      ////////////////////////
    public static void main(String[] args) {
        Project4 tpo = new Project4();
    }

    ////////////            CONSTRUCTOR   /////////////////////
    public Project4() {
        myFrame = this;
        // need a static variable reference to a JFrame object

        northPanel = new JPanel();
        northPanel.setBackground(Color.blue);
        shuffleButton = new JButton("Shuffle");
        northPanel.add(shuffleButton);
        shuffleButton.addActionListener(this);
        newButton = new JButton("New Deck");
        northPanel.add(newButton);
        newButton.addActionListener(this);
        exitButton = new JButton("Exit");
        northPanel.add(exitButton);
        exitButton.addActionListener(this);
        getContentPane().add("North", northPanel);

        centerPanel = new MyPanel();

        getContentPane().add("Center", centerPanel);
        getContentPane().setBackground(Color.green);
        //setColor of getContentPane for background of centerPanel
        //line above comment

        bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.blue);
        hitButton = new JButton("HIT");
        hitButton.addActionListener(this);
        bottomPanel.add(hitButton);
        stayButton = new JButton("STAY");
        stayButton.addActionListener(this);
        bottomPanel.add(stayButton);
        splitButton = new JButton("SPLIT");
        splitButton.addActionListener(this);
        bottomPanel.add(splitButton);
        replay = new JButton("PLAY");
        replay.addActionListener(this);
        bottomPanel.add(replay);
        /////////////////========addding bet buttons to SOUTHPANEL--------------////////////
        bet100 = new JButton("$100"); //$100 button
        bet100.addActionListener(this);
        bottomPanel.add(bet100);
        bet500 = new JButton("$500"); // $500 button
        bet500.addActionListener(this);
        bottomPanel.add(bet500);
        bet1000 = new JButton("$1000");
        bet1000.addActionListener(this);
        bottomPanel.add(bet1000);

        playButton = new JButton("Play");
        playButton.addActionListener(this);
        bottomPanel.add(playButton);
        getContentPane().add("South", bottomPanel);

        theDeck = new CardList(52);
        hand = new CardList(0);
        dealer = new CardList(0);
        splitList = new CardList(0);
        show = false;
        hAce = false;
        splitBoolean = false;
        splitShift = false;
        splitEnd = false;
        canSplit = true;
        splitString = "";
        handString = "";
        startGame = false;

        setSize(800, 700);
        setLocation(winxpos, winypos);

        setVisible(true);
        //bottomPanel.setVisible(false);//------------
        northPanel.setVisible(false);
        splitButton.setVisible(false);
        hitButton.setVisible(false);
        replay.setVisible(false);
        stayButton.setVisible(false);
        playButton.setVisible(true);
        bet100.setVisible(false);
        bet500.setVisible(false);
        bet1000.setVisible(false);

        str = "Bet";
        theDeck.shuffle();
        //deal();

        ///constructor betting
        betting = true;
        handOver = false;
        moneyBet = 0;
        moneyTotal = 1000;
        chip100 = 0;
        chipSplit=chip100;
    }

    ////////////   BUTTON CLICKS ///////////////////////////
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == exitButton) {
            dispose();
            System.exit(0);
        }
        if (e.getSource() == shuffleButton) {
            theDeck.shuffle();
            //deal();
            repaint();
        }
        if (e.getSource() == newButton) {
            theDeck = new CardList(52);
            repaint();
        }
        if (e.getSource() == hitButton && hand.getFirstCard() != null) {///////////////////////////////////hit
            if (show == false && splitShift == false) {
                Card temp;
                temp = theDeck.getFirstCard();
                theDeck.deleteCard(0);
                hand.insertCard(temp);
                System.out.println(temp.getCardNum(true));
                handTotal = addHand(false);
                canSplit = false;
                checkDeck();
                repaint();
            } else if (show == false && splitShift == true) {
                Card temp;
                temp = theDeck.getFirstCard();
                theDeck.deleteCard(0);
                splitList.insertCard(temp);
                splitTotal = addSplit(false);
                repaint();
            }
        }
        if (e.getSource() == stayButton && dealer.getFirstCard() != null && show==false) {
            if (handTotal <= 21 && splitBoolean == false) {
                show = true;
                handTotal = addHand(false);
                dealerTotal = addDealer(false);
                System.out.println(handTotal + " " + dealerTotal);
                if (dealerTotal <= 21) {
                    handString = totalCompare(handTotal, dealerTotal);
                }
                // repaint();
            } else if (splitBoolean == true && splitEnd == false) {
                splitShift = true;
                splitEnd = true;
            } else if (splitEnd == true) {
                show = true;
                handTotal = addHand(false);
                dealerTotal = addDealer(false);
                splitTotal = addSplit(false);
                if (dealerTotal <= 21) {
                    handString = totalCompare(handTotal, dealerTotal);
                    splitString = totalCompare(splitTotal, dealerTotal);
                }
            }

            repaint();
        }
        if (e.getSource() == replay) {
            if (betting == true || moneyBet == 0) {
                show = false;
                hAce = false;
                splitBoolean = false;
                splitShift = false;
                splitEnd = false;
                canSplit = true;
                hand.clear();
                dealer.clear();
                splitList.clear();
                str = "Bet";
                handString = "";
                splitString = "";
                checkDeck();
                handTotal = addHand(false);

                betting = false;
                checkDeck();
                repaint();
            } else if (betting == false && show == true) {
                show = false;
                hAce = false;
                splitBoolean = false;
                splitShift = false;
                splitEnd = false;
                canSplit = true;
                hand.clear();
                dealer.clear();
                splitList.clear();
                str = "Make a Bet";
                handString = "";
                handTotal = 0;
                splitString = "";
                checkDeck();
                // handTotal = addHand(false);
                checkDeck();
                moneyBet = 0;//--------set MoneyBet
                betting = true;
                chip100 = 0;
                chipSplit=0;
                repaint();
            } else if (hand.getFirstCard() == null) {
                show = false;
                hAce = false;
                splitBoolean = false;
                splitShift = false;
                splitEnd = false;
                canSplit = true;
                hand.clear();
                dealer.clear();
                splitList.clear();
                str = "Play";
                handString = "";
                splitString = "";

                //moneyBet=0;
                // chip100=0;
                checkDeck();
                deal();
                handTotal = addHand(false);
                checkDeck();
                // betting = false;
                repaint();
            }
        }
        if (e.getSource() == splitButton && hand.getFirstCard() != null) { ////////////tis a split button Listener///////////////////////
            int firstCard = hand.getFirstCard().getCardNum(true);
            int secondCard = hand.getFirstCard().getNextCard().getCardNum(true);
            int firstNum = hand.getFirstCard().getNum();
            int secondNum = hand.getFirstCard().getNextCard().getNum();

            if (canSplit == true) {
                if (moneyBet > moneyTotal) {
                    str = "not enough money";
                } else {

                    if (firstCard == secondCard && firstCard <= 9) {
                        Card temp;
                        splitBoolean = true;
                        temp = hand.getFirstCard();
                        hand.deleteCard(0);
                        splitList.insertCard(temp);
                        splitTotal = addSplit(false);
                        handTotal = addHand(false);
                        
                        moneyBet+=moneyBet;
                        chipSplit=chip100;
                        
                        repaint();
                    }
                }
            }
        }
        if (e.getSource() == playButton) {// ----------hit on start screen
            startGame = true;
            playButton.setVisible(false);
            bottomPanel.setVisible(true);
            northPanel.setVisible(true);
            hitButton.setVisible(true);
            splitButton.setVisible(true);
            replay.setVisible(true);
            stayButton.setVisible(true);
            bet100.setVisible(true);
            //bet500.setVisible(true);
            //bet1000.setVisible(true);

            repaint();
        }

        if (e.getSource() == bet100 && hand.getFirstCard() == null) {//________________bets $100
            if (moneyBet >= 1200) {
                str = "Exceeded Betting Max";
            } else if (moneyTotal <= 0) {
                str = "Out of money";
            } else {
                moneyBet += 100;
                moneyTotal -= 100;
                chip100++;
            }

            repaint();
        }

    }

// This routine will load an image into memory
//
    public static Image load_picture(String fname) {
        // Create a MediaTracker to inform us when the image has
        // been completely loaded.
        Image image;
        MediaTracker tracker = new MediaTracker(myFrame);

        // getImage() returns immediately.  The image is not
        // actually loaded until it is first used.  We use a
        // MediaTracker to make sure the image is loaded
        // before we try to display it.
        image = myFrame.getToolkit().getImage(fname);

        // Add the image to the MediaTracker so that we can wait
        // for it.
        tracker.addImage(image, 0);
        try {
            tracker.waitForID(0);
        } catch (InterruptedException e) {
            System.err.println(e);
        }

        if (tracker.isErrorID(0)) {
            image = null;
        }
        return image;
    }
// --------------   end of load_picture ---------------------------

    class MyPanel extends JPanel {

        public MyPanel() {

            chipRect = new Rectangle(550, 450, 100, 100);
            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseClicked(MouseEvent e) {
                    System.out.println("workign");
                }

            });
        }

        ////////////    PAINT   ////////////////////////////////
        public void paintComponent(Graphics g) {
            //
            if (startGame == false) {
                Image background = Project4.load_picture("images/openimage2.jpg");
                g.drawImage(background, 200, 100, this);

                //next two lines are test images 
                //Image dCard = Project4.load_picture("images/gbCard52.gif");
                //g.drawImage(dCard, 500, 75, this);
            } else {

                /////paint strings////// STRINGS!!!!!!
                Font f = new Font("Dialog", Font.BOLD, 22);
                Font moneyFont = new Font("Dialog", Font.BOLD, 18);
                g.setFont(f);

                g.drawString(str, 100, 75);

                g.setFont(moneyFont);
                g.drawString("MoneyBet " + "\n$" + moneyBet, 500, 300);
                g.drawString("Bank " + " \n $" + moneyTotal, 500, 350);

                ////paint deck/////
                Image dCard = Project4.load_picture("images/gbCard52.gif");
                g.drawImage(dCard, 500, 75, this);
                g.drawImage(dCard, 510, 75, this);
                g.drawImage(dCard, 500, 95, this);

                //---------------paint poker chips------////// -c-c-c-c-c-c-c-c-c-c-c-c-c-c-c-c-cc--cc
                int chip100X = 50, chip100Y = 320;
                // PokerChip pokerChip=new PokerChip(chipX, chipY);
                //chipRect=new Rectangle(chipX, chipY);
                Image chip = Project4.load_picture("images/redchip3.gif");
                for (int i = 0; i < chip100; i++) {
                    g.drawImage(chip, chip100X, chip100Y, this);

                    if (chip100Y <= 200) {
                        chip100X = 150;
                        chip100Y = 320;
                    } else {
                        chip100Y -= 20;
                    }
                }
                
                int chipSplitX=chip100X+100, chipSplitY=320;
                for(int i=0; i<chipSplit; i++){
                    g.drawImage(chip, chipSplitX, chipSplitY, this);
                    if(chipSplitY<=200){
                        chipSplitX=chip100X+200;
                        chipSplitY=320;
                    }
                    else{
                        chipSplitY-=20;
                    }
                }
                

                //////////CARD COORDINATES
                int xpos = 25, ypos = 5;
                int hx = 200, hy = 450;
                int dx = 200, dy = 75;
                int sX = 200, sY = 350;
                if (theDeck == null) {
                    return;
                }
                Card current = theDeck.getFirstCard();
                /*while (current!=null){
                 Image tempimage = current.getCardImage();
                 Image handImage =current.getCardImage();
                 g.drawImage(tempimage, xpos, ypos, this);
                 // g.drawImage(handImage,xpos+40,ypos+40,this);
                 // note: tempimage member variable must be set BEFORE paint is called
                 xpos += 80;
                 if (xpos > 700) {
                 xpos = 25; ypos += 105;
                 }
                 current = current.getNextCard();
                 } //while*/
                Card handc = hand.getFirstCard();
                ///////////paint player hand/////////////-------------------
                g.drawString(handTotal + "", 175, hy);
                g.drawString(handString, 30, hy);
                Card currentH = hand.getFirstCard();
                while (currentH != null) {
                    Image handImage = currentH.getCardImage();
                    g.drawImage(handImage, hx, hy, this);
                    currentH = currentH.getNextCard();
                    hx += 50;
                }

                ////////////paint dealer hand///////////
                Card dealerCurrent = dealer.getFirstCard();
                while (dealerCurrent != null) {
                    if (show == true) {
                        Image dealImage = dealerCurrent.getCardImage();
                        g.drawImage(dealImage, dx, dy, this);
                        dealerCurrent = dealerCurrent.getNextCard();
                        dx += 50;
                    } else {
                        Image dealImage = dealerCurrent.getCardImage();
                        Image backCard = Project4.load_picture("images/gbCard52.gif");
                        g.drawImage(backCard, dx, dy, this);
                        dx += 50;
                        g.drawImage(dealImage, dx, dy, this);
                        dx += 50;
                        dealerCurrent = null;
                    }
                }
                //////////PAINT SPLIT////////
                if (splitBoolean == true) {
                    g.drawString(splitTotal + " ", 175, 350);
                    g.drawString(splitString, 30, 350);
                    Card splitCurrent = splitList.getFirstCard();
                    while (splitCurrent != null) {
                        Image splitImage = splitCurrent.getCardImage();
                        g.drawImage(splitImage, sX, sY, this);
                        splitCurrent = splitCurrent.getNextCard();
                        sX += 50;
                    }
                }
            }
        }
    }

    public static void deal() {
        for (int i = 0; i < 1; i++) {
            Card temp;
            temp = theDeck.getFirstCard();
            theDeck.deleteCard(i);
            hand.insertCard(temp);
            System.out.println(temp.getCardNum(false));

            temp = theDeck.getFirstCard();
            theDeck.deleteCard(i);
            hand.insertCard(temp);
            System.out.println(temp.getCardNum(false));

            temp = theDeck.getFirstCard();
            theDeck.deleteCard(i);
            dealer.insertCard(temp);

            temp = theDeck.getFirstCard();
            theDeck.deleteCard(i);
            dealer.insertCard(temp);

        }
    }

    public static void dealerHand() {

    }

    ////// adds hand/-------------------------------------------------------------------------------
    public int addHand(boolean bool) {
        int total = 0;
        Card temp;
        temp = hand.getFirstCard();
        while (temp != null) {
            total += temp.getCardNum(bool);
            temp = temp.getNextCard();
        }
        if (total > 21 && bool == false) {
            //hAce = true;
            //total=0;
            return addHand(true);

        } else if (total > 21 && bool == true) {

            System.out.println("LOSER");
            handString = "BUST";
           // betting=true;

            if (splitBoolean == true) {
                splitShift = true;
            }
            if (splitBoolean == false) {
                show = true;
                //betting = true;
            }
        }
        return total;

    }

    ///////adds dealer hand
    public int addDealer(boolean aceTest) {
        int total = 0;
        boolean bool = false;
        Card temp;
        temp = dealer.getFirstCard();
        while (temp != null) {
            total += temp.getCardNum(aceTest);
            temp = temp.getNextCard();
        }
        while (bool == false) {
            if (total > 21 && aceTest == false) {

                return addDealer(true);
            }

            if (total > 21) {
                str = "DEALER BUST";
                bool = true;
                moneyTotal += (2 * moneyBet);
            } else if (total >= 17) {
                bool = true;
                str = total + "";

            } else {
                temp = theDeck.getFirstCard();
                theDeck.deleteCard(0);
                dealer.insertCard(temp);

                total += temp.getCardNum(aceTest);

            }

        }
        return total;
    }

    ////method COMPARES THE TWO TOTALS IN PARAMATERS
    public String totalCompare(int hand, int dealer) {
        String theString;
        if (hand > dealer && hand <= 21) {
            theString = "this hand won";
            System.out.println(hand + " " + dealer);
            moneyTotal += (2 * moneyBet);
        } else if (hand == dealer) {
            theString = "tis a push";
            moneyTotal += moneyBet;
        } else {
            theString = "dealer wins";
            System.out.println(hand + " " + dealer);
        }
        return theString;

    }

    ///////adds split hand///////
    public int addSplit(boolean bool) {
        int total = 0;
        Card temp;
        temp = splitList.getFirstCard();
        while (temp != null) {
            total += temp.getCardNum(bool);
            temp = temp.getNextCard();
        }
        if (total > 21 && bool == false) {
            //hAce = true;
            //total=0;
            return addSplit(true);

        } else if (total > 21 && bool == true) {

            System.out.println("LOSER");
            splitString = "BUST";
            show = true;
            handOver = true;
            // betting = true;
        }
        return total;

    }

    public void checkDeck() { //check if theDeck needs to be reshuffled
        if (theDeck.getFirstCard() == null || theDeck.getFirstCard().getNextCard() == null
                || theDeck.getFirstCard().getNextCard().getNextCard() == null) {
            theDeck = new CardList(52);
            theDeck.shuffle();
        }
    }

}    // End Of class Project4

