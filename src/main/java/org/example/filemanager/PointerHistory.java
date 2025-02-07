package org.example.filemanager;

import java.util.ArrayList;
import java.util.List;

public class PointerHistory {
    private String pointer;
    private List<String> pointerHistory = new ArrayList<>();
    private FileController fileController;

    PointerHistory(String pointer, FileController fileController) {
        this.pointer = pointer;
        pointerHistory.add(pointer);
        this.fileController = fileController;
    }

    public String getPointerForward() {
        String resultpointer = fileController.getPointer(); // default value
        int currentPointerIndex = pointerHistory.indexOf(fileController.getPointer());

        try {
            if (pointerHistory.get(currentPointerIndex + 1) != null) {
                resultpointer = pointerHistory.get(currentPointerIndex + 1);
            }
        }catch (IndexOutOfBoundsException e) {
            return resultpointer;
        }

        return resultpointer;
    }

    public String getPointerBackwards() {
        String resultpointer = fileController.getPointer(); // default value
        int currentPointerIndex = pointerHistory.indexOf(fileController.getPointer());

        try {
            if (pointerHistory.get(currentPointerIndex - 1) != null) {
                resultpointer = pointerHistory.get(currentPointerIndex - 1);
            }
        } catch (IndexOutOfBoundsException e) {
            return resultpointer;
        }

        return resultpointer;
    }

    public void addPointerToHistory(String pointer) {
        if (!pointerHistory.contains(pointer)) {
            pointerHistory.add(pointer);
        }
    }

    public List<String> getPointerHistory() {
        return pointerHistory;
    }

    public void removePointersAtIndex(int index) {
        for (int i = index + 1; i < pointerHistory.size(); i++) {
            System.out.println("Removing: " + pointerHistory.get(i));
            pointerHistory.remove(i);
        }
    }
}
