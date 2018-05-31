package mains;

import exceptions.ConnException;
import data.*;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;


import static exceptions.ConnException.ErrorTypes.*;

public class Model {
    private Connection conn;

    public enum UserType {admin, student, teacher}

    ;
    private UserType userType;
    Person me;

    public Model() throws SQLException{

    }

    /**
     * Method checkUser gets information from the database tables (Teachers, Students and Admins)
     * and compares it with data given by user (mail and password).
     * <p>
     * Method implemented in this class method openConnection
     *
     * @param mail            e-mail adress entered by user in the InitialView
     * @param enteredPassword pass entered by user in view
     * @return type of user (enum) - admin, teacher or student. On this depends type of downloaded data
     * and consequently view
     * @throws ConnException informs InitView module about type of error occuring
     *                       (connection with database, wrong mail, wrong password)
     * @throws SQLException  if the process of connection with database breaks, then this exception is thrown
     * @see #logIn(String, String)
     */
    UserType checkUser(String mail, String enteredPassword) throws ConnException, SQLException {
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
            pass = result.getString("pass");
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
     *
     * @throws SQLException if something goes wrong with closing connection, the exception is thrown
     */
    public void closeConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) conn.close();
    }

    private void openDatabaseConnection() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/apograf", "access", "xxxx");
    }

    public void logIn(String mail, String pass) throws ConnException {

        if (isHacker(mail) || isHacker(pass)) {
            throw new ConnException(hacker);
        }
        try {
            openDatabaseConnection();
            //check if user has given the right data
            userType = checkUser(mail, pass);
            //download personal data
            Statement statement = conn.createStatement();
            String query;
            switch (userType) {
                case teacher:
                    query = "SELECT * FROM Teachers WHERE mail='" + mail + "' LIMIT 1;";
                    me = new Teacher(statement.executeQuery(query));
                    break;
                case admin:
                    query = "SELECT * FROM Teachers WHERE mail='" + mail + "' LIMIT 1;";
                    me = new Teacher(statement.executeQuery(query));
                    break;
                case student:
                default:
                    query = "SELECT * FROM Students WHERE mail='" + mail + "' LIMIT 1;";
                    me = new Student(statement.executeQuery(query));
            }
            statement.close();
        } catch (SQLException e) {
            throw new ConnException(err);
        }
    }

    private boolean isWaitingInDatabase(String type) throws SQLException, ConnException {
        if (isHacker(type)) {
            throw new ConnException(hacker);
        }
        ResultSet result;
        Statement statement = conn.createStatement();
        String query = "SELECT pass FROM Waiting WHERE mail='" +me.getMail() + "' AND type='"+type+"' LIMIT 1;";
        result = statement.executeQuery(query);
        if (result.next()) {
            statement.close();
            return true; //this value is just waiting in database
        }
        statement.close();
        return false;
    }
    private boolean isMailInDatabase(String mail)throws SQLException{
        ResultSet result;
        Statement statement = conn.createStatement();
        String query = "SELECT mail FROM Teachers WHERE mail='"+mail+"' LIMIT 1;";
        //TEACHERS - checking if there is mail existing in database

        result = statement.executeQuery(query);
        if(result.next()){
            return true; //mail is just in database
        }
        //STUDENTS
        query = "SELECT mail FROM Students WHERE mail='"+mail+"' LIMIT 1;";
        result = statement.executeQuery(query);
        if(result.next()){
            return true;
        }
        //WAITING
        query = "SELECT mail FROM Waiting WHERE mail='"+mail+"' LIMIT 1;";
        result = statement.executeQuery(query);
        if(result.next()){
            return true;
        }
        statement.close();
        return false;
    }

    public void register(String mail, String pass)throws ConnException{
        if(isHacker(mail) || isHacker(pass)){
            throw new ConnException(hacker);
        }
        try {
            openDatabaseConnection();
            if(isMailInDatabase(mail)){
                throw new ConnException(existing);
            }
            //enter this mail and password into waiting
            Statement stmt=conn.createStatement();
            stmt.executeUpdate("INSERT INTO Waiting (mail, pass) VALUES ('"+mail+"', '"+pass+"');");
            stmt.close();
        } catch (SQLException e) {
            throw new ConnException(err);
        }
    }
    public Person getMe(){
        return me;
    }
    public UserType getUserType(){
        return userType;
    }

    public void signOut(){
        me=null;
        userType=null;
        try {
            conn.close();
        }
        catch(SQLException e){
            conn=null;
        }
    }

    public void changePass(String newPassword) throws SQLException, ConnException{
        if(isHacker(newPassword)){
            throw new ConnException(hacker);
        }
        Statement stat;
        stat = conn.createStatement();
        String table;
        if(userType==UserType.student) {
            table = "Students";
        }
        else{
            table = "Teachers";
        }
        stat.executeUpdate("UPDATE "+table+" SET pass='"+newPassword+"' WHERE id = "+me.getId()+";");
        stat.close();
    }

    private static boolean isHacker(String value){
        if(value.contains("'") || value.contains(";")){
            return true;
        }
        return false;
    }
    private void update(String value, String field) throws SQLException, ConnException{
        if(isHacker(value) || isHacker(field)){
            throw new ConnException(hacker);
        }
        Statement stat;
        stat = conn.createStatement();
        String table;
        if(userType==UserType.student) {
            table = "Students";
        }
        else{
            table = "Teachers";
        }
        stat.executeUpdate("UPDATE "+table+" SET "+field+"='"+value+"' WHERE id = "+me.getId()+";");
        if(field.equals("mail")){
            stat.executeUpdate("UPDATE Waiting SET mail='"+value+"' WHERE mail = '"+me.getMail()+"';");
        }
        stat.close();
    }
    public void changeMail(String mail)throws SQLException,ConnException{
        if(isMailInDatabase(mail)){
            throw new ConnException(existing);
        }
        update(mail, "mail");

        me.setMail(mail);
    }
    public void changeName(String name)throws SQLException,ConnException{
        update(name, "name");
        me.setName(name);
    }
    public void askForChange(String name, String type)throws SQLException,ConnException{
        if(isHacker(name) || isHacker(type)){
            throw new ConnException(hacker);
        }
        Statement stat;
        stat = conn.createStatement();
        String table;
        if(isWaitingInDatabase(type)){
            stat.executeUpdate("UPDATE Waiting SET pass='"+name+"' WHERE mail='" +me.getMail() +"' AND type='"+type+"';");
        }
        else {
            stat.executeUpdate("INSERT INTO Waiting (mail, pass, type) VALUES ( '" +me.getMail() + "','" + name + "','" + type + "');");
        }
        stat.close();
    }
    int lastId;
    public ArrayList<Student> getStudents(int showingState, Group group) throws SQLException{
        if(isHacker(group.getName())){
            throw new SQLException();
        }
        if(showingState==0){
            lastId=0;
        }
        Statement stat = conn.createStatement();
        String query = "SELECT id, name FROM Students WHERE class="+group.getId()+" AND id >= "+lastId+
                " ORDER BY id LIMIT 27;";
        ResultSet result = stat.executeQuery(query);
        ArrayList<Student> students = new ArrayList<>(27);
        for(Student student: students){
            student = new Student(result, true);
        }
        lastId+=students.size();
        stat.close();
        return students;
    }
    public void takeAttendance(ArrayList<Student> students, Group group) throws SQLException{
        Statement stat = conn.createStatement();
        String query = "INSERT INTO Attendance (student, class, date, present) values ";
        for(Student student: students){
            query += "("+student.getId()+", "+group.getId()+", '"+
                    student.getAttendance().getDate()+"', "+student.getAttendance().isPresent()+"), ";
        }
        query = query.replace(query.substring(query.length()-2), ";");
        stat.executeUpdate(query);
        stat.close();
    }
    public ArrayList<Group> getGroups(int showingState)throws SQLException{
        if(showingState==0){
            lastId=0;
        }
        Statement stat = conn.createStatement();
        String query;
        if(userType==UserType.admin) {
            query = "SELECT id, name FROM Classes WHERE id >= " + lastId + " ORDER BY id LIMIT 27;";
        }
        else if(userType == UserType.teacher){
            query = "SELECT Classes.id, Classes.name FROM Classes INNER JOIN Teachersclasses" +
                    " on Teachersclasses.class=Classes.id WHERE Teachersclasses.teacher="+me.getId()+
                    " AND Classes.id >= " + lastId + " ORDER BY Classes.id LIMIT 27;";
        }
        else throw new SQLException();//student
        ResultSet result = stat.executeQuery(query);
        ArrayList<Group> groups = new ArrayList<>(27);
        for(Group group: groups){
            group = new Group(result, true);
        }
        lastId+=groups.size();
        stat.close();
        return groups;
    }

}
