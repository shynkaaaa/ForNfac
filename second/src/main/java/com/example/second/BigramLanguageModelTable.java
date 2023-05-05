package com.example.second;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BigramLanguageModelTable extends Application {
    private final TableView<BigramProbabilities> table = new TableView<>();

    @Override
    public void start(Stage stage) {
        BigramLanguageModel nameGenerator = new BigramLanguageModel("C:\\Users\\shynk\\IdeaProjects\\second\\src\\main\\java\\names.txt");

        TableColumn<BigramProbabilities, String> bigramCol = new TableColumn<>("Bigram");
        bigramCol.setCellValueFactory(new PropertyValueFactory<>("bigram"));

        TableColumn<BigramProbabilities, Double> probabilityCol = new TableColumn<>("Probability");
        probabilityCol.setCellValueFactory(new PropertyValueFactory<>("probability"));

        for (String bigram : nameGenerator.bigramCounts.keySet()) {
            int count = nameGenerator.bigramCounts.get(bigram);
            double probability = (double) count / nameGenerator.getTotalCount();
            table.getItems().add(new BigramProbabilities(bigram, probability));
        }

        table.getColumns().addAll(bigramCol, probabilityCol);

        VBox vbox = new VBox(table);
        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static class BigramProbabilities {
        private final String bigram;
        private final double probability;

        public BigramProbabilities(String bigram, double probability) {
            this.bigram = bigram;
            this.probability = probability;
        }

        public String getBigram() {
            return bigram;
        }

        public double getProbability() {
            return probability;
        }
    }
}