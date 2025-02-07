package org.example.filemanager;

public class File {
    private String name;
    private long size;
    private String date;
    private String path;
    private boolean isDirectory;
    private boolean isChosen = false;

    public File(String name, long size, String date, String path, boolean isDirectory) {
        this.name = name;
        this.size = size;
        this.date = date;
        this.path = path;
        this.isDirectory = isDirectory;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public String getDate() {
        return date;
    }

    public String getPath() {
        return path;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public boolean isChosen() {
        return isChosen;
    }

    public void setIsChosenTo(boolean isChosen) {
        this.isChosen = isChosen;
    }
}
