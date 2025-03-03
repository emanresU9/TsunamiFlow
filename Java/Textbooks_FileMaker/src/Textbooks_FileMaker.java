import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Textbooks_FileMaker {
    public static void main(String[] args){
        File src_dir = null;
        File dest_file_location = null;
        String  new_file_name = "/TextbookNames.txt";
        if (args.length > 0 && args.length <= 2){
            src_dir = new File(args[0]);
            if (src_dir.isDirectory()){
                if (args.length > 1){
                    dest_file_location = new File(args[1]);
                    if (!dest_file_location.isDirectory()) {
                        System.out.println("second argument should be directory for created TextbooksFile");
                    }
                } else {
                    dest_file_location = new File(System.getProperty("user.dir"));
                }
            } else {
                System.out.println("first argument should be a directory with pdfs");
                return;
            }
        } else {
            System.out.println("At least one argument is required but no more than two");
            return;
        }
        assert src_dir != null && dest_file_location != null;

        ArrayList<String> textBookList = new ArrayList<>();
        searchForPdfs(src_dir, textBookList);

        try {
            FileWriter fwriter = new FileWriter(new File(dest_file_location + new_file_name));
            BufferedWriter bwriter = new BufferedWriter(fwriter);
            PrintWriter out = new PrintWriter(bwriter);
            for (String textbook: textBookList){
                out.println(textbook.substring(0,textbook.length()-4));
            }
            out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }


    public static void searchForPdfs(File dir, List<String> textBookList)
    {
        File[] pdf_files = SearchUtils.listpdfs(dir);
        for (File pdf: pdf_files){
            textBookList.add(pdf.getName());
        }

        List<File> dirs = SearchUtils.listdirs(dir);
        for (File next: dirs) {
            searchForPdfs(next, textBookList);
        }
    }
}
