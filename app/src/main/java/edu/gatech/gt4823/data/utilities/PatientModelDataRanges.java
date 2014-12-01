package edu.gatech.gt4823.data.utilities;

/**
 * Created by Demerrick Moton on 11/2/2014.
 */
public class PatientModelDataRanges {
    // age (in years)
    public static int[] ageRange = new int[]{0,100};
    // estimated time of arrival
    public static int[] ETARange = new int[]{0,60};
    // blood pressure systolic
    public static int[] bpSysRange = new int[]{40,200};
    // blood pressure diastolic
    public static int[] bpDiaRange = new int[]{25,120};
    // blood glucose level
    public static int[] bglRange = new int[]{0,30};
    // total gcs score range
    public static int[] gcsScoreRange = new int[]{3,15};
    // heart rate
    public static int[] hrRange = new int[]{50,215};
}
