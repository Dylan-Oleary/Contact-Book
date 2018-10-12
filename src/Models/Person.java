package Models;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.time.LocalDate;

/** This class creates a Person object which we will add to the contact book */

public class Person {
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate birthday;
    private String address;
    private String phoneNumber;
    private String occupation;
    private File imageFile;


    public Person (String firstName, String lastName, String gender, LocalDate birthday, String address,String phoneNumber, String occupation){
        setFirstName(firstName);
        setLastName(lastName);
        setGender(gender);
        setBirthday(birthday);
        setAddress(address);
        setPhoneNumber(phoneNumber);
        setOccupation(occupation);
        setImageFile(new File("./src/images/contact-icon.png"));
    }

    public Person (String firstName, String lastName, String gender, LocalDate birthday, String address,String phoneNumber, String occupation, File imageFile){
        setFirstName(firstName);
        setLastName(lastName);
        setGender(gender);
        setBirthday(birthday);
        setAddress(address);
        setPhoneNumber(phoneNumber);
        setOccupation(occupation);
        setImageFile(imageFile);
        copyImageFile();
    }

    public Person(String phoneNumber){
        setPhoneNumber(phoneNumber);
    }

    public String getFirstName() {
        return firstName;
    }

    /** Sets the firstName variable and makes the first letter capital */

    public void setFirstName(String firstName) {
        if(!firstName.isEmpty()){
            this.firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase();
        }else{
            throw new IllegalArgumentException("Field cannot be empty!");
        }
    }

    public String getLastName() {
        return lastName;
    }

    /** Sets the lastName variable and makes the first letter capital */

    public void setLastName(String lastName) {
        if(!lastName.isEmpty()){
            this.lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase();
        }else{
            throw new IllegalArgumentException("Field cannot be empty!");
        }
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday (LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getGender(){
        return gender;
    }

    public void setGender(String gender){

        if(gender.equalsIgnoreCase("male")){
            this.gender = "Male";
        }else if(gender.equalsIgnoreCase("female")){
            this.gender = "Female";
        }else{
            this.gender = "Other";
        }
    }

    public String getAddress() {
        return address;
    }

    /** Sets the address variable and throws an exception if the field is empty */

    public void setAddress(String address) {
        if(!address.isEmpty()){
            this.address = address;
        }else{
            throw new IllegalArgumentException("Address cannot be empty");
        }
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    /** Sets the phoneNumber variable. The format xxx-xxx-xxxx will always be returned, regardless of user input */

    public void setPhoneNumber(String phoneNumber){
        if(!phoneNumber.isEmpty()){
                if(phoneNumber.substring(3,4).equals("-") && phoneNumber.substring(7,8).equals("-")){
                    this.phoneNumber = phoneNumber;
                }else{
                    this.phoneNumber = phoneNumber.substring(0,3) + "-" + phoneNumber.substring(3,6) + "-" + phoneNumber.substring(6);
                }
        }else{
            throw new IllegalArgumentException("Phone number cannot be blank");
        }
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getOccupation() {
        return occupation;
    }

    public File getImageFile(){
        return imageFile;
    }

    public void setImageFile(File imageFile){
        this.imageFile = imageFile;
    }

    public void copyImageFile() {
        //creates path to copy the image into local directory

        try{
            Path sourcePath = imageFile.toPath();

            String uniqueFileName = getUniqueFileName(imageFile.getName());

            Path targetPath = Paths.get("./src/images"+ uniqueFileName);

            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

            imageFile = new File(targetPath.toString());

        }catch (Exception e){
            System.out.print(e.getMessage());
        }

    }

    /**
     *
     *
     * This method will receive a string that represents a file name and return a string with a random, unique set of letters prefixed to it
     *
     */

    private String getUniqueFileName(String fileName){
        SecureRandom rng = new SecureRandom();
        String newName;

        do{

            newName = "";

            for(int i = 1; i <=32; i++){
                int nextChar;

                do{
                    nextChar = rng.nextInt(123);

                }while(!validChatacterValue(nextChar)); //retun false when char is invalid, continues to the first do-loop when true

                newName = String.format("%s%c", newName, nextChar);
            }

            newName += fileName;

        }while (!uniqueFileInDirectory(newName));
        return newName;
    }

    public boolean validChatacterValue(int nextChar){

        if (nextChar >= 48 && nextChar <= 57)
            return true;

        if(nextChar >= 65 && nextChar <= 90)
            return true;

        if(nextChar >= 97 && nextChar <= 122)
            return true;

        return false;
    }

    public boolean uniqueFileInDirectory (String fileName){

        File directory = new File("./src/images");

        File [] dir_contents = directory.listFiles();

        for(File file: dir_contents){
            if(file.getName().equals(fileName))
                return false;
        }
        return true;
        }

    @Override
    public String toString(){
        return "Hey " + firstName + " you are a cool person";
    }
}
