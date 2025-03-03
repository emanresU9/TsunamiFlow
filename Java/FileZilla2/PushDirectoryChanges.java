import java.io.File;

public class PushDirectoryChanges {
    public static void main(String[] args) {
        if (args.length != 2 && args.length != 3)
            System.err.println("Method requires two directory paths");
        if (!new File(args[0]).exists())
            System.err.println(args[0]+" does not exist");
        if (!new File(args[1]).exists())
            System.err.println(args[1]+" does not exist");
        else
            if (args.length == 3) {
                if (args[2].equalsIgnoreCase("-i"))
                    FileZilla.pushChangesIgnoreDuplicates(new File(args[0]).getAbsoluteFile(), new File(args[1]).getAbsoluteFile());
                else if (args[2].equalsIgnoreCase("-f"))
                    FileZilla.forceChanges(new File(args[0]).getAbsoluteFile(), new File(args[1]).getAbsoluteFile());
                else if (args[2].equalsIgnoreCase("-df") || args[2].equalsIgnoreCase("-fd"))
                    FileZilla.forceChangesDuplicatesOnly(new File(args[0]), new File(args[1]));
                else if (args[2].equalsIgnoreCase("-d"))
                    FileZilla.pushChangesDuplicatesOnly(new File(args[0]), new File(args[1]));
            }
            else
                FileZilla.pushChanges(new File(args[0]).getAbsoluteFile(),new File(args[1]).getAbsoluteFile());
    }
}
