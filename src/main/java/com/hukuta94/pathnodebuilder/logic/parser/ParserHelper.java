package com.hukuta94.pathnodebuilder.logic.parser;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ParserHelper
{
    public final static String BASE_FOLDER = "/testContent/parser/";

    public static String loadTestFile(String testFolderName, String testFileName) throws Exception
    {
        testFolderName = BASE_FOLDER.concat(testFolderName);

        if (testFileName == null) {
            File folder = Paths.get(ParserHelper.class.getResource(testFolderName).toURI()).toFile();
            for (File f : folder.listFiles())
            {
                String fileName = f.getName();
                if (fileName.equals("inputData"))
                {
                    testFileName = fileName;
                    break;
                }
            }
        }

        Path path = Paths.get(ParserHelper.class.getResource(testFolderName.concat(testFileName)).toURI());
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
