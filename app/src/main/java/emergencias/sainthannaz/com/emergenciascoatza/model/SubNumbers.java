package emergencias.sainthannaz.com.emergenciascoatza.model;

/**
 * Created by Gabriel on 08/11/2017.
 */

public class SubNumbers { int card_id, phone_card_id;
    String card_address,  card_phone, card_map, card_update;

    public SubNumbers() {
    }

    public SubNumbers(int phone_card_id, int card_id, String card_address, String card_phone, String card_map, String card_update) {
        this.phone_card_id = phone_card_id;
        this.card_id = card_id;
        this.card_address = card_address;
        this.card_phone = card_phone;
        this.card_map = card_map;
        this.card_update = card_update;
    }

    public SubNumbers(int card_id, String card_address, String card_phone, String card_map, String card_update) {
        this.card_id = card_id;
        this.card_address = card_address;
        this.card_phone = card_phone;
        this.card_map = card_map;
        this.card_update = card_update;
    }

    public int getCard_phone_id() {
        return phone_card_id;
    }
    public void setCard_phone_id(int phone_id) {
        this.phone_card_id = phone_id;
    }

    public int getCard_id() {
        return card_id;
    }
    public void setCard_id(int id) {
        this.card_id = id;
    }

    public String getCard_address() {
        return card_address;
    }
    public void setCard_address(String address) {
        this.card_address = address;
    }

    public String getCard_phone() {
        return card_phone;
    }
    public void setCard_phone(String phone) {
        this.card_phone = phone;
    }

    public String getCard_map() {
        return card_map;
    }
    public void setCard_map(String map) {
        this.card_map = map;
    }

    public String getCard_update() {
        return card_update;
    }
    public void setCard_update(String update) {
        this.card_update = update;
    }


}
