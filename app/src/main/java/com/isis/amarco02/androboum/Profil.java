package com.isis.amarco02.androboum;

public class Profil {


    //Les différents statuts d'un utilisateur
    public enum BombStatut { IDLE, AWAITING, BOMBER, BOMBED };

    private String email;
    boolean isConnected;
    private String uid;

    // mon statut actuel
    private BombStatut statut = BombStatut.IDLE;
    // l'identifiant de mon adversaire
    private String otherUserUID;
    // l'email de mon adversaire
    private String otherUseremail;
    // mon score
    private long score = 0;
    // coordonnées GPS
    private double latitude;
    private double longitude;


    public Profil() {
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public boolean isConnected() {
        return isConnected;
    }
    public void setConnected(boolean connected) {
        isConnected = connected;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public BombStatut getStatut() {
        return statut;
    }

    public void setStatut(BombStatut statut) {
        this.statut = statut;
    }

    public String getOtherUserUID() {
        return otherUserUID;
    }

    public void setOtherUserUID(String otherUserUID) {
        this.otherUserUID = otherUserUID;
    }

    public String getOtherUseremail() {
        return otherUseremail;
    }

    public void setOtherUseremail(String otherUseremail) {
        this.otherUseremail = otherUseremail;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
