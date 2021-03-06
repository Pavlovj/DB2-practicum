package nl.hva.ict.data.MongoDB;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import nl.hva.ict.MainApplication;
import nl.hva.ict.models.Reiziger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Haal alle reizigers van Big Five Safari op uit de NoSQL database
 */
public class MongoReizigers extends MongoDB {

    private final List<Reiziger> reizigers;

    /**
     * Construtor
     */
    public MongoReizigers() {

        // init de arraylist
        reizigers = new ArrayList<>();

        // startup methode
        load();
    }

    /**
     * Haal alle objecten op. Niet gebruikt in deze class maar door de interface data wel verplicht
     * @return een lijst
     */
    @Override
    public List getAll() {
        return reizigers;
    }

    /**
     * Haal 1 object op. Niet gebruikt in deze class maar door de interface data wel verplicht
     * @return een object
     */
    @Override
    public Object get() {
        return null;
    }

    /**
     * Voeg een object toe aan de arraylist. Niet gebruikt in deze class maar door de interface data wel verplicht
     */
    @Override
    public void add(Object object) {

        if (object instanceof HashMap<?, ?>) {
            HashMap<String, String> reizigerObj = (HashMap<String, String>) object;
            collection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("reiziger_code", reizigerObj.get("reiziger_code"))
                    .append("voornaam", reizigerObj.get("voornaam"))
                    .append("achternaam", reizigerObj.get("achternaam"))
                    .append("adres", reizigerObj.get("adres"))
                    .append("postcode", reizigerObj.get("postcode"))
                    .append("plaats", reizigerObj.get("plaats"))
                    .append("land", reizigerObj.get("land"))
                    .append("gezinshoofd", reizigerObj.get("gezinshoofd"))
            );
            load();
        }
    }


    /**
     * Update een object toe aan de arraylist. Niet gebruikt in deze class maar door de interface data wel verplicht
     */
    @Override
    public void update(Object object) {
        if (object instanceof HashMap<?, ?>) {
            HashMap<String, String> reizigerObj = (HashMap<String, String>) object;
            Bson filter = Filters.eq("reiziger_code", reizigerObj.get("oldReizigersCode"));
            Bson update = Updates.combine(
                    Updates.set("reiziger_code", reizigerObj.get("newReizigersCode")),
                    Updates.set("voornaam", reizigerObj.get("voornaam")),
                    Updates.set("achternaam", reizigerObj.get("achternaam")),
                    Updates.set("adres", reizigerObj.get("adres")),
                    Updates.set("postcode", reizigerObj.get("postcode")),
                    Updates.set("plaats", reizigerObj.get("plaats")),
                    Updates.set("land", reizigerObj.get("land")),
                    Updates.set("gezinshoofd", reizigerObj.get("gezinshoofd"))
            );

            UpdateResult result = collection.updateOne(filter, update);
            load();
        }
    }

    /**
     * Update een object toe aan de arraylist. Niet gebruikt in deze class maar door de interface data wel verplicht
     */
    @Override
    public void remove(Object object) {
        if (object instanceof Reiziger) {
            Bson filter = Filters.eq("reiziger_code", ((Reiziger) object).getReizigersCode());
            DeleteResult result = collection.deleteOne(filter);
            load();
        }
    }

    /**
     * Laad bij startup
     */
    public void load() {

        // Als je geen NoSQL server hebt opgegeven gaat de methode niet verder anders zou je een nullpointer krijgen
        if (MainApplication.getNosqlHost().equals(""))

            return;

        // Selecteer de juiste collecton in de NoSQL server
        this.selectedCollection("reiziger");

        // Haal alles op uit deze collection en loop er 1 voor 1 doorheen
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            // Zolang er data is
            while (cursor.hasNext()) {
                // warning Java is case sensitive
                // Haal alle velden per record
                Document tempReiziger = cursor.next();
                String reizigerCode = (String) tempReiziger.get("reizigers_code");
                String voornaam = (String) tempReiziger.get("voornaam");
                String achternaam = (String) tempReiziger.get("achternaam");
                String adres = (String) tempReiziger.get("adres");
                String postcode = (String) tempReiziger.get("postcode");
                String plaats = (String) tempReiziger.get("plaats");
                String land = (String) tempReiziger.get("land");
                String hoofdreiziger = (String) tempReiziger.get("gezinshoofd");

                // Maak een nieuw object en voeg deze toe aan de arraylist
                reizigers.add(new Reiziger(reizigerCode, voornaam, achternaam, adres, postcode, plaats, land, hoofdreiziger));
            }
        } finally {
            // Sluit de stream
            cursor.close();
        }
    }
}
