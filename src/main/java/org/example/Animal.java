package org.example;


import java.util.Objects;

public class Animal extends Entity{
    private String species;
    private String Food;


    public Animal(String species, int ID, String Food, String description) {
        this.species = species;
        this.setId(ID);
        this.Food = Food;
        this.description = description;
    }

    public @Override String GetDescription(){
        return description;
    }
    public void Feed(Crop crop){
        boolean canEat = false;
        if (Food.contains(crop.getCropType())){
            canEat = true;
        }
        if (canEat && crop.getQuantity() > 0){
            System.out.println("The " +species + "ate the " + crop.getCropType() + ".");
            crop.setQuantity(crop.getQuantity()-1);
        }else{
            if (!canEat){
                System.out.println("The " +species + "cat eat the " + crop.getCropType() + ".");
            }
            if (crop.getQuantity() == 0){
                System.out.println("You have no " + crop.getCropType() + " left.");
            }
        }
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getFood() {
        return Food;
    }
}
