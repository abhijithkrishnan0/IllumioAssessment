import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.FlowRecord;
import model.CsvLookupKey;
import model.Tag;
import service.FlowRecordBatchService;
import service.FlowRecordCsvWriterServiceImpl;
import service.FlowRecordWriterService;
import util.FlowRecordAnalyzerUtil;


public class Main {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: java Main <batchSize> <flowlogFile> <lookupCsv> <outputCsvPrefix>");
            return;
        }

        try {
            int batchSize = Integer.parseInt(args[0]);

            String flowlogFile = args[1];
            String lookupCSV = args[2];
            String outputCsvPrefix = args[3];
            FlowRecordBatchService flowRecordBatchService = new FlowRecordBatchService(batchSize, flowlogFile);
            Map<CsvLookupKey, Tag> lookupCsvMap = FlowRecordAnalyzerUtil.lookupCsvParser(lookupCSV);
            Map<Tag, Integer> tagCountMap = new HashMap<>();
            Map<CsvLookupKey, Integer> portProtocolCountMap = new HashMap<>();
            List<FlowRecord> records;
            do {
                records = flowRecordBatchService.getBatchFlowRecords();
                for (FlowRecord record : records) {
                    CsvLookupKey searchKey = new CsvLookupKey(record.getDstPort(), FlowRecordAnalyzerUtil.getProtocolFromId(record.getProtocol()));
                    Tag tag = lookupCsvMap.getOrDefault(searchKey, new Tag("Unknown"));
                    tagCountMap.merge(tag, 1, Integer::sum);

                    CsvLookupKey portProtocolSearchKey = new CsvLookupKey(record.getDstPort(), FlowRecordAnalyzerUtil.getProtocolFromId(record.getProtocol()));
                    portProtocolCountMap.merge(portProtocolSearchKey, 1, Integer::sum);
                }
            } while (!records.isEmpty());
            FlowRecordWriterService flowRecordCsvWriterService = new FlowRecordCsvWriterServiceImpl(outputCsvPrefix);
            flowRecordCsvWriterService.exportTagCounts(tagCountMap);
            flowRecordCsvWriterService.exportPortProtocolCount(portProtocolCountMap);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}