package controllers;

import mains.Model;

class TeacherController {
    Model model;
    Controller cont;
    public TeacherController(Controller controller, Model mod){
        model = mod;
        cont = controller;

    }

}
