package nl.hva.ict.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.hva.ict.MainApplication;
import nl.hva.ict.models.Reiziger;
import nl.hva.ict.views.ReizigersView;
import nl.hva.ict.views.View;

import java.util.HashMap;

/**
 * ReizigerController
 * @author HBO-ICT
 */
public class ReizigerController extends Controller {

    private final ReizigersView reizigersView;

    public ReizigerController() {
        reizigersView = new ReizigersView();
        reizigersView.getReizigersViewListView().getSelectionModel().selectedItemProperty()
                .addListener(e -> getItemsInFields());
        reizigersView.getComboReistSamenMet().getSelectionModel().selectedItemProperty()
                .addListener(e -> getItemsComboBox());
        reizigersView.getBtSave().setOnAction(e -> save());
        reizigersView.getBtUpdateData().setOnAction(e -> refreshData());
        reizigersView.getBtNew().setOnAction(e -> insert());
        reizigersView.getBtDelete().setOnAction(e -> delete());
        loadData();
    }

    private void loadData() {
        //haal de waardes op uit de database voor MySQL
//        ObservableList<Reiziger> reizigers = FXCollections.observableArrayList(MainApplication.getMySQLReizigers().getAll());

        // voor NOSQL
        ObservableList<Reiziger> reizigers = FXCollections.observableArrayList(MainApplication.getMongoDBReizigers().getAll());


        reizigersView.getReizigersViewListView().setItems(reizigers);
        reizigersView.getComboReistSamenMet().setItems(reizigers);
        reizigersView.getComboReistSamenMet().getSelectionModel().select(null);

    }


    private void refreshData() {
        MainApplication.getMySQLReizigers().reload();
    }

    private void save() {
        // pas1 dit record aan
        HashMap<String, String> reizerObj = new HashMap<>();
        reizerObj.put("oldReizigersCode", reizigersView.getReizigersViewListView().getSelectionModel().getSelectedItem().getReizigersCode());
        reizerObj.put("newReizigersCode", reizigersView.getTxtReizigersCode().getText());
        reizerObj.put("voornaam", reizigersView.getTxtVoornaam().getText());
        reizerObj.put("achternaam", reizigersView.getTxtAchternaam().getText());
        reizerObj.put("adres", reizigersView.getTxtAdres().getText());
        reizerObj.put("postcode", reizigersView.getTxtPostcode().getText());
        reizerObj.put("plaats", reizigersView.getTxtPlaats().getText());
        reizerObj.put("land", reizigersView.getTxtLand().getText());
        reizerObj.put("gezinshoofd", reizigersView.getComboReistSamenMet().getSelectionModel().getSelectedItem().getReizigersCode());

        MainApplication.getMongoDBReizigers().update(reizerObj);
        reizigersView.getReizigersViewListView().setItems(FXCollections.observableArrayList(MainApplication.getMongoDBReizigers().getAll()));

    }

    private void delete() {
        // delete dit record
        MainApplication.getMongoDBReizigers().remove(reizigersView.getReizigersViewListView().getSelectionModel().getSelectedItem());
        reizigersView.getReizigersViewListView().setItems(FXCollections.observableArrayList(MainApplication.getMongoDBReizigers().getAll()));
    }

    private void insert() {
        //Voeg toe
        HashMap<String, String> reizigerObj = new HashMap<>();
        reizigerObj.put("reiziger_code", reizigersView.getTxtReizigersCode().getText());
        reizigerObj.put("voornaam", reizigersView.getTxtVoornaam().getText());
        reizigerObj.put("achternaam", reizigersView.getTxtAchternaam().getText());
        reizigerObj.put("adres", reizigersView.getTxtAdres().getText());
        reizigerObj.put("postcode", reizigersView.getTxtPostcode().getText());
        reizigerObj.put("plaats", reizigersView.getTxtPlaats().getText());
        reizigerObj.put("land", reizigersView.getTxtLand().getText());
        reizigerObj.put("gezinshoofd", reizigersView.getComboReistSamenMet().getSelectionModel().getSelectedItem().getReizigersCode());

        MainApplication.getMongoDBReizigers().add(reizigerObj);
        reizigersView.getReizigersViewListView().setItems(FXCollections.observableArrayList(MainApplication.getMongoDBReizigers().getAll()));

    }

    private void getItemsInFields() {
        Reiziger currentReiziger = reizigersView.getReizigersViewListView().getSelectionModel().getSelectedItem();
        reizigersView.getTxtReizigersCode().setText(currentReiziger.getReizigersCode());
        reizigersView.getTxtVoornaam().setText(currentReiziger.getVoornaam());
        reizigersView.getTxtAchternaam().setText(currentReiziger.getAchternaam());
        reizigersView.getTxtAdres().setText(currentReiziger.getAdres());
        reizigersView.getTxtPostcode().setText(currentReiziger.getPostcode());
        reizigersView.getTxtPlaats().setText(currentReiziger.getPlaats());
        reizigersView.getTxtLand().setText(currentReiziger.getLand());


        ObservableList<Reiziger> reizigers = FXCollections.observableArrayList(MainApplication.getMongoDBReizigers().getAll());

        for (Reiziger reiziger : reizigers) {
            if (reiziger.getReizigersCode().equals(currentReiziger.getHoofdreiziger())) {
                reizigersView.getComboReistSamenMet().getSelectionModel().select(reiziger);
                break;
            } else {
                reizigersView.getComboReistSamenMet().getSelectionModel().clearSelection();
            }
        }
    }


    /**
     * Nog niets mee gedaan
     */
    private void getItemsComboBox() {
    }

    /**
     * Methode om de view door te geven zoals dat ook bij OOP2 ging
     * @return View
     */
    @Override
    public View getView() {
        return reizigersView;
    }
}
