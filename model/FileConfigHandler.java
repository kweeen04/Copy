package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FileConfigHandler {

    public FileConfigHandler() {
    }

    public boolean isFileExist() {
        File propFile = new File("config.properties");
        return propFile.exists();
    }

    public void readFileConfig(Config config) {
        File propertiesFile = new File("config.properties");
        Properties prop = new Properties();

        if (propertiesFile.exists()) {
            try (FileReader fr = new FileReader(propertiesFile)) {
                prop.load(fr);
                config.setCopyFolder(prop.getProperty("COPY_FOLDER"));
                config.setDataType(prop.getProperty("DATA_TYPE"));
                config.setPath(prop.getProperty("PATH"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void createFileConfig(Config config) {
        File propertiesFile = new File("config.properties");
        Properties prop = new Properties();

        try (OutputStream os = new FileOutputStream(propertiesFile)) {
            prop.setProperty("COPY_FOLDER", config.getCopyFolder());
            prop.setProperty("DATA_TYPE", config.getDataType());
            prop.setProperty("PATH", config.getPath());
            prop.store(os, null);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("File configure can not create");
            System.out.println("System shutdown");
            System.exit(0);
        }
    }

    public void checkConfig(Config config) {
        List<String> errors = new ArrayList<>();

        if (config.getCopyFolder().isEmpty()) {
            errors.add("Can't read File Cofigure");
            errors.add("Folder source is not output");
            errors.add("Can't find folder source");
        }

        if (config.getDataType().isEmpty()) {
            errors.add("Data type is not input");
        }

        if (config.getPath().isEmpty()) {
            errors.add("Folder Destination is not input");
            errors.add("Can't make folder Destination");
        } 

        if (!errors.isEmpty()) {
            errors.forEach(System.err::println);
        }
    }

    public List<String> copyFile(Config config) {
        List<String> copiedFiles = new ArrayList<>();
        File sourceFolder = new File(config.getCopyFolder());
        File destinationFolder = new File(config.getPath());
    
        if (sourceFolder.exists() && sourceFolder.isDirectory() && destinationFolder.exists() && destinationFolder.isDirectory()) {
            File[] listOfFiles = sourceFolder.listFiles();
    
            if (listOfFiles != null) {
                System.out.println("Copy is running...");
                System.out.println("------- File Name ---------");
    
                for (File file : listOfFiles) {
                    if (file.isFile()) {
                        String fileName = file.getName();
                        System.out.println(fileName);
                        copyFile(file, new File(destinationFolder, fileName));
                        copiedFiles.add(fileName);
                    }
                }
    
                System.out.println("Copy is finished...");
            } else {
                System.err.println("No files found in the source folder.");
            }
        } else {
            System.err.println("Invalid source or destination folder.");
        }
    
        return copiedFiles;
    }
    

    private void copyFile(File sourceFile, File destinationFile) {
        try (FileInputStream fis = new FileInputStream(sourceFile);
             FileOutputStream fos = new FileOutputStream(destinationFile)) {
            int b;
            while ((b = fis.read()) != -1) {
                fos.write(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
