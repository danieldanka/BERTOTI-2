package org.example.ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Game;

import java.util.List;
import java.util.Optional;

public class GameUI extends Application {

    private final GameService service = new GameService();
    private final TableView<Game> table = new TableView<>();

    @Override
    public void start(Stage stage) {

        Label title = new Label("Sistema de Jogos");

        TableColumn<Game, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Game, String> nameCol = new TableColumn<>("Nome");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Game, String> genreCol = new TableColumn<>("Gênero");
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));

        TableColumn<Game, String> platformCol = new TableColumn<>("Plataforma");
        platformCol.setCellValueFactory(new PropertyValueFactory<>("platform"));

        TableColumn<Game, Integer> yearCol = new TableColumn<>("Ano");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));

        table.getColumns().addAll(idCol, nameCol, genreCol, platformCol, yearCol);

        Button loadBtn = new Button("Carregar");
        loadBtn.setOnAction(e -> loadGames());

        Button addBtn = new Button("Adicionar");
        addBtn.setOnAction(e -> addGame());

        Button editBtn = new Button("Editar");
        editBtn.setOnAction(e -> editGame());

        Button deleteBtn = new Button("Deletar");
        deleteBtn.setOnAction(e -> deleteGame());

        VBox layout = new VBox(10, title, loadBtn, addBtn, editBtn, deleteBtn, table);

        Scene scene = new Scene(layout, 700, 500);

        stage.setTitle("Games CRUD");
        stage.setScene(scene);
        stage.show();
    }

    private void loadGames() {
        table.setItems(FXCollections.observableArrayList(service.getGames()));
    }

    private void addGame() {
        Game game = showGameDialog(null);
        if (game != null) {
            service.addGame(game);
            loadGames();
        }
    }

    private void editGame() {
        Game selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Game updated = showGameDialog(selected);
            if (updated != null) {
                service.updateGame(selected.getId(), updated);
                loadGames();
            }
        }
    }

    private void deleteGame() {
        Game selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.deleteGame(selected.getId());
            loadGames();
        }
    }

    private Game showGameDialog(Game game) {

        Dialog<Game> dialog = new Dialog<>();
        dialog.setTitle("Cadastro de Jogo");

        Label nameLabel = new Label("Nome:");
        TextField nameField = new TextField();

        Label genreLabel = new Label("Gênero:");
        TextField genreField = new TextField();

        Label platformLabel = new Label("Plataforma:");
        TextField platformField = new TextField();

        Label yearLabel = new Label("Ano:");
        TextField yearField = new TextField();

        if (game != null) {
            nameField.setText(game.getName());
            genreField.setText(game.getGenre());
            platformField.setText(game.getPlatform());
            yearField.setText(String.valueOf(game.getReleaseYear()));
        }

        VBox layout = new VBox(10,
                nameLabel, nameField,
                genreLabel, genreField,
                platformLabel, platformField,
                yearLabel, yearField
        );

        dialog.getDialogPane().setContent(layout);

        ButtonType saveButton = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == saveButton) {
                try {
                    Game g = new Game();

                    g.setName(nameField.getText());
                    g.setGenre(genreField.getText());
                    g.setPlatform(platformField.getText());
                    g.setReleaseYear(Integer.parseInt(yearField.getText()));

                    return g;

                } catch (Exception e) {
                    System.out.println("Erro ao converter dados");
                }
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }

    public static void main(String[] args) {
        launch();
    }
}