package constants;

public class ApplicationConstants {
    public static final String TAG_COUNT_HEADER = "Tag, Count";
    public static final String PORT_PROTO_COUNT_HEADER = "Port, Protocol, Count";
    public static final Integer NUM_FLOW_LOG_TOKENS = 14;

    public static final String OUTPUT_FILE_TAG_COUNT_SUFFIX = "_tag_count.csv";
    public static final String OUTPUT_FILE_PORT_PROTO_COUNT_SUFFIX = "_port_proto_count.csv";

    public static final String ERROR_INVALID_PROTOCOL_ID_PASSED = "Invalid protocol id passed - ";
    public static final String ERROR_UNABLE_TO_OPEN_FILE = "Unable to open file ";

    public static final String UNABLE_TO_PARSE_LOG_ENTRY_ENTRY_SKIPPED = "Unable to parse log entry. Entry skipped. ";
    public static final String ERROR_INVALID_ENTRY = "Log entry does not contain required number of tokens. Entry skipped.  ";
    public static final String ERROR_COULD_NOT_OPEN_THE_FILE = "Error: Could not open the file ";
    public static final String ERROR_NULL_FILENAME_PASSED = "Null filename passed";
}
