package org.jboss.qe.collector;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jan Dobes
 */
public class FileLoaderTestCase {

   @Test
   public void testSinglePathFileExists() {
      List<File> files = Tools.fileLoader("src/test/resources/fileLoader/reports/test/testjournal.xml");
      Assert.assertTrue("File doesn't exist, path: " + files.get(0).getAbsolutePath(), files.get(0).getAbsoluteFile().exists());
   }

   @Test
   public void testMultipleFileExists() {
      List<File> files = Tools.fileLoader("src/test/resources/fileLoader/reports/**/*.xml");
      for (File file : files) {
         Assert.assertTrue(file.getName() + " doesn't exists", file.exists());
      }
   }

   @Test
   public void muhehe() {
      List<File> loadedFiles = Tools.fileLoader("src/test/resources/fileLoader/reports/test/asfsf/qwef.xml");
      List<File> files = new ArrayList();
      files.add(new File("./src/test/resources/fileLoader/reports/test/asfsf/qwef.xml"));
      File[] loadedFilesArray = loadedFiles.toArray(new File[loadedFiles.size()]);
      File[] filesArray = files.toArray(new File[files.size()]);
      Assert.assertArrayEquals("Files isn't equals ", loadedFilesArray, filesArray);
   }
   @Test
   public void testFileMatch() {
      List<File> loadedFiles = Tools.fileLoader("src/test/resources/fileLoader/reports/**/**/*.xml");
      List<File> files = new ArrayList();
      files.add(new File("./src/test/resources/fileLoader/reports/test/asfsf/qwef.xml"));
      files.add(new File("./src/test/resources/fileLoader/reports/test3/asd/asdwq.xml"));
      File[] loadedFilesArray = loadedFiles.toArray(new File[loadedFiles.size()]);
      File[] filesArray = files.toArray(new File[files.size()]);
      Assert.assertArrayEquals("Files isn't equals ", loadedFilesArray, filesArray);
   }

   @Test
   public void testFileMatch2() {
      List<File> loadedFiles = Tools.fileLoader("src/test/resources/**/reports/**/test2/**/*.xml");
      List<File> files = new ArrayList();
      files.add(new File("./src/test/resources/fileLoader/reports/qfg/test2/test/testjournal.xml"));
      Assert.assertEquals("Files isn't equals", files.get(0).getAbsolutePath(), loadedFiles.get(0).getAbsolutePath());
   }

   @Test
   public void testFileMatch3() {
      List<File> loadedFiles = Tools.fileLoader("src/test/resources/fileLoader/reports/**/asfsf/*.xml");
      List<File> files = new ArrayList();
      files.add(new File("./src/test/resources/fileLoader/reports/test/asfsf/qwef.xml"));
      Assert.assertEquals("Files isn't equals", files.get(0).getAbsolutePath(), loadedFiles.get(0).getAbsolutePath());
   }

   @Test
   public void testFileMatch4() {
      List<File> loadedFiles = Tools.fileLoader("src/test/resources/fileLoader/reports/qfg/test2/test/**");
      List<File> files = new ArrayList();
      files.add(new File("./src/test/resources/fileLoader/reports/qfg/test2/test/testjournal.xml"));
      Assert.assertEquals("Files isn't equals", files.get(0).getAbsolutePath(), loadedFiles.get(0).getAbsolutePath());
   }

   @Test
   public void testFileMatch5() {
      List<File> loadedFiles = Tools.fileLoader("src/test/resources/fileLoader/reports/qfg/test2/test/*.xml");
      List<File> files = new ArrayList();
      files.add(new File("./src/test/resources/fileLoader/reports/qfg/test2/test/testjournal.xml"));
      Assert.assertEquals("Files isn't equals", files.get(0).getAbsolutePath(), loadedFiles.get(0).getAbsolutePath());
   }
}
