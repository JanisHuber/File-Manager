import org.example.filemanager.filehandling.FileController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileCopyPasteTest {
    @Test
    void testCopyPaste() {
        FileController fileController = new FileController();
        fileController.setPointer("C:\\Users\\janis\\");
        fileController.makeDirectory(fileController.getPointer(), ".A");
        fileController.makeDirectory(fileController.getPointer(), ".B");

        fileController.setPointer("C:\\Users\\janis\\.A\\");
        fileController.makeFile(fileController.getPointer(), "Test.txt");
        fileController.updateFilesList();

        fileController.chosenFile = fileController.searchForFile("Test.txt");
        fileController.chosenFile.setIsChosenTo(true);

        fileController.copyFile(fileController.getPointer());
        fileController.setPointer("C:\\Users\\janis\\.B\\");
        fileController.pasteFile();

        assertNotNull(fileController.searchForFile("Test.txt"), "Im Ordner B sollte test.txt vorhanden sein");

        fileController.chosenFile = fileController.searchForFile("Test.txt");
        fileController.chosenFile.setIsChosenTo(true);
        fileController.deleteFile(fileController.getPointer());

        fileController.setPointer("C:\\Users\\janis\\.A\\");
        fileController.chosenFile = fileController.searchForFile("Test.txt");
        fileController.chosenFile.setIsChosenTo(true);
        fileController.deleteFile(fileController.getPointer());


        fileController.setPointer("C:\\Users\\janis\\");
        fileController.updateFilesList();
        fileController.chosenFile = fileController.searchForFile(".A");
        fileController.chosenFile.setIsChosenTo(true);
        fileController.deleteFile(fileController.getPointer());
        fileController.chosenFile = fileController.searchForFile(".B");
        fileController.chosenFile.setIsChosenTo(true);
        fileController.deleteFile(fileController.getPointer());
    }
}
