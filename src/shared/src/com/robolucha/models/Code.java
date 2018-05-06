package com.robolucha.models;

public class Code {

    private long id;
    private String event;
    private long version = 1L;
    private String script;
    private String exception;

    @Override
    public String toString() {
        return "Code [id=" + id + ", event=" + event + ", version=" + version + ", script=" + script + ", exception="
                + exception + "]";
    }

    public Code() {
    }

    public Code(String event, String script) {
        this.event = event;
        this.script = script;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }


}
