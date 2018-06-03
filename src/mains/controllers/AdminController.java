
package mains.controllers;

import mains.Model;
import views.Window;

//not public, because we want only Controller to access this class
class AdminController  extends PersonController {
    public AdminController(Controller controller, Model mod){
        super(controller, mod);
    }
}
