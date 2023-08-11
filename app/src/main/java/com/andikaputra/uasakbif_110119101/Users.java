package com.andikaputra.uasakbif_110119101;

/*
 * Nim   : 10119101
 * Nama  : Andika Putra
 * Kelas : IF-1
 */

public class Users {

    String userId, name, profile;

    String email, katasandi;

    public Users(String userId, String name, String profile, String email, String katasandi){
        this.userId = userId;
        this.name = name;
        this.profile = profile;
        this.email = email;
        this.katasandi = katasandi;
    }

    public Users(){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.profile = email;
    }

    public String getKatasandi() {
        return katasandi;
    }

    public void setKatasandi(String password) {
        this.profile = katasandi;
    }

}
