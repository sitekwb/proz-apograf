package mains;

import additional.ConnException;

import java.sql.*;

import static additional.ConnException.ErrorTypes.*;

public class Model {
    private Connection conn;
    private Statement stat;
    public enum UserType{admin, student, teacher};
    private UserType userType;
    public Model() {


    }

    /**
     * Method checkUser gets information from the database tables (Teachers, Students and Admins)
     * and compares it with data given by user (mail and password).
     *
     * Method implemented in this class method openConnection
     * @see #openConnection(String, String)
     *
     * @param mail e-mail adress entered by user in the InitialView
     * @param enteredPassword pass entered by user in view
     * @return type of user (enum) - admin, teacher or student. On this depends type of downloaded data
     * and consequently view
     * @throws ConnException informs View module about type of error occuring
     * (connection with database, wrong mail, wrong password)
     * @throws SQLException if the process of connection with database breaks, then this exception is thrown
     */
    UserType checkUser(String mail, String enteredPassword)throws ConnException, SQLException {
        String pass,query;

        PreparedStatement statement;
        query = "SELECT password FROM ? WHERE mail='?' LIMIT 1;";
        statement = conn.prepareStatement(query);
        statement.setString(2,mail);

        ResultSet result;

        //TEACHER - checking if logging user is a teacher
        statement.setString(1,"Teachers");
        result = statement.executeQuery();
        if(result.next()){//if something was found
            pass = result.getString("password");
            if(pass.equals(enteredPassword)) return UserType.teacher;
            //if pass from database doesn't equal entered password
            throw new ConnException(wrongPass);
        }
        //STUDENT
        statement.setString(1,"Students");
        result = statement.executeQuery();
        if(result.next()){//if something was found
            pass = result.getString("password");
            if(pass.equals(enteredPassword)) return UserType.student;
            //if pass from database doesn't equal entered password
            throw new ConnException(wrongPass);
        }
        //ADMIN
        statement.setString(1,"Admins");
        result = statement.executeQuery();
        if(result.next()){//if something was found
            pass = result.getString("password");
            if(pass.equals(enteredPassword)) return UserType.admin;
            //if pass from database doesn't equal entered password
            throw new ConnException(wrongPass);
        }
        //if e-mail was not found in any database => throw not-existing exception
        throw new ConnException(notExitsting);
    }

    /**
     * Closes connection with database
     * @throws SQLException if something goes wrong with closing connection, the exception is thrown
     */
    public void closeConnection()throws SQLException{
        conn.close();
    }
    public void openConnection(String mail, String pass)throws ConnException{
        try {
            //getting connection with database
            conn = DriverManager.getConnection("jdbc:mysql://localhost", "root", "xxx");
            stat = conn.createStatement();
            stat.execute("USE apograf;");

            //check if user has given the right data
            userType = checkUser(mail, pass);


            //TODO download personal data
        }
        catch(SQLException e){
            throw new ConnException(err);
        }
    }

}
