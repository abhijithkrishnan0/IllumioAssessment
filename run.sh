BATCH_SIZE=10000
FLOWLOG_FILE=flowlog.log
LOOKUP_CSV_FILE=lookup.csv
OP_FILE_PREFIX=output


python3 scripts/flow_log_generator.py
python3 scripts/lookup_csv_generator.py


javac -d bin -cp "src:lib/*" src/*.java
jar cfm app.jar MANIFEST.MF -C bin/ .
java -jar app.jar $BATCH_SIZE $FLOWLOG_FILE $LOOKUP_CSV_FILE $OP_FILE_PREFIX