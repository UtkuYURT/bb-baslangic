package com.example.olmazsa_soverim3.models;

import java.util.ArrayList;

public class WordList {
    private ArrayList<Deneme> wordList = new ArrayList<>();

    public WordList(ArrayList<Deneme> wordList) {
        this.wordList = wordList;
    }

    public WordList() {
    }

    public ArrayList<Deneme> getWordList() {
        return wordList;
    }

    public void setWordList(ArrayList<Deneme> denemeArrayList) {
        this.wordList = denemeArrayList;
    }
}
