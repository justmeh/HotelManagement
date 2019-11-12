package controllers;

public class InputVerifier {

    public boolean onlyLetters(String aString){
        if(aString.matches("[0-9]+") && aString.length() > 1 ){
            return true;
        }
        return false;
    }

    public boolean onlyDigits(String aString){
        if(aString.matches("[a-zA-Z ]+") && aString.length() > 1) {
            return true;
        }
        return false;
    }
}
