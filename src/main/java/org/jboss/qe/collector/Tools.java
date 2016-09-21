package org.jboss.qe.collector;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author Jan Dobes
 */
public class Tools {
   private static Map<String, String> env;
   static {
      env = new HashMap<>();
   }

   /*public static List<File> fileLoader(String path) {
      String[] names = path.split(" ");
      List<File> listFiles = new LinkedList<>();
      for (String name : names) {
         listFiles.add(new File(name));
      }
      return listFiles;
   }*/
   public static List<File> fileLoader(String path) {
      String[] items = path.split("/");
      ArrayList<ArrayList<File>> listOfDirectories = new ArrayList<>();
      for (int i = 0; i < items.length; i++) {
         if (items[i].equals("**")) {
            String directoryPath = "";
            if (listOfDirectories.size() > 0) {
               ArrayList<File> directories = new ArrayList<>();
               listOfDirectories.get(listOfDirectories.size() - 1).stream().map(File::listFiles).filter((it) -> (it != null)).forEach((it) -> {
                  for (File item : it) {
                     if (item.isDirectory()) {
                        directories.add(item);
                     }
                  }
               });
               listOfDirectories.add(directories);
            }
            else {
               for (int ii = 0; ii < i; ii++) {
                  directoryPath += items[ii] + "/";
               }
               File directory = new File(directoryPath);
               File[] listOfFiles = directory.listFiles();
               if (listOfFiles != null) {
                  ArrayList<File> directories = new ArrayList<>();
                  for (File file : listOfFiles) {
                     if (file.isDirectory()) {
                        directories.add(file);
                     }
                  }
                  listOfDirectories.add(directories);
               }
            }
         }
         else {
            if (!listOfDirectories.isEmpty()) {
               ArrayList<File> directories = new ArrayList<>();
               for (File directory : listOfDirectories.get(listOfDirectories.size() - 1)) {
                  if (!items[i].equals("*.xml")) {
                     directories.add(new File(directory.getAbsolutePath() + "/" + items[i]));
                  }
               }
               listOfDirectories.add(directories);
            }
         }
      }

      return listFiles(path, items, listOfDirectories);
   }

   private static List<File> listFiles(String path, String[] items, ArrayList<ArrayList<File>> listOfDirectories) {
      ArrayList<File> testFiles = new ArrayList<>();
      if (listOfDirectories.isEmpty()) {
         if (items[items.length - 1].equals("*.xml")) {
            File directory = new File(items[items.length - 2]);
            File[] listOfFiles = directory.listFiles();
            if (listOfFiles != null) {
               for (File file : listOfFiles) {
                  String fileExtension = getFileExtension(file.getName());
                  if (fileExtension.equals("xml")) {
                     testFiles.add(file);
                  }
               }
            }
            return testFiles;
         }
         testFiles.add(new File(path));
         return testFiles;
      }

      List<File> directories = listOfDirectories.get(listOfDirectories.size() - 2);

      if (items[items.length - 1].equals("*.xml") ) {
         for (File directory : directories) {
            File[] listOfFiles = directory.listFiles();
            if (listOfFiles != null) {
               for (File file : listOfFiles) {
                  String fileExtension = getFileExtension(file.getName());
                  if (fileExtension.equals("xml")) {
                     testFiles.add(file);
                  }
               }
            }
         }
      }
      else {
         testFiles.addAll(directories.stream().map(directory -> new File(directory.getAbsolutePath() + "/" + items[items.length - 1])).collect(Collectors.toList()));
      }
      return testFiles;
   }

   private static String getFileExtension(String fileName) {
      String fileExtension = "";
      int lastIndex = fileName.lastIndexOf(".");
      if (lastIndex > 0) {
         fileExtension = fileName.substring(lastIndex + 1);
      }
      return fileExtension;
   }

   public static String getEnvironmentVariable(String name) {
      Map<String, String> env = System.getenv();

      if (isDefinedEnvironmentVariable(name)) {
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
            case "DELETE_IF_FILTERED":
               return "false";
            default:
               throw new IllegalStateException("Unexpected environment variable: " + name);
         }
      }
   }

   static boolean isDefinedEnvironmentVariable(String name) {
      Map<String, String> env = System.getenv();
      return env.get(name) != null;
   }

   static void setEnvironmentVariable(String key, String value) {
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

   public static boolean isRunningOnJenkinse() {
      return isDefinedEnvironmentVariable("JOB_NAME") && isDefinedEnvironmentVariable("HUDSON_HOME");
   }
}
