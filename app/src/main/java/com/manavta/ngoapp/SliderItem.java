package com.manavta.ngoapp;

public class SliderItem {
    String strDescrtiption;
    int imgIntro;
    public SliderItem(String strDescrtiption, int imgIntro) {
        this.strDescrtiption = strDescrtiption;
        this.imgIntro = imgIntro;

    }

    public String getStrDescrtiption() {
        return strDescrtiption;
    }

    public void setStrDescrtiption(String strDescrtiption) {
        this.strDescrtiption = strDescrtiption;
    }

    public int getImgIntro() {
        return imgIntro;
    }

    public void setImgIntro(int imgIntro) {
        this.imgIntro = imgIntro;
    }
}
