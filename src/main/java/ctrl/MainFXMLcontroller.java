package ctrl;

import domain.CazCaritabil;
import domain.Donatie;
import domain.DonatorDTO;
import domain.Voluntar;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import repository.CaritabilRepo;
import repository.DonatieRepo;
import repository.VoluntarRepo;
import repository.jdbc.CaritabilDbRepo;
import repository.jdbc.DonatieDbRepo;
import repository.jdbc.VoluntarDbRepo;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MainFXMLcontroller {
    ObservableList <String> presentingList;

    VoluntarRepo voluntarRepo;
    DonatieRepo donatieRepo;
    CaritabilRepo caritabilRepo;

    @FXML
    ListView list_1 = new ListView<String>();

    @FXML
    ListView list_2 = new ListView<String>();

    @FXML
    AnchorPane login_pane = new AnchorPane();

    @FXML
    AnchorPane working_pane = new AnchorPane();

    @FXML
    Label login_label1 = new Label();

    @FXML
    Label login_label2 = new Label();

    @FXML
    TextField user_field = new TextField();

    @FXML
    TextField pass_field = new TextField();

    @FXML
    TextField nume_field = new TextField();

    @FXML
    TextField telefon_field = new TextField();

    @FXML
    TextField suma_field = new TextField();



    public MainFXMLcontroller(){
        //repoVoluntari = new VoluntarMemoryRepo();
    }


    @FXML
    public void initialize(){

        Properties props = new Properties();

        try {
            System.out.println("Searching bd.config in directory: " + ((new File(".")).getAbsolutePath()));
            props.load(new FileReader("bd.config"));
        }
        catch (IOException e){
            System.err.println("Configuration file bd.config not found " + e);
        }

        voluntarRepo = new VoluntarDbRepo(props);
        donatieRepo = new DonatieDbRepo(props);
        caritabilRepo = new CaritabilDbRepo(props);



        for(CazCaritabil caz : caritabilRepo.filterByName("")) {
            double suma = 0;
            for (Donatie d : donatieRepo.filterByCaritabilID(caz.getID()))
            {
                suma += d.getSuma();
                caz.setSum(suma);
            }
            list_1.getItems().add(caz);
        }

        for(Donatie d : donatieRepo.filterByDonator(""))
        {
            list_2.getItems().add(new DonatorDTO(d.getNume_donator(), d.getNumar_telefon()));
        }

    }


    public void login_clicked(ActionEvent actionEvent) {

        if (voluntarRepo.login(user_field.getText(), pass_field.getText()) != null) {
            login_pane.setVisible(false);
            working_pane.setVisible(true);
        }
        else {
            login_label1.setText("User sau parola invalida!");
            user_field.setText("");
            login_label2.setText("User sau parola invalida!");
            pass_field.setText("");
        }
    }

    public void adauga_clicked(ActionEvent actionEvent) {
        CazCaritabil c = (CazCaritabil) list_1.getSelectionModel().getSelectedItem();
        donatieRepo.add(new Donatie(7, c.getID()
                , nume_field.getText()
                , telefon_field.getText()
                , Double.parseDouble(suma_field.getText())));

        list_1.getItems().clear();

        for(CazCaritabil caz : caritabilRepo.filterByName("")) {
            double suma = 0;
            for (Donatie d : donatieRepo.filterByCaritabilID(caz.getID()))
            {
                suma += d.getSuma();
                caz.setSum(suma);
            }
            list_1.getItems().add(caz);
        }

        nume_field.setText("");
        telefon_field.setText("");
        suma_field.setText("");
    }

    public void cauta_clicked(ActionEvent actionEvent) {
        list_2.getItems().clear();
        for(Donatie d : donatieRepo.filterByDonator(nume_field.getText()))
        {
            list_2.getItems().add(new DonatorDTO(d.getNume_donator(), d.getNumar_telefon()));
        }
    }

    public void table_2_clicked(MouseEvent mouseEvent) {
        DonatorDTO d = (DonatorDTO) list_2.getSelectionModel().getSelectedItem();
        nume_field.setText(d.getNume());
        telefon_field.setText(d.getTelefon());
    }
}
