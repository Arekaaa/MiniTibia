package controller;

import Box.AlertBox;
import Box.ConfirmBox;
import Box.SaveBox;
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
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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

    @FXML
    private TableColumn<EqBeans, Void> tableKup;

    private CharacterBean character = new CharacterBean();
    ObservableList<EqBeans> eq = FXCollections.observableArrayList();
    private List<Object> listaKupiona = new ArrayList<Object>();
    private int iloscKupionych=0;

    private int zalozonyId;
    private String zalozonaNazwaMiecz="Podstawowy Miecz";
    private String zalozonaNazwaZbroja="Podstawowa Zbroja";
    private String zalozonaNazwaHelm="Podstawowy Hełm";
    private String zalozonaNazwaButy="Podstawowe Buty";
    private String zalozonaNazwaSpodnie="Podstawowe Spodnie";
    private String zalozonyTyp="null";
    private int zalozonaCena;
    private int zalozonyLVL;
    private int zalozonyHpp;
    private int zalozonyDmgg ;
    private int zalozonyArmorr;


    public void addButtonToTable() {

        Callback<TableColumn<EqBeans, Void>, TableCell<EqBeans, Void>> cellFactory = new Callback<TableColumn<EqBeans, Void>, TableCell<EqBeans, Void>>() { // Callback między buttonem a całym wierszem tabeli

            @Override
            public TableCell<EqBeans, Void> call(final TableColumn<EqBeans, Void> param) {
                final TableCell<EqBeans, Void> cell = new TableCell<EqBeans, Void>() {

                    private final Button kupButton = new Button("Kup");
                    private final Button zalozButton = new Button("Załóż");
                    private final Button sciagnijButton = new Button("Ściągnij");
                    private final Button sprzedajButton = new Button("Sprzedaj");

                    HBox sprzedajZalozPane = new HBox(zalozButton, sprzedajButton);
                    {

                        /*if(shopTable.getItems().get()) {
                            setGraphic(sprzedajZalozPane);
                        }*/

                        kupButton.setOnAction((ActionEvent event) -> {
                            final int[] kasa = {Integer.parseInt(labelMonety.getText())};
                            int lvl = Integer.parseInt(labelLVL.getText());
                            EqBeans eqBeans = getTableView().getItems().get(getIndex());
                            if(kasa[0] >= eqBeans.getEqMonety() && lvl >= eqBeans.getEqLvl()) {
                                boolean answer = ConfirmBox.display("Kup produkt","Kupić : '"+ eqBeans.getNazwa()+"' ? Zostanie odjęte "+eqBeans.getEqMonety()+" monet.");
                                if (answer) {
                                    AlertBox.display("Kupiono produkt", "Zakupiono produkt: '" + eqBeans.getNazwa());
                                    kasa[0] = kasa[0] - eqBeans.getEqMonety();
                                    String kasaTekst = Integer.toString(kasa[0]);
                                    labelMonety.setText(kasaTekst);
                                    ShopController.this.getListaKupiona().add(eqBeans.getId() +eqBeans.getNazwa() +eqBeans.getTyp()+eqBeans.getEqMonety() +eqBeans.getEqLvl()+
                                            eqBeans.getEqHp()+eqBeans.getEqDmg()+eqBeans.getEqArmor());
                                   /* id=eqBeans.getId();
                                    nazwa=eqBeans.getNazwa();
                                    typ=eqBeans.getTyp();
                                    cena=eqBeans.getEqMonety();
                                    LVL=eqBeans.getEqLvl();
                                    hpp=eqBeans.getEqHp();
                                    dmgg=eqBeans.getEqDmg();
                                    armorr=eqBeans.getEqArmor();*/
                                    iloscKupionych++;
                                    //System.out.println(getIloscKupionych());
                                    character.setKupione(listaKupiona);
                                    //System.out.println(listaKupiona);
                                    setGraphic(sprzedajZalozPane);
                                }
                            }
                            else{
                                AlertBox.display("Błąd","Nie masz wystarczającej ilości monet lub Twój poziom jest zbyt niski. Trenuj dalej !");

                            }
                        });

                        zalozButton.setOnAction((ActionEvent event) -> {
                            EqBeans eqBeans = getTableView().getItems().get(getIndex());
                            boolean answer = ConfirmBox.display("Załóż produkt", "Założyć : '" + eqBeans.getNazwa() + "' ? ");
                            if (answer) {
                                if(eqBeans.getTyp().equals("Miecz")){
                                    ShopController.this.setZalozonaNazwaMiecz(eqBeans.getNazwa());
                                    //ShopController.this.setZalozonyTyp(eqBeans.getTyp());
                                }
                                else if(eqBeans.getTyp().equals("Zbroja")){
                                    ShopController.this.setZalozonaNazwaZbroja(eqBeans.getNazwa());

                                }
                                else if(eqBeans.getTyp().equals("Spodnie")){
                                    ShopController.this.setZalozonaNazwaSpodnie(eqBeans.getNazwa());
                                }
                                else if(eqBeans.getTyp().equals("Hełm")){
                                    ShopController.this.setZalozonaNazwaHelm(eqBeans.getNazwa());
                                }
                                else if(eqBeans.getTyp().equals("Buty")){
                                    ShopController.this.setZalozonaNazwaButy(eqBeans.getNazwa());
                                }

                                AlertBox.display("Sukces", "Produkt: '" + eqBeans.getNazwa() + "' został założony. Można używać go podczas treningu.");
                                setGraphic(sciagnijButton);
                            }
                        });

                        sciagnijButton.setOnAction((ActionEvent event) -> {
                            EqBeans eqBeans = getTableView().getItems().get(getIndex());
                            boolean answer = ConfirmBox.display("Ściągnij produkt", "Ściągnąć : '" + eqBeans.getNazwa() + "' ? ");
                            if (answer) {
                                if(eqBeans.getTyp().equals("Miecz")){
                                    ShopController.this.setZalozonaNazwaMiecz("Podstawowy Miecz");
                                    //ShopController.this.setZalozonyTyp(eqBeans.getTyp());
                                }
                                else if(eqBeans.getTyp().equals("Zbroja")){
                                    ShopController.this.setZalozonaNazwaZbroja("Podstawowa Zbroja");

                                }
                                else if(eqBeans.getTyp().equals("Spodnie")){
                                    ShopController.this.setZalozonaNazwaSpodnie("Podstawowe Spodnie");
                                }
                                else if(eqBeans.getTyp().equals("Hełm")){
                                    ShopController.this.setZalozonaNazwaHelm("Podstawowy Hełm");
                                }
                                else if(eqBeans.getTyp().equals("Buty")){
                                    ShopController.this.setZalozonaNazwaButy("Podstawowe Buty");
                                }
                                setGraphic(sprzedajZalozPane);
                            }
                        });

                        sprzedajButton.setOnAction((ActionEvent event) -> {
                            EqBeans eqBeans = getTableView().getItems().get(getIndex());
                            int cenaSprzedazowa = eqBeans.getEqMonety() * 80/100;
                            boolean answer = ConfirmBox.display("Sprzedaj produkt", "Sprzedać za : '" + cenaSprzedazowa + "', czyli około 80% ceny pierwotnej ? ");
                            if (answer) {
                                final int[] kasa = {Integer.parseInt(labelMonety.getText())};
                                kasa[0] = kasa[0] + cenaSprzedazowa;
                                String kasaTekst = Integer.toString(kasa[0]);
                                labelMonety.setText(kasaTekst);
                                setGraphic(kupButton);
                            }
                        });

                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        }
                        else {
                            setGraphic(kupButton);
                        }
                    }
                };
                return cell;
            }
        };

        tableKup.setCellFactory(cellFactory);

        //shopTable.getColumns().add(tableKup);

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
        tableKup.setReorderable(false);

       // DODAWANIE NOWYCH PRODUKTÓW
        eq.add(new EqBeans(1,"Zbroja panicza","Armor",5,5,40,5,8));
        eq.add(new EqBeans(2,"Spodnie paladyna","Spodnie",5,3,15,3,5));
        eq.add(new EqBeans(3,"Hełm żołnierza","Hełm",3,4,7,0,3));
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
        //anchorPane.getChildren().addAll(kupButton);
        addButtonToTable();
        //addButtonZalozToTable();

    }

    public void loadShopData(String lvl, String money,String exp, String maxExp, String hp, String dmg, String armor,List<Object> kupione,int iloscKupionych,
                             String zalozonaNazwaMiecz,String zalozonaNazwaButy,String zalozonaNazwaHelm,String zalozonaNazwaSpodnie,String zalozonaNazwaZbroja,String zalozonyTyp){
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
        this.setListaKupiona(kupione);
        this.setIloscKupionych(iloscKupionych);
        //this.setId(id);
        this.setZalozonaNazwaMiecz(zalozonaNazwaMiecz);
        this.setZalozonaNazwaButy(zalozonaNazwaButy);
        this.setZalozonaNazwaHelm(zalozonaNazwaHelm);
        this.setZalozonaNazwaSpodnie(zalozonaNazwaSpodnie);
        this.setZalozonaNazwaZbroja(zalozonaNazwaZbroja);
        this.setZalozonyTyp(zalozonyTyp);
        /*this.setTyp(typ);
        this.setCena(cena);
        this.setLVL(LVL);
        this.setHp(hpp);
        this.setDmg(dmgg);
        this.setArmor(armorr);*/
        //System.out.println(character.getKupione());
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
    void onExitClick(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void onExitClickMenu(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void onZapiszClick(ActionEvent event) {
        String lvl = labelLVL.getText();
        int exp = character.getExp();
        int maxExp = character.getMaxExp();
        String monety = labelMonety.getText();
        int hp = character.getHp();
        int dmg = character.getDmg();
        int armor = character.getArmor();
        String kupionePrzedmioty = listaKupiona.toString();

        String strExp = Integer.toString(exp);
        String strMaxExp = Integer.toString(maxExp);
        String strHp = Integer.toString(hp);
        String strDmg = Integer.toString(dmg);
        String strArmor = Integer.toString(armor);

        try {
            PrintWriter zapis = new PrintWriter(SaveBox.save("SaveGame", "Wpisz nazwę pod jaką chcesz zapisać swój stan gry :"));

            zapis.println(lvl);
            zapis.println(strExp);
            zapis.println(strMaxExp);
            zapis.println(monety);
            zapis.println(strHp);
            zapis.println(strDmg);
            zapis.println(strArmor);
            zapis.println(kupionePrzedmioty);

            zapis.close();
            AlertBox.display("Zapisano Grę","Zapisano grę pod nazwą: "+SaveBox.getNazwaZapisana());

        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    @FXML
    void onLoadClick(ActionEvent event) {
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
                String odczytPrzedmioty = odczyt.nextLine();

                int wczytanyLvl = Integer.parseInt(odczytLvl);
                int wczytanyExp = Integer.parseInt(odczytExp);
                int wczytanyMaxExp = Integer.parseInt(odczytMaxExp);
                int wczytaneMonety = Integer.parseInt(odczytMonet);
                int wczytaneHp = Integer.parseInt(odczytHp);
                int wczytaneDmg = Integer.parseInt(odczytDmg);
                int wczytanyArmor = Integer.parseInt(odczytArmor);
                List<Object> wczytanePrzedmioty = new ArrayList<Object>();
                wczytanePrzedmioty.add(odczytPrzedmioty);

                character.setLvl(wczytanyLvl);
                character.setExp(wczytanyExp);
                character.setMaxExp(wczytanyMaxExp);
                character.setMoney(wczytaneMonety);
                character.setHp(wczytaneHp);
                character.setDmg(wczytaneDmg);
                character.setArmor(wczytanyArmor);
                character.setKupione(wczytanePrzedmioty);
                //System.out.println(character.getKupione());

                labelLVL.setText(odczytLvl);
                labelMonety.setText(odczytMonet);


                double percent = (wczytanyExp * 100 / wczytanyMaxExp);
                double progress = percent/100;


                AlertBox.display("Wczytano","Wczytano stan rozgrywki: "+selectedFile.getName());
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    @FXML
    void onNowaGraClick(ActionEvent event) {
        boolean answer = ConfirmBox.display("Nowa gra","Rozpocząć nową grę i zresetować postać ?");

        if(answer) {
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
            labelMonety.setText(resetMonet);


            AlertBox.display("Reset gry","Zresetowano grę!");
        }
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
                trening.loadTreningData(labelLVL.getText(),labelMonety.getText(),exp,maxExp,hp,dmg,armor,progress,getListaKupiona(),iloscKupionych,
                        zalozonaNazwaMiecz,zalozonaNazwaButy,zalozonaNazwaHelm,zalozonaNazwaSpodnie,zalozonaNazwaZbroja,zalozonyTyp);
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

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }



    public int getIloscKupionych() {
        return iloscKupionych;
    }

    public void setIloscKupionych(int iloscKupionych) {
        this.iloscKupionych = iloscKupionych;
    }

    public List<Object> getListaKupiona() {
        return listaKupiona;
    }

    public void setListaKupiona(List<Object> listaKupiona) {
        this.listaKupiona = listaKupiona;
    }


    public Label getLabelLVL() {
        return labelLVL;
    }

    public void setLabelLVL(Label labelLVL) {
        this.labelLVL = labelLVL;
    }


    public int getZalozonyId() {
        return zalozonyId;
    }

    public void setZalozonyId(int zalozonyId) {
        this.zalozonyId = zalozonyId;
    }

    public String getZalozonaNazwaMiecz() {
        return zalozonaNazwaMiecz;
    }

    public void setZalozonaNazwaMiecz(String zalozonaNazwaMiecz) {
        this.zalozonaNazwaMiecz = zalozonaNazwaMiecz;
    }

    public String getZalozonaNazwaZbroja() {
        return zalozonaNazwaZbroja;
    }

    public void setZalozonaNazwaZbroja(String zalozonaNazwaZbroja) {
        this.zalozonaNazwaZbroja = zalozonaNazwaZbroja;
    }

    public String getZalozonaNazwaHelm() {
        return zalozonaNazwaHelm;
    }

    public void setZalozonaNazwaHelm(String zalozonaNazwaHelm) {
        this.zalozonaNazwaHelm = zalozonaNazwaHelm;
    }

    public String getZalozonaNazwaButy() {
        return zalozonaNazwaButy;
    }

    public void setZalozonaNazwaButy(String zalozonaNazwaButy) {
        this.zalozonaNazwaButy = zalozonaNazwaButy;
    }

    public String getZalozonaNazwaSpodnie() {
        return zalozonaNazwaSpodnie;
    }

    public void setZalozonaNazwaSpodnie(String zalozonaNazwaSpodnie) {
        this.zalozonaNazwaSpodnie = zalozonaNazwaSpodnie;
    }

    public String getZalozonyTyp() {
        return zalozonyTyp;
    }

    public void setZalozonyTyp(String zalozonyTyp) {
        this.zalozonyTyp = zalozonyTyp;
    }

    public int getZalozonaCena() {
        return zalozonaCena;
    }

    public void setZalozonaCena(int zalozonaCena) {
        this.zalozonaCena = zalozonaCena;
    }

    public int getZalozonyLVL() {
        return zalozonyLVL;
    }

    public void setZalozonyLVL(int zalozonyLVL) {
        this.zalozonyLVL = zalozonyLVL;
    }

    public int getZalozonyHpp() {
        return zalozonyHpp;
    }

    public void setZalozonyHpp(int zalozonyHpp) {
        this.zalozonyHpp = zalozonyHpp;
    }

    public int getZalozonyDmgg() {
        return zalozonyDmgg;
    }

    public void setZalozonyDmgg(int zalozonyDmgg) {
        this.zalozonyDmgg = zalozonyDmgg;
    }

    public int getZalozonyArmorr() {
        return zalozonyArmorr;
    }

    public void setZalozonyArmorr(int zalozonyArmorr) {
        this.zalozonyArmorr = zalozonyArmorr;
    }
}
