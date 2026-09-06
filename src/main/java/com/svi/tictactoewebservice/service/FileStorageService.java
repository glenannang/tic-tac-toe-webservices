package com.svi.tictactoewebservice.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileStorageService {
    void createDirectory(File directory);
    void createFile(File file) throws IOException;
    void appendLine(File file, String content) throws IOException;
    List<String> readLines(File file) throws IOException;
}
