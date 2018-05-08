package mains;

public class Main {
    public static void main(String [] args){
        Controller cont = new Controller();
        Model model = new Model(cont);
        View view = new View(cont, model);
    }
}
