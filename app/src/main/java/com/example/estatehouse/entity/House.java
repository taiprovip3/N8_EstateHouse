package com.example.estatehouse.entity;

import java.util.ArrayList;
import java.util.List;

public class House {
    private String type;
    private double cost;
    private String address;
    private double sale;
    private List<String> tags;
    private int bedrooms;
    private int bathrooms;
    private int livingarea;
    private String image;

    public String getType() {
        return type;
    }

    public void setType(String name) {
        this.type = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public House() {
    }

    public House(String type, double cost, String address, double sale, List<String> tags, int bedrooms, int bathrooms, int livingarea, String image) {
        this.type = type;
        this.cost = cost;
        this.address = address;
        this.sale = sale;
        this.tags = tags;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.livingarea = livingarea;
        this.image = image;
    }

    @Override
    public String toString() {
        return "House{" +
                "name='" + type + '\'' +
                ", cost=" + cost +
                ", address='" + address + '\'' +
                ", sale=" + sale +
                ", tags=" + tags +
                ", bedrooms=" + bedrooms +
                ", bathrooms=" + bathrooms +
                ", livingarea=" + livingarea +
                ", image='" + image + '\'' +
                '}';
    }
}
