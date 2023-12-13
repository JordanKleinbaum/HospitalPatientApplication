// Name: Jordan Kleinbaum & Carey Datre
// Purpose: Group Project Part 3: DB Integration with Patient GUI
// Date: December 13th, 2023
package com.example;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

// Database Imports
import oracle.jdbc.pool.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App extends Application {
    public static OracleDataSource oDS;
    public static Connection jsqlConn;
    public static PreparedStatement jsqlStmt;
    public static ResultSet jsqlResults;

    private static final String SELECT_PATIENT_QUERY = "SELECT * FROM PATIENT";
    private static final String SELECT_EVENTS_QUERY = "SELECT * FROM PATIENTEVENTS";

    @Override
    public void start(Stage stage) throws IOException {
        // Creating the Root Node
        // (Carey)
        Group root = new Group();

        // Creating the Scene
        // (Carey)
        Scene scene = new Scene(root, Color.LAVENDER);

        // _______________________________________________________

        // Creating the Title Node
        // (Carey)
        Text title = new Text("Welcome to Patient Application!");
        title.setX(620);
        title.setY(100);
        title.setFont(new Font(50));

        // _______________________________________________________

        // Creating Radio Button Nodes & Setting the Positions for Add, Delete, AddEvent
        // (Carey)
        RadioButton radioButtonAdd = new RadioButton("Add");
        radioButtonAdd.setLayoutX(200);
        radioButtonAdd.setLayoutY(150);
        radioButtonAdd.setFont(new Font(35));
        radioButtonAdd.setCursor(Cursor.HAND);

        RadioButton radioButtonDelete = new RadioButton("Delete");
        radioButtonDelete.setLayoutX(350);
        radioButtonDelete.setLayoutY(150);
        radioButtonDelete.setFont(new Font(35));
        radioButtonDelete.setCursor(Cursor.HAND);

        RadioButton radioButtonAddEvent = new RadioButton("Add Event");
        radioButtonAddEvent.setLayoutX(550);
        radioButtonAddEvent.setLayoutY(150);
        radioButtonAddEvent.setFont(new Font(35));
        radioButtonAddEvent.setCursor(Cursor.HAND);

        // Creating the Toggle Group for Add, Delete, AddEvent
        ToggleGroup addOrDeleteOrAddEventToggle = new ToggleGroup();

        // Adding the radio buttons to the toggle group
        radioButtonAdd.setToggleGroup(addOrDeleteOrAddEventToggle);
        radioButtonDelete.setToggleGroup(addOrDeleteOrAddEventToggle);
        radioButtonAddEvent.setToggleGroup(addOrDeleteOrAddEventToggle);

        // _______________________________________________________

        // Create Radio Buttons, Set the Positions for Patient, InPatient, OutPatient
        // (Carey)
        RadioButton radioButtonPatient = new RadioButton("Patient");
        radioButtonPatient.setLayoutX(1100);
        radioButtonPatient.setLayoutY(150);
        radioButtonPatient.setFont(new Font(35));
        radioButtonPatient.setCursor(Cursor.HAND);

        RadioButton radioButtonInPatient = new RadioButton("InPatient");
        radioButtonInPatient.setLayoutX(1290);
        radioButtonInPatient.setLayoutY(150);
        radioButtonInPatient.setFont(new Font(35));
        radioButtonInPatient.setCursor(Cursor.HAND);

        RadioButton radioButtonOutPatient = new RadioButton("OutPatient");
        radioButtonOutPatient.setLayoutX(1500);
        radioButtonOutPatient.setLayoutY(150);
        radioButtonOutPatient.setFont(new Font(35));
        radioButtonOutPatient.setCursor(Cursor.HAND);

        // Creating the Toggle Group for Add, Delete, AddEvent
        ToggleGroup patientOrInPatientOrOutPatientToggleGroup = new ToggleGroup();

        // Adding the radio buttons to the toggle group
        radioButtonPatient.setToggleGroup(patientOrInPatientOrOutPatientToggleGroup);
        radioButtonInPatient.setToggleGroup(patientOrInPatientOrOutPatientToggleGroup);
        radioButtonOutPatient.setToggleGroup(patientOrInPatientOrOutPatientToggleGroup);

        // _______________________________________________________

        // Creating Text Fields, and Their Layouts
        // (Carey)

        // First Name
        Text textTitleFirstName = new Text("First Name:");
        textTitleFirstName.setX(200);
        textTitleFirstName.setY(290);
        textTitleFirstName.setFont(new Font(20));

        TextField textFieldFirstName = new TextField();
        textFieldFirstName.setPrefWidth(300);
        textFieldFirstName.setLayoutX(200);
        textFieldFirstName.setLayoutY(300);
        textFieldFirstName.setFont(javafx.scene.text.Font.font(25));

        // Last Name
        Text textTitleLastName = new Text("Last Name:");
        textTitleLastName.setX(200);
        textTitleLastName.setY(390);
        textTitleLastName.setFont(new Font(20));

        TextField textFieldLastName = new TextField();
        textFieldLastName.setPrefWidth(300);
        textFieldLastName.setFont(javafx.scene.text.Font.font(25));
        textFieldLastName.setLayoutX(200);
        textFieldLastName.setLayoutY(400);

        // Room Number
        Text textTitleRoomNumber = new Text("Room Number:");
        textTitleRoomNumber.setX(200);
        textTitleRoomNumber.setY(490);
        textTitleRoomNumber.setFont(new Font(20));

        TextField textFieldRoomNumber = new TextField();
        textFieldRoomNumber.setPrefWidth(300);
        textFieldRoomNumber.setFont(javafx.scene.text.Font.font(25));
        textFieldRoomNumber.setLayoutX(200);
        textFieldRoomNumber.setLayoutY(500);

        // Additional Info
        Text textTitleAdditionalInfo = new Text("Additional Info: ");
        textTitleAdditionalInfo.setX(200);
        textTitleAdditionalInfo.setY(790);
        textTitleAdditionalInfo.setFont(new Font(20));

        TextField textFieldAdditionalInfo = new TextField();
        textFieldAdditionalInfo.setPrefWidth(300);
        textFieldAdditionalInfo.setFont(javafx.scene.text.Font.font(25));
        textFieldAdditionalInfo.setLayoutX(200);
        textFieldAdditionalInfo.setLayoutY(800);

        // Event Description
        Text textTitleEventDescription = new Text("Event Description: ");
        textTitleEventDescription.setX(200);
        textTitleEventDescription.setY(690);
        textTitleEventDescription.setFont(new Font(20));

        TextField textFieldEventDescription = new TextField();
        textFieldEventDescription.setPrefWidth(300);
        textFieldEventDescription.setFont(javafx.scene.text.Font.font(25));
        textFieldEventDescription.setLayoutX(200);
        textFieldEventDescription.setLayoutY(700);

        // Pain Level
        Text textTitlePainLevel = new Text("Pain Level: ");
        textTitlePainLevel.setX(200);
        textTitlePainLevel.setY(590);
        textTitlePainLevel.setFont(new Font(20));

        TextField textFieldPainLevel = new TextField();
        textFieldPainLevel.setPrefWidth(300);
        textFieldPainLevel.setFont(javafx.scene.text.Font.font(25));
        textFieldPainLevel.setLayoutX(200);
        textFieldPainLevel.setLayoutY(600);

        // _______________________________________________________
        // (Carey)

        // Creating the Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setPrefWidth(300);
        submitButton.setFont(javafx.scene.text.Font.font(25));
        submitButton.setLayoutX(200);
        submitButton.setLayoutY(900);
        submitButton.setCursor(Cursor.HAND);

        // _______________________________________________________
        // (Carey)

        // Creating the Text Area
        TextArea displayArea = new TextArea();
        displayArea.setEditable(false);
        displayArea.setPrefWidth(800);
        displayArea.setPrefHeight(655);
        displayArea.setLayoutX(550);
        displayArea.setLayoutY(300);
        displayArea.setStyle("-fx-font-size: 25px");

        // _______________________________________________________

        // Creating the Search For Patient Button
        // (Carey)
        Button searchForPatientButton = new Button("Search For Patient");
        searchForPatientButton.setPrefWidth(300);
        searchForPatientButton.setFont(javafx.scene.text.Font.font(25));
        searchForPatientButton.setLayoutX(1450);
        searchForPatientButton.setLayoutY(800);
        searchForPatientButton.setCursor(Cursor.HAND);

        // Creating the Show All Patients Button
        Button showAllPatientsButton = new Button("All Patients ");
        showAllPatientsButton.setPrefWidth(300);
        showAllPatientsButton.setFont(javafx.scene.text.Font.font(25));
        showAllPatientsButton.setLayoutX(1450);
        showAllPatientsButton.setLayoutY(900);
        showAllPatientsButton.setCursor(Cursor.HAND);

        // ______________________________________________________

        // Creating the Search First Name TextField
        // (Carey)
        Text textTitleSearchFirstName = new Text("Search First Name: ");
        textTitleSearchFirstName.setX(1450);
        textTitleSearchFirstName.setY(590);
        textTitleSearchFirstName.setFont(new Font(20));

        TextField textFieldSearchFirstName = new TextField();
        textFieldSearchFirstName.setPrefWidth(300);
        textFieldSearchFirstName.setFont(javafx.scene.text.Font.font(25));
        textFieldSearchFirstName.setLayoutX(1450);
        textFieldSearchFirstName.setLayoutY(600);

        // Creating the Search Last Name TextField
        // (Carey)
        Text textTitleSearchLastName = new Text("Search Last Name: ");
        textTitleSearchLastName.setX(1450);
        textTitleSearchLastName.setY(690);
        textTitleSearchLastName.setFont(new Font(20));

        TextField textFieldSearchLastName = new TextField();
        textFieldSearchLastName.setPrefWidth(300);
        textFieldSearchLastName.setFont(javafx.scene.text.Font.font(25));
        textFieldSearchLastName.setLayoutX(1450);
        textFieldSearchLastName.setLayoutY(700);

        // _______________________________________________________

        // Creating Jordan & Carey's Headshot (We both did this)

        // Image headshotJordan = new Image("headshot1.jpg");

        // ImageView headshotJordanImageView = new ImageView(headshotJordan);
        // headshotJordanImageView.setFitWidth(200);
        // headshotJordanImageView.setFitHeight(200);
        // headshotJordanImageView.setTranslateX(1400);
        // headshotJordanImageView.setTranslateY(300);

        // Image headshotCarey = new Image("headshot2.jpg");
        // ImageView headshotCareyImageView = new ImageView(headshotCarey);
        // headshotCareyImageView.setFitWidth(200);
        // headshotCareyImageView.setFitHeight(200);
        // headshotCareyImageView.setTranslateX(1625);
        // headshotCareyImageView.setTranslateY(300);

        // _______________________________________________________

        // START OF FUNCTIONALITY / VALIDATION
        // (Jordan)

        // Submit Button
        submitButton.setOnAction(e -> {
            boolean isFirstNameGood = isFirstNameGoodMethod(textFieldFirstName.getText());
            boolean isLastNameGood = isLastNameGoodMethod(textFieldLastName.getText());
            boolean isRoomNumberGood = isRoomNumberGoodMethod(textFieldRoomNumber.getText());
            boolean isAdditionalInfoGood = isAdditionalInfoGoodMethod(textFieldAdditionalInfo.getText());

            // ADD A NEW PATIENT
            // (Jordan)
            if (radioButtonAdd.isSelected() && radioButtonPatient.isSelected()) {
                textFieldFirstName.requestFocus();

                // Is first name text field valid?
                if (!isFirstNameGood) {
                    displayArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 25px;");
                    displayArea.setText("Error: First Name Field\nMust Contain Only Letters\nCannot Be Blank");
                    return;
                }

                // Change focus to next line
                textFieldLastName.requestFocus();

                // Is last name text field valid?
                if (!isLastNameGood) {
                    displayArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 25px;");
                    displayArea.setText("Error: Last Name Field\nMust Contain Only Letters\nCannot Be Blank");
                    return;
                }

                // Change focus to next line
                textFieldRoomNumber.requestFocus();

                // Is room number text field valid?
                if (!isRoomNumberGood) {
                    displayArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 25px;");
                    displayArea.setText("Error: Room Number Field\nMust Contain Only Letters\nCannot Be Blank");
                    return;
                }

                // Reset the style for success message
                displayArea.setStyle("-fx-text-fill: -fx-text-inner-color; -fx-font-size: 25px;");

                // Create a new patient
                Patient newPatient = new Patient(textFieldFirstName.getText(), textFieldLastName.getText(),
                        textFieldRoomNumber.getText());

                Patient.createPatient(newPatient);
                insertNewPatient(newPatient);

                // Display Success Message
                displayArea.setText("New Patient Added Successfully!");

                // Clear the Input Fields
                textFieldFirstName.clear();
                textFieldLastName.clear();
                textFieldRoomNumber.clear();
                textFieldPainLevel.clear();
                textFieldEventDescription.clear();
                textFieldAdditionalInfo.clear();
            }

            // ---------------- submit button ---------------------

            // ADD A NEW INPATIENT
            // (Jordan)
            if (radioButtonAdd.isSelected() && radioButtonInPatient.isSelected()) {

                // Is first name text field valid?
                if (!isFirstNameGood) {
                    displayArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 25px;");
                    displayArea.setText("Error: First Name Field\nMust Contain Only Letters\nCannot Be Blank");
                    return;
                }

                // Change focus to next line
                textFieldLastName.requestFocus();

                // Is last name text field valid?
                if (!isLastNameGood) {
                    displayArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 25px;");
                    displayArea.setText("Error: Last Name Field\nMust Contain Only Letters\nCannot Be Blank");
                    return;
                }

                // Change focus to next line
                textFieldRoomNumber.requestFocus();

                // Is room number text field valid?
                if (!isRoomNumberGood) {
                    displayArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 25px;");
                    displayArea.setText("Error: Room Number Field\nCannot Be Blank");
                    return;
                }

                // Change focus to next line
                textFieldAdditionalInfo.requestFocus();

                // Is additional info text field valid?
                if (!isAdditionalInfoGood) {
                    displayArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 25px;");
                    displayArea.setText("Error: Additional Info Field\nCannot Be Blank");
                    return;
                }

                // Reset the style for success message
                displayArea.setStyle("-fx-text-fill: -fx-text-inner-color; -fx-font-size: 25px;");

                // Create a new InPatient
                Patient newInPatient = new InPatient(textFieldFirstName.getText(), textFieldLastName.getText(),
                        textFieldRoomNumber.getText(), textFieldAdditionalInfo.getText());
                Patient.createPatient(newInPatient);
                insertNewPatient(newInPatient);

                // Display Success Message
                displayArea.setText("New InPatient Added Successfully!");

                // Clear the Input Fields
                textFieldFirstName.clear();
                textFieldLastName.clear();
                textFieldRoomNumber.clear();
                textFieldPainLevel.clear();
                textFieldEventDescription.clear();
                textFieldAdditionalInfo.clear();
            }

            // ---------------- submit button ---------------------

            // ADD A NEW OUTPATIENT
            // (Jordan)
            if (radioButtonAdd.isSelected() && radioButtonOutPatient.isSelected()) {

                // Is first name text field valid?
                if (!isFirstNameGood) {
                    displayArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 25px;");
                    displayArea.setText("Error: First Name Field\nMust Contain Only Letters\nCannot Be Blank");
                    return;
                }

                // Change focus to next line
                textFieldLastName.requestFocus();

                // Is last name text field valid?
                if (!isLastNameGood) {
                    displayArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 25px;");
                    displayArea.setText("Error: Last Name Field\nMust Contain Only Letters\nCannot Be Blank");
                    return;
                }

                // Change focus to next line
                textFieldRoomNumber.requestFocus();

                // Is room number text field valid?
                if (!isRoomNumberGood) {
                    displayArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 25px;");
                    displayArea.setText("Error: Room Number Field\nCannot Be Blank");
                    return;
                }

                // Change focus to next line
                textFieldAdditionalInfo.requestFocus();

                // Is additional info text field valid?
                if (!isAdditionalInfoGood) {
                    displayArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 25px;");
                    displayArea.setText("Error: Additional Info Field\nCannot Be Blank");
                    return;
                }

                // Reset the style for success message
                displayArea.setStyle("-fx-text-fill: -fx-text-inner-color; -fx-font-size: 25px;");

                // Create a new OutPatient
                Patient newOutPatient = new OutPatient(textFieldFirstName.getText(), textFieldLastName.getText(),
                        textFieldRoomNumber.getText(), textFieldAdditionalInfo.getText());
                Patient.createPatient(newOutPatient);
                insertNewPatient(newOutPatient);

                // Display Success Message
                displayArea.setText("New OutPatient Added Successfully!");

                // Clear the Input Fields
                textFieldFirstName.clear();
                textFieldLastName.clear();
                textFieldRoomNumber.clear();
                textFieldPainLevel.clear();
                textFieldEventDescription.clear();
                textFieldAdditionalInfo.clear();
            }

            // _______________________________________________________

            // DELETE A PATIENT
            // (Jordan)
            if (radioButtonDelete.isSelected()) {
                int idValueUserWantsToDelete;

                try {
                    idValueUserWantsToDelete = Integer.parseInt(textFieldAdditionalInfo.getText());
                } catch (NumberFormatException v) {
                    displayArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 25px;");
                    displayArea.setText("Error: Invalid Input for Patient ID");
                    return;
                }

                // First, remove the patient from the local data structure
                boolean deletionSuccessful = Patient.removePatientFromMap(idValueUserWantsToDelete);

                if (deletionSuccessful) {
                    // If successful, delete the patient from the database
                    deletePatientFromDatabase(idValueUserWantsToDelete);

                    displayArea.setStyle("-fx-text-fill: -fx-text-inner-color; -fx-font-size: 25px;");
                    displayArea.setText("User Deleted!");
                    textFieldAdditionalInfo.clear();
                } else {
                    displayArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 25px;");
                    displayArea.setText("Error: No Patient Exists!");
                }
            }

            // _______________________________________________________

            // ADD AN EVENT
            // (Jordan & Carey)
            if (radioButtonAddEvent.isSelected()) {
                // User Input
                String positionInput = textFieldAdditionalInfo.getText().trim();
                String eventDescriptionUserWantsToAdd = textFieldEventDescription.getText().trim();
                String painLevelInput = textFieldPainLevel.getText().trim();

                // Validate Data Types
                int positionUserWantsToAddAnEventFor;
                int userSelectedPainLevel;

                if (isInteger(positionInput)) {
                    positionUserWantsToAddAnEventFor = Integer.parseInt(positionInput);

                    // Additional validation for non-negative position
                    if (positionUserWantsToAddAnEventFor < 0) {
                        displayArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 25px;");
                        displayArea.setText("Error: Position Must Be A Positive Integer!");
                        return; // Stop execution if input is invalid
                    }

                    if (isInteger(painLevelInput)) {
                        userSelectedPainLevel = Integer.parseInt(painLevelInput);

                        // Check if pain level is greater than 10 or negative
                        if (userSelectedPainLevel > 10 || userSelectedPainLevel < 0) {
                            displayArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 25px;");
                            displayArea.setText("Error: Pain Level must be between 0 and 10 (inclusive)!");
                            return;
                        }

                        // Check if eventDescription is not empty
                        if (!eventDescriptionUserWantsToAdd.isEmpty()) {
                            // Is User Input Valid?
                            boolean eventAdditionSuccessful = Patient.addEvent(positionUserWantsToAddAnEventFor,
                                    eventDescriptionUserWantsToAdd, userSelectedPainLevel);

                            // Functionality
                            if (eventAdditionSuccessful) {
                                addEventToDatabase(positionUserWantsToAddAnEventFor, eventDescriptionUserWantsToAdd,
                                        userSelectedPainLevel);
                                displayArea.setStyle("-fx-text-fill: -fx-text-inner-color; -fx-font-size: 25px;");
                                displayArea.setText("Event Added!");
                                textFieldAdditionalInfo.clear();
                                textFieldEventDescription.clear();
                                textFieldPainLevel.clear();
                            } else {
                                displayArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 25px;");
                                displayArea.setText("Error: No Patient Exists At That ID Position!");
                            }
                        } else {
                            textFieldEventDescription.requestFocus();
                            displayArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 25px;");
                            displayArea.setText("Error: Event Description Cannot Be Empty!\nCannot Be Blank");
                        }
                    } else {
                        textFieldPainLevel.requestFocus();
                        displayArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 25px;");
                        displayArea.setText("Error: Pain Level Cannot Be Blank");
                    }
                } else {
                    textFieldAdditionalInfo.requestFocus();
                    displayArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 25px;");
                    displayArea.setText("Error: Additional Info Must Be An Integer!\nCannot Be Blank");
                }
            }
        }); // END OF SUBMIT BUTTON

        // _______________________________________________________

        // SHOW ALL VALUES WHEN DELETE A PATIENT RADIO IS SELECTED
        // (Jordan)
        radioButtonDelete.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // Delete Button is Selected
                if (Patient.getPatientMap().isEmpty()) {
                    displayArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 25px;");
                    displayArea.setText("Error: No Patients In Database!\nAdd A Patient First!");
                }

                if (!Patient.getPatientMap().isEmpty()) {
                    StringBuilder allPatientsInfo = new StringBuilder();
                    allPatientsInfo.append("SELECT THE ID OF THE PATIENT YOU WISH TO DELETE")
                            .append("\n------------------------------------------------------------\n\n");

                    for (Patient patient : Patient.getPatientMap().values()) {
                        allPatientsInfo.append(patient.printPatientInfo())
                                .append("\n");
                    }

                    textFieldAdditionalInfo.requestFocus();
                    displayArea.setStyle("-fx-text-fill: -fx-text-inner-color; -fx-font-size: 25px;");
                    displayArea.setText(allPatientsInfo.toString());
                }

            } else {
                displayArea.clear();
            }
        });

        // _______________________________________________________

        // SHOW ALL VALUES WHEN ADD EVENT RADIO IS SELECTED
        // (Jordan)
        radioButtonAddEvent.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // Add Event Button is Selected
                if (Patient.getPatientMap().isEmpty()) {
                    displayArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 25px;");
                    displayArea.setText("Error: No Patients In Database!\nAdd A Patient First!");
                }

                if (!Patient.getPatientMap().isEmpty()) {
                    StringBuilder allPatientsInfo = new StringBuilder();
                    allPatientsInfo.append("SELECT THE ID OF THE PATIENT YOU WISH TO ADD AN EVENT FOR")
                            .append("\n------------------------------------------------------------\n\n");

                    for (Patient patient : Patient.getPatientMap().values()) {
                        allPatientsInfo.append(patient.printPatientInfo())
                                .append("\n");
                    }

                    textFieldAdditionalInfo.requestFocus();
                    displayArea.setStyle("-fx-text-fill: -fx-text-inner-color; -fx-font-size: 25px;");
                    displayArea.setText(allPatientsInfo.toString());
                }

            } else {
                displayArea.clear();
            }
        });

        // _______________________________________________________

        // SHOW ALL PATIENTS BUTTON
        // (Jordan)
        showAllPatientsButton.setOnAction(e -> {
            StringBuilder allPatientsInfo = new StringBuilder();

            for (Patient patient : Patient.getPatientMap().values()) {
                allPatientsInfo.append(patient.printPatientInfo()).append("\n");
            }

            displayArea.setStyle("-fx-text-fill: -fx-text-inner-color; -fx-font-size: 25px;");
            displayArea.setText(allPatientsInfo.toString());
        });

        // _______________________________________________________

        // Search For Specific Patient Button
        // (Jordan & Carey)
        searchForPatientButton.setOnAction(e -> {
            StringBuilder resultBuilder = new StringBuilder();
            boolean matchFound = false;

            // Get the text from the TextFields
            String valueFromTextFieldSearchFirstName = textFieldSearchFirstName.getText().trim().toLowerCase();
            String valueFromTextFieldSearchLastName = textFieldSearchLastName.getText().trim().toLowerCase();

            if (valueFromTextFieldSearchFirstName.isEmpty() || valueFromTextFieldSearchLastName.isEmpty()) {
                displayArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 25px;");
                displayArea.setText("Error: Both First/Last Name Fields Cannot Be Empty");
                return;
            }

            // Loop over the entire object array, looking for first & last name
            for (Patient patient : Patient.getPatientMap().values()) {
                String patientFirstName = patient.getFirstName().toLowerCase();
                String patientLastName = patient.getLastName().toLowerCase();

                // If Patient Matches
                if (patientFirstName.equals(valueFromTextFieldSearchFirstName) &&
                        patientLastName.equals(valueFromTextFieldSearchLastName)) {

                    // Print patient information
                    resultBuilder.append(patient.printPatientInfo());

                    // Print events for this patient using printEvents() method
                    resultBuilder.append(patient.printEvents());

                    matchFound = true; // Set the flag to true since a match was found
                }
            }

            // Check if any matches were found
            // Set the text outside the loop, after all patients have been checked
            if (matchFound) {
                displayArea.setText(resultBuilder.toString());
            } else {
                displayArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 25px;");
                displayArea.setText("Error: No Matching Names in Database!");
            }

            // Reset the style for display
            displayArea.setStyle("-fx-text-fill: -fx-text-inner-color; -fx-font-size: 25px;");

            // Clear textFields
            textFieldSearchFirstName.clear();
            textFieldSearchLastName.clear();
        });

        // _______________________________________________________

        // DISPLAY DATABASE ON STARTUP
        String patientData = fetchDataFromTable(SELECT_PATIENT_QUERY, "PATIENT");

        String eventsData = fetchDataFromTable(SELECT_EVENTS_QUERY, "PATIENTEVENTS");

        // _______________________________________________________

        // ADD ALL NODES TO ROOT NODE -> (Jordan & Carey)
        root.getChildren().addAll(
                // Title & Radio Buttons
                title, radioButtonAdd, radioButtonDelete, radioButtonAddEvent, radioButtonPatient,
                radioButtonInPatient, radioButtonOutPatient,
                // Input fields and labels
                textTitleFirstName, textFieldFirstName, textTitleLastName, textFieldLastName,
                textTitleRoomNumber, textFieldRoomNumber, textTitleAdditionalInfo, textFieldAdditionalInfo,
                textTitleEventDescription, textFieldEventDescription, textTitlePainLevel, textFieldPainLevel,
                // Buttons
                submitButton, searchForPatientButton, showAllPatientsButton,
                // Display area
                displayArea,
                // Search fields and labels
                textTitleSearchFirstName, textFieldSearchFirstName, textTitleSearchLastName, textFieldSearchLastName);

        // _______________________________________________________

        // Full Screen, Setting the Scene, and Showing the Stage -> (Jordan)
        stage.setTitle("Patient Application");
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();

    }

    // _______________________________________________________

    // User Validation Methods
    // (Jordan)

    // Helper method to check if a string is an integer
    // (Used for Add Event)
    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // First Name
    public static boolean isFirstNameGoodMethod(String userInput) {
        // Check if the input is not empty and contains only letters
        return !userInput.isEmpty() && userInput.matches("^[a-zA-Z]+$");
    }

    // Last Name
    public static boolean isLastNameGoodMethod(String userInput) {
        // Check if the input is not empty and contains only letters
        return !userInput.isEmpty() && userInput.matches("^[a-zA-Z]+$");
    }

    // Room Number
    public static boolean isRoomNumberGoodMethod(String userInput) {
        // Check if the input is not empty and contains only letters
        return !userInput.isEmpty();
    }

    // Pain Level
    public static boolean isPainLevelGoodMethod(String userInput) {
        // Check if the input is not empty and contains only numbers
        return !userInput.isEmpty() && userInput.matches("^[0-9]+$");
    }

    // Event Description
    public static boolean isEventDescriptionGoodMethod(String userInput) {
        // Check if the input is not empty
        return !userInput.isEmpty();
    }

    public static boolean isAdditionalInfoGoodMethod(String userInput) {
        return !userInput.isEmpty();
    }

    // _______________________________________________________

    // EZELL'S DATABASE METHOD
    public static void runDBQuery(String query, char queryType) {

        // queryType - Using the C.R.U.D. acronym
        // 'r' - SELECT
        // 'c', 'u', or 'd' - UPDATE, INSERT, DELETE

        try {
            String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
            String user = "system";
            String pass = "Dunnicliffe";

            oDS = new OracleDataSource();
            oDS.setURL(URL);

            jsqlConn = oDS.getConnection(user, pass);
            jsqlStmt = jsqlConn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            if (queryType == 'r') {
                jsqlResults = jsqlStmt.executeQuery();
            } else {
                jsqlStmt.executeUpdate();
            }
        } catch (SQLException sqlex) {
            System.out.println(sqlex.toString());
        }
    }

    // ------------------------------------

    // FETCH DATA FROM TABLE FOR READING
    private String fetchDataFromTable(String query, String tableName) {
        StringBuilder result = new StringBuilder();
        try {
            runDBQuery(query, 'r'); // 'r' for SELECT
            ResultSet resultSet = jsqlResults;
            while (resultSet.next()) {
                // If the table is PATIENT
                if (tableName.equals("PATIENT")) {

                    // Get the values from the SQL Table
                    int id = resultSet.getInt("PATIENTID");
                    String firstName = resultSet.getString("FIRSTNAME");
                    String lastName = resultSet.getString("LASTNAME");
                    String roomNumber = resultSet.getString("ROOMNUMBER");
                    String patientType = resultSet.getString("PATIENTTYPE");
                    String additionalInfo = resultSet.getString("ADDITIONALINFO");

                    // If PATIENTTYPE column == Patient, create the Patient
                    if ("Patient".equalsIgnoreCase(patientType)) {
                        Patient.createPatient(new Patient(firstName, lastName, roomNumber));
                    }

                    // If PATIENTTYPE column == InPatient, create the InPatient
                    if ("InPatient".equalsIgnoreCase(patientType)) {
                        Patient.createPatient(new InPatient(firstName, lastName, patientType, additionalInfo));
                    }

                    // If PATIENTTYPE column == OutPatient, create the OutPatient
                    if ("OutPatient".equalsIgnoreCase(patientType)) {
                        Patient.createPatient(new OutPatient(firstName, lastName, patientType, additionalInfo));
                    }

                    // Display PATIENT table to textArea
                    result.append(String.format("%s: Department: %s\n", patientType.toUpperCase(), additionalInfo));
                    result.append(
                            String.format("ID: %s - Name: %s, %s - Room: %s\n", id, lastName, firstName, roomNumber));
                    result.append("------------------------------------\n\n");

                    // If the table is PATIENTEVENTS
                } else if (tableName.equals("PATIENTEVENTS")) {

                    // Get the values from the SQL Table
                    // int eventId = resultSet.getInt("EVENTID");
                    int patientId = resultSet.getInt("PATIENTID");
                    String description = resultSet.getString("DESCRIPTION");
                    // String eventDate = resultSet.getString("EVENTDATE");
                    int painLevel = resultSet.getInt("PAINLEVEL");

                    // Create the Event
                    Patient.addEvent(patientId, description, painLevel);
                }
            }
            // Close ResultSet and PreparedStatement after use
            resultSet.close();
            jsqlStmt.close();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
        return result.toString();
    }

    // WRITING TO DATABASE LOGIC
    public static void insertNewPatient(Patient patient) {
        String additionalInfo = "";

        if (patient instanceof InPatient) {
            additionalInfo = ((InPatient) patient).getDepartment();
        } else if (patient instanceof OutPatient) {
            additionalInfo = ((OutPatient) patient).getProcedureName();
        }

        String sql = "INSERT INTO PATIENT (PATIENTID, FIRSTNAME, LASTNAME, ROOMNUMBER, PATIENTTYPE, ADDITIONALINFO) VALUES ("
                + patient.getID() + ", '" + patient.getFirstName().replace("'", "''") + "', '"
                + patient.getLastName().replace("'", "''") + "', '" + patient.getRoomLocation().replace("'", "''")
                + "', '" + patient.getPatientType() + "', '" + additionalInfo.replace("'", "''") + "')";
        runDBQuery(sql, 'c');
    }

    // DELETING FROM DATABASE LOGIC
    public static void deletePatientFromDatabase(int patientID) {
        String sqls = "DELETE FROM PATIENTEVENTS WHERE PATIENTID = " + patientID;
        runDBQuery(sqls, 'd');
        String sql = "DELETE FROM PATIENT WHERE PATIENTID = " + patientID;
        runDBQuery(sql, 'd');
    }

    // ADD EVENT LOGIC
    public static void addEventToDatabase(int patientID, String eventDescription, int painLevel) {
        // Build the SQL query with parameters
        String sql = "INSERT INTO PATIENTEVENTS (PATIENTID, DESCRIPTION, PAINLEVEL) VALUES ("
                + patientID + ", '" + eventDescription + "', " + painLevel + ")";

        // Call runDBQuery with the query and query type 'c' for INSERT
        runDBQuery(sql, 'c');
    }

    // _______________________________________________________

    public static void main(String[] args) {
        // PRINTS DATABASE VALUES TO THE CONSOLE FOR TESTING
        // try {
        // runDBQuery("SELECT * FROM PATIENTEVENTS", 'r');
        // while (jsqlResults.next()) {
        // System.out.println(
        // jsqlResults.getString(1) + " " + jsqlResults.getString(2) + " " +
        // jsqlResults.getString(3) + " "
        // + jsqlResults.getString(4) + " "
        // + jsqlResults.getString(5));
        // }
        // } catch (Exception ex) {
        // ex.printStackTrace();
        // }
        launch(args);
    }
}