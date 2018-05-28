package com.example.sam.tourplan.Event.Summary.Memory;

/**
 * Created by ASUS on 4/30/2017.
 */

public class MemoryModelClass {

    private int memoryId;
    private int eventId;
    private String memoryText;
    private byte[] memoryImage;

    public MemoryModelClass(int eventId, String memoryText, byte[] memoryImage) {
        this.eventId = eventId;
        this.memoryText = memoryText;
        this.memoryImage = memoryImage;
    }

    public MemoryModelClass() {
    }

    public MemoryModelClass(int memoryId, int eventId, String memoryText, byte[] memoryImage) {
        this.memoryId = memoryId;
        this.eventId = eventId;
        this.memoryText = memoryText;
        this.memoryImage = memoryImage;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getMemoryId() {
        return memoryId;
    }

    public void setMemoryId(int memoryId) {
        this.memoryId = memoryId;
    }

    public String getMemoryText() {
        return memoryText;
    }

    public void setMemoryText(String memoryText) {
        this.memoryText = memoryText;
    }

    public byte[] getMemoryImage() {
        return memoryImage;
    }

    public void setMemoryImage(byte[] memoryImage) {
        this.memoryImage = memoryImage;
    }
}
