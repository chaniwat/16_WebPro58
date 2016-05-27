package com.alumnisystem.utility;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileHelper {

    private static ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<>();

    private static final String SURVEY_SCHEMA_PATH = "/WEB-INF/surveyschema/";

    private FileHelper() { }

    public static void setThreadLocal(HttpServletRequest request) {
        requestThreadLocal.set(request);
    }

    public static File saveSurveySchema(String filename, String formSchema) throws IOException {
        String appPath = requestThreadLocal.get().getServletContext().getRealPath("");
        String savePath = appPath + SURVEY_SCHEMA_PATH;

        File savePathDir = new File(savePath);
        if(!savePathDir.exists()) {
            savePathDir.mkdirs();
        }

        Path file = Paths.get(savePath + File.separator + filename + ".fyschema");
        Files.write(file, Arrays.asList(formSchema), Charset.forName("UTF-8"));

        return new File(savePath + File.separator + filename + ".fyschema");
    }

    public static String getSurveySchema(String schema) throws IOException {
        String appPath = requestThreadLocal.get().getServletContext().getRealPath("");
        String savePath = appPath + SURVEY_SCHEMA_PATH;

        File file = new File(savePath + schema + ".fyschema");

        if(!file.exists()) {
            throw new RuntimeException("Cannot find schema file");
        }

        Path path = Paths.get(savePath + File.separator + schema + ".fyschema");
        String formschema = "";
        for (String line : Files.readAllLines(path)) {
            formschema += line;
        }

        return formschema;
    }

}