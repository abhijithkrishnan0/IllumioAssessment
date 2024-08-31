package model;

import java.util.Objects;

public class LookupCsvKey {
    private int port;
    private int protocol;

    public LookupCsvKey(int port, int protocol) {
        this.port = port;
        this.protocol = protocol;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LookupCsvKey that = (LookupCsvKey) o;
        return port == that.port && protocol == that.protocol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(port, protocol);
    }
}
