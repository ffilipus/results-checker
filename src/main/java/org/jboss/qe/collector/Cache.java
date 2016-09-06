package org.jboss.qe.collector;

import org.apache.cxf.helpers.IOUtils;

import java.io.*;
import java.util.Date;

/**
 * Created by fjerabek on 6.9.16.
 */
public class Cache {
    private String filePath;
    private File file;
    public Cache(String filename){
        this.filePath = "/tmp/" + filename;
        file = new File(this.filePath);

    }

    public void add(String data){

        try (FileOutputStream os = new FileOutputStream(filePath)) {
            os.write(data.getBytes()); //cache passed data
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isActual(int time){
        if (time == 0) {
            return false;
        }
        Date lastModified = new Date(file.lastModified());
        long fileAge = (new Date().getTime() - lastModified.getTime()) / 1000;
        if (fileAge > time) { //compare last date of modification of file with current time and date
            return false;
        }else{
            return true;
        }
    }

    public boolean exist(){
        return file.exists();
    }

    public String getAll(){
        try (FileInputStream is = new FileInputStream(filePath)) {
            String data = IOUtils.toString(is);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}