package com.pluralsight;

import java.util.ArrayList;


public class ShoppingCart {
 private ArrayList cartItems = new ArrayList();
 private double dblOrderTotal ;

 public int getLineItemCount() {
  return cartItems.size();
 }

 public void addCartItem(Book book, int quantity) {
   CartItem cartItem = new CartItem(book, quantity);
   cartItems.add(cartItem);
   calculateOrderTotal();
 }

 public void addCartItem(CartItem cartItem) {
  cartItems.add(cartItem);
 }

 public CartItem getCartItem(int iItemIndex) {
  CartItem cartItem = null;
  if(cartItems.size()>iItemIndex) {
   cartItem = (CartItem) cartItems.get(iItemIndex);
  }
  return cartItem;
 }

 public ArrayList getCartItems() {
  return cartItems;
 }
 public void setCartItems(ArrayList cartItems) {
  this.cartItems = cartItems;
 }
 public double getOrderTotal() {
  return dblOrderTotal;
 }
 public void setOrderTotal(double dblOrderTotal) {
  this.dblOrderTotal = dblOrderTotal;
 }

 protected void calculateOrderTotal() {
  double dblTotal = 0;
  for(int counter=0;counter<cartItems.size();counter++) {
   CartItem cartItem = (CartItem) cartItems.get(counter);
   dblTotal+=cartItem.getTotalCost();

  }
  setOrderTotal(dblTotal);
 }

}
