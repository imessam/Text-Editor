/*
 * Copyright (c) 2020. Mohamed Essam Abdelfattah
 */

package sample;

import java.util.ArrayList;

public class Indexer {
    private ArrayList<Word> words;


    public Indexer() {
        words = new ArrayList<>();
    }


    public ArrayList<Word> getWords() {
        return words;
    }

    public void addWord(String s, String filename, int location) {
        Word word;
        boolean found = false;
        for (Word temp :
                words) {
            if (temp.getWord().equals(s)) {
                temp.addWordIndex(filename, location);
                found = true;
                break;
            }
        }
        if (!found) {
            word = new Word(s);
            word.addWordIndex(filename, location);
            words.add(word);
            // System.out.println("Adding word "+s);
        }
    }

    public void printWords() {
        for (Word word :
                words) {
            word.printIndexes();
            System.out.println("==============================================================");
            word.printFrequencies();
            System.out.println("==============================================================");
        }
    }
}
