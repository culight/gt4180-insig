package edu.gatech.gt4823.domain.data_collection.metadata.call_info;

import java.sql.Date;
import java.sql.Time;

import edu.gatech.gt4823.domain.data_collection.call_info.Agency;
import edu.gatech.gt4823.domain.data_collection.call_info.ArrivalData;
import edu.gatech.gt4823.domain.data_collection.call_info.Unit;

/**
 * Created by Demerrick Moton on 11/2/2014.
 */
public class CallModel {
    Date callDate;
    Time callTime;
    Agency callAgency;
    Unit callUnit;
    ArrivalData patientArrivalData;
}







