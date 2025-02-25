package com.company;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentController {
    EnrollModel model;
    Sview view;
    public StudentController(EnrollModel model){
        this.model=model;
        try{
            model.connect();
            model.CreateStatement();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    public void setView(Sview view){
        this.view = view;
        view.exitBun.setOnAction(e-> Platform.exit());
        EventHandler<ActionEvent> PrintInfo= e->HandlePrintStudentInfo(view.StudentID.getValue(),view.Stext);
        view.searchBtn.setOnAction(PrintInfo);
        EventHandler<ActionEvent> PutGrade= e->HandleUpdateGrade(view.putgrade.getValue(),view.StudentID.getValue(),view.Subject.getValue());
        view.submit.setOnAction(PutGrade);
    }

    // This list must be pulled from the database
    public ObservableList<String> getStudentID(){
        ArrayList<String> StudentID=model.SQLQueryStudentNames();
        ObservableList<String> SID= FXCollections.observableList(StudentID);
        return SID;
    }

    //Try to pull out subject from the database
    public ObservableList<String> getSubject(){
        ArrayList<String> Subject = model.SQLQuerySubject();
        ObservableList<String> SUB= FXCollections.observableList(Subject);
        return SUB;
    }

    //Make ObservableList for Grade
    public ObservableList<Integer> setGrade() {
       ArrayList<Integer> Grade = new ArrayList<>();
                       Grade.add(-3);
                       Grade.add(0);
                       Grade.add(2);
                       Grade.add(4);
                       Grade.add(7);
                       Grade.add(10);
                       Grade.add(12);
       ObservableList<Integer> GradeObs = FXCollections.observableList(Grade);
       return GradeObs;
    }

    ///make handle updape DATA
    public void HandleUpdateGrade(Integer grade,String StudentID,String Coursename){
        model.UpdateGrade(grade,StudentID,Coursename);
    }

    public void HandlePrintStudentInfo(String StudentID, TextArea txtArea){
        txtArea.clear();
        txtArea.appendText("====================Student Data System====================\n");
        txtArea.appendText("Student ID: "+"  "+" Student Name:"+"           "+" City:"+"                   "+" Course Name:"+"                    "+" Grade:"+"          "+"Course Average:"+"      GPA:"+"\n");
        model.PrepareStmtFindInfo();
        ArrayList<StudentTrip> info = model.FindInfo(StudentID);

        for (int i =0;i< info.size();i++){
            txtArea.appendText(info.get(i).StudentID+"                  "+info.get(i).Name+"                "+info.get(i).City+"      "+info.get(i).Coursename+"            "+info.get(i).Grade+"             "+info.get(i).AverageGradeofthisCourse+"                                 "+info.get(i).GPA+"\n");

        }
    }
}
