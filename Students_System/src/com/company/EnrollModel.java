package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.sql.DriverManager.getConnection;

public class EnrollModel {
    Connection conn=null;
    String url;
    //make a query
    Statement stmt =null;
    PreparedStatement pstmt=null;
    ResultSet rs=null;

    EnrollModel(String url){
        this.url=url;
    }
    public void connect() throws SQLException{
        conn=getConnection(url);
    }
    public void close() throws SQLException{
        if(conn != null)
            conn.close();
    }
    //make a method here to create statement
    public void CreateStatement() throws SQLException{
        this.stmt=conn.createStatement();
    }

    //query student names
    public ArrayList<String> SQLQueryStudentNames(){
        ArrayList<String> Names = new ArrayList<>();
        String sql="Select StudentID From Students;";
        try {
            rs = stmt.executeQuery(sql);
            while (rs!= null && rs.next()){
                String id = rs.getString(1);
                Names.add(id);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        rs=null;
        return Names;
    }
    ///////////////////////////////////////
    //query Subject
    public ArrayList<String> SQLQuerySubject(){
        ArrayList<String> Courses = new ArrayList<>();
        String sql ="SELECT CourseName FROM Course;";
        try {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()) {
                String sub = rs.getString(1);
                Courses.add(sub);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        rs=null;
        return Courses;
    }

    //query with user input
    public void StudentInfoQuery(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please write Student id:");
        String id=scanner.next();
        String sql="SELECT StudentID,Name,City from Students where StudentID='"+id+"';";
        try{
            rs = stmt.executeQuery(sql);
            if(rs==null )
                System.out.println("No information with that StudentID");
            while (rs!=null && rs.next())
            {
                String StudentID = rs.getString(1);
                String Name = rs.getString(2);
                String City = rs.getString(3);
                System.out.println("Student ID :"+StudentID+","+" Name: "+Name+","+" City: "+City);
            }
            rs=null;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    //Make an update dat grade OVER here
    //
    public void UpdateGrade( Integer grade,String StudentID, String CourseID){

        String sql="UPDATE Enrollment SET Grade=?\n" +
                "WHERE StudentID = ? AND CourseName = ? AND Grade is NULL;";
        try{
            PreparedStatement pstmtU = conn.prepareStatement(sql);
            //rs=stmt.executeQuery(sql);
            pstmtU.setInt(1,grade);
            pstmtU.setString(2,StudentID);
            pstmtU.setString(3,CourseID);

              int norows =pstmtU.executeUpdate();
            System.out.println(norows);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

// Make  a prepare statement Query
    public void PrepareStmtFindInfo(){
      //  String sql ="SELECT * FROM Enrollment,Students WHERE Enrollment.StudentID=Students.StudentID AND Enrollment.StudentID=?;";
        String sql ="SELECT a.StudentID,c.Name,c.City, a.Coursename,a.Grade,b.AverageGradeofthisCourse,d.GPA\n" +
                "FROM Enrollment a\n" +
                "         INNER JOIN (SELECT CourseName,AVG(Grade) as AverageGradeofthisCourse\n" +
                "                     FROM Enrollment\n" +
                "                     GROUP BY CourseName) b\n" +
                "                    ON a.CourseName=b.CourseName\n" +
                "         INNER JOIN (SELECT StudentID,Name,City FROM Students) c\n" +
                "                    ON a.StudentID = c.StudentID\n" +
                "         INNER JOIN (SELECT StudentID,AVG(Grade) as GPA\n" +
                "                     FROM Enrollment\n" +
                "                     GROUP BY StudentID) d\n" +
                "                     ON d.StudentID=a.StudentID\n" +
                "                     WHERE a.StudentID =?;";
       try {
           pstmt = conn.prepareStatement(sql); //beacause it is prepare statment
       }catch (SQLException e){
           System.out.println(e.getMessage());
       }
    }



    public ArrayList<StudentTrip> FindInfo(String StudentID){
        ArrayList<StudentTrip> StudentTrip=new ArrayList<>();
      try{
          pstmt.setString(1,StudentID);
          rs=pstmt.executeQuery();
          if(rs==null)
              System.out.println("NO record fetched.");
          while (rs!=null && rs.next()){
              Object object= rs.getObject(5);
              Double grade= rs.getDouble(5);
              if (object== null){
                  grade=null;
              }
              object= rs.getObject(6);
              Double avgC= rs.getDouble(6);
              if (object== null){
                  avgC=null;
              }
              object= rs.getObject(7);
              Double GPA= rs.getDouble(7);
              if (object== null){
                  GPA=null;
              }



              StudentTrip.add(new StudentTrip(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
                      grade,
                      avgC,
                      GPA));
              System.out.println("StudentID: "+rs.getString(1)+" Name: "+rs.getString(2)+" City:"+rs.getString(3)+
                      " Course Name: "+rs.getString(4)+" Grade: "+rs.getObject(5)+" AverageGradeofthiscourse: "+rs.getObject(6)+" GPA: "+rs.getObject(7));
          }
      }catch (SQLException e){
          System.out.println(e.getMessage());
      }
        return StudentTrip;
    }


    public void PrintName(ArrayList<String> StudentNames){
        for (int i=0;i<StudentNames.size();i++){
            System.out.println(StudentNames.get(i));
        }
    }


}
class StudentTrip{
    String StudentID;
    String Name;
    String City;
    String Coursename;
    Double Grade;
    Double AverageGradeofthisCourse;
    Double GPA;

    public StudentTrip(String studentID, String name, String city, String coursename, Double grade, Double averageGradeofthisCourse, Double GPA) {
        StudentID = studentID;
        Name = name;
        City = city;
        Coursename=coursename;
        Grade = grade;
        AverageGradeofthisCourse = averageGradeofthisCourse;
        this.GPA = GPA;
    }
}
