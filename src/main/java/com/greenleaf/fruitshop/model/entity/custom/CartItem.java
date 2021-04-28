package com.greenleaf.fruitshop.model.entity.custom;

import com.greenleaf.fruitshop.model.entity.FruitInfo;

/**
 * 购物项
 */
public class CartItem {


    private FruitInfo fruitInfo;

    private double subTotal;

    private int buyNum;

    private boolean checked = true;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public FruitInfo getFruitInfo() {
        return fruitInfo;
    }

    public void setFruitInfo(FruitInfo fruitInfo) {
        this.fruitInfo = fruitInfo;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }
}

