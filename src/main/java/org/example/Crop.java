package org.example;

public class Crop extends Entity{

    private String cropType;
    private int quantity;



    public Crop(String cropType, int quantity, int ID, String description) {
        this.cropType = cropType;
        this.quantity = quantity;
        this.setId(ID);
        this.description = description;

    }

    public @Override String GetDescription(){
        return description;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
