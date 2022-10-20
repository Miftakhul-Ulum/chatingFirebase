package com.project105.chatapp.pesan;

public class ListPesan {

    private String nama, nomor, pesanTerkirim, profilImage, chatKey;
    private int pesanGagal;

    public ListPesan(String nama, String nomor, String pesanTerkirim, String profilImage, int pesanGagal, String chatKey){
        this.nama = nama;
        this.nomor = nomor;
        this.pesanTerkirim = pesanTerkirim;
        this.profilImage = profilImage;
        this.pesanGagal = pesanGagal;
        this.chatKey = chatKey;

    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getProfilImage() {
        return profilImage;
    }

    public void setProfilImage(String profilImage) {
        this.profilImage = profilImage;
    }

    public String getPesanTerkirim() {
        return pesanTerkirim;
    }

    public void setPesanTerkirim(String pesanTerkirim) {
        this.pesanTerkirim = pesanTerkirim;
    }

    public int getPesanGagal() {
        return pesanGagal;
    }

    public void setPesanGagal(int pesanGagal) {
        this.pesanGagal = pesanGagal;
    }

    public String getChatKey() {
        return chatKey;
    }
}
