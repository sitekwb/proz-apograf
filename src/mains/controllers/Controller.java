package mains.controllers;

import init.InitController;
import mains.Model;

public class Controller {
    private Model model;
    private InitController initController;
    public Controller(Model mod) {
        model=mod;
        initController = new InitController(this, model);
    }

    protected Controller() {

    }
    public void signOut(){
        initController.startViewAgain();
    }
    public void startController() {
        switch (model.getUserType()) {
            case student:
                StudentController studentController = new StudentController(this, model);
                break;
            case teacher:
                TeacherController TeacherController = new TeacherController(this, model);
                break;
            case admin:
                AdminController AdminController = new AdminController(this, model);
                break;
        }
    }
}