package homemgmtsystem.dto;

import java.io.File;

public class BckRestoreDTO {

    private File Directory;
    private String filename;
    private String filepath;
    private String fileloc;

    public File getDirectory() {
        return Directory;
    }

    public void setDirectory(File Directory) {
        this.Directory = Directory;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFileloc() {
        return fileloc;
    }

    public void setFileloc(String fileloc) {
        this.fileloc = fileloc;
    }
}
