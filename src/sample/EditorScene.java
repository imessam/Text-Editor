/*
 * Copyright (c) 2020. Mohamed Essam Abdelfattah
 */

package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.*;


public class EditorScene {
    static Stage window;
    int tabCount = 2;
    Indexer indexer;

    @FXML
    private TabPane tabPane;
    @FXML
    private BorderPane borderPane;

    public static void getStage(Stage temp) {
        window = temp;
    }

    public void initialize() {
        indexer = new Indexer();
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        MenuItem save = new MenuItem("Save");
        MenuItem load = new MenuItem("Load");
        MenuItem find = new MenuItem("Find");
        MenuItem newFile = new MenuItem("New");
        tabPane.getTabs().add(new Tab("New", new TextArea()));
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        fileMenu.getItems().add(newFile);
        fileMenu.getItems().add(save);
        fileMenu.getItems().add(load);
        editMenu.getItems().add(find);
        menuBar.getMenus().addAll(fileMenu, editMenu);
        borderPane.setTop(menuBar);


        newFile.setOnAction(event -> addTab(true, null));


        save.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text file", "*.txt"));
            fileChooser.setInitialFileName(tabPane.getSelectionModel().getSelectedItem().getText());
            TextArea textArea = (TextArea) tabPane.getSelectionModel().getSelectedItem().getContent();
            File file = fileChooser.showSaveDialog(window);
            if (file != null) {
                index(saveFile(file, textArea.getText()));
                tabPane.getSelectionModel().getSelectedItem().setText(file.getName());
            }
        });
        load.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text file", "*.txt"));
            File file = fileChooser.showOpenDialog(window);
            if (file != null) {
                Pair<String, String> temp = loadFile(file);
                addTab(false, temp.getValue());
                TextArea textArea = (TextArea) tabPane.getSelectionModel().getSelectedItem().getContent();
                textArea.setText(temp.getKey());
                tabPane.getSelectionModel().getSelectedItem().setText(temp.getValue());
                index(temp);
            }
        });
        find.setOnAction(event -> {

        });
    }

    private void index(Pair<String, String> key) {
        String replace = key.getKey().replace('\n', ' ');
        String[] s1 = replace.split(" ");
        int count = 0;
        for (String s : s1
        ) {
            indexer.addWord(s, key.getValue(), count);
            count++;
        }
        indexer.printWords();
    }

    private void addTab(boolean isNew, String name) {
        Tab tab;
        boolean found = false;
        if (isNew) {
            tab = new Tab("New " + tabCount, new TextArea());
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
            tabCount++;
        } else {
            for (Tab temp : tabPane.getTabs()) {
                if (temp.getText().equals(name)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                tab = new Tab("New " + tabCount, new TextArea());
                tabPane.getTabs().add(tab);
                tabPane.getSelectionModel().select(tab);
            }
        }
    }

    private Pair<String, String> loadFile(File file) {
        String line;
        StringBuilder s = new StringBuilder();
        try {
            if (file.exists()) {
                System.out.println(file.getName());
                try (BufferedReader bw = new BufferedReader(new FileReader(file))) {
                    while ((line = bw.readLine()) != null) {
                        s.append(line);
                        s.append("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return new Pair<>(s.toString(), file.getPath());
    }

    private Pair<String, String> saveFile(File file, String text) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(text);
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        boolean found = false;
//        StringBuilder filename;
//        int i = 0;
//        while (!found) {
//            if (!file.exists()) {
//                found = true;
//                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
//                    bw.write(text);
//                    System.out.println(text);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                System.out.println("File with the same name found");
//                i++;
//                filename = new StringBuilder(file.getName().substring(0, file.getName().indexOf(".txt")));
//                filename.append(i).append(".txt");
//                file.renameTo(new File(file.getAbsolutePath().substring(0, file.getAbsolutePath()
//                        .indexOf(file.getName())) + filename.toString()));
//            }
//
//
//        }
        return new Pair<>(text, file.getPath());
    }
}
