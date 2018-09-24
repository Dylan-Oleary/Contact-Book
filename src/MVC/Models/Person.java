package MVC.Models;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.Date;

/** This class creates a Person object which we will add to the contact book */

public class Person {
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate birthday;
    private String address;
    private String phoneNumber;
    private String occupation;
    private Image image;

    /** Since the IMAGE and OCCUPATION variables are optional, 2 person constructors are created */

    public Person (String firstName, String lastName, String gender, String birthday, String address, String phoneNumber){
        setFirstName(firstName);
        setLastName(lastName);
        setBirthday(birthday);
        setGender(gender);
        setAddress(address);
        setPhoneNumber(phoneNumber);
    }

    /** This constructor includes the IMAGE and OCCUPATION instance variables */

    public Person (String firstName, String lastName, String gender, String birthday, String address,String phoneNumber, String occupation, Image image){
        setFirstName(firstName);
        setLastName(lastName);
        setGender(gender);
        setBirthday(birthday);
        setAddress(address);
        setPhoneNumber(phoneNumber);
        setOccupation(occupation);
        setImage(image);
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

    public void setBirthday (String birthday) {
        this.birthday = LocalDate.parse(birthday);
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

    public String getWorkplace() {
        return occupation;
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

    public boolean isValidPhoneNumber(){
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        int x = 1;

        for(int i = 0; i < alphabet.length(); i++){

            if(i == alphabet.length() - 1){
                String check = alphabet.substring(i);

                if(phoneNumber.contains(check)){
                    return false;
                }
            }

            String check = alphabet.substring(i,x);

            if(phoneNumber.contains(check)){
                return false;
            }

            x++;
        }

        if(phoneNumber.length() > 10){
            return false;
        }
        return true;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Image getImage(){
        return image;
    }

    public void setImage(Image image){
        this.image = image;
    }

    @Override
    public String toString(){
        return "Hey " + firstName + " you are a cool person";
    }
}
