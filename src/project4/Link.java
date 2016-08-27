/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package project4;

/*****************************************************************
   Class Link, the base class for a link list of playing cards
   May be placed in a file named Link.java

******************************************************************/
class Link {
  protected Link next;

  public Link getNext() { return next; }
  public void setNext(Link newnext) { next = newnext; }

}  // end class Link

