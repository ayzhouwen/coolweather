package com.coolweather.app.util;

/**
 * Created by admin on 2017/1/6.
 */

public class test {


  public   boolean testb(){
        int k=2;
        int j=1;
        if (k>j){
            System.out.println("k:"+k);
            return  true;
        }
        return  false;
    }
    void testv(){
        int k=2;
        int j=1;
        if (k>j){
            System.out.println("k:"+k);
            return ;
        }
        return  ;
    }

    int testi(){
        int k=2;
        int j=1;
        if (k>j){
            System.out.println("k:"+k);
            return k;
        }
        return  j;
    }
public static void main (String arr[]){
    test st=new test();
    boolean y= st.testb();

    System.out.println("y:"+y);
    st.testv();
    int ti=st.testi();
    System.out.println("ti:"+ti);
}
}
