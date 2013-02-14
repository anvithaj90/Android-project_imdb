package com.app.fb_imdb;

import java.io.Serializable;


public class ABC implements Serializable
{
   private static final long serialVersionUID = 1L;
   String[][] str;
   private static ABC singletonObject;
    public static ABC getSingletonObject() {
        if (singletonObject == null) {
            singletonObject = new ABC ();
        }
        return singletonObject;
    }
   public void setString(String[][] str)
   {
      this.str = str;
   }
   public String[][] getString()
   {
      return str;
   }
}

