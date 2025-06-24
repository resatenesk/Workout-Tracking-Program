package antrenmantakipcom;

public class KullaniciVeri {
    private int antrenman_id;
    private int user_id;
    private String username;
    private String antrenman_tipi;
    private int gun_sayisi;

    public KullaniciVeri(int antrenman_id, int user_id, String username, String antrenman_tipi, int gun_sayisi) {
        this.antrenman_id = antrenman_id;
        this.user_id = user_id;
        this.username = username;
        this.antrenman_tipi = antrenman_tipi;
        this.gun_sayisi = gun_sayisi;
    }

    public int getAntrenmanID() {
        return this.antrenman_id;
    }

    public void setAntrenmanID(int antrenman_id) {
        this.antrenman_id = antrenman_id;
    }

    public int getUserID() {
        return this.user_id;
    }

    public void setUserID(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAntrenmanTipi() {
        return this.antrenman_tipi;
    }

    public void setAntrenmanTipi(String antrenman_tipi) {
        this.antrenman_tipi = antrenman_tipi;
    }

    public int getGunSayisi() {
        return this.gun_sayisi;
    }

    public void setGunSayisi(int gun_sayisi) {
        this.gun_sayisi = gun_sayisi;
    }

}
