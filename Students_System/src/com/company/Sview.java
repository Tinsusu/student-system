package com.company;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;


public class Sview {
    StudentController controller;
    private GridPane startView;
    Button exitBun = new Button("Exit");
    Button searchBtn = new Button("Search Data");
    Label keyinId = new Label("Please input Student ID");
    TextArea Stext= new TextArea();
    ComboBox<String> StudentID = new ComboBox<>();

    Label keyinGrade = new Label("Please input Students Grade:");
    Label selectCourse = new Label("Select the course here:");
 // make a combobox for subject
    ComboBox<String> Subject = new ComboBox<>();
// make a textfield to get grade
    ComboBox<Integer> putgrade = new ComboBox<>();
    Button submit = new Button("Submit");

    public Sview(StudentController controller){
        this.controller=controller;
        CreateAndConfigure();
    }
    private void CreateAndConfigure(){
        startView=new GridPane();
        startView.setMinSize(300,200);
        startView.setPadding(new Insets(10,10,10,10));
        startView.setVgap(5);
        startView.setHgap(1);
        startView.add(keyinId,1,1);
        startView.add(StudentID,15,1);
        startView.add(searchBtn,15,6);
        startView.add(Stext,1,7,15,7);
        startView.add(exitBun,20,35);
        ObservableList<String> SID = controller.getStudentID();
        StudentID.setItems(SID);
        StudentID.getSelectionModel().selectFirst();
        startView.add(keyinGrade,15,20);
        startView.add(selectCourse,1,20);
        startView.add(Subject,1,21);
        ObservableList<String> SUB = controller.getSubject();
        Subject.setItems(SUB);
        Subject.getSelectionModel().selectFirst();
        startView.add(putgrade,15,21);
        startView.add(submit,15,23);
        ObservableList<Integer> GRADE = controller.setGrade();
        putgrade.setItems(GRADE);
        putgrade.getSelectionModel().selectFirst();
    }
    public Parent asParent(){
        return startView;
    }
}
