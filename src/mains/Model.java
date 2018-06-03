package mains;

import exceptions.ConnException;
import data.*;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;


import static exceptions.ConnException.ErrorTypes.*;

public class Model {
    private Connection conn;

    public enum UserType {admin, student, teacher};

    private UserType userType;
    Person me;


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
                    me = new Teacher(statement.executeQuery(query), BuildingType.normal);
                    break;
                case admin:
                    query = "SELECT * FROM Teachers WHERE mail='" + mail + "' LIMIT 1;";
                    me = new Teacher(statement.executeQuery(query), BuildingType.normal);
                    break;
                case student:
                default:
                    query = "SELECT * FROM Students WHERE mail='" + mail + "' LIMIT 1;";
                    me = new Student(statement.executeQuery(query), BuildingType.normal);
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

    public void changePass(Person person, String newPassword) throws SQLException, ConnException{
        if(isHacker(newPassword)){
            throw new ConnException(hacker);
        }
        Statement stat;
        stat = conn.createStatement();
        String table;
        if(person instanceof Student) {
            table = "Students";
        }
        else{
            table = "Teachers";
        }
        stat.executeUpdate("UPDATE "+table+" SET pass='"+newPassword+"' WHERE id = "+person.getId()+";");
        stat.close();
    }

    public static boolean isHacker(String value){
        if(value.contains("'") || value.contains(";")){
            return true;
        }
        return false;
    }
    private void update(Person person, String value, String field) throws SQLException, ConnException{
        if(isHacker(value) || isHacker(field)){
            throw new ConnException(hacker);
        }
        Statement stat;
        stat = conn.createStatement();
        String table;
        if(person instanceof Student) {
            table = "Students";
        }
        else{
            table = "Teachers";
        }
        stat.executeUpdate("UPDATE "+table+" SET "+field+"='"+value+"' WHERE id = "+person.getId()+";");
        if(field.equals("mail")){
            stat.executeUpdate("UPDATE Waiting SET mail='"+value+"' WHERE mail = '"+person.getMail()+"';");
        }
        stat.close();
    }
    public void changeMail(Person person, String mail)throws SQLException,ConnException{
        if(isMailInDatabase(mail)){
            throw new ConnException(existing);
        }
        update(person, mail, "mail");

        person.setMail(mail);
    }
    public void changeStudentID(Student student, int student_id)throws SQLException,ConnException{
        update(student, String.valueOf(student_id), "student_id");
        student.setStudentID(student_id);
    }
    public void changeName(Person person, String name)throws SQLException,ConnException{
        update(person, name, "name");
        person.setName(name);
    }
    public void changeGenGroup(Student student, String genGroup)throws SQLException,ConnException{
        update(student, genGroup, "gen_class");
        student.setGenGroup(genGroup);
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
    public int getGroupIdByName(String groupName) throws SQLException, ConnException{
        if(isHacker(groupName)){
            throw new ConnException(hacker);
        }
        Statement stat = conn.createStatement();
        String query = "SELECT id FROM Classes WHERE name='"+groupName+"';";
        ResultSet result = stat.executeQuery(query);
        int val;
        if(result.next()) {
            val = result.getInt("id");
        }
        else throw new ConnException(notExitsting);
        stat.close();
        return val;
    }
    public void createGroup(Group group)throws SQLException, ConnException{
        Statement stat = conn.createStatement();
        String query;
        if(isHacker(group.getName())){
            throw new ConnException(hacker);
        }
        query="INSERT INTO Classes (name, day, start_hour, finish_hour) VALUES ('"+group.getName()+"', "+
                (group.getDayId()+1)+", '"+group.getStartHour()+"', '"+group.getFinishHour()+"');";
        stat.executeUpdate(query);

        query = "SELECT id FROM Classes WHERE name='"+group.getName()+"';";
        ResultSet result = stat.executeQuery(query);
        result.next();
        group.setId(result.getInt("id"));
        stat.close();
    }
    public void addGroup(Person person, int groupId) throws SQLException{
        String table,query;
        if(person instanceof Student){
            table = "Studentsclasses (student, class) ";
            query = "student, class FROM Studentsclasses WHERE student=";
        }
        else {
            table = "Teachersclasses (teacher, class) ";
            query = "teacher, class FROM Teachersclasses WHERE teacher=";
        }
        Statement stat = conn.createStatement();

        ResultSet result = stat.executeQuery("SELECT "+query+person.getId()+" AND class="+groupId+";");
        if(!result.next()) {//there is no this class in database for this person
            stat.executeUpdate("INSERT INTO " + table + " VALUES (" + person.getId() + ", " + groupId + ");");
        }
        stat.close();
    }
    private int lastId;
    public ArrayList<Student> getStudents(int showingState, Group group) throws SQLException{
        if(isHacker(group.getName())){
            throw new SQLException();
        }
        if(showingState==0){
            lastId=0;
        }
        Statement stat = conn.createStatement();
        String query = "SELECT Students.id, Students.name FROM Students JOIN Studentsclasses" +
                " ON Students.id=Studentsclasses.student WHERE class="+group.getId()+" AND Students.id >= "+lastId+
                " ORDER BY Students.id LIMIT 27;";
        ResultSet result = stat.executeQuery(query);
        ArrayList<Student> students = new ArrayList<>();
        for(int i=0;i<27;i++){
            try {
                Student student = new Student(result, false);
                students.add(student);
            }
            catch(SQLException e){
                break;
            }
        }
        lastId=(students.size()==0)?0:(students.get(students.size()-1).getId()+1);
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
        query = query.substring(0, query.length()-2)+";";
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
            query = "SELECT * FROM Classes WHERE id >= " + lastId + " ORDER BY id;";
        }
        else if(userType == UserType.teacher){
            query = "SELECT * FROM Classes INNER JOIN Teachersclasses" +
                    " on Teachersclasses.class=Classes.id WHERE Teachersclasses.teacher="+me.getId()+
                    " AND Classes.id >= " + lastId + " ORDER BY Classes.id;";
        }
        else{ //if(userType == UserType.student){
            query = "SELECT * FROM Classes INNER JOIN Studentsclasses" +
                    " ON Studentsclasses.class=Classes.id WHERE Studentsclasses.student="+me.getId()+
                    " AND Classes.id >= " + lastId + " ORDER BY Classes.id;";
        }
        ResultSet result = stat.executeQuery(query);
        ArrayList<Group> groups = new ArrayList<Group>();
        for(int i=0;i<27;i++){
            try {
                Group group = new Group(result, BuildingType.normal);
                groups.add(group);
            }
            catch(SQLException e){
                break;
            }
        }
        stat.close();
        return groups;
    }

    public ArrayList<Student> getAttendance(Group group) throws SQLException{
        Statement stat = conn.createStatement();
        String query = "SELECT Students.id, Students.name, date, present FROM Attendance " +
                "INNER JOIN Students ON Attendance.student=Students.id WHERE ";
        if(userType==UserType.student){
            query += "Students.id="+me.getId()+" AND ";
        }
        query+="Attendance.class="+group.getId()+" ORDER BY date DESC, Students.name;";

        ResultSet result = stat.executeQuery(query);
        ArrayList<Student> students = new ArrayList<Student>();
        for(int i=0;i<27;i++){
            try {
                Student student = new Student(result, true);
                students.add(student);
            }
            catch(SQLException e){
                break;
            }
        }
        stat.close();
        return students;
    }
    public void updateAttendance(ArrayList<Student> students, Group group) throws SQLException{
        Statement stat = conn.createStatement();
        String query = "";
        for(Student student: students){
            query = "UPDATE Attendance SET present = "+student.getAttendance().isPresent()+" WHERE student="+student.getId()+
                    " AND class="+group.getId()+" AND date='"+student.getAttendance().getDate()+"';\n";
            stat.executeUpdate(query);
        }
        stat.close();
    }
    public ArrayList<Person> getPeople(boolean onlyStudents) throws SQLException{
        ArrayList<Person> people = new ArrayList<>();
        Statement stat = conn.createStatement();
        String query;
        if(userType == UserType.student){
            query = "SELECT * FROM Teachers LEFT JOIN Teachersclasses ON Teachers.id=Teachersclasses.teacher " +
                    "LEFT JOIN Classes ON Teachersclasses.class=Classes.id WHERE Classes.id IN " +
                    "(SELECT id FROM Studentsclasses WHERE student="+me.getId()+");";
            ResultSet result = stat.executeQuery(query);
            while(result.next()) {
                people.add(new Teacher(result, BuildingType.full));
            }
        }
        else if(userType == UserType.teacher){
            query = "SELECT * FROM Students LEFT JOIN Studentsclasses ON Students.id=Studentsclasses.student " +
                    "LEFT JOIN Classes ON Studentsclasses.class=Classes.id WHERE Classes.id IN " +
                    "(SELECT id FROM Teachersclasses WHERE teacher="+me.getId()+");";
            ResultSet result = stat.executeQuery(query);
            while(result.next()) {
                people.add(new Student(result, BuildingType.full));
            }
        }
        else {//admin
            if(onlyStudents) {
                query = "SELECT * FROM Students;";
                ResultSet result = stat.executeQuery(query);
                while (result.next()) {
                    people.add(new Student(result, BuildingType.fullAdmin));
                }
            }
            else {
                query = "SELECT * FROM Teachers;";
                ResultSet result = stat.executeQuery(query);
                while (result.next()) {
                    people.add(new Teacher(result, BuildingType.fullAdmin));
                }
            }
        }
        return people;
    }

    public void addPerson(Person person) throws SQLException{
        if(isMailInDatabase(person.getMail())){
            throw new SQLException();
        }
        Statement stat = conn.createStatement();
        String query;
        if(person instanceof Teacher){
            query="INSERT INTO Teachers (mail, pass, name) VALUES ('"+person.getMail()+"', '"+
                    person.getPass()+"', '"+person.getName()+"');";
        }
        else{//student
            query = "INSERT INTO Students (mail, pass, name, student_id, gen_class) VALUES ('"+person.getMail()+"', '"+
                    person.getPass()+"', '"+person.getName()+"', "+((Student)person).getStudentID()+", '"+
                    ((Student)person).getGenGroup()+"');";
        }
        stat.executeUpdate(query);
    }

    public ArrayList<Group> getTimetable() throws SQLException{
        Statement stat = conn.createStatement();
        String query;
        if(userType == UserType.teacher || userType==UserType.admin){
            query = "SELECT * FROM Classes INNER JOIN Teachersclasses" +
                    " ON Teachersclasses.class=Classes.id WHERE Teachersclasses.teacher="+me.getId()+
                    " ORDER BY Classes.day, Classes.start_hour;";
        }
        else{ //if(userType == UserType.student){
            query = "SELECT * FROM Classes INNER JOIN Studentsclasses" +
                    " ON Studentsclasses.class=Classes.id WHERE Studentsclasses.student="+me.getId()+
                    " ORDER BY Classes.day, Classes.start_hour;";
        }
        ResultSet result = stat.executeQuery(query);
        ArrayList<Group> groups = new ArrayList<Group>();
        while(result.next()) {
            Group group = new Group(result, BuildingType.full);
            groups.add(group);
        }
        stat.close();
        return groups;
    }

}
