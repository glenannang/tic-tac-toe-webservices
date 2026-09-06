package com.svi.tictactoewebservice.service.impl;

import com.svi.tictactoewebservice.service.FileStorageService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileStorageServiceImpl implements FileStorageService {

    @Override
    public void createDirectory(File directory) {
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    @Override
    public void createFile(File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    @Override
    public void appendLine(File file, String content) throws IOException {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(content);
            writer.write(System.lineSeparator());
        }

    }

    @Override
    public List<String> readLines(File file) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }



}
