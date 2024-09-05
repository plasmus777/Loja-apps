package com.github.plasmus777.view;

public abstract class View{
    public abstract void show();

    public void cleanTerminal(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
