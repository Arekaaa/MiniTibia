package controller;

import Box.AlertBox;
import Box.ConfirmBox;
import Box.SaveBox;
import beans.CharacterBean;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TreningController {
    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label labelLVL;

    @FXML
    private Label labelHP;

    @FXML
    private Label labelArmor;

    @FXML
    private Label labelDMG;

    @FXML
    private Label labelMiecz;

    @FXML
    private Label labelHelm;

    @FXML
    private Label labelZbroja;

    @FXML
    private Label labelSpodnie;

    @FXML
    private Label labelButy;

    @FXML
    private Label labelEXP;

    @FXML
    private Label labelMaxExp;

    @FXML
    private Label labelMonety;

    @FXML
    private ListView<Object> treningList;

    @FXML
    private ToggleButton treningButton;

    @FXML
    void onEkwipunekClick(ActionEvent event) {

    }

    @FXML
    void onSklepClick(ActionEvent event) {

    }

    @FXML
    void onTreningClick(ActionEvent event) {

    }

    private int losujExp(){
        Random generatorExpa = new Random();
        int min=1;
        int max=30;
        int wylosowanyExp=generatorExpa.nextInt((max-min)+1)+min;
        return wylosowanyExp;
    }

    private int losujSleep(){
        Random generatorSleepa = new Random();
        int min=1000;
        int max=10000;
        int wylosowanySleep=generatorSleepa.nextInt((max-min)+1)+min;
        return wylosowanySleep;
    }
    private int losujMonety(){
        Random generatorMonet = new Random();
        int min=1;
        int max=5;
        int wylosowaneMonety=generatorMonet.nextInt((max-min)+1)+min;
        return wylosowaneMonety;
    }

    private int losujSleepMonet(){
        Random generatorMonet = new Random();
        int min=60000;
        int max=100000;
        int wylosowanySleepMonet=generatorMonet.nextInt((max-min)+1)+min;
        return wylosowanySleepMonet;
    }

    void statystyki(){
        int hp = Integer.parseInt(labelHP.getText()) + 50;
        String hpZsumowane = Integer.toString(hp);
        labelHP.setText(hpZsumowane);

        int dmg = Integer.parseInt(labelDMG.getText())+10;
        String dmgZsumowane = Integer.toString(dmg);
        labelDMG.setText(dmgZsumowane);

        int armor = Integer.parseInt(labelArmor.getText())+5;
        String armorZsumowane = Integer.toString(armor);
        labelArmor.setText(armorZsumowane);
    }

    @FXML
    void onTrenujClick(ActionEvent event) {
        CharacterBean character = new CharacterBean();
        try {
            if (treningButton.isSelected()) {
                treningList.getItems().add("Trening rozpoczęty...");
                Thread t2 = new Thread(new Runnable() { // Wątek obsługujący freezowanie buttona na 20 sekund w celu unikania oszustwa.
                    @Override
                    public void run() {
                        treningButton.setDisable(true);
                        try {
                            Thread.sleep(20000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                treningButton.setDisable(false);
                            }
                        });
                    }
                });

                Thread t3 = new Thread(new Runnable() { // Wątek obsługuje losowanie monet w czasie treningu.
                    @Override
                    public void run() {
                        while(treningButton.isSelected()) {
                            try {
                                Thread.sleep(losujSleepMonet());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Calendar now = Calendar.getInstance();
                                    String minuta;
                                    String godzina;
                                    if (now.get(Calendar.MINUTE) <= 9) {
                                        minuta = "0" + now.get(Calendar.MINUTE);
                                    } else {
                                        minuta = Integer.toString(now.get(Calendar.MINUTE));
                                    }
                                    if (now.get(Calendar.HOUR_OF_DAY) <= 9) {
                                        godzina = "0" + now.get(Calendar.HOUR_OF_DAY);
                                    } else {
                                        godzina = Integer.toString(now.get(Calendar.HOUR_OF_DAY));
                                    }
                                    String czas = godzina + ":" + minuta;

                                    character.setMoney(losujMonety());
                                    treningList.getItems().add(czas + " -> Zdobyto kolejne monety: " + character.getMoney() + " monety!");
                                    int monety = Integer.parseInt(labelMonety.getText()) + character.getMoney();
                                    String monetyZsumowane = Integer.toString(monety);
                                    labelMonety.setText(monetyZsumowane);
                                }
                            });
                        }
                    }
                });
                Thread t = new Thread(new Runnable() { // Wątek dodający expa i lvl
                    @Override
                    public void run() {
                        t2.start(); // uruchomiony wątek drugi w tym miejscu
                        while (treningButton.isSelected()) {
                            try {
                                Thread.sleep(losujSleep());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Calendar now = Calendar.getInstance();
                                        String minuta;
                                        String godzina;
                                        if(now.get(Calendar.MINUTE)<=9){
                                             minuta = "0"+now.get(Calendar.MINUTE);
                                        }
                                        else{
                                            minuta=Integer.toString(now.get(Calendar.MINUTE));
                                        }
                                        if(now.get(Calendar.HOUR_OF_DAY)<=9){
                                            godzina = "0"+now.get(Calendar.HOUR_OF_DAY);
                                        }
                                        else{
                                            godzina = Integer.toString(now.get(Calendar.HOUR_OF_DAY));
                                        }
                                        String czas = godzina + ":" +minuta;
                                        //Losowanie punktów doświadczenia.
                                        List items = treningList.getItems();
                                        int index = items.size();
                                        character.setExp(losujExp());
                                        treningList.getItems().add(czas +" -> Zdobyto punkty doświadczenia: " + character.getExp() +" punkty/ów");
                                        //System.out.println(character.getExp());
                                        int maxExp = Integer.parseInt(labelMaxExp.getText());
                                        int labelExpToInt = Integer.parseInt(labelEXP.getText());
                                        int randomExp = character.getExp();
                                        int zsumowanyExp = labelExpToInt + randomExp;
                                        String expDodany = Integer.toString(zsumowanyExp);
                                        double percent = (zsumowanyExp * 100 / maxExp);
                                        double progress = percent/100;
                                        //System.out.println(percent);
                                        progressBar.setProgress(progress);

                                        if (zsumowanyExp >= maxExp) { //Jeśli exp będzie 100/100 to dodaje nowy lvl
                                            labelEXP.setText("0");
                                            progressBar.setProgress(0.0);
                                            maxExp = maxExp +20;
                                            int labelLvlToInt = Integer.parseInt(labelLVL.getText());
                                            labelLvlToInt = labelLvlToInt + 1;
                                            String lvlDodany = Integer.toString(labelLvlToInt);
                                            String maxExpToString = Integer.toString(maxExp);
                                            labelLVL.setText(lvlDodany);
                                            labelMaxExp.setText(maxExpToString);
                                            statystyki();
                                            String nowyLvl = czas +" -> Gratulację! Osiągnięto nowy lvl !!! " + lvlDodany +" lvl";
                                            treningList.getItems().add(nowyLvl);
                                        } else { // W przeciwnym razie dodaje expa dalej
                                            //treningList.setStyle("-fx-text-fill: black");
                                            labelEXP.setText(expDodany);

                                        }
                                        treningList.scrollTo(index); // Scrolluje do nowych danych na liście
                                    }
                                });
                                // Losuje czas zdobywania nowego doświadczenia.
                            }
                            catch(InterruptedException en){
                                en.printStackTrace();
                            }
                        }
                        Thread.currentThread().interrupt();
                        t3.interrupt();
                        //treningList.getItems().add("BONUS: Otrzymano bonus w postaci partii expa oraz monet :)");
                    }
                });
                t.setDaemon(true);
                t.start(); // uruchomiony wątek pierwszy
                t3.setDaemon(true);
                t3.start(); // uruchamiany wątek losowania monet w tym miejscu
            }
        }
        catch(Exception e){
                e.printStackTrace();
        }
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
    void onNowaGraClick(ActionEvent event) {

        Boolean answer = ConfirmBox.display("Nowa gra","Rozpocząć nową grę i zresetować postać ?");

        if(answer==true) {
            CharacterBean character = new CharacterBean();
            character.setLvl(1);
            character.setExp(0);
            character.setMaxExp(100);
            character.setMoney(0);
            character.setHp(100);
            character.setDmg(20);
            character.setArmor(10);

            String resetLvl = Integer.toString(character.getLvl());
            String resetExp = Integer.toString(character.getExp());
            String resetMaxExp = Integer.toString(character.getMaxExp());
            String resetMonet = Integer.toString(character.getMoney());
            String resetHp = Integer.toString(character.getHp());
            String resetDmg = Integer.toString(character.getDmg());
            String resetArmor = Integer.toString(character.getArmor());

            labelLVL.setText(resetLvl);
            labelEXP.setText(resetExp);
            labelMaxExp.setText(resetMaxExp);
            labelMonety.setText(resetMonet);
            labelHP.setText(resetHp);
            labelDMG.setText(resetDmg);
            labelArmor.setText(resetArmor);
            progressBar.setProgress(0.0);

            treningList.getItems().add("Zresetowano grę!");
        }
    }

    @FXML
    void onZapiszClick(ActionEvent event) {
        String lvl = labelLVL.getText();
        String exp = labelEXP.getText();
        String maxExp = labelMaxExp.getText();
        String monety = labelMonety.getText();
        String hp = labelHP.getText();
        String dmg = labelDMG.getText();
        String armor = labelArmor.getText();

        try {
            PrintWriter zapis = new PrintWriter(SaveBox.save("SaveGame", "Wpisz nazwę pod jaką chcesz zapisać swój stan gry :"));

            zapis.println(lvl);
            zapis.println(exp);
            zapis.println(maxExp);
            zapis.println(monety);
            zapis.println(hp);
            zapis.println(dmg);
            zapis.println(armor);

            zapis.close();
            treningList.getItems().add("Zapisano grę pod nazwą: "+SaveBox.getNazwaZapisana());
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
    @FXML
    void onLoadClick(ActionEvent event) {
        CharacterBean character = new CharacterBean();
        Stage stage = new Stage();
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Wczytaj postać. Domyślnie plik .txt w folderze z grą.");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = chooser.showOpenDialog(stage);
        try {
            if (selectedFile != null) {
                Scanner odczyt = new Scanner(selectedFile);
                String odczytLvl = odczyt.nextLine();
                String odczytExp = odczyt.nextLine();
                String odczytMaxExp = odczyt.nextLine();
                String odczytMonet = odczyt.nextLine();
                String odczytHp = odczyt.nextLine();
                String odczytDmg = odczyt.nextLine();
                String odczytArmor = odczyt.nextLine();

                int wczytanyLvl = Integer.parseInt(odczytLvl);
                int wczytanyExp = Integer.parseInt(odczytExp);
                int wczytanyMaxExp = Integer.parseInt(odczytMaxExp);
                int wczytaneMonety = Integer.parseInt(odczytMonet);
                int wczytaneHp = Integer.parseInt(odczytHp);
                int wczytaneDmg = Integer.parseInt(odczytDmg);
                int wczytanyArmor = Integer.parseInt(odczytArmor);

                character.setLvl(wczytanyLvl);
                character.setExp(wczytanyExp);
                character.setMaxExp(wczytanyMaxExp);
                character.setMoney(wczytaneMonety);
                character.setHp(wczytaneHp);
                character.setDmg(wczytaneDmg);
                character.setArmor(wczytanyArmor);

                labelLVL.setText(odczytLvl);
                labelEXP.setText(odczytExp);
                labelMaxExp.setText(odczytMaxExp);
                labelMonety.setText(odczytMonet);
                labelHP.setText(odczytHp);
                labelDMG.setText(odczytDmg);
                labelArmor.setText(odczytArmor);

                double percent = (wczytanyExp * 100 / wczytanyMaxExp);
                double progress = percent/100;
                progressBar.setProgress(progress);

                treningList.getItems().add("Wczytano stan rozgrywki: "+selectedFile.getName());
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }

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


    public ListView<Object> getTreningList() {
        return treningList;
    }

    public void setTreningList(ListView<Object> treningList) {
        this.treningList = treningList;
    }
}