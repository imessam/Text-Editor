/*
 * Copyright (c) 2020. Mohamed Essam Abdelfattah
 */

package sample;

public class PairItem<T, K> {

    private T Key;
    private K Value;

    public PairItem(T key, K value) {
        Key = key;
        Value = value;
    }

    public T getKey() {
        return Key;
    }

    public K getValue() {
        return Value;
    }

    public void updateValue(K newValue) {
        Value = newValue;
    }
}
