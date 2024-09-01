BATCH_SIZE=1000
FLOWLOG_FILE=flowlog.log
LOOKUP_CSV_FILE=lookup.csv
TAG_COUNT_OP_FILE=tag_count_op.csv
PORT_PROTO_COUNT_OP_FILE=port_proto_count_op.csv


python3 scripts/flow_log_generator.py
python3 scripts/lookup_csv_generator.py


javac -d bin -cp "src:lib/*" src/*.java
jar cfm app.jar MANIFEST.MF -C bin/ .
java -jar app.jar $BATCH_SIZE $FLOWLOG_FILE $LOOKUP_CSV_FILE $TAG_COUNT_OP_FILE $PORT_PROTO_COUNT_OP_FILE