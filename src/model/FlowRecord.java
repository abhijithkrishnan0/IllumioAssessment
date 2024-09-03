package model;

import constants.ApplicationConstants;

import static constants.ApplicationConstants.NUM_FLOW_LOG_TOKENS;

public class FlowRecord {
    private int version;
    private String accountId;
    private String interfaceId;
    private String srcAddr;
    private String dstAddr;
    private int srcPort;
    private int dstPort;
    private int protocol;
    private long packets;
    private long bytes;
    private long start;
    private long end;
    private String action;
    private String logStatus;

    public FlowRecord(String logRecord) {
        String[] tokens = logRecord.split("\\s+");
        if (tokens.length != NUM_FLOW_LOG_TOKENS) {
            throw new IllegalArgumentException(ApplicationConstants.ERROR_INVALID_ENTRY + logRecord);
        }
        try {
            version = Integer.parseInt(tokens[0]);
            accountId = tokens[1];
            interfaceId = tokens[2];
            srcAddr = tokens[3];
            dstAddr = tokens[4];
            srcPort = Integer.parseInt(tokens[5]);
            dstPort = Integer.parseInt(tokens[6]);
            protocol = Integer.parseInt(tokens[7]);
            packets = Long.parseLong(tokens[8]);
            bytes = Long.parseLong(tokens[9]);
            start = Long.parseLong(tokens[10]);
            end = Long.parseLong(tokens[11]);
            action = tokens[12];
            logStatus = tokens[13];
        } catch (Exception e) {
            throw new IllegalArgumentException(ApplicationConstants.ERROR_INVALID_ENTRY + logRecord);
        }
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(String logStatus) {
        this.logStatus = logStatus;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getSrcAddr() {
        return srcAddr;
    }

    public void setSrcAddr(String srcAddr) {
        this.srcAddr = srcAddr;
    }

    public String getDstAddr() {
        return dstAddr;
    }

    public void setDstAddr(String dstAddr) {
        this.dstAddr = dstAddr;
    }

    public int getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(int srcPort) {
        this.srcPort = srcPort;
    }

    public int getDstPort() {
        return dstPort;
    }

    public void setDstPort(int dstPort) {
        this.dstPort = dstPort;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public long getPackets() {
        return packets;
    }

    public void setPackets(long packets) {
        this.packets = packets;
    }

    public long getBytes() {
        return bytes;
    }

    public void setBytes(long bytes) {
        this.bytes = bytes;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
