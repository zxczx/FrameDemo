package com.example.framedemo.downloader;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;

import java.io.File;

/**
 * FileDownloader的监听回调
 */
public abstract class SimpleFileDownloadListener extends FileDownloadListener {

    protected abstract void updateProgress(int progress);

    private int totalSize;

    public SimpleFileDownloadListener(int totalSize) {
        this.totalSize = totalSize;
    }

    public SimpleFileDownloadListener() {
    }

    @Override
    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        if (totalSize > 0) {
            updateProgress((int) (soFarBytes * (100f / totalSize)));
        }else {
            updateProgress((int)(soFarBytes * (100f / totalBytes)));
        }
    }

    @Override
    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

    }

    @Override
    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

    }

    @Override
    protected void warn(BaseDownloadTask task) {

    }


}
