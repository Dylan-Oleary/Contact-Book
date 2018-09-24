package MVC.Models;

import java.time.LocalDate;

public class Tester {

    public static void main (String[] args){

        Person p = new Person("Dylab", "Wild", "Male", "1992-09-14", "42 Hell Yeah", "6-472031946");

        System.out.print(p.getPhoneNumber());
        p.isValidPhoneNumber();
        System.out.print(p.isValidPhoneNumber());

    }
}
