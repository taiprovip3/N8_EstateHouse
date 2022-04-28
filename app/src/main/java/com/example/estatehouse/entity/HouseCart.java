package com.example.estatehouse.entity;

public class HouseCart {
    private double price;
    private String seller;
    private int bedrooms;
    private int bathrooms;
    private int livingarea;
    private int image;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public int getLivingarea() {
        return livingarea;
    }

    public void setLivingarea(int livingarea) {
        this.livingarea = livingarea;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public HouseCart() {
    }

    public HouseCart(double price, String seller, int bedrooms, int bathrooms, int livingarea, int image) {
        this.price = price;
        this.seller = seller;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.livingarea = livingarea;
        this.image = image;
    }

    @Override
    public String toString() {
        return "HouseCart{" +
                "price=" + price +
                ", seller='" + seller + '\'' +
                ", bedrooms=" + bedrooms +
                ", bathrooms=" + bathrooms +
                ", livingarea=" + livingarea +
                ", image=" + image +
                '}';
    }
}
