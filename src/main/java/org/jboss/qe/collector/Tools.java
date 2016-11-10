package org.jboss.qe.collector;

import java.awt.geom.IllegalPathStateException;
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
      long startTime = System.nanoTime();
      List<File> f3 = fileLoader3(path);
      long stopTime = System.nanoTime();
      System.out.println("Time of first algorithm is  " + (stopTime - startTime));

      startTime = System.nanoTime();
      List<File> f2 = fileLoader2(path);
      stopTime = System.nanoTime();
      System.out.println("Time of second algorithm is " + (stopTime - startTime));

      startTime = System.nanoTime();
      List<File> f4 = cyclicFileLoader(path);
      stopTime = System.nanoTime();
      System.out.println("Time of third algorithm is  " + (stopTime - startTime));

      if (f2.size() != f3.size()) {
         System.out.println("some algorithm do not works correctly (1)(" + f2.size() + " != " + f3.size() + ")");
      } else if (f3.size() != f4.size()) {
         System.out.println("some algorithm do not works correctly (2) (" + f3.size() + " != " + f4.size() + ")");
      } else {
         System.out.println("all algorithms has the same size (" + f2.size() + ")");
      }

      return f3;
   }

   public static List<File> fileLoader3(String path) {
      List<File> res = new LinkedList<>();
      List<String> start = new LinkedList<>();
      // TODO test it multiplatform
      /*
       path could be like:
         /path/from/root
         C:\path\from\root
         path/from/current/location
         path\from\current\location
        */
      if (path.startsWith("/")) {
         start.add("/");
         path = path.substring(1);
      } else if (path.split(File.separator)[0].contains(":")) {
         start.add(path.split(File.separator)[0]);
         path = path.substring(path.split(File.separator)[0].length() + 1);
      } else {
         start.add(".");
      }
      res.addAll(recursiveFileLoader(start, Arrays.asList(path.split(File.separator))).stream().map(File::new).collect(Collectors.toList()));
      return res;
   }

   private static List<String> recursiveFileLoader(List<String> nodes, List<String> path) {
      List<String> output = new LinkedList<>();
      if (path.size() == 0) {
         for (String filename : nodes) {
            File file = new File(filename);
            if (file.isFile()) {
               output.add(filename);
            }
         }
         return output;
      } else {
         if (path.get(0).equals("**")) {
            for (String fn : nodes) {
               File file = new File(fn);
               if (file.isDirectory() && file.list().length > 0) {
                  for (String child : file.list()) {
                     output.add(fn + File.separator + child);
                  }
               }
            }
         } else if (path.get(0).contains("*")) {
            for (String fn : nodes) {
               File file = new File(fn);
               if (path.get(0).equals("*.xml")) {
                  if (file.isDirectory() && file.list().length > 0) {
                     for (String child : file.list()) {
                        if (child.endsWith(".xml")) {
                           output.add(fn + File.separator + child);
                        }
                     }
                  }
               } else { // other wildcard is not supported
                  throw new IllegalPathStateException("not supported wild card");
               }
            }
         } else {
            if (nodes.size() == 1) {
               output.add(nodes.get(0) + File.separator + path.get(0));
            } else {
               for (String fn : nodes) {
                  File file = new File(fn);
                  if (file.isDirectory() && (new File(fn + File.separator + path.get(0))).exists() ) { //arrayContains(file.list(), path.get(0))) {
                     output.add(fn + File.separator + path.get(0));
                  }
               }
            }
         }
         return recursiveFileLoader(output, path.subList(1,path.size()));
      }
   }

   private static List<File> cyclicFileLoader(String path) {
      List<File> res = new LinkedList<>();
      List<String> start = new LinkedList<>();
      /*
       path could be like:
         /path/from/root
         C:\path\from\root
         path/from/current/location
         path\from\current\location
        */
      if (path.startsWith("/")) {
         start.add("/");
         path = path.substring(1);
      } else if (path.split(File.separator)[0].contains(":")) {
         start.add(path.split(File.separator)[0]);
         path = path.substring(path.split(File.separator)[0].length() + 1);
      } else {
         start.add(".");
      }
      List<String> paths = Arrays.asList(path.split(File.separator));
      List<String> output = new LinkedList<>();

      for (String step : paths) {

         if (step.equals("**")) {
            for (String fn : start) {
               File file = new File(fn);
               if (file.isDirectory() && file.list().length > 0) {
                  for (String child : file.list()) {
                     output.add(fn + File.separator + child);
                  }
               }
            }
         } else if (step.contains("*")) {
            for (String fn : start) {
               File file = new File(fn);
               if (step.equals("*.xml")) {
                  if (file.isDirectory() && file.list().length > 0) {
                     for (String child : file.list()) {
                        if (child.endsWith(".xml")) {
                           output.add(fn + File.separator + child);
                        }
                     }
                  }
               } else { // other wildcard is not supported
                  throw new IllegalPathStateException("not supported wild card");
               }
            }
         } else {
            if (start.size() == 1) {
               output.add(start.get(0) + File.separator + step);
            } else {
               for (String fn : start) {
                  File file = new File(fn);
                  if (file.isDirectory() && (new File(fn + File.separator + step)).exists()) {
                     output.add(fn + File.separator + step);
                  }
               }
            }
         }
         start.clear();
         start.addAll(output);
         output.clear();
      }
      for (String filename : start) {
         File file = new File(filename);
         if (file.isFile()) {
            output.add(filename);
         }
      }
      res.addAll(output.stream().map(File::new).collect(Collectors.toList()));
      return res;
   }

   private static boolean arrayContains(String[] list, String key) {
      for (String value : list) {
         if (value.equals(key)) return true;
      }
      return false;
   }

   public static List<File> fileLoader2(String path) {
      String[] items = path.split(File.separator);
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
                  directoryPath += items[ii] + File.separator;
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
                  if (directories.size() != 0) {
                     listOfDirectories.add(directories);
                  }
               }
            }
         }
         else {
            if (!listOfDirectories.isEmpty()) {
               ArrayList<File> directories = new ArrayList<>();
               for (File directory : listOfDirectories.get(listOfDirectories.size() - 1)) {
                  if (!items[i].equals("*.xml")) {
                     directories.add(new File(directory.getAbsolutePath() + File.separator + items[i]));
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
         if (items[items.length - 1].equals("*.xml") || items[items.length - 1].equals("**")) {
            String respath = "";
            for (int i = 0; i < items.length - 1; i++) {
               respath += items[i] + File.separator;
            }
            File directory = new File(respath);
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

      if (items[items.length - 1].equals("*.xml") || items[items.length - 1].equals("**")) {
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
         testFiles.addAll(directories.stream().map(directory -> new File(directory.getAbsolutePath() + File.separator + items[items.length - 1])).collect(Collectors.toList()));
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
            case "RESPONSE_TIMEOUT_SECONDS":
               return "60";
            case "JAR_PATH":
               return "./results-checker-filters.jar";
            case "PACKAGE":
               throw new IllegalStateException("Package variable must be set");
            default:
               throw new IllegalStateException("Unexpected environment variable: " + name);
         }
      }
   }

   static boolean isDefinedEnvironmentVariable(String name) {
      Map<String, String> env = System.getenv();
      return env.get(name) != null;
   }

   public static void setEnvironmentVariable(String key, String value) {
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
