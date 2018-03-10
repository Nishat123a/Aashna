package aashna.com.aashna.aashna_main;


public class Card {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    private int thumbnail;

    public Card(String name, int thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
    }



}
