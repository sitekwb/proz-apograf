package controllers;

import mains.Model;

public class Controller {
    private Model model;
    public Controller(Model mod) {
        model=mod;
        InitController initController = new InitController(this, model);
    }

    protected Controller() {

    }

    public void startController(Model.UserType type) {
        switch (type) {
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