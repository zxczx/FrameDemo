package com.example.framedemo.file;

import android.os.Environment;
import com.example.framedemo.FrameApplication;
import java.io.File;
import timber.log.Timber;

public class FileManager {

    public static final String TAG = FileManager.class.getSimpleName();
    public static final String VIDEO_DIR = "video";
    public static final String CACHE_DIR = "cache";
    public static final String AVATAR_DIR = "avatar";

    public static boolean isVideoMidiFileExist(String midiHash) {
        File videoDir = getResourceDir(VIDEO_DIR);
        if (videoDir.exists()) {
            File midiFile = new File(videoDir, midiHash + ".mp3");
            if (midiFile.exists()) {
                return true;
            }
        }
        return false;
    }

    public static String getVideoMidiFilePath(String midiHash) {
        File file = getResourceDir(VIDEO_DIR);
        return new File(file, midiHash + ".mp3").getAbsolutePath();
    }


    public static boolean isDataDirExist(String typeDir, String hash) {
        File dir = getResourceDir(typeDir);
        if (dir.exists()) {
            File dataDir = new File(dir, hash);
            if (dataDir.exists() && dataDir.isDirectory()) {
                return true;
            }
        }
        return false;
    }


    public static File getZipFileByType(String typeDir, String hash) {
        File file = getResourceDir(typeDir);
        return new File(file, hash + ".zip");
    }

    public static String getZipFilePathByType(String typeDir, String hash) {
        return getZipFileByType(typeDir, hash).getAbsolutePath();
    }

    public static File getDirectoryOfType(String typeDir, String hash) {
        File file = getResourceDir(typeDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        File directory = new File(file, hash);
        if (!directory.exists()) {
            directory.mkdirs();
        }


        return directory;
    }

    public static String getDirectoryPathOfType(String typeDir, String hash) {
        return getDirectoryOfType(typeDir, hash).getAbsolutePath();
    }


    public static File getResourceDir(String dirName){
        File file = getWritableFileDir();

        File subDirFile = new File(file,dirName);
        if (!subDirFile.exists()){
            if (!subDirFile.mkdirs()){
                Timber.tag(TAG).d(dirName + "directory not created");
            }
        }
        return subDirFile;
    }


    public static File getWritableFileDir(){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            File file = FrameApplication.getApp().getExternalFilesDir(null);
            if (file == null){
                file = FrameApplication.getApp().getFilesDir();
            }
            return file;
        } else {
            return FrameApplication.getApp().getFilesDir();
        }
    }



    public static void deleteFile(File file) {
        if (file.exists()) {
            if (!file.isDirectory()) {
                file.delete();
            } else {
                File[] files = file.listFiles();
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        deleteFile(files[i]);
                    }
                }
                file.delete();
            }
        }
    }

    public static File getCacheFile() {
        return new File(getResourceDir(FileManager.CACHE_DIR), "netcache");
    }




}
