import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

public class SearchUtils {
    public static File[] listpdfs(File dir){
        File[] pdf_files = null;
        if (dir.listFiles()!= null && dir.listFiles().length > 0) {
          // FilenameFilter should include only files with pdf extension
            pdf_files = dir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name){
                    return name.matches(".*\\.pdf$");
                }
            });
        }
        if (pdf_files != null)
            return pdf_files;
        else {
            return new File[0];
        }
    }
    public static void testListPdfs(File dir){
        File[] list = listpdfs(dir);
        for (File pdf: list){
            System.out.println(pdf.getName());
        }
    }
    public static List<File> listdirs(File dir){
        File[] dirs = null;
        List<File> dirList = null;
        if (dir.listFiles().length != 0) {
          //FilenameFilter omits directories with '---'
            dirs = dir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name){
                    return !name.matches("^.*---.*$");
                }
            });
           //dirList should contain directories
            dirList = Arrays.asList(dirs).stream()
                    .filter(File::isDirectory)
                    .toList();
        }
        return dirList;
    }
    public static void testListDirs(File dir){
        List<File> list = listdirs(dir);
        for (File next: list){
            System.out.println(next.getName());
        }
    }
}
