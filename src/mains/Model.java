package mains;

import additional.ConnException;
import people.*;

import java.sql.*;
import java.util.Properties;


import static additional.ConnException.ErrorTypes.*;

public class Model {
    private Connection conn;
    private Statement stat;
    public enum UserType{admin, student, teacher};
    private UserType userType;
    Person me;
    public Model() {


    }

    /**
     * Method checkUser gets information from the database tables (Teachers, Students and Admins)
     * and compares it with data given by user (mail and password).
     *
     * Method implemented in this class method openConnection
     * @see #logIn(String, String)
     *
     * @param mail e-mail adress entered by user in the InitialView
     * @param enteredPassword pass entered by user in view
     * @return type of user (enum) - admin, teacher or student. On this depends type of downloaded data
     * and consequently view
     * @throws ConnException informs InitView module about type of error occuring
     * (connection with database, wrong mail, wrong password)
     * @throws SQLException if the process of connection with database breaks, then this exception is thrown
     */
UserType checkUser(String mail, String enteredPassword)throws ConnException, SQLException {
    String pass, query;
    ResultSet result;
    Statement statement = conn.createStatement();

    //STUDENT
    query = "SELECT pass FROM Students WHERE mail='" + mail + "' LIMIT 1;";

    result = statement.executeQuery(query);
    if (result.next()) {//if something was found
        pass = result.getString("pass");
        if (pass.equals(enteredPassword))
            return UserType.student;
        //if pass from database doesn't equal entered password
        throw new ConnException(wrongPass);
    }
    //TEACHER - checking if logging user is a teacher or admin
    query = "SELECT pass,admin FROM Teachers WHERE mail='" + mail + "' LIMIT 1;";
    result = statement.executeQuery(query);
    if (result.next()) {//if something was found
        pass = result.getString("password");
        if (pass.equals(enteredPassword)) {
            if (result.getBoolean("admin")) {
                return UserType.admin;
            }
            return UserType.teacher;
        }

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
        if(conn!=null&& !conn.isClosed()) conn.close();
    }
    private void openDatabaseConnection()throws SQLException{
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/apograf", "access", "xxxx");
    }
    public void logIn(String mail, String pass)throws ConnException{
        try {
            openDatabaseConnection();
            //check if user has given the right data
            userType = checkUser(mail, pass);
            //download personal data
            Statement statement = conn.createStatement();
            String query;
            switch(userType){
                case teacher:
                    query = "SELECT * FROM Teachers WHERE mail='"+mail+"' LIMIT 1;";
                    me = new Teacher(statement.executeQuery(query));
                    break;
                case admin:
                    query = "SELECT * FROM Teachers WHERE mail='"+mail+"' LIMIT 1;";
                    me = new Admin(statement.executeQuery(query));
                    break;
                case student:default:
                    query = "SELECT * FROM Students WHERE mail='"+mail+"' LIMIT 1;";
                    me = new Student(statement.executeQuery(query));
            }
            statement.close();
        }
        catch(SQLException e){
            throw new ConnException(err);
        }
    }
    public void register(String mail, String pass)throws ConnException{
        try {
            openDatabaseConnection();

            ResultSet result;
            Statement statement = conn.createStatement();
            String query = "SELECT mail FROM Teachers WHERE mail='"+mail+"' LIMIT 1;";
            //TEACHERS - checking if there is mail existing in database

            result = statement.executeQuery(query);
            if(result.next()){
                throw new ConnException(existing);
            }
            //STUDENTS
            query = "SELECT mail FROM Students WHERE mail='"+mail+"' LIMIT 1;";
            result = statement.executeQuery(query);
            if(result.next()){
                throw new ConnException(existing);
            }
            //WAITING
            query = "SELECT mail FROM Waiting WHERE mail='"+mail+"' LIMIT 1;";
            result = statement.executeQuery(query);
            if(result.next()){
                throw new ConnException(existing);
            }
            statement.close();
            //enter this mail and password into waiting
            Statement stmt=conn.createStatement();
            stmt.executeUpdate("INSERT INTO Waiting (mail, pass) VALUES ('"+mail+"', '"+pass+"');");
            stmt.close();
        } catch (SQLException e) {
            throw new ConnException(err);
        }
    }

    public UserType getUserType(){
        return userType;
    }
}
