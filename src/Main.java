import java.io.IOException;
import java.util.List;
import java.util.Map;

import model.FlowRecord;
import model.LookupCsvKey;
import service.FlowRecordBatchService;
import service.UtilService;


public class Main {
    public static void main(String[] args) {
        try {
            FlowRecordBatchService flowRecordBatchService = new FlowRecordBatchService(1000, "");
            Map<LookupCsvKey, String> lookupCsvMap = UtilService.lookupCsvParser("");

            while(true){
                List<FlowRecord> records = flowRecordBatchService.getBatchFlowRecords();
                if (records.isEmpty()) break;
                for (FlowRecord record : records) {
                    LookupCsvKey searchKey = new LookupCsvKey(record.getDstPort(), record.getProtocol());
                    String value = "Untagged";
                    if (lookupCsvMap.containsKey(searchKey)) {
                        value = lookupCsvMap.get(searchKey);
                    }

                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }


    }
}