package com.java_app.demo.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

public class CsvReader {

  public static String TYPE = "text/csv";

  public static boolean hasCsvFormat(MultipartFile file) {
    return TYPE.equals(file.getContentType());
  }

  public static List<String> csvToStuList(InputStream is) {
    try (BufferedReader bReader =
            new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        CSVParser csvParser =
            new CSVParser(
                bReader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
      List<String> stuList = new ArrayList<>();
      Iterable<CSVRecord> csvRecords = csvParser.getRecords();
      for (CSVRecord csvRecord : csvRecords) {
        String username = csvRecord.get("Username");
        stuList.add(username);
      }
      return stuList;
    } catch (IOException e) {
      throw new RuntimeException("CSV data is failed to parse: " + e.getMessage());
    }
  }
}
