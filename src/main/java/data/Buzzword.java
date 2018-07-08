package data;

public class Buzzword {

    public String get() {
        return buzzword;
    }


    private String buzzword;

    public Buzzword(String buzzword) {
        this.buzzword = buzzword;
    }

    public String getBuzzword() {
        return buzzword;
    }

    public void setBuzzword(String buzzword) {
        this.buzzword = buzzword;
    }

}
