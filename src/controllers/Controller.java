package controllers;

import mains.Model;

public class Controller {

    public Controller(Model model){
        InitController initController = new InitController(this, model);
    }
    protected Controller(){

    }
    protected void startController(Model.UserType type){

    }
}
