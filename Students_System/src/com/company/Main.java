package com.company;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import static java.sql.DriverManager.getConnection;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception{

        String url = "jdbc:sqlite:/Users/suthongchaisuriyasuphapong/Documents/4th_SEM/Port3/Uni.db";
        EnrollModel EDB=new EnrollModel(url);
        StudentController controller = new StudentController(EDB);
        Sview view = new Sview(controller);
        controller.setView(view);
        primaryStage.setTitle("Students Enrollment Data");
        primaryStage.setScene(new Scene(view.asParent(),600,475));
        primaryStage.show();
    }
    public static void main(String[] args){
        launch(args);
    }


/*
    public static void main(String[] args) {
	// write your code here
        String url = "jdbc:sqlite:/Users/suthongchaisuriyasuphapong/Documents/4th_SEM/Port3/Uni.db";
        EnrollModel EDB=new EnrollModel(url);
        try{
            EDB.connect();
            EDB.CreateStatement();
            ArrayList<String> names=EDB.SQLQueryStudentNames();
          //  EDB.PrintName(names);
          //  EDB.StudentInfoQuery();
            EDB.PrepareStmtFindInfo();
            EDB.FindInfo("S01");

           }catch(SQLException e){
            e.printStackTrace();
        }
        finally {
                try{
                    EDB.close();
                }catch (SQLException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }*/
    }

