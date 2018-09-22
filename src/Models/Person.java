package Models;

import javafx.scene.image.Image;

import java.util.Date;

/** This class creates a Person object which we will add to the contact book */

public class Person {
    private String firstName;
    private String lastName;
    private String gender;
    private Date birthday;
    private String address;
    private String phoneNumber;
    private String occupation;
    private Image image;

    /** Since the IMAGE and OCCUPATION variables are optional, 2 person constructors are created */

    public Person (String firstName, String lastName,String gender, String address,String phoneNumber){
        setFirstName(firstName);
        setLastName(lastName);
        setGender(gender);
        setAddress(address);
        setPhoneNumber(phoneNumber);
    }

    /** This constructor includes the IMAGE and OCCUPATION instance variables */

    public Person (String firstName, String lastName, String gender, Date birthday, String address,String phoneNumber, String occupation, Image image){
        setFirstName(firstName);
        setLastName(lastName);
        setGender(gender);
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

    public Date getBirthday() {
        return birthday;
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

    public void setBirthday (Date birthday) {
        this.birthday = birthday;
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
            if(phoneNumber.contains("-")){
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

    public Image getImage(){
        return image;
    }

    public void setImage(Image image){
        this.image = image;
    }
}
