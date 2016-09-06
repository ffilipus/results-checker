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
        try {
            FileOutputStream outputStream = new FileOutputStream(filePath);//get file to write to
            outputStream.write(data.getBytes());//cache passed data
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isActual(int time){
        if(time == 0){
            return false;
        }
        Date lastModified = new Date(file.lastModified());
        long fileAge = (new Date().getTime() - lastModified.getTime()) / 1000;
        if(fileAge > time){ //compare last date of modification of file with current time and date
            return false;
        }else{
            return true;
        }
    }

    public boolean exist(){
        return file.exists();
    }

    public String getAll(){
        try {
            FileInputStream inputStream = new FileInputStream(this.filePath); //get file to read from and read the cache
            String data = IOUtils.toString(inputStream);
            inputStream.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}