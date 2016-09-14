package org.jboss.qe.collector;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

/**
 *
 * @author Jan Dobes
 */
public class Tools {
   public static List<File> fileLoader(String path) {
      String[] items = path.split("/");
      ArrayList<ArrayList<File>> listOfDirectories = new ArrayList<ArrayList<File>>();
      for (int i = 0; i < items.length; i++) {
         //System.out.println(items[i]);
         if (items[i].equals("**")) {
            String directoryPath = "";
            if (listOfDirectories.size() > 0) {
               //System.out.println("Size > 0");
               ArrayList<File> directories = new ArrayList();
               for (File directory : listOfDirectories.get(listOfDirectories.size() - 1)) {
                  File[] it = directory.listFiles();
                  if (it != null) {
                     for (File item : it) {
                        if (item.isDirectory()) {
                           directories.add(item);
                           //System.out.println(item.getName());
                        }
                     }
                  }
                  //System.out.println(directoryPath);
               }
               listOfDirectories.add(directories);
            }
            else {
               for (int ii = 0; ii < i; ii++) {
                  directoryPath += items[ii] + "/";
                  //System.out.println(directoryPath);
               }
               //System.out.println(directoryPath);
               File directory = new File(directoryPath);
               //System.out.println(directory.getName());
               File[] listOfFiles = directory.listFiles();
               if (listOfFiles != null) {
                  ArrayList<File> directories = new ArrayList();
                  for (int ii = 0; ii < listOfFiles.length; ii++) {
                     if (listOfFiles[ii].isDirectory()) {
                        directories.add(listOfFiles[ii]);
                        //System.out.println(listOfFiles[ii].getName());
                     }
                  }
                  listOfDirectories.add(directories);
               }
            }
         }
         else {
            if (!listOfDirectories.isEmpty()) {
               ArrayList<File> directories = new ArrayList();
               int count = 0;
               for (File directory : listOfDirectories.get(listOfDirectories.size() - 1)) {
                  if (!items[i].equals("*.xml")) {
                     directories.add(new File(directory.getAbsolutePath() + "/" + items[i]));
                     //System.out.println(directory.getAbsolutePath() + "/" + items[i]);
                  }
                  count++;
               }
               listOfDirectories.add(directories);
               /*for (File dirs : listOfDirectories.get(listOfDirectories.size() - 1)) {
                  System.out.println(dirs.getName());
               }*/
            }
         }
      }

      ArrayList<File> testFiles = new ArrayList<File>();
      if (listOfDirectories.isEmpty()) {
         //System.out.println("List of directories is empty");
         if (items[items.length - 1].equals("*.xml")) {
            File directory = new File(items[items.length - 2]);
            //System.out.println(directory.getName());
            File[] listOfFiles = directory.listFiles();
            if (listOfFiles != null) {
               //System.out.println("Is not null");
               for (int i = 0; i < listOfFiles.length; i++) {
                  String fileName = listOfFiles[i].getName();
                  String fileExtension = "";
                  int lastIndex = fileName.lastIndexOf(".");
                  if (lastIndex > 0) {
                     fileExtension = fileName.substring(lastIndex + 1);
                  }
                  if (fileExtension.equals("xml")) {
                     testFiles.add(listOfFiles[i]);
                     //System.out.println(listOfFiles[i].getName());
                  }
               }
            }
            for (int i = 0; i < testFiles.size(); i++) {
               //System.out.println(testFiles.get(i).getName());
            }
            return testFiles;
         }
         testFiles.add(new File(path));
         return testFiles;
      }

      ArrayList<File> directories = listOfDirectories.get(listOfDirectories.size() - 2);
      if (listOfDirectories.get(listOfDirectories.size() - 1).isEmpty()) {
         //System.out.println("List of dirs is empty");
      }
      for (File dirs : directories) {
         //System.out.println(dirs.getName());
      }
      /*for (File dir : listOfDirectories.get(listOfDirectories.size() - 1)) {
         System.out.println(dir.getName());
      }
      /*for (ArrayList<File> dir : listOfDirectories) {
         for (File it : dir) {
            System.out.println(it.getName());
         }
      }*/
      if (items[items.length - 1].equals("*.xml") ) {
         //System.out.println("item equals xml");
         for (int i = 0; i < directories.size(); i++) {
            //System.out.println(i);
            //System.out.println(directories.get(i).getName());
            File[] listOfFiles = directories.get(i).listFiles();
            //System.out.println(directories.get(i).getName());
            if (listOfFiles != null) {
               for (File file : listOfFiles) {
                  String fileName = file.getName();
                  //System.out.println(file.getName());
                  String fileExtension = "";
                  int lastIndex = fileName.lastIndexOf(".");
                  if (lastIndex > 0) {
                     fileExtension = fileName.substring(lastIndex + 1);
                  }
                  if (fileExtension.equals("xml")) {
                     testFiles.add(file);
                     //System.out.println(file.getAbsolutePath());
                  }
               }
            }
         }
      }
      else {
         for (int i = 0; i < directories.size(); i++) {
            testFiles.add(new File(directories.get(i).getAbsolutePath() + "/" + items[items.length - 1]));
            //System.out.println(directories.get(i).getAbsolutePath() + "/" + items[items.length - 1]);
         }
      }
      return testFiles;
   }

   public static String getEnvironmentVariable(String name) {
      Map<String, String> env = System.getenv();

      if (env.get(name) != null) {
         return env.get(name);
      } else {
         switch (name) {
            case "CACHE_TIME_VALIDITY":
               return "300";
            case "SERVER_NAME":
               return "jenkinse.zloutek-soft.cz";
               //return "jenkins.mw.lab.eng.bos.redhat.com";
            case "MATRIX_FULL":
               return "false";
            case "CHECKER_ENVIRONMENT":
               return "";
            default:
               throw new IllegalStateException("Unexpected environment variable: " + name);
         }
      }
   }

   public static void setEnvironmentVariable(String key, String value) {
      Map<String, String> env = new HashMap<>();
      env.put(key,value);
      set(env);
   }

   @SuppressWarnings("unchecked")
   private static void set(Map<String, String> newenv) {
      try {
         try {
            Class[] classes = Collections.class.getDeclaredClasses();
            Map<String, String> env = System.getenv();
            for (Class cl : classes) {
               if ("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
                  Field field = cl.getDeclaredField("m");
                  field.setAccessible(true);
                  Object obj = field.get(env);
                  Map<String, String> map = (Map<String, String>) obj;
                  map.clear();
                  map.putAll(newenv);
               }
            }
         } catch (NoSuchFieldException e) {
            e.printStackTrace();
         }
      } catch (IllegalAccessException e) {
         e.printStackTrace();
      }
   }
}
