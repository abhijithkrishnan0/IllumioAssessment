package service;

import constants.ApplicationConstants;
import model.CsvLookupKey;
import model.Tag;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static constants.ApplicationConstants.PORT_PROTO_COUNT_HEADER;
import static constants.ApplicationConstants.TAG_COUNT_HEADER;

public class FlowRecordCsvWriterServiceImpl implements FlowRecordWriterService{

    @Override
    public void exportTagCounts(String filename, Map<Tag, Integer> tagCounts) throws IOException {
        try (BufferedWriter outFile = new BufferedWriter(new FileWriter(filename, false))) {
            outFile.write(TAG_COUNT_HEADER);
            outFile.newLine();
        } catch (IOException ex) {
            throw new IOException(ApplicationConstants.ERROR_UNABLE_TO_OPEN_FILE + filename, ex);
        }
        try (BufferedWriter outFile = new BufferedWriter(new FileWriter(filename, true))) {
            for (Map.Entry<Tag, Integer> entry : tagCounts.entrySet()) {
                outFile.write(entry.getKey().getTagName() + "," + entry.getValue());
                outFile.newLine();
            }
        } catch (IOException e) {
            throw new IOException(ApplicationConstants.ERROR_UNABLE_TO_OPEN_FILE + filename, e);
        }
    }

    @Override
    public void exportPortProtocolCount(String filename, Map<CsvLookupKey, Integer> map) throws IOException {
        try (BufferedWriter outFile = new BufferedWriter(new FileWriter(filename, false))) {
            outFile.write(PORT_PROTO_COUNT_HEADER);
            outFile.newLine();

        } catch (IOException ex) {
            throw new IOException(ApplicationConstants.ERROR_UNABLE_TO_OPEN_FILE + filename, ex);
        }
        try (BufferedWriter outFile = new BufferedWriter(new FileWriter(filename, true))) {

            for (Map.Entry<CsvLookupKey, Integer> entry : map.entrySet()) {
                outFile.write(entry.getKey().getPort() + "," + entry.getKey().getProtocol() + ", " + entry.getValue());
                outFile.newLine();
            }
        } catch (IOException e) {
            throw new IOException(ApplicationConstants.ERROR_UNABLE_TO_OPEN_FILE + filename, e);
        }
    }
}
