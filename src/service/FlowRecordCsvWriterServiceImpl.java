package service;

import constants.ApplicationConstants;
import model.CsvLookupKey;
import model.Tag;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static constants.ApplicationConstants.*;

public class FlowRecordCsvWriterServiceImpl implements FlowRecordWriterService {
    private String filePrefix;

    public FlowRecordCsvWriterServiceImpl(String filePrefix) {
        this.filePrefix = filePrefix;
    }

    @Override
    public void exportTagCounts(Map<Tag, Integer> tagCounts) throws IOException {
        try (BufferedWriter outFile = new BufferedWriter(new FileWriter(filePrefix + OUTPUT_FILE_TAG_COUNT_SUFFIX, false))) {
            outFile.write(TAG_COUNT_HEADER);
            outFile.newLine();
            for (Map.Entry<Tag, Integer> entry : tagCounts.entrySet()) {
                outFile.write(entry.getKey().getTagName() + "," + entry.getValue());
                outFile.newLine();
            }
        } catch (IOException ex) {
            throw new IOException(ApplicationConstants.ERROR_UNABLE_TO_OPEN_FILE + filePrefix + OUTPUT_FILE_TAG_COUNT_SUFFIX, ex);
        }
    }

    @Override
    public void exportPortProtocolCount( Map<CsvLookupKey, Integer> map) throws IOException {
        try (BufferedWriter outFile = new BufferedWriter(new FileWriter(filePrefix + OUTPUT_FILE_PORT_PROTO_COUNT_SUFFIX, false))) {
            outFile.write(PORT_PROTO_COUNT_HEADER);
            outFile.newLine();
            for (Map.Entry<CsvLookupKey, Integer> entry : map.entrySet()) {
                outFile.write(entry.getKey().getPort() + "," + entry.getKey().getProtocol() + ", " + entry.getValue());
                outFile.newLine();
            }
        } catch (IOException ex) {
            throw new IOException(ApplicationConstants.ERROR_UNABLE_TO_OPEN_FILE + filePrefix + OUTPUT_FILE_PORT_PROTO_COUNT_SUFFIX, ex);
        }
    }
}
