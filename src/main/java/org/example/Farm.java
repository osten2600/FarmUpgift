package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Farm {
    private ArrayList<Animal> animalList;
    private ArrayList<Crop> cropList;
    Scanner scanner = new Scanner(System.in);
    String answer;
    int intAnswer;
    int min;
    int max;
    boolean quiting;

    public Farm() {
        boolean readingCrop = true;
        this.animalList = new ArrayList<>();
        this.cropList = new ArrayList<>();
        char[] tempArray = new char[100];
        String[] fileText;

        try {
            FileReader reader = new FileReader("FarmData.txt");
            reader.read(tempArray);
            fileText = String.valueOf(tempArray).trim().split("\n");
            for (int i = 0; i < fileText.length; i++) {
                String[] variables = fileText[i].split(",");
                if (variables.length < 2){
                    readingCrop = false;
                }else if (readingCrop) {
                    cropList.add(new Crop(variables[2],Integer.parseInt(variables[1]),Integer.parseInt(variables[0]),variables[3]));
                }else {
                    animalList.add(new Animal(variables[1],Integer.parseInt(variables[0]),variables[2],variables[3]));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void MainMenu(){
        while (!quiting){
            System.out.println("What do you want to do?");
            System.out.println("1. View Crops");
            System.out.println("2. Remove Crop");
            System.out.println("3. Add Crop");
            System.out.println("4. View Animals");
            System.out.println("5. Remove Animal");
            System.out.println("6. Add Animal");
            System.out.println("7. Save");
            System.out.println("8. Feed Animal");
            System.out.println("9. Quit");
            switch (ReadInput(1, 8)) {
                case 1 -> ViewCrops();
                case 2 -> RemoveCrop();
                case 3 -> AddCrop();
                case 4 -> ViewAnimals();
                case 5 -> RemoveAnimal();
                case 6 -> AddAnimal();
                case 7 -> Save();
                case 8 -> FeedAnimal();
                case 9 -> quiting = true;
                default -> {
                }
            }
        }
    }

    private void FeedAnimal(){
        ViewAnimals();
        System.out.println("Write the ID of the animal you wish to feed.");
        int temp = ReadInput(0,Integer.MAX_VALUE);
        for (Animal animal:animalList) {
            if (animal.getId() == temp){
                ViewCrops();
                System.out.println("Write the ID of the crop you wish to feed to the animal.");
                temp = ReadInput(0,Integer.MAX_VALUE);
                for (Crop crop:cropList) {
                    if (crop.getId() == temp){
                        animal.Feed(crop);
                        break;
                    }
                }
                break;
            }
        }
    }
    private void ViewCrops(){
        System.out.println("Current crops:");
        for (Crop crop:cropList) {
            System.out.println("ID: " + crop.getId());
            System.out.println("Type: " + crop.getCropType());
            System.out.println("Amount: " + crop.getQuantity());
            System.out.println("Description: " + crop.GetDescription());
            System.out.println();
        }
    }
    private void RemoveCrop(){
        min = Integer.MAX_VALUE;
        max = Integer.MIN_VALUE;
        ViewCrops();
        System.out.println("Write the id of the crop you want to remove.");
        for (Crop crop:cropList) {
            if (crop.getId() > max){
                max = crop.getId();
            }
            if (crop.getId() < min){
                min = crop.getId();
            }
        }
        intAnswer = ReadInput(min,max);
        cropList.removeIf(crop -> crop.getId() == intAnswer);
    }
    private void AddCrop(){
        boolean loop = true;
        ViewCrops();
        System.out.println("What ID do you want your crop to have?");
        while (loop){
            loop = false;
            intAnswer = ReadInput(0,Integer.MAX_VALUE);
            for (Crop crop:cropList) {
                if (intAnswer == crop.getId()) {
                    loop = true;
                    break;
                }
            }
            if (loop){
                System.out.println("That ID already belongs to a crop.");
            }
            else {
                System.out.println("What type of crop is it?");
                String temp = scanner.nextLine();
                System.out.println("What is the amount of the crop?");
                int temp2 = ReadInput(0,Integer.MAX_VALUE);
                System.out.println("What is the description of crop?");
                String temp3 = scanner.nextLine();
                cropList.add(new Crop(temp,temp2,intAnswer,temp3));
            }
        }
    }
    private void ViewAnimals(){
        System.out.println("Current animals:");
        for (Animal animal:animalList) {
            System.out.println("ID: " + animal.getId());
            System.out.println("Type: " + animal.getSpecies());
            System.out.println("Description: " + animal.GetDescription());
            System.out.println();
        }
    }
    private void AddAnimal(){
        boolean loop = true;
        ViewAnimals();
        System.out.println("What ID do you want your animal to have?");
        while (loop){
            loop = false;
            intAnswer = ReadInput(0,Integer.MAX_VALUE);
            for (Animal animal:animalList) {
                if (intAnswer == animal.getId()) {
                    loop = true;
                    break;
                }
            }
            if (loop){
                System.out.println("That ID already belongs to an animal.");
            }
            else {
                System.out.println("What type of animal is it?");
                String temp = scanner.nextLine();
                System.out.println("What does the animal eat?(Pleas write the foods with only spaces between them.");
                String temp2 = scanner.nextLine();
                System.out.println("What is the description of animal?");
                String temp3 = scanner.nextLine();
                animalList.add(new Animal(temp,intAnswer,temp2,temp3));
            }
        }
    }

    private void RemoveAnimal(){
        min = Integer.MAX_VALUE;
        max = Integer.MIN_VALUE;
        ViewAnimals();
        System.out.println("Write the id of the animal you want to remove.");
        for (Animal animal:animalList) {
            if (animal.getId() > max){
                max = animal.getId();
            }
            if (animal.getId() < min){
                min = animal.getId();
            }
        }
        intAnswer = ReadInput(min,max);
        animalList.removeIf(animal -> animal.getId() == intAnswer);

    }
    private void Save(){
        File file = new File("FarmData.txt");
        try {
            FileWriter fileWriter = new FileWriter("FarmData.txt");
            for (Crop crop:cropList) {
                fileWriter.write(crop.getId() +"," + crop.getQuantity() +"," + crop.getCropType() + "," + crop.GetDescription());
            }
            fileWriter.write("w");
            for (Animal animal:animalList) {
                fileWriter.write(animal.getId() +"," + animal.getSpecies() +"," + animal.getFood() + "," + animal.GetDescription());
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }

    }

    private int ReadInput(int min, int max){
        while (true){
            answer = scanner.nextLine();
            try {
                intAnswer = parseInt(answer);
                if (intAnswer >= min && intAnswer <= max) {
                    break;
                }
            }catch (Exception ignored){

            }
            System.out.println("Pleas only answer with a number.");
        }
        return intAnswer;
    }
}
