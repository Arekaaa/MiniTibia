package controller;

import Box.ConfirmBox;
import beans.CharacterBean;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class ShopController {

    @FXML
    private Label labelLVL;

    @FXML
    private Label labelMonety;

    @FXML
    private ListView<?> shopList;
    CharacterBean character = new CharacterBean();

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
