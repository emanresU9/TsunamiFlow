import java.io.File;

public class CopyDirectoryStructure {
    public static void main(String[] args) {
        if (args.length != 2 && args.length != 3)
            System.err.println("Method requires two directory paths");
        if (!new File(args[0]).exists())
            System.err.println(args[0]+" does not exist");
        if (!new File(args[1]).exists())
            System.err.println(args[1]+" does not exist");
        else {
            if (args.length==3 && args[2].equals("-e")) {
                FileZilla.copyStructure(new File(args[0]).getAbsoluteFile(),new File(args[1]).getAbsoluteFile(), true);
            }
            else {
                FileZilla.copyStructure(new File(args[0]).getAbsoluteFile(),new File(args[1]).getAbsoluteFile(), false);
            }
        }
    }
}
