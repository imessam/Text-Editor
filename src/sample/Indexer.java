/*
 * Copyright (c) 2020. Mohamed Essam Abdelfattah
 */

package sample;

import java.util.ArrayList;
import java.util.Collections;

public class Indexer {
    private final ArrayList<Word> words;


    public Indexer() {
        words = new ArrayList<>();
    }


    public ArrayList<Word> getWords() {
        return words;
    }

    public void addWord(String s, String filename, int location) {
        Word word = new Word(s);
        int loc;
//        for (Word temp :
//                words) {
//            if (temp.getWord().equals(s)) {
//                temp.addWordIndex(filename, location);
//                found = true;
//                break;
//            }
//        }
        if ((loc = findWord(word)) >= 0) {
            words.get(loc).addWordIndex(filename, location);
        } else {
            word.addWordIndex(filename, location);
            words.add(word);
            Collections.sort(words);
        }
//        found=words.contains(word);
//        if (!found) {
//            //word = new Word(s);
//            word.addWordIndex(filename, location);
//            words.add(word);
//            Collections.sort(words);
//            // System.out.println("Adding word "+s);
//        }else {
//            words.get(words.indexOf(word)).addWordIndex(filename,location);
//        }
    }

    private int findWord(Word word) {
        return Collections.binarySearch(words, word);
    }

    public void printWords() {
        for (Word word :
                words) {
            word.printIndexes();
            System.out.println("==============================================================");
            // word.printFrequencies();
            System.out.println("==============================================================");
        }
    }
}
