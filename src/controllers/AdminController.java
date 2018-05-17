
package controllers;

import mains.Model;

//not public, because we want only Controller to access this class
class AdminController  extends PersonController {
    Model model;
    public AdminController(Controller controller, Model mod){
        model = mod;
        cont = controller;
        //TODO
    }
}
