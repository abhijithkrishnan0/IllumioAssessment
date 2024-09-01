package service;
import constants.ApplicationConstants;
import model.FlowRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static constants.ApplicationConstants.NUM_FLOW_LOG_TOKENS;


public class FlowRecordBatchService {
    private int batchSize;
    private BufferedReader file;

    public FlowRecordBatchService(int batchSize, String filename) throws IOException {
        if(filename == null) {
            throw new IllegalArgumentException(ApplicationConstants.ERROR_NULL_FILENAME_PASSED);
        }
        this.batchSize = batchSize;
        this.file = new BufferedReader(new FileReader(filename));
        if (file == null) {
            throw new IOException(ApplicationConstants.ERROR_COULD_NOT_OPEN_THE_FILE + filename);
        }
    }

    public List<FlowRecord> getBatchFlowRecords() throws IOException {
        List<FlowRecord> records = new ArrayList<>();
        String line;
        int count = 0;
        while (count < batchSize && (line = file.readLine()) != null) {
            String[] tokens = line.split("\\s+");
            if(tokens.length != NUM_FLOW_LOG_TOKENS) {
                System.out.println(ApplicationConstants.ERROR_INVALID_ENTRY + line);
                continue;
            }
            int version = 0;
            String accountId = null;
            String interfaceId = null;
            String srcAddr = null;
            String dstAddr = null;
            int srcPort = 0;
            int dstPort = 0;
            int protocol = 0;
            long packets = 0;
            long bytes = 0;
            long start = 0;
            long end = 0;
            String action = null;
            String logStatus = null;
            try {
                version = Integer.parseInt(tokens[0]);
                accountId = tokens[1];
                interfaceId = tokens[2];
                srcAddr = tokens[3];
                dstAddr = tokens[4];
                srcPort = Integer.parseInt(tokens[5]);
                dstPort = Integer.parseInt(tokens[6]);
                protocol = Integer.parseInt(tokens[7]);
                packets = Long.parseLong(tokens[8]);
                bytes = Long.parseLong(tokens[9]);
                start = Long.parseLong(tokens[10]);
                end = Long.parseLong(tokens[11]);
                action = tokens[12];
                logStatus = tokens[13];
            } catch (Exception e) {
                System.out.println(ApplicationConstants.UNABLE_TO_PARSE_LOG_ENTRY_ENTRY_SKIPPED + " " + line);
                continue;
            }
            FlowRecord record = new FlowRecord(version, accountId, interfaceId, srcAddr, dstAddr,
                    srcPort, dstPort, protocol, packets, bytes, start, end, action, logStatus);
            records.add(record);
            count++;
        }

        return records;
    }
}
