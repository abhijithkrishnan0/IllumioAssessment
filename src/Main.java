import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.FlowRecord;
import model.LookupCsvKey;
import model.PortProtocol;
import model.Tag;
import service.FlowRecordBatchService;
import util.FlowRecordAnalyzerUtil;


public class Main {
    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Usage: java Main <batchSize> <flowlogFile> <lookupCSV> <tagCountCSV> <portProtocolCountCSV>");
            return;
        }

        try {
            int batchSize = Integer.parseInt(args[0]);

            String flowlogFile = args[1];
            String lookupCSV = args[2];
            String tagCountCSV = args[3];
            String portProtocolCountCSV = args[4];
            FlowRecordBatchService flowRecordBatchService = new FlowRecordBatchService(batchSize, flowlogFile);
            Map<LookupCsvKey, Tag> lookupCsvMap = FlowRecordAnalyzerUtil.lookupCsvParser(lookupCSV);
            Map<Tag, Integer> tagCountMap = new HashMap<>();
            Map<PortProtocol, Integer> portProtocolCountMap = new HashMap<>();
            while(true){
                List<FlowRecord> records = flowRecordBatchService.getBatchFlowRecords();
                if (records.isEmpty()) break;
                for (FlowRecord record : records) {
                    LookupCsvKey searchKey = new LookupCsvKey(record.getDstPort(), record.getProtocol());
                    Tag tag = lookupCsvMap.getOrDefault(searchKey, new Tag("Unknown"));
                    tagCountMap.merge(tag, 1, Integer::sum);
                    PortProtocol portProtocolSearchKey = new PortProtocol(record.getDstPort(), FlowRecordAnalyzerUtil.getProtocolFromId(record.getProtocol()));
                    portProtocolCountMap.merge(portProtocolSearchKey, 1, Integer::sum);
                }
            }
            FlowRecordAnalyzerUtil.exportTagCounts(tagCountCSV, tagCountMap);
            FlowRecordAnalyzerUtil.exportPortProtocolCount(portProtocolCountCSV, portProtocolCountMap);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}