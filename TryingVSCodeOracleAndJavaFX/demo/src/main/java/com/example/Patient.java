// Name: Jordan Kleinbaum & Carey Datre
// Purpose: Group Project Part 3: DB Integration with Patient GUI
// Date: December 13th, 2023

package com.example;

import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;

public class Patient {
  // Implement HashMaps to have an unlimited amount of users, as well as not
  // moving up after deletion
  private static HashMap<Integer, Patient> patientMap = new HashMap<>();

  public static HashMap<Integer, Patient> getPatientMap() {
    return patientMap;
  }

  private HashMap<Integer, PatientEvent> patientEvents = new HashMap<>();

  public HashMap<Integer, PatientEvent> getEvents() {
    return patientEvents;
  }

  public static int patientObjectsCreatedCounter = 0;
  private static int mostRecentIDUsed = 0;

  public int patientEventsCounter;
  private int ID = 1;
  private String fName;
  private String lName;
  private String roomLoc;

  // Default Constructor
  public Patient() {
    this.fName = "___";
    this.lName = "___";
    this.roomLoc = "___";

  }

  // Full Constructor
  public Patient(String fName, String lName, String roomLoc) {
    this.ID = mostRecentIDUsed++;
    this.fName = fName;
    this.lName = lName;
    this.roomLoc = roomLoc;
    if (patientEvents == null) {
      this.patientEvents = new HashMap<>();
    }
    this.patientEventsCounter = 0;
  }

  public void addPatientEvent(String eventDescription, int painLevel) {
    String currentDateAndTime = getCurrentDateAndTime();
    patientEvents.put(patientEventsCounter++, new PatientEvent(currentDateAndTime, eventDescription, painLevel));
  }

  private String getCurrentDateAndTime() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date currentDate = new Date();
    return dateFormat.format(currentDate);
  }

  public String printEvents() {
    StringBuilder eventsString = new StringBuilder();
    if (!patientEvents.isEmpty()) {
      for (PatientEvent event : patientEvents.values()) {
        if (event != null) {
          eventsString.append("Date: ").append(event.getDate()).append(" - Pain Level: ").append(event.getPainLevel())
              .append("\nDescription: ").append(event.getDescription()).append("\n\n");
        }
      }
    } else {
      eventsString.append("No events for Patient ID: ").append(ID);
    }
    return eventsString.toString();
  }

  public String printPatientInfo() {
    StringBuilder patientInfoString = new StringBuilder();
    patientInfoString.append("ID: ").append(ID).append(" - Name: ").append(lName).append(", ").append(fName)
        .append(" - Room: ").append(roomLoc).append("\n------------------------------------\n");
    return patientInfoString.toString();
  }

  // Getters
  public int getID() {
    return ID;
  }

  public String getFirstName() {
    return fName;
  }

  public String getLastName() {
    return lName;
  }

  public String getRoomLocation() {
    return roomLoc;
  }

  public String getPatientType() {
    return "Patient";
  }

  // Setters
  public void setFirstName(String firstName) {
    if (!firstName.equals("")) {
      this.fName = firstName;
    }
  }

  public void setLastName(String lastName) {
    if (!lastName.equals("")) {
      this.lName = lastName;
    }
  }

  public void setRoomLocation(String roomLocation) {
    if (!roomLocation.equals("")) {
      this.roomLoc = roomLocation;
    }
  }

  public static void addPatientToMap(Patient patient) {
    patientMap.put(patient.getID(), patient);
    patientObjectsCreatedCounter++;
  }

  // FIXED THIS
  // NOW DELETE PATIENT SAVES PROPERLLY
  public static boolean removePatientFromMap(int id) {
    if (patientMap.containsKey(id)) {
      patientMap.remove(id);
      return true; // Deletion successful
    }
    return false; // Deletion failed
  }

  public static String readPatientEvents(int id) {
    StringBuilder result = new StringBuilder();
    Patient patient = patientMap.get(id);
    if (patient != null) {
      result.append(patient.printEvents());
    } else {
      result.append("No patient with ID: ").append(id);
    }
    return result.toString();
  }

  public static String readAllPatientsInfo() {
    StringBuilder result = new StringBuilder();
    for (Patient patient : patientMap.values()) {
      if (patient != null) {
        result.append(patient.printPatientInfo()).append("\n");
      } else {
        return "No patient at position";
      }
    }
    return result.toString();
  }

  public static boolean addEvent(int id, String eventDescription, int painLevel) {
    Patient patient = patientMap.get(id);
    if (patient != null) {
      patient.addPatientEvent(eventDescription, painLevel);
      return true;
    } else {
      System.out.println("No patient with ID: " + id);
      return false;
    }
  }

  public static int searchPatient(String firstName, String lastName) {
    for (Patient patient : patientMap.values()) {
      if (patient != null && patient.getLastName().equals(lastName) && patient.getFirstName().equals(firstName)) {
        return patient.getID();
      }
    }
    return -1;
  }

  public static void createPatient(Patient patient) {
    patientMap.put(patient.getID(), patient);
    patientObjectsCreatedCounter++;
  }

}
