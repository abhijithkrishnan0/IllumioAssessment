package service;
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
            throw new IllegalArgumentException("Null filename");
        }
        this.batchSize = batchSize;
        this.file = new BufferedReader(new FileReader(filename));
        if (file == null) {
            throw new IOException("Error: Could not open the file " + filename);
        }
    }

    public List<FlowRecord> getBatchFlowRecords() throws IOException {
        List<FlowRecord> records = new ArrayList<>();
        String line;
        int count = 0;
        while (count < batchSize && (line = file.readLine()) != null) {
            String[] tokens = line.split("\\s+");
            if(tokens.length != NUM_FLOW_LOG_TOKENS) {
                System.out.println("Log entry does not contain required number of tokens. Entry skipped.  " + line);
                continue;
            }
            int version = Integer.parseInt(tokens[0]);
            String accountId = tokens[1];
            String interfaceId = tokens[2];
            String srcAddr = tokens[3];
            String dstAddr = tokens[4];
            int srcPort = Integer.parseInt(tokens[5]);
            int dstPort = Integer.parseInt(tokens[6]);
            int protocol = Integer.parseInt(tokens[7]);
            long packets = Long.parseLong(tokens[8]);
            long bytes = Long.parseLong(tokens[9]);
            long start = Long.parseLong(tokens[10]);
            long end = Long.parseLong(tokens[11]);
            String action = tokens[12];
            String logStatus = tokens[13];

            FlowRecord record = new FlowRecord(version, accountId, interfaceId, srcAddr, dstAddr,
                    srcPort, dstPort, protocol, packets, bytes, start, end, action, logStatus);
            records.add(record);
            count++;
        }

        return records;
    }
}
