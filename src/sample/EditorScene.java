/*
 * Copyright (c) 2020. Mohamed Essam Abdelfattah
 */

package sample;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.*;
import java.time.LocalTime;


public class EditorScene {
    static Stage window;
    FindScene findScene;
    LoadingScene loadingScene;
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
        loadingScene = new LoadingScene();
        indexer = new Indexer();
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        MenuItem save = new MenuItem("Save");
        MenuItem open = new MenuItem("Open");
        MenuItem load = new MenuItem("Load");
        MenuItem find = new MenuItem("Find");
        MenuItem newFile = new MenuItem("New");
        tabPane.getTabs().add(new Tab("New", new TextArea()));
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        fileMenu.getItems().addAll(newFile, open, save, load);
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

        open.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text file", "*.txt"));
            File file = fileChooser.showOpenDialog(window);

            if (file != null) {
                try {
                    loadingScene.display(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Pair<String, String> temp = loadFile(file);
                addTab(false, temp.getValue());
                TextArea textArea = (TextArea) tabPane.getSelectionModel().getSelectedItem().getContent();
                textArea.setText(temp.getKey());
                tabPane.getSelectionModel().getSelectedItem().setText(temp.getValue());
                index(temp);
                //indexer.printWords();
            }
        });

        load.setOnAction(event -> {
            File[] files = new DirectoryChooser().showDialog(window).listFiles((dir, name) -> name.endsWith(".txt"));
            if (files != null) {
                try {
                    loadingScene.display(files.length);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Task<Void> task = new Task<>() {
                    @Override
                    protected Void call() {
                        System.out.println(LocalTime.now());
                        for (File file :
                                files) {
                            index(loadFile(file));
                        }
                        System.out.println(LocalTime.now());
                        return null;
                    }
                };
                Thread thread = new Thread(task);
                thread.setDaemon(true);
                thread.start();
                //indexer.printWords();
            }

        });

        find.setOnAction(event -> {
            findScene = new FindScene();
            findScene.setIndexer(indexer);
            findScene.setFilename(tabPane.getSelectionModel().getSelectedItem().getText());
            try {
                findScene.display();
            } catch (Exception e) {
                e.printStackTrace();
            }
            PairItem<Integer, Integer> location = findScene.getLocation();
            TextArea textArea = (TextArea) tabPane.getSelectionModel().getSelectedItem().getContent();
            System.out.println(textArea.getText().length());
            textArea.selectRange(location.getKey(), location.getValue());
        });
    }

    private void index(Pair<String, String> key) {
        String key1 = key.getKey();
        String value = key.getValue();
        int index, count = 0;

        Platform.runLater(() -> {
            loadingScene.setFileName(value);
            loadingScene.setLoadingBar();
        });
        for (String s1 :
                key1.split("\n")) {
            index = 0;
            for (String s2 :
                    s1.split(" ")) {
                index = s1.indexOf(s2, index);
                indexer.addWord(s2, value, index + count);
                index += s2.length() + 1;
                //System.out.println(key1.substring(key1.indexOf(s2)+count,s2.length()+key1.indexOf(s2)+count));
            }
            count = s1.length() + 1;
        }
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
//        for (int i = 0; i <s.toString().length() ; i++) {
//            System.out.print(i+" : ");
//            for (int j = i; j <s.toString().indexOf(" ") ; j++) {
//                System.out.print(s.charAt(j));
//                i++;
//            }
//            System.out.println("\n");
//        }
        return new Pair<>(s.toString(), file.getPath());
    }

    private Pair<String, String> saveFile(File file, String text) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(text);
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Pair<>(text, file.getPath());
    }
}
