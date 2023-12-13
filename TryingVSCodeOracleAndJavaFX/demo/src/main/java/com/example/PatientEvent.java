// Name: Jordan Kleinbaum & Carey Datre
// Purpose: Group Project Part 3: DB Integration with Patient GUI
// Date: December 13th, 2023

package com.example;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PatientEvent {
  private String date;
  private String description;
  private int painLevel;

  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  Date Date = new Date();

  public PatientEvent(String date, String description, int painLevel) {

    this.date = date;
    this.description = description;

    if (painLevel <= 10 && painLevel >= 0) {
      this.painLevel = painLevel;
    } else {
      this.painLevel = -1;
    }

  }

  // Getters
  public String getDate() {
    return this.date;
  }

  public String getDescription() {
    return this.description;
  }

  public int getPainLevel() {
    return this.painLevel;
  }
}
