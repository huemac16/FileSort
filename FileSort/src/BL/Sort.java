package BL;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class Sort {

    private ArrayList<String> folderNames = new ArrayList<>();

    private File file;
    private File sorted;

    private File[] listOfFiles;

    public Sort(File f) {
        this.file = f;
        listOfFiles = file.listFiles();
        sorted = new File(file + "\\" + "sortiert");

        if (!sorted.exists()) {
            sorted.mkdirs();
        }

    }

    public void printFiles() {
        for (int i = 0; i < listOfFiles.length; i++) {

            System.out.println("" + listOfFiles[i].getName());

        }
    }

    public void createFolders() {

        for (int i = 0; i < listOfFiles.length; i++) {
            try {
                boolean exists = false;

                String ext = listOfFiles[i].toString().substring(listOfFiles[i].toString().lastIndexOf(".") + 1, listOfFiles[i].toString().length());

                for (String folderName : folderNames) {
                    if (folderName.equals(ext)) {
                        exists = true;
                    }

                }

                if (!exists && ext.length() < 10 && !ext.equals("lnk")) {
                    folderNames.add(ext);
                }

            } catch (Exception e) {
            }

        }

        for (int j = 0; j < folderNames.size(); j++) {
            File f = new File(sorted + "\\" + folderNames.get(j));

            if (!f.exists()) {
                f.mkdir();
            }
        }

    }

    public void moveFiles() {
        for (int i = 0; i < listOfFiles.length; i++) {
            String ext = listOfFiles[i].toString().substring(listOfFiles[i].toString().lastIndexOf(".") + 1, listOfFiles[i].toString().length());
            if (!(ext.equals("lnk"))) {
                for (String folderName : folderNames) {
                    if (folderName.equals(ext)) {
                        try {
                            File f = new File(sorted.toPath() + "\\" + ext + "\\" + listOfFiles[i].getName());
                            Files.move(Paths.get(listOfFiles[i].toPath().toString()), Paths.get(f.toPath().toString()), StandardCopyOption.REPLACE_EXISTING);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }
    }

    public void run() throws Exception {
        try {
            this.createFolders();
            this.moveFiles();

        } catch (Exception e) {
            throw new Exception();
        }

    }

}
