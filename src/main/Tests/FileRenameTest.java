import org.example.filemanager.filehandling.File;
import org.example.filemanager.filehandling.FileController;
import org.example.filemanager.filehandling.test;
import org.example.filemanager.filesearch.SearchMethod;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileRenameTest {
    @Test
    void testRename() {
        FileController fileController = new FileController();

        fileController.navigateTo(Paths.get("C:\\Users\\janis\\ChessApp\\"));
        fileController.makeFileInCurrentFolder("\\test.txt");

        fileController.getFilesFrom(Paths.get("C:\\Users\\janis\\ChessApp\\"));
        List<File> files = fileController.search(SearchMethod.NIO, "test.txt", 1, fileController.getPointer());
        fileController.setChosenFile(files.get(0));
        fileController.getChosenFile().get().setIsChosenTo(true);

        fileController.renameFile(fileController.getPointer(), "test2.txt");

        assertNotNull(fileController.search(SearchMethod.NIO, "test2.txt", 0, fileController.getPointer()), "File should be renamed to test2.txt");

        List<File> files2 = fileController.search(SearchMethod.NIO, "test2.txt", 1, fileController.getPointer());
        fileController.setChosenFile(files2.get(0));
        fileController.getChosenFile().get().setIsChosenTo(true);
        fileController.deleteFile(fileController.getPointer());
    }

    @Test
    void test() {
        File file = new File("test", 0, "test", Paths.get("test"), false);
        File file2 = new File("test", 0, "test", Paths.get("test"), false);
        assertEquals(file, file2);
    }

    @Test
    void test2() {
        test test = new test("test");
        test test2 = new test("test");
        assertEquals(test, test2);
    }
}
