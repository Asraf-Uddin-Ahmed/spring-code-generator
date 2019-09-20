import org.jboss.dna.common.text.Inflector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

public class FileUtil {
    private static final String PACKAGE_ROOT = "com.crichubs.rsrc";

    private static final String OUTPUT_FOLDER = "out_file\\";


    static void createRepositoryFile(String fileName, String repoStr, String repoCrudStr) {
        final String PACKAGE_REPO = PACKAGE_ROOT + ".repositories";
        final String PACKAGE_ENTITY = PACKAGE_ROOT + ".entities";
        final String OUTPUT_FOLDER_REPO = OUTPUT_FOLDER + "repositories\\";

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
        final String PACKAGE_REQUEST_DTO = PACKAGE_ROOT + ".dtos.request";
        final String OUTPUT_FOLDER_REQUEST_DTO = OUTPUT_FOLDER + "request_dto\\";
        String resultRequestDtoStr = MessageFormat.format(requestDtoStr, PACKAGE_REQUEST_DTO, fileName, strFields);
        try {
            createTextFile(OUTPUT_FOLDER_REQUEST_DTO + fileName + "RequestDto.java", resultRequestDtoStr);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    static void createResponseDtoFile(String fileName, String responseDtoStr, String strFields) {
        final String PACKAGE_RESPONSE_DTO = PACKAGE_ROOT + ".dtos.response";
        final String OUTPUT_FOLDER_RESPONSE_DTO = OUTPUT_FOLDER + "response_dto\\";
        String resultResponseDtoStr = MessageFormat.format(responseDtoStr, PACKAGE_RESPONSE_DTO, fileName, strFields);
        try {
            createTextFile(OUTPUT_FOLDER_RESPONSE_DTO + fileName + "ResponseDto.java", resultResponseDtoStr);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    static void createMapperFile(String fileName, String mapperIntfStr, String mapperImplStr) {
        final String OUTPUT_FOLDER_MAPPER_INTF = OUTPUT_FOLDER + "mapper\\intf\\";
        final String OUTPUT_FOLDER_MAPPER_IMPL = OUTPUT_FOLDER + "mapper\\impl\\";
        String resultMapperIntfStr = MessageFormat.format(mapperIntfStr, PACKAGE_ROOT, fileName);
        String resultMapperImplStr = MessageFormat.format(mapperImplStr, PACKAGE_ROOT, fileName);
        try {
            createTextFile(OUTPUT_FOLDER_MAPPER_INTF + fileName + "Mapper.java", resultMapperIntfStr);
            createTextFile(OUTPUT_FOLDER_MAPPER_IMPL + fileName + "MapperImpl.java", resultMapperImplStr);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    static void createServiceFile(String fileName, String serviceStr, String serviceImplStr) {
        final String OUTPUT_FOLDER_SERVICE_INTF = OUTPUT_FOLDER + "service\\intf\\";
        final String OUTPUT_FOLDER_SERVICE_IMPL = OUTPUT_FOLDER + "service\\impl\\";

        String fileNameInCamelCase = Inflector.getInstance().lowerCamelCase(fileName);
        String resultServiceIntfStr = MessageFormat.format(serviceStr, PACKAGE_ROOT, fileName, fileNameInCamelCase);
        String resultServiceImplStr = MessageFormat.format(serviceImplStr, PACKAGE_ROOT, fileName, fileNameInCamelCase);
        try {
            createTextFile(OUTPUT_FOLDER_SERVICE_INTF + fileName + "Service.java", resultServiceIntfStr);
            createTextFile(OUTPUT_FOLDER_SERVICE_IMPL + fileName + "ServiceImpl.java", resultServiceImplStr);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    static void createResourceFile(String fileName, String resourceStr) {
        final String OUTPUT_FOLDER_RESOURCE = OUTPUT_FOLDER + "resource\\";

        String fileNameInCamelCase = Inflector.getInstance().lowerCamelCase(fileName);
        Inflector inflector = Inflector.getInstance();
        String hyphenSeparatedPluralizedFileName = inflector.underscore(inflector.pluralize(fileName)).replaceAll("_", "-");
        String resultResourceStr = MessageFormat.format(resourceStr, PACKAGE_ROOT, fileName, fileNameInCamelCase, hyphenSeparatedPluralizedFileName);
        try {
            createTextFile(OUTPUT_FOLDER_RESOURCE + fileName + "Resource.java", resultResourceStr);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    static void createResourceAssemblerFile(String fileName, String resourceAssemblerStr) {
        final String OUTPUT_FOLDER_RESOURCE_ASSEMBLER = OUTPUT_FOLDER + "resource_assembler\\";

        String fileNameInCamelCase = Inflector.getInstance().lowerCamelCase(fileName);
        String resultResourceAssemblerStr = MessageFormat.format(resourceAssemblerStr, PACKAGE_ROOT, fileName, fileNameInCamelCase);
        try {
            createTextFile(OUTPUT_FOLDER_RESOURCE_ASSEMBLER + fileName + "ResourceAssembler.java", resultResourceAssemblerStr);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    static void createControllerFile(String fileName, String controllerStr) {
        final String OUTPUT_FOLDER_CONTROLLER = OUTPUT_FOLDER + "controller\\";

        String fileNameInCamelCase = Inflector.getInstance().lowerCamelCase(fileName);
        Inflector inflector = Inflector.getInstance();
        String hyphenSeparatedPluralizedFileName = inflector.underscore(inflector.pluralize(fileName)).replaceAll("_", "-");
        String resultControllerStr = MessageFormat.format(controllerStr, PACKAGE_ROOT, fileName, fileNameInCamelCase, hyphenSeparatedPluralizedFileName);
        try {
            createTextFile(OUTPUT_FOLDER_CONTROLLER + fileName + "Controller.java", resultControllerStr);
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
