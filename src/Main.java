import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Arrays;

public class Main {

    private static final String PACKAGE_REPO = "com.crichubs.rsrc.repositories";
    private static final String PACKAGE_ENTITY = "com.crichubs.rsrc.entities";
    private static final String OUTPUT_FOLDER = "out_file\\";
    private static final String ENTITY_FOLDER = "F:\\crichubs-resource\\src\\main\\java\\com\\crichubs\\rsrc\\entities";

    public static void main(String[] args) throws IOException {
        String repoStr = readFileAsString("Repository.txt");
        String repoCrudStr = readFileAsString("RepositoryCrud.txt");

        File folder = new File(ENTITY_FOLDER);
        Arrays.stream(folder.listFiles()).filter(File::isFile).map(file -> getFileNameWithoutExtension(file.getName())).forEach(fileName -> {
            System.out.println(fileName);
            String resultRepoStr = MessageFormat.format(repoStr, PACKAGE_REPO, PACKAGE_ENTITY, fileName);
            String resultRepoCrudStr = MessageFormat.format(repoCrudStr, PACKAGE_REPO, PACKAGE_ENTITY, fileName);
            try {
                createTextFile(OUTPUT_FOLDER + fileName + "Repository.java", resultRepoStr);
                createTextFile(OUTPUT_FOLDER + fileName + "RepositoryCrud.java", resultRepoCrudStr);
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });

    }

    private static String readFileAsString(String fileName) throws IOException {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    private static void createTextFile(String fileNameWithExtension, String text) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(fileNameWithExtension, "UTF-8");
        writer.println(text);
        writer.close();
    }

    private static String getFileNameWithoutExtension(String fileName) {
        int pos = fileName.lastIndexOf('.');
        return pos > 0 ? fileName.substring(0, pos) : fileName;
    }

}
