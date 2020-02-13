package beans;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class EqBeans {

    private final SimpleIntegerProperty id;
    private final SimpleIntegerProperty eqLvl;
    private final SimpleIntegerProperty eqMonety;
    private final SimpleIntegerProperty eqHp;
    private final SimpleIntegerProperty eqDmg;
    private final SimpleIntegerProperty eqArmor;
    private final SimpleStringProperty typ;
    private final SimpleStringProperty nazwa;

    /*public EqBeans(){
        this.nazwa = " ";
        this.typ = " ";
        this.eqLvl = 0;
        this.eqMonety = 0;
        this.eqHp = 0;
        this.eqDmg = 0;
        this.eqArmor = eqArmor;
    }*/

    public EqBeans(int id,String nazwa, String typ, int eqLvl, int eqMonety, int eqHp, int eqDmg, int eqArmor){
        this.id = new SimpleIntegerProperty(id);
        this.nazwa = new SimpleStringProperty(nazwa);
        this.typ = new SimpleStringProperty(typ);
        this.eqLvl = new SimpleIntegerProperty(eqLvl);
        this.eqMonety = new SimpleIntegerProperty(eqMonety);
        this.eqHp = new SimpleIntegerProperty(eqHp);
        this.eqDmg = new SimpleIntegerProperty(eqDmg);
        this.eqArmor = new SimpleIntegerProperty(eqArmor);
    }


    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getEqLvl() {
        return eqLvl.get();
    }

    public SimpleIntegerProperty eqLvlProperty() {
        return eqLvl;
    }

    public void setEqLvl(int eqLvl) {
        this.eqLvl.set(eqLvl);
    }

    public int getEqMonety() {
        return eqMonety.get();
    }

    public SimpleIntegerProperty eqMonetyProperty() {
        return eqMonety;
    }

    public void setEqMonety(int eqMonety) {
        this.eqMonety.set(eqMonety);
    }

    public int getEqHp() {
        return eqHp.get();
    }

    public SimpleIntegerProperty eqHpProperty() {
        return eqHp;
    }

    public void setEqHp(int eqHp) {
        this.eqHp.set(eqHp);
    }

    public int getEqDmg() {
        return eqDmg.get();
    }

    public SimpleIntegerProperty eqDmgProperty() {
        return eqDmg;
    }

    public void setEqDmg(int eqDmg) {
        this.eqDmg.set(eqDmg);
    }

    public int getEqArmor() {
        return eqArmor.get();
    }

    public SimpleIntegerProperty eqArmorProperty() {
        return eqArmor;
    }

    public void setEqArmor(int eqArmor) {
        this.eqArmor.set(eqArmor);
    }

    public String getTyp() {
        return typ.get();
    }

    public SimpleStringProperty typProperty() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ.set(typ);
    }

    public String getNazwa() {
        return nazwa.get();
    }

    public SimpleStringProperty nazwaProperty() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa.set(nazwa);
    }
}
