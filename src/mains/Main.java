package mains;

public class Main {
    public static void main(String [] args){
        Model model = new Model();
        Controller cont = new Controller(model);
        View view = new View(cont);
    }
}
