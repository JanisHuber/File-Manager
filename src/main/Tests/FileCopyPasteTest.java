import org.example.filemanager.filehandling.File;
import org.example.filemanager.filehandling.FileController;
import org.example.filemanager.filesearch.SearchMethod;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileCopyPasteTest {
    @Test
    void testCopyPaste() {
        FileController fileController = new FileController();
        fileController.navigateTo(Paths.get("C:\\Users\\janis\\"));
        fileController.makeDirectory(fileController.getPointer(), ".A");
        fileController.makeDirectory(fileController.getPointer(), ".B");

        fileController.navigateTo(Paths.get("C:\\Users\\janis\\.A\\"));
        fileController.makeFile(fileController.getPointer(), "Test.txt");

        List<File> files = fileController.search(SearchMethod.NIO, "Test.txt", 1);
        fileController.chosenFile = files.get(0);
        fileController.chosenFile.setIsChosenTo(true);

        fileController.copyFile(fileController.getPointer());
        fileController.navigateTo(Paths.get("C:\\Users\\janis\\.B\\"));
        fileController.pasteFile();

        assertNotNull(fileController.search(SearchMethod.NIO, "test.txt", 1), "Im Ordner B sollte test.txt vorhanden sein");

        List<File> files1 = fileController.search(SearchMethod.NIO, "Test.txt", 1);
        fileController.chosenFile = files1.get(0);
        fileController.chosenFile.setIsChosenTo(true);
        fileController.deleteFile(fileController.getPointer());

        fileController.navigateTo(Paths.get("C:\\Users\\janis\\.A\\"));
        List<File> files2 = fileController.search(SearchMethod.NIO, "Test.txt", 1);
        fileController.chosenFile = files2.get(0);
        fileController.chosenFile.setIsChosenTo(true);
        fileController.deleteFile(fileController.getPointer());


        fileController.navigateTo(Paths.get("C:\\Users\\janis\\"));
        List<File> files3 = fileController.search(SearchMethod.NIO, ".A", 1);
        fileController.chosenFile = files3.get(0);
        fileController.chosenFile.setIsChosenTo(true);
        fileController.deleteFile(fileController.getPointer());
        List<File> files4 = fileController.search(SearchMethod.NIO, ".B", 1);
        fileController.chosenFile = files4.get(0);
        fileController.chosenFile.setIsChosenTo(true);
        fileController.deleteFile(fileController.getPointer());
    }
}
