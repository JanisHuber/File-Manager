package org.example.filemanager.filesearch;

import org.example.filemanager.filehandling.File;

import java.nio.file.Path;
import java.util.List;

public interface FileSearch {
    List<File> search(Path path, String searchQuery, int depth);
}
