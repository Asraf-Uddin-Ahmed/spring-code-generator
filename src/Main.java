import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    private static final String PACKAGE_ROOT = "com.crichubs.rsrc";
    private static final String PACKAGE_REPO = PACKAGE_ROOT + ".repositories";
    private static final String PACKAGE_ENTITY = PACKAGE_ROOT + ".entities";
    private static final String PACKAGE_REQUEST_DTO = PACKAGE_ROOT + ".dtos.request";

    private static final String INPUT_ENTITY_FOLDER = "F:\\crichubs-resource\\src\\main\\java\\com\\crichubs\\rsrc\\entities";

    private static final String OUTPUT_FOLDER = "out_file\\";
    private static final String OUTPUT_FOLDER_REPO = OUTPUT_FOLDER + "repositories\\";
    private static final String OUTPUT_FOLDER_REQUEST_DTO = OUTPUT_FOLDER + "request_dto\\";
    private static final String OUTPUT_FOLDER_RESPONSE_DTO = OUTPUT_FOLDER + "response_dto\\";


    public static void main(String[] args) throws IOException {
        String repoStr = readFileAsString("Repository.txt");
        String repoCrudStr = readFileAsString("RepositoryCrud.txt");
        String requestDtoStr = readFileAsString("RequestDto.txt");

        File folder = new File(INPUT_ENTITY_FOLDER);
        Arrays.stream(folder.listFiles()).filter(File::isFile).forEach(file -> {
            String fileName = getFileNameWithoutExtension(file.getName());
            System.out.println(fileName);
            String strFields = getAllLangFields(file);

            createRequestDtoFile(fileName, requestDtoStr, strFields);
            createRepositoryFile(fileName, repoStr, repoCrudStr);
        });

    }

    private static String getAllLangFields(File file) {
        String strFields = "";
        try {
            Scanner scanner = new Scanner(file.toPath());
            while(scanner.hasNextLine()){
                String line = scanner.nextLine().trim();
                if(line.startsWith("private") && line.endsWith(";") && isLangDataTypeExists(line)) {
                    line = line.replaceFirst(" long ", " Long ");
                    line = line.replaceFirst(" int ", " Integer ");
                    line = line.replaceFirst(" double ", " Double ");
                    line = line.replaceFirst(" float ", " Float ");
                    line = line.replaceFirst(" boolean ", " Boolean ");
                    line = line.replaceFirst(" byte ", " Byte ");
                    line = line.replaceFirst(" char ", " Character ");
                    line = line.replaceFirst(" short ", " Short ");
                    strFields += ("\n\t" + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strFields;
    }

    private static void createRequestDtoFile(String fileName, String requestDtoStr, String strFields) {
        String resultRequestDtoStr = MessageFormat.format(requestDtoStr, PACKAGE_REQUEST_DTO, fileName, strFields);
        try {
            createTextFile(OUTPUT_FOLDER_REQUEST_DTO + fileName + "RequestDto.java", resultRequestDtoStr);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static boolean isLangDataTypeExists(String line) {
        String lineLower = line.toLowerCase();
        return lineLower.contains("long") || lineLower.contains("int") || lineLower.contains("integer")
                || lineLower.contains("double") || lineLower.contains("float") || lineLower.contains("boolean")
                || lineLower.contains("String") || lineLower.contains("Date") || lineLower.contains("byte")
                || lineLower.contains("char") || lineLower.contains("character") || lineLower.contains("short");
    }

    private static void createRepositoryFile(String fileName, String repoStr, String repoCrudStr) {
        String resultRepoStr = MessageFormat.format(repoStr, PACKAGE_REPO, PACKAGE_ENTITY, fileName);
        String resultRepoCrudStr = MessageFormat.format(repoCrudStr, PACKAGE_REPO, PACKAGE_ENTITY, fileName);
        try {
            createTextFile(OUTPUT_FOLDER_REPO + fileName + "Repository.java", resultRepoStr);
            createTextFile(OUTPUT_FOLDER_REPO + fileName + "RepositoryCrud.java", resultRepoCrudStr);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
