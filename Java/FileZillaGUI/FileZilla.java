package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileZilla {
    public static void pushChanges(File src, File dest){
        if (src.list() != null && src.list().length!=0)
        {
            List<File> filesToReplace = Arrays.stream(src.listFiles())
                    .filter(fileToReplace -> (
                            fileToReplace.isFile()
                                    && Arrays.asList(dest.list()).contains(fileToReplace.getName())
                    ))
                    .toList();
            String lastAccessed1;
            String lastAccessed2;
            char userChoice;
            DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT,FormatStyle.SHORT)
                    .withZone(ZoneId.systemDefault());
            for (File file: filesToReplace)
            {
                System.out.printf("\nFolders Containing Duplicates: %s\n",dest.getPath());
                System.out.println("Type 's' to skip this folder\n");
                lastAccessed1 = dtf.format(Instant.ofEpochMilli(file.lastModified()));
                lastAccessed2 = dtf.format(Instant.ofEpochMilli(new File(dest,file.getName()).lastModified()));
                System.out.printf("    -Src File  : %s\n\t-Last Modified: %s\n",file.getPath(), lastAccessed1);
                System.out.printf("    -Dest File : %s\n\t-Last Modified: %s\n",new File(dest, file.getName()).getPath(), lastAccessed2);
                userChoice = Character.toUpperCase(input("\nOkay to replace in Dest? [Y/n/s] : "));
                if (userChoice=='Y') {
                    System.out.println("Replacing File : " + file.getName() + " in " + dest.getPath());
                    try {
                        Files.copy(file.toPath(), new File(dest, file.getName()).toPath()
                                , StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        System.out.println("Failed...");
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                else if(userChoice=='S')
                    break;
            }
            List<File> filesToAdd = Arrays.stream(src.listFiles())
                    .filter( eachFile-> (
                            (! Arrays.asList(dest.list()).contains(eachFile.getName()))
                                    && eachFile.isFile()
                    ))
                    .toList();
            for (File file: filesToAdd) {
                try {
                    Files.copy(file.toPath(), new File(dest, file.getName()).toPath());
                } catch(IOException e) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.println("Failed to copy "+ file.getName()+" from \n"
                            + file.getName() + " to " + dest.getPath());
                }
            }
        
            List<File> newdirs = Arrays.stream(src.listFiles())
                    .filter(f-> (
                            f.isDirectory()
                                    && ! Arrays.asList(dest.list()).contains(f.getName())
                    ))
                    .toList();
            for (File file: newdirs)
            {
                try {
                    Files.createDirectory(new File(dest.getPath(), file.getName()).toPath());
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.println("Failed to create directory : "+ file.getName()
                            + "\nin " + dest.getPath());
                }
            }
            List<File> dirs = Arrays.stream(src.listFiles())
                    .filter(f->f.isDirectory()).toList();
            for (File directory : dirs)
            {
                pushChanges(directory,new File(dest,directory.getName()));
            }
        }
    }
    
    public static void forceChanges(File src, File dest) {
        if (src.list() != null && src.list().length!=0)
        {
            List<File> filesToReplace = Arrays.stream(src.listFiles())
            .filter(fileToReplace -> (
                    fileToReplace.isFile()
                            && Arrays.asList(dest.list()).contains(fileToReplace.getName())
            ))
            .toList();
            for (File file: filesToReplace)
            {
                System.out.println("Replacing File : " + file.getName() + " in " + dest.getPath());
                try {
                    Files.copy(file.toPath(), new File(dest, file.getName()).toPath()
                            , StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    System.out.println("Failed...");
                    System.out.println("Error: " + e.getMessage());
                }
            }

            List<File> filesToAdd = Arrays.stream(src.listFiles())
                    .filter( eachFile-> (
                            (! Arrays.asList(dest.list()).contains(eachFile.getName()))
                                    && eachFile.isFile()
                    ))
                    .toList();
            for (File file: filesToAdd) {
                try {
                    Files.copy(file.toPath(), new File(dest, file.getName()).toPath());
                } catch(IOException e) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.println("Failed to copy "+ file.getName()+" from \n"
                            + file.getName() + " to " + dest.getPath());
                }
            }
            
            List<File> newdirs = Arrays.stream(src.listFiles())
                    .filter(f-> (
                            f.isDirectory()
                                    && ! Arrays.asList(dest.list()).contains(f.getName())
                    ))
                    .toList();
            for (File file: newdirs)
            {
                try {
                    Files.createDirectory(new File(dest.getPath(), file.getName()).toPath());
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.println("Failed to create directory : "+ file.getName()
                            + "\nin " + dest.getPath());
                }
            }
            List<File> dirs = Arrays.stream(src.listFiles())
                    .filter(f->f.isDirectory()).toList();
            for (File directory : dirs)
            {
                forceChanges(directory,new File(dest,directory.getName()));
            }
        }
    }
    public static void pushChangesIgnoreDuplicates(File src, File dest){
        if (src.list() != null && src.list().length!=0)
        {
            List<File> filesToAdd = Arrays.stream(src.listFiles())
                    .filter( eachFile-> (
                            (! Arrays.asList(dest.list()).contains(eachFile.getName()))
                                    && eachFile.isFile()
                    ))
                    .toList();
            for (File file: filesToAdd) {
                try {
                    Files.copy(file.toPath(), new File(dest, file.getName()).toPath());
                } catch(IOException e) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.println("Failed to copy "+ file.getName()+" from \n"
                            + file.getName() + " to " + dest.getPath());
                }
            }

            List<File> newdirs = Arrays.stream(src.listFiles())
                    .filter(f-> (
                            f.isDirectory()
                                    && ! Arrays.asList(dest.list()).contains(f.getName())
                    ))
                    .toList();
            for (File file: newdirs)
            {
                try {
                    Files.createDirectory(new File(dest.getPath(), file.getName()).toPath());
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.println("Failed to create directory : "+ file.getName()
                            + "\nin " + dest.getPath());
                }
            }
            List<File> dirs = Arrays.stream(src.listFiles())
                    .filter(f->f.isDirectory()).toList();
            for (File directory : dirs)
            {
                pushChangesIgnoreDuplicates(directory,new File(dest,directory.getName()));
            }
        }
    }
    public static void pushChangesDuplicatesOnly(File src, File dest){
        if (src.list() != null && src.list().length!=0) {
            List<File> filesToReplace = Arrays.stream(src.listFiles())
                    .filter(fileToReplace -> (
                            fileToReplace.isFile()
                                    && Arrays.asList(dest.list()).contains(fileToReplace.getName())
                    ))
                    .toList();
            String lastAccessed1;
            String lastAccessed2;
            char userChoice;
            DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT)
                    .withZone(ZoneId.systemDefault());
            for (File file : filesToReplace) {
                System.out.printf("\nFolders Containing Duplicates: %s\n", dest.getPath());
                System.out.println("Type 's' to skip this folder\n");
                lastAccessed1 = dtf.format(Instant.ofEpochMilli(file.lastModified()));
                lastAccessed2 = dtf.format(Instant.ofEpochMilli(new File(dest, file.getName()).lastModified()));
                System.out.printf("    -Src File  : %s\n\t-Last Modified: %s\n", file.getPath(), lastAccessed1);
                System.out.printf("    -Dest File : %s\n\t-Last Modified: %s\n", new File(dest, file.getName()).getPath(), lastAccessed2);
                userChoice = Character.toUpperCase(input("\nOkay to replace in Dest? [Y/n/s] : "));
                if (userChoice == 'Y') {
                    System.out.println("Replacing File : " + file.getName() + " in " + dest.getPath());
                    try {
                        Files.copy(file.toPath(), new File(dest, file.getName()).toPath()
                                , StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        System.out.println("Failed...");
                        System.out.println("Error: " + e.getMessage());
                    }
                } else if (userChoice == 'S')
                    break;
            }
            List<File> newdirs = Arrays.stream(src.listFiles())
                    .filter(f-> (
                            f.isDirectory()
                                    && ! Arrays.asList(dest.list()).contains(f.getName())
                    ))
                    .toList();

            for (File file: newdirs)
            {
                try {
                    Files.createDirectory(new File(dest.getPath(), file.getName()).toPath());
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.println("Failed to create directory : "+ file.getName()
                            + "\nin " + dest.getPath());
                }
            }
            List<File> dirs = Arrays.stream(src.listFiles())
                    .filter(f->f.isDirectory()).toList();
            for (File directory : dirs)
            {
                pushChangesDuplicatesOnly(directory,new File(dest,directory.getName()));
            }
        }
    }
    public static void forceChangesDuplicatesOnly(File src, File dest) {
        if (src.list() != null && src.list().length!=0)
        {
            List<File> filesToReplace = Arrays.stream(src.listFiles())
                    .filter(fileToReplace -> (
                            fileToReplace.isFile()
                                    && Arrays.asList(dest.list()).contains(fileToReplace.getName())
                    ))
                    .toList();
            for (File file: filesToReplace)
            {
                System.out.println("Replacing File : " + file.getName() + " in " + dest.getPath());
                try {
                    Files.copy(file.toPath(), new File(dest, file.getName()).toPath()
                            , StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    System.out.println("Failed...");
                    System.out.println("Error: " + e.getMessage());
                }
            }
            List<File> newdirs = Arrays.stream(src.listFiles())
                    .filter(f-> (
                            f.isDirectory()
                                    && ! Arrays.asList(dest.list()).contains(f.getName())
                    ))
                    .toList();
            for (File file: newdirs)
            {
                try {
                    Files.createDirectory(new File(dest.getPath(), file.getName()).toPath());
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.println("Failed to create directory : "+ file.getName()
                            + "\nin " + dest.getPath());
                }
            }
            List<File> dirs = Arrays.stream(src.listFiles())
                    .filter(f->f.isDirectory()).toList();
            for (File directory : dirs)
            {
                forceChangesDuplicatesOnly(directory,new File(dest,directory.getName()));
            }
        }
    }
    public static void copyStructure(File src, File dest, boolean erase){
        if (!src.isDirectory() || !dest.isDirectory())
        {
            System.err.println("Method requires a root folder to copy its structure and a target\n"
                    + " directory to place the copy");
            return;
        }
        // if (erase && new File(dest,src.getName()).isDirectory()) {
        //     if (!(Character.toUpperCase(input("\n Warning: There is a destination folder already,"
        //             + " would you\n like to continue, doing so would replace it with an empty structure [Y/n] : ")) == 'Y'))
        //     {
        //         System.out.println("\n\nExited\n\n");
        //         return;
        //     }
        //     else {
        //         try { Files.delete(new File(dest,src.getName()).toPath()); } catch (IOException e) {System.out.println("Error: could not delete " + e.getMessage());}
        //     }
        // }
        try { Files.createDirectory(new File(dest,src.getName()).toPath()); } catch (IOException e) {System.out.println("Writing to existing folder: "+ src.getName() );}
        copyStructureInner(src,new File(dest,src.getName()),erase);
        System.out.println("\n\nFinished\n");
    }
    private static void copyStructureInner(File src, File target, boolean erase) {

        //Erase all files in the current folder
        if (erase)
        {
            List<File> targetFiles = Arrays.stream(target.listFiles()).filter(f->f.isFile()).toList();
            for (File targetFile: targetFiles) {
                try {
                    Files.delete(targetFile.toPath());
                } catch (IOException e) {
                    System.out.println("Errer " + e.getMessage());
                    System.out.println("Could not delete " + targetFile.getName() + " in the target Directory : " + targetFile.getParent());
                }
            }
        }
        
        //Add any missing directories from the src folder to the destination folder 
        List<String> directories = Arrays.stream(src.list())
        .filter(n->(new File(src,n).isDirectory() && !Arrays.asList(target.list()).contains(n)))
        .toList();
        for (String dir: directories)
        {
            try {
                Files.createDirectory(Paths.get(target.getPath(), dir));
                //Traverse the new target Directory
            } catch (IOException e)
            {
                System.out.println("Error: " + e.getMessage());
                System.out.printf("Could not create a new %s folder in the location\n %s\n\n",dir,target.getPath());
            }
        }

        //Traverse each folder in target 
        for (File srcDir: Arrays.stream(src.listFiles()).filter(f->f.isDirectory()).toList()) {
            copyStructureInner(srcDir,new File(target,srcDir.getName()),erase);
        }
    }

    public static char input(String prompt) {
        Scanner sc = new Scanner(System.in);
        System.out.print(prompt);
        String input = sc.nextLine();
        return input.charAt(0);
    }

}