package emergencias.sainthannaz.com.emergenciascoatza.model;

/**
 * Created by Gabriel on 13/11/2017.
 */

public class InnerTables {
    int card_id, unique_id;
    String card_title, card_description, card_category, card_map;

    public InnerTables() {
    }

    public InnerTables(int card_id, String card_title, String card_description, String card_category, int unique_id, String card_map) {
        this.card_id = card_id;
        this.card_title = card_title;
        this.card_description = card_description;
        this.card_category = card_category;
        this.unique_id = unique_id;
        this.card_map = card_map;
    }

    public InnerTables(String card_title, String card_description, String card_category, int unique_id, String card_map) {
        this.card_title = card_title;
        this.card_description = card_description;
        this.card_category = card_category;
        this.unique_id = unique_id;
        this.card_map = card_map;
    }

    public int getCard_id() {
        return card_id;
    }
    public void setCard_id(int id) {
        this.card_id = id;
    }

    public String getCard_title() {
        return card_title;
    }
    public void setCard_title(String title) {
        this.card_title = title;
    }

    public String getCard_description() {
        return card_description;
    }
    public void setCard_description(String description) {
        this.card_description = description;
    }

    public String getCard_category() {
        return card_category;
    }
    public void setCard_category(String category) {
        this.card_category = category;
    }

    public String getCard_map() {
        return card_map;
    }
    public void setCard_map(String map) {
        this.card_map = map;
    }

    public int getCard_unique_id(){
        return unique_id;
    }
    public void setCard_unique_id(int unique_id) {
        this.unique_id = unique_id;
    }
}
