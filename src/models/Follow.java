package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "follows")

@NamedQueries({

    @NamedQuery(//フォローしているかを確認
            name = "followedCheck",
            query = "select count(f) from Follow AS f WHERE f.follower_id = :logid AND f.followed_id = :employee "),
    @NamedQuery(//フォローしている時フォロー情報を取得
            name = "getFollowData",
            query = "select f from Follow AS f WHERE f.follower_id = :logid AND f.followed_id = :employee "),
    @NamedQuery(//フォローしている人のID取得
            name = "getAllFollowData",
            query = "select f from Follow AS f WHERE f.follower_id = :logid "),
})

@Entity
public class Follow {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "followed_id" ,  nullable = false)
    private Integer followed_id;

    @Column(name = "follower_id" ,  nullable = false)
    private Integer follower_id;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFollowed_id() {
        return followed_id;
    }

    public void setFollowed_id(Integer followed_id) {
        this.followed_id = followed_id;
    }
    public Integer getFollower_id() {
        return follower_id;
    }
    public void setFollower_id(Integer follower_id) {
        this.follower_id = follower_id;
    }
}