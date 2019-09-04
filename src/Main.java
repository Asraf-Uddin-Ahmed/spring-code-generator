import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final String INPUT_ENTITY_FOLDER = "F:\\crichubs-resource\\src\\main\\java\\com\\crichubs\\rsrc\\entities";

    public static void main(String[] args) throws IOException {
        String repoStr = FileUtil.readFileAsString("Repository.txt");
        String repoCrudStr = FileUtil.readFileAsString("RepositoryCrud.txt");
        String requestDtoStr = FileUtil.readFileAsString("RequestDto.txt");
        String responseDtoStr = FileUtil.readFileAsString("ResponseDto.txt");

        File folder = new File(INPUT_ENTITY_FOLDER);
        List<File> files =  Arrays.stream(folder.listFiles())
                .filter(File::isFile)
                .sorted((o1, o2) -> -o1.getName().compareTo(o2.getName()))
                .collect(Collectors.toList());
        String strFields = "";
        for(File file : files) {
            String fileName = FileUtil.getFileNameWithoutExtension(file.getName());
            strFields += getAllLangFields(file);
            System.out.print(fileName + " -> ");
            if (fileName.endsWith("Id")) {
                continue;
            }
            System.out.println(fileName);

            FileUtil.createRepositoryFile(fileName, repoStr, repoCrudStr);
            FileUtil.createRequestDtoFile(fileName, requestDtoStr, strFields);
            FileUtil.createResponseDtoFile(fileName, responseDtoStr, strFields);
            strFields = "";
        }

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

    private static boolean isLangDataTypeExists(String line) {
        String lineLower = line.toLowerCase();
        return lineLower.contains("long") || lineLower.contains("int") || lineLower.contains("integer")
                || lineLower.contains("double") || lineLower.contains("float") || lineLower.contains("boolean")
                || lineLower.contains("String") || lineLower.contains("Date") || lineLower.contains("byte")
                || lineLower.contains("char") || lineLower.contains("character") || lineLower.contains("short");
    }

}
