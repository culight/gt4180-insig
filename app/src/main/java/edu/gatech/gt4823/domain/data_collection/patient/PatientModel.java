package edu.gatech.gt4823.domain.data_collection.patient;

/**
 * Created by Demerrick Moton on 11/2/2014.
 */
public class PatientModel {
    public PatientInfo patientInfo;
    public PatientStatus patientStatus;
    public Vitals patientVitals;

    public PatientModel () {
        patientInfo = new PatientInfo();
        patientStatus = new PatientStatus();
        patientVitals = new Vitals();
    }
}




