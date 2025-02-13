package org.example.filemanager.filehandling;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PointerHistory {
    private List<Path> pointerHistory = new ArrayList<>();
    private FileController fileController;
    private Path pointer;

    PointerHistory(FileController fileController) {
        this.fileController = fileController;
    }

    public Path getPointerForward() {
        Path resultpointer = pointer;
        int currentPointerIndex = pointerHistory.indexOf(pointer);

        try {
            if (pointerHistory.get(currentPointerIndex + 1) != null) {
                resultpointer = pointerHistory.get(currentPointerIndex + 1);
            }
        } catch (IndexOutOfBoundsException e) {
            return resultpointer;
        }

        return resultpointer;
    }

    public Path getPointerBackwards() {
        Path resultPointer = pointer;
        int currentPointerIndex = pointerHistory.indexOf(pointer);

        try {
            if (pointerHistory.get(currentPointerIndex - 1) != null) {
                resultPointer = pointerHistory.get(currentPointerIndex - 1);
            }
        } catch (IndexOutOfBoundsException e) {
            return resultPointer;
        }

        return resultPointer;
    }

    public List<Path> getPointerHistory() {
        return pointerHistory;
    }

    public void removePointersAtIndex(int index) {
        for (int i = index + 1; i < pointerHistory.size(); i++) {
            System.out.println("Removing: " + pointerHistory.get(i));
            pointerHistory.remove(i);
        }
    }

    public Path getPointer() {
        return pointer;
    }

    public void setPointer(Path pointer) {
        this.pointer = pointer;
        if (!pointerHistory.contains(pointer)) {
            pointerHistory.add(pointer);
        }
    }
}
