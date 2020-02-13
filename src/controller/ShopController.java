package controller;

import Box.AlertBox;
import beans.CharacterBean;
import beans.EqBeans;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;

public class ShopController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label labelLVL;

    @FXML
    private Label labelMonety;

    @FXML
    private TableView<EqBeans> shopTable = new TableView<>();
    @FXML
    private TableColumn<EqBeans, Integer> tableNumer;

    @FXML
    private TableColumn<EqBeans, String> tableNazwa;

    @FXML
    private TableColumn<EqBeans, String> tableTyp;

    @FXML
    private TableColumn<EqBeans, Integer> tableLvl;

    @FXML
    private TableColumn<EqBeans, Integer> tableCena;

    @FXML
    private TableColumn<EqBeans, Integer> tableHp;

    @FXML
    private TableColumn<EqBeans, Integer> tableDmg;

    @FXML
    private TableColumn<EqBeans, Integer> tableArmor;

    private CharacterBean character = new CharacterBean();
    private List<Button> buttonlist = new ArrayList<>();
    private Button button;

    @FXML
    void onKupClick(ActionEvent event){
        System.out.println("SAS");
        AlertBox.display("Kupiono produkt","Zakupiony produkt został przeniesiony do ekwipunku.");
    }

    public void dodajEq(){
        // Tworzenie listy z danymi
        tableNumer.setReorderable(false); // do każdej oraz zeby bylo na srodku
        tableNazwa.setReorderable(false);
        tableLvl.setReorderable(false);
        tableTyp.setReorderable(false);
        tableCena.setReorderable(false);
        tableHp.setReorderable(false);
        tableDmg.setReorderable(false);
        tableArmor.setReorderable(false);

        ObservableList<EqBeans> eq = FXCollections.observableArrayList();// DODAWANIE NOWYCH PRODUKTÓW
        eq.add(new EqBeans(1,"Zbroja panicza","Armor",5,5,40,5,8));
        eq.add(new EqBeans(2,"Spodnie paladyna","Spodnie",5,3,15,3,5));
        eq.add(new EqBeans(3,"Rękawice żołnierza","Rękawice",3,4,7,0,3));
        eq.add(new EqBeans(4,"Buty szewca","Buty",2,2,5,0,1));
        eq.add(new EqBeans(5,"Legendarny miecz elfów","Miecz",10,50,5,60,15));

        tableNumer.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        tableNazwa.setCellValueFactory(cellData -> cellData.getValue().nazwaProperty());
        tableTyp.setCellValueFactory(cellData -> cellData.getValue().typProperty());
        tableLvl.setCellValueFactory(cellData -> cellData.getValue().eqLvlProperty().asObject());
        tableCena.setCellValueFactory(cellData -> cellData.getValue().eqMonetyProperty().asObject());
        tableHp.setCellValueFactory(cellData -> cellData.getValue().eqHpProperty().asObject());
        tableDmg.setCellValueFactory(cellData -> cellData.getValue().eqDmgProperty().asObject());
        tableArmor.setCellValueFactory(cellData -> cellData.getValue().eqArmorProperty().asObject());
        shopTable.setItems(eq);

        Iterator<EqBeans> iterator = eq.iterator();
        final double[] layoutBazowy = {182};

        iterator.forEachRemaining(e ->{
            buttonlist.add(button = new Button("KUP"));
            button.setOnAction(this::onKupClick); // Wykonuje tą samą metodę onKupClick dla każdego buttona.
            button.setLayoutX(913);
            button.setLayoutY(layoutBazowy[0]);
            layoutBazowy[0] = layoutBazowy[0] +23;
        });
        anchorPane.getChildren().addAll(buttonlist);
    }

    public void loadShopData(String lvl, String money,String exp, String maxExp, String hp, String dmg, String armor){
        labelLVL.setText(lvl);
        labelMonety.setText(money);

        int lvlParse = Integer.parseInt(lvl);
        int moneyParse = Integer.parseInt(money);
        int expParse = Integer.parseInt(exp);
        int maxExpParse = Integer.parseInt(maxExp);
        int hpParse = Integer.parseInt(hp);
        int dmgParse = Integer.parseInt(dmg);
        int armorParse = Integer.parseInt(armor);

        character.setLvl(lvlParse);
        character.setMoney(moneyParse);
        character.setExp(expParse);
        character.setMaxExp(maxExpParse);
        character.setHp(hpParse);
        character.setDmg(dmgParse);
        character.setArmor(armorParse);
}

    @FXML
    void onAboutClick(ActionEvent event) {
        Runtime rs = Runtime.getRuntime();
        try {
            rs.exec("C:\\Windows\\notepad.exe C:\\Users\\Arek\\Desktop\\MiniTibia\\src\\DataFiles\\changelog.txt");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onChangelogClick(ActionEvent event) {
        Runtime rs = Runtime.getRuntime();

        try {
            rs.exec("C:\\Windows\\notepad.exe C:\\Users\\Arek\\Desktop\\MiniTibia\\src\\DataFiles\\changelog.txt");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onEkwipunekClick(ActionEvent event) {

    }

    @FXML
    void onExitClick(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void onExitClickMenu(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void onZapiszClick(ActionEvent event) {

    }

    @FXML
    void onLoadClick(ActionEvent event) {

    }

    @FXML
    void onNowaGraClick(ActionEvent event) {

    }

    @FXML
    void onSklepClick(ActionEvent event) {
        // Can be empty
    }

    @FXML
    void onTreningClick(ActionEvent event) {
        String exp = Integer.toString(character.getExp());
        String maxExp = Integer.toString(character.getMaxExp());
        String hp = Integer.toString(character.getHp());
        String dmg = Integer.toString(character.getDmg());
        String armor = Integer.toString(character.getArmor());
        double percent = (character.getExp() * 100 / character.getMaxExp());
        double progress = percent/100;

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/trening.fxml"));
                Parent root = fxmlLoader.load();
                TreningController trening = fxmlLoader.getController();
                trening.loadTreningData(labelLVL.getText(),labelMonety.getText(),exp,maxExp,hp,dmg,armor,progress);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.setTitle("Trening");
                stage.show();
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
}
