package com.program;

public class WiseSaying {
    private String wiseSay;
    private String author;
    private int index;

    public WiseSaying(String wiseSay, String author, int index) {
        this.wiseSay = wiseSay;
        this.author = author;
        this.index = index;
    }

    public String getWiseSay() {
        return wiseSay;
    }

    public void setWiseSay(String wiseSay) {
        this.wiseSay = wiseSay;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return index + " / " + wiseSay + " / " + author;
    }
}
