


import java.io.*;

class Game{

    public int elemento;
    public Game prox;

    public Game(Game game) {
        elemento = game.elemento;
        prox = null;

