package com.media.conexahotspot.Item;

public class PaketItem {
    private int imageResId;
    private String title;
    private String description;
    private String price;

    public PaketItem(int imageResId, String title, String description, String price) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public  int getImageResId(){
        return imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }
}
