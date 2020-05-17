/*
 * Copyright (c) 2020. Mohamed Essam Abdelfattah
 */

package sample;

import java.util.ArrayList;

public class Word implements Comparable<Word> {
    private final String word;
    private final ArrayList<PairItem<String, Integer>> wordIndex;
    private final ArrayList<PairItem<String, Integer>> wordFrequency;

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
        //  if (!locationFound(filename, location)) {
        //int found ;
//            for (PairItem<String, Integer> pair :
//                    wordFrequency) {
//                if (pair.getKey().equals(filename)) {
//                    pair.updateValue(pair.getValue() + 1);
//                    found = true;
//                    break;
//                }
//            }

        if (!locationFound(filename, location)) {
            wordIndex.add(new PairItem<>(filename, location));
        }
        //System.out.println("Adding word "+word+" of "+filename);
//            if (!found) {
//                wordFrequency.add(new PairItem<>(filename, 1));
//            }
        // }
    }

    private boolean locationFound(String filename, int location) {
        PairItem<String, Integer> temp = new PairItem<>(filename, location);
        for (PairItem<String, Integer> pair :
                wordIndex) {
            if (pair.equals(temp)) {
                return true;
            }
        }
        //return Collections.binarySearch(wordIndex, new PairItem<>(filename, location)) >= 0;
        return false;
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
        Word temp;
        if (this == obj) {
            return true;
        }
        if (obj instanceof Word) {

            temp = (Word) obj;
            return temp.getWord().equalsIgnoreCase(this.word);
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


    @Override
    public int compareTo(Word o) {
        return this.word.compareTo(o.word);
    }
}

