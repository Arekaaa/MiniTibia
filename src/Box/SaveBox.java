package Box;

import controller.TreningController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class SaveBox {
    private static String nazwaZapisana;
    public static String save(String title,String message) {
        TreningController trening = new TreningController();
        Stage window = new Stage();
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);

        Label label1 = new Label();
        label1.setText("Postać zostanie zapisana w katalogu z grą.");

        TextField textField = new TextField();

        Button zapiszButton = new Button("Zapisz");
        zapiszButton.setOnAction(e-> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label1,label,textField,zapiszButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout,350,180);
        window.setScene(scene);
        window.showAndWait();
            while (textField.getText().trim().isEmpty()) {
                AlertBox.display("Błąd", "Błąd podczas zapisywania! Spróbuj ponownie.");
                window.showAndWait();
        }
        nazwaZapisana = textField.getText() + ".txt";
        return textField.getText() + ".txt";
    }

    public static String getNazwaZapisana() {
        return nazwaZapisana;
    }

    public static void setNazwaZapisana(String nazwaZapisana) {
        SaveBox.nazwaZapisana = nazwaZapisana;
    }
}
