package controllers;

import mains.Model;
import views.WindowPattern;

import javax.naming.ldap.Control;

class StudentController {
    Model model;
    Controller cont;
    public StudentController(Controller controller, Model mod){
        model = mod;
        cont = controller;

    }
}
