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
        String pass,query;

        PreparedStatement statement;
        query = "SELECT password ? FROM ? WHERE mail='?' LIMIT 1;";
        statement = conn.prepareStatement(query);
        statement.setString(3,mail);

        ResultSet result;

        //STUDENT
        statement.setString(1,"");
        statement.setString(2,"Students");
        result = statement.executeQuery();
        if(result.next()){//if something was found
            pass = result.getString("password");
            if(pass.equals(enteredPassword)) return UserType.student;
            //if pass from database doesn't equal entered password
            throw new ConnException(wrongPass);
        }
        //TEACHER - checking if logging user is a teacher or admin
        statement.setString(1,",admin");
        statement.setString(2,"Teachers");
        result = statement.executeQuery();
        if(result.next()){//if something was found
            pass = result.getString("password");
            if(pass.equals(enteredPassword)) {
                if(result.getBoolean("admin")){
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
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/apograf", "root", "xxx");//jdbc:mysql://localhost:3306
    }
    public void logIn(String mail, String pass)throws ConnException{
        try {
            openDatabaseConnection();
            //check if user has given the right data
            userType = checkUser(mail, pass);
            //download personal data
            PreparedStatement prepSt = conn.prepareStatement("SELECT * FROM ? WHERE mail=? LIMIT 1;");
            prepSt.setString(2,mail);
            Person me;
            switch(userType){
                case teacher:
                    prepSt.setString(1,"Teachers");
                    me = new Teacher(prepSt.executeQuery());
                    break;
                case admin:
                    prepSt.setString(1,"Teachers");
                    me =new Admin(prepSt.executeQuery());
                    break;
                case student:default:
                    prepSt.setString(1,"Students");
                    me = new Student(prepSt.executeQuery());
            }
            prepSt.close();
        }
        catch(SQLException e){
            throw new ConnException(err);
        }
    }
    public void register(String mail, String pass)throws ConnException{
        try {
            openDatabaseConnection();

            ResultSet result;
            PreparedStatement statement;
            String query = "SELECT mail FROM ? WHERE mail=? LIMIT 1;";
            statement = conn.prepareStatement(query);
            statement.setString(2,mail);

            //TEACHERS - checking if there is mail existing in database
            statement.setString(1,"Teachers");
            result = statement.executeQuery();
            if(result.next()){
                throw new ConnException(existing);
            }
            //STUDENTS
            statement.setString(1,"Students");
            result = statement.executeQuery();
            if(result.next()){
                throw new ConnException(existing);
            }
            //ADMINS
            statement.setString(1,"Admins");
            result = statement.executeQuery();
            if(result.next()){
                throw new ConnException(existing);
            }
            //WAITING
            statement.setString(1,"Waiting");
            result = statement.executeQuery();
            if(result.next()){
                throw new ConnException(existing);
            }
            statement.close();
            //enter this mail and password into waiting
            Statement stmt=conn.createStatement();
            stmt.executeUpdate("INSERT INTO Waiting VALUES ("+mail+", "+pass+");");
            stmt.close();
        } catch (SQLException e) {
            throw new ConnException(err);
        }
    }

    public UserType getUserType(){
        return userType;
    }
}
