import org.example.filemanager.File;
import org.example.filemanager.FileController;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileRenameTest {
    @Test
    void testRename() {
        FileController fileController = new FileController();

        fileController.setPointer("C:\\Users\\janis\\Documents\\");
        fileController.makeFile(fileController.getPointer(), "test.txt");

        fileController.getFilesFrom("C:\\Users\\janis\\Documents\\");
        fileController.chosenFile = fileController.searchForFile("test.txt");
        fileController.chosenFile.setIsChosenTo(true);

        fileController.renameFile(fileController.getPointer(), "test2.txt");

        fileController.updateFilesList();
        assertNotNull(fileController.searchForFile("test2.txt"), "File should be renamed to test2.txt");

        fileController.chosenFile = fileController.searchForFile("test2.txt");
        fileController.chosenFile.setIsChosenTo(true);
        fileController.deleteFile(fileController.getPointer());
    }
}
