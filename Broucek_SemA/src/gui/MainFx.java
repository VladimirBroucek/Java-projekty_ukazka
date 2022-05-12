/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import moduly.IObyvatele;
import moduly.Obec;
import moduly.Obyvatele;
import moduly.enumKraj;
import moduly.enumPozice;
import struktura.AbstrDoubleList;

/**
 *
 * @author ladik
 */
public class MainFx extends Application {

    private IObyvatele obyvatel = new Obyvatele();
    private final ObservableList<Obec> obce = FXCollections.observableArrayList();
    private ListView<Obec> list = new ListView<>();

    private static final String BIN_FILE = "zaloha.bin";
    private static final String CSV_FILE = "kraje.csv";

    ComboBox<enumKraj> comboKraje = new ComboBox();
    ComboBox<enumPozice> comboPozic = new ComboBox();

    TextField txtPrumerKraje = new TextField();
    TextField krajVeKteremSeMeriPrumer = new TextField();

    ComboBox<enumKraj> zobrazKraje;
    private final ObservableList<enumKraj> seznamKraju = FXCollections.observableArrayList(enumKraj.values());
    private final ObservableList<enumPozice> seznamPozic = FXCollections.observableArrayList(enumPozice.values());

    @Override
    public void start(Stage primaryStage) throws Exception {
        comboKraje.setItems(seznamKraju);
        comboPozic.setItems(seznamPozic);

        VBox buttonPane = new VBox();
        buttonPane.setPadding(new Insets(10, 30, 30, 30));
        buttonPane.setOpacity(10);
        buttonPane.setSpacing(7);

        Label ovladani = new Label();
        ovladani.setText("Ovladání seznamu");
        buttonPane.getChildren().add(ovladani);

        Button btnZobrazObec = new Button("zobraz obec");
        buttonPane.getChildren().add(btnZobrazObec);
        btnZobrazObec.setOnAction(dejObec);

        Button btnOdeberObec = new Button("odeber obec");
        buttonPane.getChildren().add(btnOdeberObec);
        btnOdeberObec.setOnAction(odebratObec);

        Button btnZrusKraj = new Button("Zruš kraj");
        buttonPane.getChildren().add(btnZrusKraj);
        btnZrusKraj.setOnAction(zrusKraj);

        Button btnPrumerKraje = new Button("Pruměr kraje");
        buttonPane.getChildren().add(btnPrumerKraje);
        btnPrumerKraje.setOnAction(zjistiPrumerKraje);

        txtPrumerKraje.setPromptText("průměrný počet obyvatel");
        txtPrumerKraje.setDisable(true);
        buttonPane.getChildren().add(txtPrumerKraje);

        Button btnzobrazNadPrumer = new Button("Zobraz nadprůměr");
        buttonPane.getChildren().add(btnzobrazNadPrumer);
        btnzobrazNadPrumer.setOnAction(dejNadPrumer);

//-------------------------------------------------------------------------------------------------------------------
        HBox spodniButtonPane = new HBox();
        spodniButtonPane.setPadding(new Insets(10, 30, 10, 10));
        spodniButtonPane.setSpacing(5);

        Button btnGeneruj = new Button("Generuj");
        spodniButtonPane.getChildren().add(btnGeneruj);
        btnGeneruj.setOnAction(generujObce);

        Button btnZalohuj = new Button("Zalohuj");
        spodniButtonPane.getChildren().add( btnZalohuj);
        btnZalohuj.setOnAction(zalohujDoBin);

        Button btnObnov = new Button("Obnov");
        spodniButtonPane.getChildren().add(btnObnov);
        btnObnov.setOnAction(obnovZBin);
        
        Button btnClear = new Button("Clear");
        spodniButtonPane.getChildren().add(btnClear);
        btnClear.setOnAction(clear);

        Button btnPridejObec = new Button("Přidání nové obce");
        spodniButtonPane.getChildren().add(btnPridejObec);
        btnPridejObec.setOnAction(pridejNovouObec);
        
        Button btnNactiZeSouboru = new Button("Načti ze souboru");
        spodniButtonPane.getChildren().add(btnNactiZeSouboru);
        btnNactiZeSouboru.setOnAction(nacitaniZeSouboru);

        zobrazKraje = new ComboBox<>();
        spodniButtonPane.getChildren().add(zobrazKraje);
        zobrazKraje.setItems(seznamKraju);
        zobrazKraje.getSelectionModel().selectFirst();
        zobrazKraje.valueProperty().addListener((ObservableValue<? extends enumKraj> observable, enumKraj oldValue, enumKraj newValue) -> {
            naplneniListu();
        });

        list.setItems(obce);

        BorderPane root = new BorderPane();
        root.setRight(buttonPane);
        root.setCenter(list);
        root.setBottom(spodniButtonPane);
        Scene scene = new Scene(root, 800, 500);

        primaryStage.setTitle("Správa dopravních prostředků");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private final EventHandler<ActionEvent> pridejNovouObec = event -> {
        pridejNovouObecAlert();
        naplneniListu();
    };
    
    private final EventHandler<ActionEvent> nacitaniZeSouboru = event -> {
        obyvatel.importData(CSV_FILE);
        naplneniListu();
    };
    
    private final EventHandler<ActionEvent> clear = event -> {
        obyvatel.zrus(enumKraj.VSECHNY_KRAJE);
        obce.clear();
        list.getItems().clear();
        naplneniListu();
    };

    private final EventHandler<ActionEvent> dejNadPrumer = event -> {
        nadPrumerKraje();
    };

    private final EventHandler<ActionEvent> zjistiPrumerKraje = event -> {
        prumerKraje();

    };

    private final EventHandler<ActionEvent> zrusKraj = event -> {
        zruseniKraje();
        naplneniListu();
    };

    private final EventHandler<ActionEvent> dejObec = event -> {
        zobrazObec();
    };

    private final EventHandler<ActionEvent> odebratObec = event -> {
        odeberObec();
        naplneniListu();
    };

    private final EventHandler<ActionEvent> generujObce = event -> {
        generator.Generator.naplnPole((AbstrDoubleList<Obec>[]) obyvatel.getSeznam());
        naplneniListu();
    };

    private final EventHandler<ActionEvent> zalohujDoBin = (ActionEvent event) -> {
        try {
            obyvatel.ulozDoBin();
        } catch (IOException ex) {
            Logger.getLogger(MainFx.class.getName()).log(Level.SEVERE, null, ex);
        }

    };
    
    private final EventHandler<ActionEvent> obnovZBin = (ActionEvent event) -> {
        try {
            obyvatel.nactiZBin();
            naplneniListu();
        } catch (IOException ex) {
            Logger.getLogger(MainFx.class.getName()).log(Level.SEVERE, null, ex);
        }

    };

    private void naplneniListu() {
        obce.clear();
        if (zobrazKraje.getSelectionModel().getSelectedItem().equals(enumKraj.VSECHNY_KRAJE)) {
            for (int i = 1; i < enumKraj.getKraje().length; i++) {
                Iterator iterator = obyvatel.zobrazObce(enumKraj.values()[i]);
                while (iterator.hasNext()) {
                    obce.add((Obec) iterator.next());
                }
                list.setItems(obce);
            }
        } else {
            Iterator iterator = obyvatel.zobrazObce(zobrazKraje.getSelectionModel().getSelectedItem());
            while (iterator.hasNext()) {
                obce.add((Obec) iterator.next());
            }
            list.setItems(obce);
        }

    }

    private void pridejNovouObecAlert() {
        Dialog<Obec> dialog = new Dialog<>();
        dialog.setTitle("Přidej novou obec");
        dialog.setHeaderText("Nová obec");

        ButtonType editBtn = new ButtonType("uložit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(editBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nazev = new TextField();
        nazev.setPromptText("Název Obce");

        TextField psc = new TextField();
        psc.setPromptText("PSČ");

        TextField pocetMuzu = new TextField();
        pocetMuzu.setPromptText("Počet mužů");

        TextField pocetZen = new TextField();
        pocetZen.setPromptText("Počet Žen");

        grid.add(nazev, 1, 0);
        grid.add(psc, 1, 1);
        grid.add(pocetMuzu, 1, 2);
        grid.add(pocetZen, 1, 3);
        grid.add(comboKraje, 1, 4);
        grid.add(comboPozic, 1, 5);

        Node EditButton = dialog.getDialogPane().lookupButton(editBtn);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == editBtn) {
                Obec obec;

                String novyNazev = nazev.getText();
                int novyPSC = Integer.parseInt(psc.getText());
                int novyPocetMuzu = Integer.parseInt(pocetMuzu.getText());
                int novyPocetZen = Integer.parseInt(pocetZen.getText());

                obec = new Obec(comboKraje.getSelectionModel().getSelectedItem().getCislo(),novyPSC, novyNazev, novyPocetMuzu, novyPocetZen, novyPocetMuzu + novyPocetZen);
                obyvatel.vlozObec(obec, comboPozic.getSelectionModel().getSelectedItem(), comboKraje.getSelectionModel().getSelectedItem());
            }

            return null;
        });

        dialog.showAndWait();
    }

    private void zobrazObec() {
        Dialog<Obec> dialog = new Dialog<>();
        dialog.setTitle("Zobraz obec");
        dialog.setHeaderText("Zobraz obec");

        ButtonType editBtn = new ButtonType("zobrazit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(editBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Label kraj = new Label();
        kraj.setText("Vyberte kraj");

        Label pozice = new Label();
        pozice.setText("Vyberte pozici");

        grid.add(kraj, 1, 0);
        grid.add(comboKraje, 1, 1);
        grid.add(pozice, 1, 2);
        grid.add(comboPozic, 1, 3);

        Node EditButton = dialog.getDialogPane().lookupButton(editBtn);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == editBtn) {
                Obec obec = obyvatel.zpristupniObec(comboPozic.getSelectionModel().getSelectedItem(), comboKraje.getSelectionModel().getSelectedItem());
                if (obec != null) {
                    list.getSelectionModel().select(obec);
                }
            }

            return null;
        });

        dialog.showAndWait();
    }

    private void odeberObec() {
        Dialog<Obec> dialog = new Dialog<>();
        dialog.setTitle("Odeber obec");
        dialog.setHeaderText("Odeber obec");

        ButtonType editBtn = new ButtonType("odebrat", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(editBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Label kraj = new Label();
        kraj.setText("Vyberte kraj");

        Label pozice = new Label();
        pozice.setText("Vyberte pozici");

        grid.add(kraj, 1, 0);
        grid.add(comboKraje, 1, 1);
        grid.add(pozice, 1, 2);
        grid.add(comboPozic, 1, 3);

        Node EditButton = dialog.getDialogPane().lookupButton(editBtn);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == editBtn) {
                obyvatel.odeberObec(comboPozic.getSelectionModel().getSelectedItem(), comboKraje.getSelectionModel().getSelectedItem());
            }

            return null;
        });

        dialog.showAndWait();
    }

    private void zruseniKraje() {
        Dialog<Obec> dialog = new Dialog<>();
        dialog.setTitle("Zrus kraj");
        dialog.setHeaderText("Zrus kraj");

        ButtonType editBtn = new ButtonType("odebrat", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(editBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Label kraj = new Label();
        kraj.setText("Vyberte kraj");

        grid.add(kraj, 1, 0);
        grid.add(comboKraje, 1, 1);

        Node EditButton = dialog.getDialogPane().lookupButton(editBtn);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == editBtn) {
                obyvatel.zrus(comboKraje.getSelectionModel().getSelectedItem());
            }

            return null;
        });

        dialog.showAndWait();
    }

    private void prumerKraje() {
        Dialog<Obec> dialog = new Dialog<>();
        dialog.setTitle("Průměrný počet obyvatel v kraji");
        dialog.setHeaderText("Průměr");

        ButtonType editBtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(editBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Label kraj = new Label();
        kraj.setText("Vyberte kraj");

        grid.add(kraj, 1, 0);
        grid.add(comboKraje, 1, 1);

        Node EditButton = dialog.getDialogPane().lookupButton(editBtn);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == editBtn) {
                txtPrumerKraje.setText(Float.toString(obyvatel.zjistiPrumer(comboKraje.getSelectionModel().getSelectedItem())));
                //krajVeKteremSeMeriPrumer.setText();
            }

            return null;
        });

        dialog.showAndWait();
    }

    private void nadPrumerKraje() {
        Dialog<Obec> dialog = new Dialog<>();
        dialog.setTitle("Nadprůměrné obce v kraji");
        dialog.setHeaderText("Nadprůměr");

        ButtonType editBtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(editBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Label kraj = new Label();
        kraj.setText("Vyberte kraj");

        grid.add(kraj, 1, 0);
        grid.add(comboKraje, 1, 1);

        Node EditButton = dialog.getDialogPane().lookupButton(editBtn);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == editBtn) {
                obce.clear();
                float prumer = obyvatel.zjistiPrumer(comboKraje.getSelectionModel().getSelectedItem());
                Iterator<Obec> iterator = obyvatel.zobrazObce(comboKraje.getSelectionModel().getSelectedItem());
                while (iterator.hasNext()) {
                    Obec next = iterator.next();
                    if (next.getCelkem() > prumer) {

                        obce.add(next);
                    }
                }
                list.setItems((obce));

            }

            return null;
        });

        dialog.showAndWait();
    }

}
