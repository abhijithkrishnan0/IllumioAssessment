package service;

import constants.ApplicationConstants;
import model.FlowRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class FlowRecordBatchService {
    private final int batchSize;
    private final BufferedReader file;

    public FlowRecordBatchService(int batchSize, String filename) throws IOException {
        if (filename == null) {
            throw new IllegalArgumentException(ApplicationConstants.ERROR_NULL_FILENAME_PASSED);
        }
        this.batchSize = batchSize;
        this.file = new BufferedReader(new FileReader(filename));
    }

    public List<FlowRecord> getBatchFlowRecords() throws IOException {
        List<FlowRecord> records = new ArrayList<>();
        String line;
        int count = 0;
        while (count < batchSize && (line = file.readLine()) != null) {
            try {
                FlowRecord record = new FlowRecord(line);
                records.add(record);
                count++;
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return records;
    }
}
