package com.omisoft.zipextractor;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * CLI Extractor
 */
public class CliExtractror {

    private static final int BUFFER_SIZE = 32*1024;


    public static void main(String[] args) {
        if (args.length != 2 || !new File(args[0]).exists() || new File(args[0]).isDirectory()) {
            System.out.println(
                "Please enter filename to extract, followed by path where to extract archive\n Example java -jar lze.jar C:/test.zip D:/ \n");
            return;
        } else {
            extractZip(args[0],args[1]);
        }
    }
    public static boolean extractZip(String zipFilePath, String directoryPath) {

        File destDirectory = new File(directoryPath);

        if (!destDirectory.exists()) {
            destDirectory.mkdirs();
        }
        ZipInputStream in = null;
        PrintWriter errorLog = null;
        try {
             errorLog = new PrintWriter(new FileWriter("error.log"));

            in = new ZipInputStream(new PushbackInputStream(new FileInputStream(zipFilePath),10));

            ZipEntry  entry = in.getNextEntry();

            while (entry != null) {
        String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
            // if the entry is a file, extracts it
            extractFile(in, filePath,errorLog);
            } else {
                File dir = new File(filePath);
                dir.mkdir();
            }
            try {
                in.closeEntry();
            } catch (Exception e) {
                e.printStackTrace();
                errorLog.println("Error closing zip entry. Continuing");
            }
            entry = in.getNextEntry();
        }
            System.out.println("Success extractiong files");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return false;

        } catch (IOException e1) {
            e1.printStackTrace();
            return false;
        }finally {
            if (in!=null) {
                try {
                    in.close();
                    errorLog.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


return true;

    }
    private static void extractFile(ZipInputStream zipIn, String filePath,PrintWriter errorLog)  {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {

            byte[] bytesIn = new byte[BUFFER_SIZE];
            int read = 0;
            System.out.println("Extracting:" + filePath);
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }catch (java.util.zip.ZipException e) {
            e.printStackTrace();
            errorLog.println("Zip problem "+filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            errorLog.println("File not found extracting "+filePath);
        } catch (IOException e) {
            e.printStackTrace();
            errorLog.println("Error extracting "+filePath);

        }
    }

}
