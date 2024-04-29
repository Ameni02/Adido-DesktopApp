package models;

public class Blog {
    private int idblog;
    private String TitleBlog;
    private String ContentBlog;
    private  String CountryBlog;
    private boolean approved;
    public Blog(){

    }

    public Blog(int idblog, String titleBlog, String contentBlog, String countryBlog, boolean approved) {
        this.idblog = idblog;
        TitleBlog = titleBlog;
        ContentBlog = contentBlog;
        CountryBlog = countryBlog;
        this.approved = approved;
    }

    public Blog(int idblog, String titleBlog, String contentBlog, String countryBlog) {
        this.idblog = idblog;
        TitleBlog = titleBlog;
        ContentBlog = contentBlog;
        CountryBlog = countryBlog;
    }

    public Blog(String titleblog, String contentBlog, String countryBlog) {
        TitleBlog = titleblog;
        ContentBlog = contentBlog;
        CountryBlog = countryBlog;
    }

    public boolean getApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public int getIdblog() {
        return idblog;
    }

    public void setIdblog(int idblog) {
        this.idblog = idblog;
    }

    public String getTitleBlog() {
        return TitleBlog;
    }

    public void setTitleBlog(String titleBlog) {
        TitleBlog = titleBlog;
    }

    public String getContentBlog() {
        return ContentBlog;
    }

    public void setContentBlog(String contentBlog) {
        ContentBlog = contentBlog;
    }

    public String getCountryBlog() {
        return CountryBlog;
    }

    public void setCountryBlog(String countryBlog) {
        CountryBlog = countryBlog;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "idblog=" + idblog +
                ", TitleBlog='" + TitleBlog + '\'' +
                ", ContentBlog='" + ContentBlog + '\'' +
                ", CountryBlog='" + CountryBlog + '\'' +
                '}';
    }
}
