package people;

public abstract class Person {
    protected String mail, password, name;
    protected int id;
    public String getMail(){
        return mail;
    }
    public String getPassword(){
        return password;
    }
    public String getName(){
        return name;
    }
    public int getId(){
        return id;
    }
    public void setMail(String m){
        mail = m;
    }
    public void setName(String n){
        name = n;
    }
}
