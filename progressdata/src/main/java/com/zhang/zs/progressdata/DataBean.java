package com.zhang.zs.progressdata;

/**
 * Created by zs on 2016/6/30.
 */
public class DataBean {


    /**
     * aid : 5025193
     * typeid : 33
     * title : 【4月】Re：从零开始的异世界生活 12
     * pic : http://i1.hdslb.com/bfs/archive/64504119a16ef9ae78e780f9edf317a917328534.jpg
     * play : 2564518
     * video_review : 112721
     * author : TV-TOKYO
     * copyright : Copy
     * typename : 连载动画
     * subtitle :
     * review : 20089
     * favorites : 2176
     * mid : 21453565
     * description : #12 再访王都
     * create : 2016-06-19 17:20
     * credit : 0
     * coins : 11433
     * duration : 24:35
     * online : 1361
     */

    private String aid;
    private int typeid;
    private String title;
    private String pic;
    private String play;
    private String video_review;
    private String author;
    private String copyright;
    private String typename;
    private String subtitle;
    private int review;
    private int favorites;
    private int mid;
    private String description;
    private String create;
    private int credit;
    private int coins;
    private String duration;
    private int online;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPlay() {
        return play;
    }

    public void setPlay(String play) {
        this.play = play;
    }

    public String getVideo_review() {
        return video_review;
    }

    public void setVideo_review(String video_review) {
        this.video_review = video_review;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }


    @Override
    public String toString() {
        return "DataBean{" +
                "aid='" + aid + '\'' +
                ", typeid=" + typeid +
                ", title='" + title + '\'' +
                ", pic='" + pic + '\'' +
                ", play='" + play + '\'' +
                ", video_review='" + video_review + '\'' +
                ", author='" + author + '\'' +
                ", copyright='" + copyright + '\'' +
                ", typename='" + typename + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", review=" + review +
                ", favorites=" + favorites +
                ", mid=" + mid +
                ", description='" + description + '\'' +
                ", create='" + create + '\'' +
                ", credit=" + credit +
                ", coins=" + coins +
                ", duration='" + duration + '\'' +
                ", online=" + online +
                '}';
    }
}
