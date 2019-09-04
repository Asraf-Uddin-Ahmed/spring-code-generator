import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

public class FileUtil {
    private static final String PACKAGE_ROOT = "com.crichubs.rsrc";
    private static final String PACKAGE_REPO = PACKAGE_ROOT + ".repositories";
    private static final String PACKAGE_ENTITY = PACKAGE_ROOT + ".entities";
    private static final String PACKAGE_REQUEST_DTO = PACKAGE_ROOT + ".dtos.request";

    private static final String OUTPUT_FOLDER = "out_file\\";
    private static final String OUTPUT_FOLDER_REPO = OUTPUT_FOLDER + "repositories\\";
    private static final String OUTPUT_FOLDER_REQUEST_DTO = OUTPUT_FOLDER + "request_dto\\";
    private static final String OUTPUT_FOLDER_RESPONSE_DTO = OUTPUT_FOLDER + "response_dto\\";


    static void createRepositoryFile(String fileName, String repoStr, String repoCrudStr) {
        String resultRepoStr = MessageFormat.format(repoStr, PACKAGE_REPO, PACKAGE_ENTITY, fileName);
        String resultRepoCrudStr = MessageFormat.format(repoCrudStr, PACKAGE_REPO, PACKAGE_ENTITY, fileName);
        try {
            createTextFile(OUTPUT_FOLDER_REPO + fileName + "Repository.java", resultRepoStr);
            createTextFile(OUTPUT_FOLDER_REPO + fileName + "RepositoryCrud.java", resultRepoCrudStr);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    static void createRequestDtoFile(String fileName, String requestDtoStr, String strFields) {
        String resultRequestDtoStr = MessageFormat.format(requestDtoStr, PACKAGE_REQUEST_DTO, fileName, strFields);
        try {
            createTextFile(OUTPUT_FOLDER_REQUEST_DTO + fileName + "RequestDto.java", resultRequestDtoStr);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    static String readFileAsString(String fileName) throws IOException {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    static String getFileNameWithoutExtension(String fileName) {
        int pos = fileName.lastIndexOf('.');
        return pos > 0 ? fileName.substring(0, pos) : fileName;
    }

    private static void createTextFile(String fileNameWithExtension, String text) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(fileNameWithExtension, "UTF-8");
        writer.println(text);
        writer.close();
    }

}
