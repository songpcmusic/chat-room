package com.songpcmusic.chat.domain;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by songpengcheng on 2017/4/29.
 */
public class Protocol implements Serializable {

    public final static short HEADER_LENGTH = 16;
    public final static short VERSION = 1;

    private Integer packetLength;
    private Short headerLength;
    private Short version;
    private Integer operation;
    private Integer extra;

    private String body;

    public Integer getPacketLength() {
        return packetLength;
    }

    public void setPacketLength(Integer packetLength) {
        this.packetLength = packetLength;
    }

    public Short getHeaderLength() {
        return headerLength;
    }

    public void setHeaderLength(Short headerLength) {
        this.headerLength = headerLength;
    }

    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public Integer getExtra() {
        return extra;
    }

    public void setExtra(Integer extra) {
        this.extra = extra;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Protocol{" +
                "packetLength=" + packetLength +
                ", headerLength=" + headerLength +
                ", version=" + version +
                ", operation=" + operation +
                ", extra=" + extra +
                ", body='" + body + '\'' +
                '}';
    }
}
