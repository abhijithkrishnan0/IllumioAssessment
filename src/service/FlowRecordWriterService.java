package service;

import model.CsvLookupKey;
import model.Tag;

import java.io.IOException;
import java.util.Map;


public interface FlowRecordWriterService {
    void exportTagCounts(String filename, Map<Tag, Integer> tagCounts) throws IOException ;

    void exportPortProtocolCount(String filename, Map<CsvLookupKey, Integer> map) throws IOException;
}
