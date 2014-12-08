package edu.gatech.gt4823.data.utilities;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;

import edu.gatech.gt4823.domain.data_collection.metadata.multimedia.NotesData;
import edu.gatech.gt4823.domain.data_collection.metadata.multimedia.PhotoData;
import edu.gatech.gt4823.domain.data_collection.metadata.multimedia.VideoData;

/**
 * Created by Demerrick on 11/29/2014.
 */
public class InsigApp extends Application {

    private static final String TAG = "InsigApp: ";
    private static InsigApp singleton;

    public EditText savedVitalsData[];
    public EditText savedInfoData[];
    public EditText savedStatusData[];

    private ArrayList<VideoData> Videos;
    private ArrayList<PhotoData> Photos;
    private ArrayList<NotesData> Notes;

    public int videoCount = 0;
    public int photoCount = 0;
    public int notesCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        savedVitalsData = new EditText[4];
        savedInfoData = new EditText[3];
        savedStatusData = new EditText[2];

        Videos = new ArrayList<VideoData>();
        Photos = new ArrayList<PhotoData>();
        Notes = new ArrayList<NotesData>();

        singleton = this;
    }

    public InsigApp getInstance() {
        return singleton;
    }

    /**
     * Save vitals data
     *
     * @param bp
     * @param pls
     * @param res
     * @param sao2
     */
    public void setSavedVitalsData( EditText bp, EditText pls,
                                    EditText res, EditText sao2) {
        savedVitalsData[0] = bp;
        savedVitalsData[1] = pls;
        savedVitalsData[2] = res;
        savedVitalsData[3] = sao2;
    }

    public void setSavedInfoData(EditText id, EditText name,
                                 EditText num) {
        savedInfoData[0] = id;
        savedInfoData[1] = name;
        savedInfoData[2] = num;
    }

    public void setSavedStatusData(EditText comp, EditText inj) {
        savedStatusData[0] = comp;
        savedStatusData[1] = inj;
    }

    public void setVideoData(String thumbnailName, String pathName) {
        VideoData video = new VideoData();
        video.videoPath = pathName;
        video.videoThumbnail = thumbnailName;
        video.index = videoCount;
        Videos.add(video);
        videoCount++;
    }

    public void setPhotoData(String pathName) {
        PhotoData photo = new PhotoData();
        photo.photoPath = pathName;
        photo.index = photoCount;
        Photos.add(photo);
        photoCount++;
    }

    public void setNotesData(String message) {
        NotesData note = new NotesData();
        note.message = message;
        note.index = notesCount;
        Notes.add(note);
        notesCount++;
    }

    public EditText[] getSavedVitalsData() {
        if(savedVitalsData.length < 1) {
            Log.d(TAG, "no vitals data");
            return null;
        }
        return savedVitalsData;
    }

    public EditText[] getSavedInfoData() {
        if(savedInfoData.length < 1) {
            Log.d(TAG, "no info data");
            return null;
        }
        return savedInfoData;
    }

    public EditText[] getSavedStatusData() {
        if(savedStatusData.length < 1) {
            Log.d(TAG, "no status data");
            return null;
        }
        return savedStatusData;
    }

    public ArrayList<VideoData> getVideoData() {
        return Videos;
    }

    public ArrayList<PhotoData> getPhotoData() {
        return Photos;
    }

    public ArrayList<NotesData> getNotesData() {
        return Notes;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
