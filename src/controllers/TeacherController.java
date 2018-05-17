package controllers;

import mains.Model;

class TeacherController  extends PersonController{
    Model model;
    public TeacherController(Controller controller, Model mod){
        model = mod;
        cont = controller;

    }

}
