BATCH_SIZE=1000
FLOWLOG_FILE=flowlog.log
LOOKUP_CSV_FILE=lookup.csv
TAG_COUNT_OP_FILE=tag_count_op.csv
PORT_PROTO_COUNT_OP_FILE=port_proto_count.csv


python3 scripts/flow_log_generator.py
python3 scripts/lookup_csv_generator.py


javac -cp "src:lib/*" src/Main.java
java -cp src Main $BATCH_SIZE $FLOWLOG_FILE $LOOKUP_CSV_FILE $TAG_COUNT_OP_FILE $PORT_PROTO_COUNT_OP_FILE



