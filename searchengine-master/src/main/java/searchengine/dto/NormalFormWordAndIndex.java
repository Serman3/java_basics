package searchengine.dto;

public class NormalFormWordAndIndex {
    private String word;
    private int index;

    public NormalFormWordAndIndex(String word, int index) {
        this.word = word;
        this.index = index;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
