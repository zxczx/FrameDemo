package com.example.framedemo.downloader;

import android.content.Context;

import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

public class Downloader {

    public static void setup(Context context) {
        FileDownloader.setup(context);
    }


    public static void startDownload(String dataUrl, String destPath, SimpleFileDownloadListener downloadListener) {
        FileDownloader.getImpl().create(dataUrl)
                .setPath(destPath)
                .setListener(downloadListener)
                .start();
    }


    public static void pauseDownload(String dataUrl, String destPath) {
        FileDownloader.getImpl().pause(FileDownloadUtils.generateId(dataUrl, destPath));
    }

    public static boolean isDownloading(String dataUrl, String destPath) {
        return FileDownloader.getImpl().getStatus(dataUrl, destPath) > 0;
    }

    public static long getTotal(String dataUrl, String destPath) {
        return FileDownloader.getImpl().getTotal(FileDownloadUtils.generateId(dataUrl, destPath));
    }

    public static long getSoFar(String dataUrl, String destPath) {
        return FileDownloader.getImpl().getSoFar(FileDownloadUtils.generateId(dataUrl, destPath));
    }

    public static int getId(String dataUrl, String destPath) {
        return FileDownloadUtils.generateId(dataUrl, destPath);
    }
}
