package controllers;

import mains.Model;
import profile.ProfileController;
import views.Window;

class StudentController extends PersonController {
    Model model;
    public StudentController(Controller controller, Model mod){
        cont = controller;
        model = mod;
        window = new Window();
        try {
            ProfileController profileController = new ProfileController(this, model);
        }catch(Exception e){

        }
    }
}
