package mains;

import java.sql.*;

public class Model {
    private boolean logFlag;
    private Connection conn;
    private Statement stat;
    public enum UserType{err, notExisting, wrongPass, admin, student, teacher};
    public Model(Controller cont) {
        logFlag=false;

    }

    void signIn(String mail, String pass){
        if(openConnection()) return ;

    }
    UserType checkUser(String mail){
        try {
            if (conn.isClosed()) return UserType.err;
            String query = "SELECT * FROM Teachers WHERE mail='" + mail + "' LIMIT 1;";
            ResultSet result;
            result = stat.executeQuery(query);
            //id | name       | surname | mail                | phone | country | password

        }
        catch(SQLException e){
            closeConnection();
        }
        return UserType.notExisting;
    }

    boolean closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            return true;
        }
        logFlag=false;
        return false;
    }
    boolean openConnection(){
        if (logFlag) return true;

        try{
            conn = DriverManager.getConnection("localhost","access","1qaz2wsx");
            stat = conn.createStatement();
            stat.execute("USE apograf;");
        }
        catch(SQLException e){
            return true;
        }
        return false;
    }

}
