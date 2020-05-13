/*
 * Copyright (c) 2020. Mohamed Essam Abdelfattah
 */

package sample;

import java.util.ArrayList;

public class Word {
    private String word;
    private ArrayList<PairItem<String, Integer>> wordIndex;
    private ArrayList<PairItem<String, Integer>> wordFrequency;

    public Word(String word) {
        this.word = word;
        wordIndex = new ArrayList<>();
        wordFrequency = new ArrayList<>();
    }

    public ArrayList<PairItem<String, Integer>> getWordFrequency() {
        return wordFrequency;
    }

    public ArrayList<PairItem<String, Integer>> getWordIndex() {
        return wordIndex;
    }

    public String getWord() {
        return word;
    }

    public void addWordIndex(String filename, int location) {

        boolean found = false;
        for (PairItem<String, Integer> pair :
                wordFrequency) {
            if (pair.getKey().equals(filename)) {
                pair.updateValue(pair.getValue() + 1);
                found = true;
                break;
            }
        }
        wordIndex.add(new PairItem<>(filename, location));
        //System.out.println("Adding word "+word+" of "+filename);
        if (!found) {
            wordFrequency.add(new PairItem<>(filename, 1));
        }
    }

    public void printIndexes() {
        System.out.println(word + " found in :");
        for (PairItem<String, Integer> pair :
                wordIndex) {
            System.out.println("Document : " + pair.getKey() + " at location : " + pair.getValue());
        }
    }

    @Override
    public boolean equals(Object obj) {
        Word temp = null;
        if (this == obj) {
            return true;
        }
        if (obj instanceof Word) {

            temp = (Word) obj;
            return temp.getWord().equals(this.word);
        }
        return false;
    }

    public void printFrequencies() {
        System.out.println(word + " found in :");
        for (PairItem<String, Integer> pair :
                wordFrequency) {
            System.out.println("Document : " + pair.getKey() + " by : " + pair.getValue());
        }
    }
}

