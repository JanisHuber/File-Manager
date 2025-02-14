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
        fileController.makeDirectoryInCurrentFolder(".A");
        fileController.makeDirectoryInCurrentFolder(".B");

        fileController.navigateTo(Paths.get("C:\\Users\\janis\\.A\\"));
        fileController.makeFileInCurrentFolder("Test.txt");

        List<File> files = fileController.search(SearchMethod.NIO, "Test.txt", 1, fileController.getPointer());
        fileController.setChosenFile(files.get(0));
        fileController.getChosenFile().get().setIsChosenTo(true);

        fileController.copyFile(fileController.getPointer());
        fileController.navigateTo(Paths.get("C:\\Users\\janis\\.B\\"));
        fileController.pasteFile();

        assertNotNull(fileController.search(SearchMethod.NIO, "test.txt", 1, fileController.getPointer()), "Im Ordner B sollte test.txt vorhanden sein");

        List<File> files1 = fileController.search(SearchMethod.NIO, "Test.txt", 1, fileController.getPointer());
        fileController.setChosenFile(files1.get(0));
        fileController.getChosenFile().get().setIsChosenTo(true);
        fileController.deleteFile(fileController.getPointer());

        fileController.navigateTo(Paths.get("C:\\Users\\janis\\.A\\"));
        List<File> files2 = fileController.search(SearchMethod.NIO, "Test.txt", 1, fileController.getPointer());
        fileController.setChosenFile(files2.get(0));
        fileController.getChosenFile().get().setIsChosenTo(true);
        fileController.deleteFile(fileController.getPointer());


        fileController.navigateTo(Paths.get("C:\\Users\\janis\\"));
        List<File> files3 = fileController.search(SearchMethod.NIO, ".A", 1, fileController.getPointer());
        fileController.setChosenFile(files3.get(0));
        fileController.getChosenFile().get().setIsChosenTo(true);
        fileController.deleteFile(fileController.getPointer());
        List<File> files4 = fileController.search(SearchMethod.NIO, ".B", 1, fileController.getPointer());
        fileController.setChosenFile(files4.get(0));
        fileController.getChosenFile().get().setIsChosenTo(true);
        fileController.deleteFile(fileController.getPointer());
    }
}
