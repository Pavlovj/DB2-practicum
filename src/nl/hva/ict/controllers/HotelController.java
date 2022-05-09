package nl.hva.ict.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.hva.ict.MainApplication;
import nl.hva.ict.models.Hotel;
import nl.hva.ict.views.HotelView;
import nl.hva.ict.views.View;


/**
 * Controller voor de hotel view
 * @author HBO-ICT
 */
public class HotelController extends Controller {

    private final HotelView hotelView;

    /**
     * Constructor
     */
    public HotelController() {

        // Maak een instance aan voor de view
        hotelView = new HotelView();

        // luister naar wijzigingen in de listview en ga naar de functie getItemsInFields() als er een item wordt geselecteerd
        hotelView.getHotelsViewListView().getSelectionModel().selectedItemProperty()
                .addListener(e -> getItemsInFields());

        // Set wat acties als op de buttons wordt geklikt
        hotelView.getBtSave().setOnAction(e -> save());
        hotelView.getBtUpdateData().setOnAction(e -> refreshData());
        hotelView.getBtNew().setOnAction(e -> insert());
        hotelView.getBtDelete().setOnAction(e -> delete());

        // Maak verbinding met de DAO, haal arrayList op met alle boekingen en stop dit in een observable list
        ObservableList<Hotel> hotels = FXCollections.observableArrayList(MainApplication.getMySQLHotel().getAll());

        //update de listview
        hotelView.getHotelsViewListView().setItems(hotels);
    }

    /**
     * reload de data uit de DAO
     */
    private void refreshData() {
        MainApplication.getMySQLHotel().reload();
    }

    /**
     * Save de data als op de knop wordt gedrukt
     */
    private void save() {
        // Lees de velden
        String accommodatieCode = hotelView.getTxtAccommodatieCode().getText();
        String naam = hotelView.getTxtNaam().getText();
        String stad = hotelView.getTxtStad().getText();
        String land = hotelView.getTxtLand().getText();
        String kamertype = hotelView.getTxtKamertype().getText();
        int aantalPersonen = Integer.valueOf(hotelView.getTxtAantalPersonen().getText());
        double prijs = Double.valueOf(hotelView.getTxtPrijsPerNacht().getText());
        boolean ontbijt = Boolean.valueOf(hotelView.getCheckOntbijt().getText());

        // Maak een object (innerclass) en stuur dit naar de dao
        MainApplication.getMySQLHotel().add(new Hotel(accommodatieCode, naam, stad, land, kamertype, aantalPersonen, prijs, ontbijt));
    }

    /**
     * Verwijder een hotel
     */
    private void delete() {
        // Welk hotel is geselecteerd?
        Hotel currentHotel = hotelView.getHotelsViewListView().getSelectionModel().getSelectedItem();
        // Roep de DAO aan om het te verwijderen
        MainApplication.getMySQLHotel().remove(currentHotel);
    }

    /**
     * Maak alle velden leeg.
     */
    private void insert(){
        // maak alle velden leeg
        hotelView.getTxtAccommodatieCode().setText("");
        hotelView.getTxtNaam().setText("");
        hotelView.getTxtStad().setText("");
        hotelView.getTxtLand().setText("");
        hotelView.getTxtKamertype().setText("");
        hotelView.getTxtAantalPersonen().setText("");
        hotelView.getTxtPrijsPerNacht().setText("");
        hotelView.getCheckOntbijt().setText("");
    }

    /**
     * Set alle velden als er een object in de Listview is aangeklikt
     */
    private void getItemsInFields() {
        // Welk object is er geselecteerd?
        Hotel currentHotel = hotelView.getHotelsViewListView().getSelectionModel().getSelectedItem();

        // Update de velden
        hotelView.getTxtAccommodatieCode().setText((currentHotel.getAccommodatieCode()));
        hotelView.getTxtNaam().setText(currentHotel.getNaam());
        hotelView.getTxtStad().setText(currentHotel.getStad());
        hotelView.getTxtLand().setText(currentHotel.getLand());
        hotelView.getTxtKamertype().setText(currentHotel.getKamer());
        hotelView.getTxtAantalPersonen().setText((String.valueOf(currentHotel.getPersonen())));
        hotelView.getTxtPrijsPerNacht().setText(String.valueOf(currentHotel.getPrijsPerNacht()));
        hotelView.getCheckOntbijt().setSelected(currentHotel.isOntbijt());
    }

    /**
     * Methode om de view door te geven zoals dat ook bij OOP2 ging
     * @return View
     */
    @Override
    public View getView() {
        return hotelView;
    }
}
