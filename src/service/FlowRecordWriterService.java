package service;

import model.CsvLookupKey;
import model.Tag;

import java.io.IOException;
import java.util.Map;


public interface FlowRecordWriterService {

    void exportTagCounts(Map<Tag, Integer> tagCounts) throws IOException;

    void exportPortProtocolCount(Map<CsvLookupKey, Integer> map) throws IOException;
}
