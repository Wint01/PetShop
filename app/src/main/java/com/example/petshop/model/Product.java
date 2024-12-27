package com.example.petshop.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private int id;
    private String title;
    private String description;
    private String color;
    private String price;
    private String image;
    private int category;

    public Product(int id, String title, String description, String color, String price, String image, int category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.color = color;
        this.price = price;
        this.image = image;
        this.category = category;
    }

    protected Product(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        color = in.readString();
        price = in.readString();
        image = in.readString();
        category = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(color);
        dest.writeString(price);
        dest.writeString(image);
        dest.writeInt(category);
    }

    public double getPriceDouble() {
        try {
            return Double.parseDouble(price.replace("$", "").replace("₽", ""));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}