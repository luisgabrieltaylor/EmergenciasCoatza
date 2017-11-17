package emergencias.sainthannaz.com.emergenciascoatza.model;

/**
 * Created by Lincoln on 15/01/16.
 */
public class Numbers {
    int card_id;
    String card_title, card_description, card_picture, card_category, card_update;

    public Numbers() {
    }

    public Numbers(int card_id, String card_title, String card_description, String card_picture, String card_category, String card_update) {
        this.card_id = card_id;
        this.card_title = card_title;
        this.card_description = card_description;
        this.card_picture = card_picture;
        this.card_category = card_category;
        this.card_update = card_update;
    }

    public Numbers(String card_title, String card_description, String card_picture, String card_category, String card_update) {
        this.card_title = card_title;
        this.card_description = card_description;
        this.card_picture = card_picture;
        this.card_category = card_category;
        this.card_update = card_update;
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

    public String getCard_picture() {
        return card_picture;
    }
    public void setCard_picture(String picture) {
        this.card_picture = picture;
    }

    public String getCard_category() {
        return card_category;
    }
    public void setCard_category(String category) {
        this.card_category = category;
    }

    public String getCard_update() {
        return card_update;
    }
    public void setCard_update(String update) {
        this.card_update = update;
    }


}
