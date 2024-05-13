package models.Blog;

import java.sql.*;

public class Blog {
    private int idblog;
    private String TitleBlog;
    private String ContentBlog;
    private  String CountryBlog;
    private int approved;
    private int iduser;
    public Blog(){

    }

    public Blog(int idblog, String titleBlog, String contentBlog, String countryBlog, int approved) {
        this.idblog = idblog;
        TitleBlog = titleBlog;
        ContentBlog = contentBlog;
        CountryBlog = countryBlog;
        this.approved = approved;
        this.iduser=iduser;
    }
    public Blog(int idblog, String titleBlog, String contentBlog, String countryBlog, int approved,int iduser) {
        this.idblog = idblog;
        TitleBlog = titleBlog;
        ContentBlog = contentBlog;
        CountryBlog = countryBlog;
        this.approved = approved;
        this.iduser=iduser;
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

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
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

    public String getImageUrlFromDatabase(int blogId) {
        String imageUrl = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Établir la connexion à la base de données
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/adido", "root", "");

            // Préparer la requête SQL pour récupérer l'URL de l'image
            String sql = "SELECT nom_image FROM image_blog WHERE idblog = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, blogId);

            // Exécuter la requête et récupérer le résultat
            rs = stmt.executeQuery();

            // Vérifier s'il y a un résultat et récupérer l'URL de l'image
            if (rs.next()) {
                imageUrl = rs.getString("image_url");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération de l'URL de l'image: " + e.getMessage());
        } finally {
            // Fermer les ressources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return imageUrl;
    }


}
