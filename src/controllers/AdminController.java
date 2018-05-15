
package controllers;

import mains.Model;

//not public, because we want only Controller to access this class
class AdminController {
    Model model;
    Controller cont;
    public AdminController(Controller controller, Model mod){
        model = mod;
        cont = controller;
        //TODO
    }
}
