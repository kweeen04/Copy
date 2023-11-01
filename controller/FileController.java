package controller;

import java.io.IOException;

import model.Config;
import model.FileConfigHandler;
import view.View;

public class FileController {
    private FileConfigHandler configFileHandler;
    private View view;

    public FileController() {
        this.configFileHandler = new FileConfigHandler();
        this.view = new View();
    }

    public void run() {
        view.displayMenu();
        Config config = new Config();

        if (!configFileHandler.isFileExist()) {
            view.showMessage("File Configure is not found!");

            view.showMessage("---- Input Configure File ----");
            String copyFolder = view.promptInput("Copy Folder: ");
            String dataType = view.promptInput("Data Type: ");
            String path = view.promptInput("File Path: ");

            config.setCopyFolder(copyFolder);
            config.setDataType(dataType);
            config.setPath(path);

            configFileHandler.createFileConfig(config);
        }

        configFileHandler.readFileConfig(config);
        configFileHandler.checkConfig(config);

        configFileHandler.copyFile(config);
    }
}
