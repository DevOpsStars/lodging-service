package com.devops.lodgingservice.util;

import org.springframework.util.StringUtils;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

public class ImageUtils {

    public String generateUniqueNumber() {
        int min = 10000;
        int max = 99999;
        int random_int = (int) (Math.random() * (max - min + 1) + min);
        return "" + random_int;
    }

    public String generateFileName(String fileName) {

        // generate random alphabet
        String shortRandomAlphabet = generateUniqueNumber();

        // create date format as string
        String dateStrFormat = DateTime.toString();

        // find extension of file
        int indexOfExtension = fileName.indexOf(".");
        String extensionName = fileName.substring(indexOfExtension);

        // return new file name
        return dateStrFormat + "_" + shortRandomAlphabet + extensionName;

    }

    public String generateDisplayName(String orgFileName) throws Exception {
        String orgCleanPath = StringUtils.cleanPath(orgFileName);

        // Check if the file's name contains invalid characters
        if (orgCleanPath.contains(".."))
            throw new Exception("Sorry! Filename contains invalid path sequence " + orgCleanPath);

        // generate new file name
        return generateFileName(orgCleanPath);
    }


}
