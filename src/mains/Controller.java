package mains;

import additional.ConnException;

public class Controller {
    Model model;
    public Controller(Model mod){
        model = mod;
    }
    public void register (String mail, String pass)throws ConnException{
        //TODO
    }
    public void logIn (String mail, String pass)throws ConnException {
        model.openConnection(mail,pass);
    }

}
