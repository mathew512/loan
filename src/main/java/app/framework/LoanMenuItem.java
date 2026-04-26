package app.framework;

public class LoanMenuItem {
    private String label;
    private String url;

    public LoanMenuItem(String label, String url) {
        this.label = label;
        this.url = url;
    }

    public String getLabel() { return label; }
    public String getUrl() { return url; }
}
