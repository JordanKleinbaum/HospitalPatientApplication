// Name: Jordan Kleinbaum & Carey Datre
// Purpose: Group Project Part 3: DB Integration with Patient GUI
// Date: December 13th, 2023

package com.example;

public class OutPatient extends Patient {
  // Data Fields
  private String procedureName;

  // Constructor
  public OutPatient(String fName, String lName, String roomLoc, String procedure) {
    super(fName, lName, roomLoc);

    if (!procedure.isEmpty()) {
      this.procedureName = procedure;
    } else {
      this.procedureName = "none";
    }
  }

  // Getter
  public String getProcedureName() {
    return this.procedureName;
  }

  @Override
  public String getPatientType() {
    return "OutPatient";
  }

  // Setter
  public void setProcedureName(String procedure) {
    if (procedure != null && !procedure.trim().isEmpty()) {
      this.procedureName = procedure;
    } else {
      this.procedureName = "none";
    }
  }

  @Override
  public String printPatientInfo() {
    StringBuilder patientInfoString = new StringBuilder();
    patientInfoString.append("OUTPATIENT: Procedure: ").append(procedureName).append("\n");
    patientInfoString.append(super.printPatientInfo());

    return patientInfoString.toString();
  }

}
