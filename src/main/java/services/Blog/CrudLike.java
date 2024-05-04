package services.Blog;

import models.Blog.LikePost;

import java.sql.SQLException;

public interface CrudLike<T> {

    void AjouterLike(T t) throws SQLException;
    int getLikeCountByBlogId(int blogId) throws SQLException;
    void deleteLikeByBlogId(int blogId, int likeId) throws SQLException;
    LikePost getLastLikeByBlogId(int blogId) throws SQLException;
}
