package org.jboss.qe.collector;

import org.apache.cxf.helpers.IOUtils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

/**
 * Created by fjerabek on 6.9.16.
 */
public class Cache {
   private Path filePath;
   private File file;

   public Cache(String filename) {
      Path path = null;
      try {
         path = Files.createTempDirectory("results_checker");
      } catch (IOException e) {
         e.printStackTrace();
      }
      this.filePath = path.resolve(filename);
      file = new File(this.filePath.toString());

   }

   public void add(String data) {
      try (FileOutputStream os = new FileOutputStream(filePath.toString())) {
         os.write(data.getBytes()); //cache passed data
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public boolean isActual(int time) {
      if (time == 0) {
         return false;
      }
      Date lastModified = new Date(file.lastModified());
      long fileAge = (new Date().getTime() - lastModified.getTime()) / 1000;
      if (fileAge > time) { //compare last date of modification of file with current time and date
         return false;
      } else {
         return true;
      }
   }

   public boolean exist() {
      return file.exists();
   }

   public String getAll() {
      try (FileInputStream is = new FileInputStream(filePath.toString())) {
         String data = IOUtils.toString(is);
         return data;
      } catch (IOException e) {
         e.printStackTrace();
         return null;
      }
   }
}