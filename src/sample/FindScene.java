/*
 * Copyright (c) 2020. Mohamed Essam Abdelfattah
 */

package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class FindScene {

    static String filename;
    static PairItem<Integer, Integer> location;
    private static Stage findWindow;
    private static Indexer indexer;
    @FXML
    TextField wordFind;
    @FXML
    Button findBTN;


    public void initialize() {
        findBTN.setOnAction(event -> find());
    }

    public void setIndexer(Indexer temp) {
        FindScene.indexer = temp;
    }

    public void setFilename(String filename) {
        FindScene.filename = filename;
    }

    public void display() throws Exception {
        Parent root = FXMLLoader.load(FindScene.class.getResource("findScene.fxml"));
        findWindow = new Stage();
        findWindow.setTitle("Find");
        findWindow.setScene(new Scene(root, 313, 131));
        findWindow.showAndWait();
    }

    private void find() {
        String text = wordFind.getText();
        for (Word temp :
                indexer.getWords()) {
            if (temp.getWord().equals(text)) {
                temp.printIndexes();
                for (PairItem<String, Integer> tempName :
                        temp.getWordIndex()) {
                    if (tempName.getKey().equals(filename)) {
                        location = new PairItem<>(tempName.getValue(), tempName.getValue() + text.length());
                        break;
                    }
                }
                break;
            }
        }
        findWindow.close();
    }

    public PairItem<Integer, Integer> getLocation() {
        return location;
    }
}
