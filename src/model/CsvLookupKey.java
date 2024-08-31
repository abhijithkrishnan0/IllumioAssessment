package model;

import java.util.Objects;

public class CsvLookupKey {

    private int port;
    private String protocol;

    public CsvLookupKey(int port, String protocol) {
        this.port = port;
        this.protocol = protocol;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CsvLookupKey that = (CsvLookupKey) o;
        return port == that.port && Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(port, protocol);
    }
}
