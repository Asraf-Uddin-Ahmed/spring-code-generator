import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private static final String INPUT_ENTITY_FOLDER = "F:\\crichubs-resource\\src\\main\\java\\com\\crichubs\\rsrc\\entities";

    public static void main(String[] args) throws IOException {
        FileUtil.createOutputDirectoryIfNotExists();

        String repoStr = FileUtil.readFileAsString("template\\Repository.txt");
        String repoCrudStr = FileUtil.readFileAsString("template\\RepositoryCrud.txt");
        String requestDtoStr = FileUtil.readFileAsString("template\\RequestDto.txt");
        String responseDtoStr = FileUtil.readFileAsString("template\\ResponseDto.txt");
        String mapperIntfStr = FileUtil.readFileAsString("template\\Mapper.txt");
        String mapperImplStr = FileUtil.readFileAsString("template\\MapperImpl.txt");
        String serviceStr = FileUtil.readFileAsString("template\\Service.txt");
        String serviceImplStr = FileUtil.readFileAsString("template\\ServiceImpl.txt");
        String resourceStr = FileUtil.readFileAsString("template\\Resource.txt");
        String resourceAssemblerStr = FileUtil.readFileAsString("template\\ResourceAssembler.txt");
        String controllerStr = FileUtil.readFileAsString("template\\Controller.txt");

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
            FileUtil.createMapperFile(fileName, mapperIntfStr, mapperImplStr);
            FileUtil.createServiceFile(fileName, serviceStr, serviceImplStr);
            FileUtil.createResourceFile(fileName, resourceStr);
            FileUtil.createResourceAssemblerFile(fileName, resourceAssemblerStr);
            FileUtil.createControllerFile(fileName, controllerStr);
            strFields = "";
        }

    }

    private static String getAllLangFields(File file) {
        String strFields = "";
        try (Scanner scanner = new Scanner(file.toPath())){
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
        String variableType = line.split(" ")[1].trim();
        String variableTypeLower = variableType.toLowerCase();
        return variableTypeLower.equals("long") || variableTypeLower.equals("int") || variableTypeLower.equals("integer")
                || variableTypeLower.equals("double") || variableTypeLower.equals("float") || variableTypeLower.equals("boolean")
                || variableTypeLower.equals("string") || variableTypeLower.equals("date") || variableTypeLower.equals("byte")
                || variableTypeLower.equals("char") || variableTypeLower.equals("character") || variableTypeLower.equals("short");
    }

}
