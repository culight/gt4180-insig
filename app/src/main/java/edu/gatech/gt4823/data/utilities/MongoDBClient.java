package edu.gatech.gt4823.data.utilities;

import android.util.Log;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoServerSelectionException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.QueryBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.UnknownHostException;

import edu.gatech.gt4823.domain.authentication.User;

/**
 * MongoDBClient.java Handles MongoDB requests
 * @author APatel
 * @Version 0.61
 */
public class MongoDBClient {
        private static final String TAG = "wcim.util:MongoClient.java";

        //Database
        private static final String DB_ADDR = "198.58.102.239"; // enter ip address
        private static final int DB_PORT = 27017;               // enter port number
        private static MongoClient mongoClient;
        private static DB db;
        private static int errorFlag = 0; //0: no error, 1: mongo timeout

        /**
         * Initializes the database. Should be called in any new method querying the database
         */
        private static void initialize(){
                try {
                        mongoClient = new MongoClient(DB_ADDR, DB_PORT);
                        db = mongoClient.getDB("wcim");
                } catch (UnknownHostException e) {
                        Log.e(TAG, "Communication Error");
                        errorFlag = 2;
                }

        }

    /**
     * Adapter for querying Account Match
     * @param query
     * @return String
     */
    private static String getAccount(QueryBuilder query){
        DBCollection patientVisits;
        DBCursor cursor;
        String responseAccounts = "";
        patientVisits = db.getCollection("accounts");
        if(query != null)
            cursor = patientVisits.find(query.get());
        else
            return null;
        try {
            while(cursor.hasNext())
                responseAccounts += cursor.next() + "\n";
        } catch(MongoTimeoutException e){
            Log.e(TAG, "MongoDB timeout: getAccount: no response");
            errorFlag = 1;
        }  catch(MongoServerSelectionException e){
            Log.e(TAG, "MongoDB ServerSelection Error: getPatientIndexData");
            errorFlag = 1;
        }finally {
            cursor.close();
        }
        return responseAccounts;
    }


    /**
     * Queries for patients that begin with the search string
     * @param search
     * @return PatientInfo[]
     */
    public static void queryAccountLogin(String username, String password){
        if(username == null || username.length() == 0 || password == null || password.length() == 0)
            return;
        initialize();
        QueryBuilder query = new QueryBuilder();
        String responseAccount = "";
        query.and(QueryBuilder.start("username").is(username).get(),
                QueryBuilder.start("password").is(password).get()
        );
        responseAccount = getAccount(query);
        close();
        parseAccount(responseAccount);
    }

        /**
         * Closes the database. Should be called at the end of any new method querying the database 
         */
        private static void close(){
                mongoClient.close();
        }

        /**
         * Parses the JSON query result
         * @param query
         * @return PatientInfo[]
         */
        private static void parseAccount(String query){
                if(query == null || query == "")
                        return;
                JSONObject main;
                String[] results = query.split("\n");
                
                try {
                        main = new JSONObject(results[0]);
                        User.setUsername(main.getString("username"));
                        User.setLastName(main.getString("lastName"));
                        User.setFirstName(main.getString("firstName"));
                        User.setLastSync(main.getInt("lastSync"));
                        JSONObject services = main.getJSONObject("services");
                        User.setSvcHash_emr(services.getString("emr"));
                        User.setSvcHash_img(services.getString("img"));
                        User.setSvcHash_tele(services.getString("tele"));
                } catch (JSONException e) {
                        Log.e(TAG, "Parse Account Error");
                }
        }

        public static String getErrorFlag(){
                int check = errorFlag;
                errorFlag = 0;
                switch(check){
                case 0:
                        return null;
                case 1:
                        return "Server not found!";
                case 2:
                        return "Communication Error!";
                default:
                        Log.e(TAG, "UNHANDLED ERROR FLAG: getErrorFlag");
                        return null;
                }
        }
        //CALL WITH:    
        /*      TestDB test = new TestDB();
        test.execute(new Void[] {});*/
/*      private class TestDB extends AsyncTask<Void, Void, Void> {

                @Override
                protected Void doInBackground(Void... params) {
                        MongoDBClient.queryPatientIndex();
                        return null;
                }

                @Override
                protected void onPostExecute(Void apple) {

                }

        }*/
}
