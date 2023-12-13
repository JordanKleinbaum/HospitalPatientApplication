// Name: Jordan Kleinbaum & Carey Datre
// Purpose: Group Project Part 3: DB Integration with Patient GUI
// Date: December 13th, 2023

package com.example;

public class InPatient extends Patient {
  // Data Field
  private String departmentName;

  // Constructors
  public InPatient(String fName, String lName, String roomLoc, String department) {
    super(fName, lName, roomLoc);

    if (!department.isEmpty()) {
      this.departmentName = department;
    } else {
      this.departmentName = "none";
    }
  }

  // Getter Method
  public String getDepartment() {
    return this.departmentName;
  }

  @Override
  public String getPatientType() {
    return "InPatient";
  }

  // Setter Method
  public void setDepartment(String department) {
    if (!department.isEmpty()) {
      this.departmentName = department;
    } else {
      this.departmentName = "none";
    }
  }

  @Override
  public String printPatientInfo() {
    StringBuilder patientInfoString = new StringBuilder();
    patientInfoString.append("INPATIENT: Department: ").append(departmentName).append("\n");
    patientInfoString.append(super.printPatientInfo());

    return patientInfoString.toString();
  }

}
