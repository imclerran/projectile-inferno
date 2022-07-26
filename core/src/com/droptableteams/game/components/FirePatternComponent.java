package com.droptableteams.game.components;

import com.droptableteams.game.LibECS.interfaces.AbstractComponent;

// TODO: consolidate fire pattern and fire control components
// TODO: create fire pattern definitions?
// EG: fire pattern type A, B, C ?
public class FirePatternComponent extends AbstractComponent {

    private float _baseDirection;
    public float rateOfFire;
    private int _numberOfBullets;
    private float _dividingAngle;
    private float _deltaTheta;
    private String _bulletType;


    public FirePatternComponent(int id, float baseDirection, float rateOfFire, String bulletType) {
        _id = id;
        _baseDirection = baseDirection;
        _numberOfBullets = 1;
        _dividingAngle = 0;
        _deltaTheta = 0;
        this.rateOfFire = rateOfFire;
        _bulletType = bulletType;
        _type = "FirePatternComponent";
    }

    public FirePatternComponent(int id, float baseDirection, float rateOfFire, int numberOfBullets,
                                float dividingAngle, float deltaTheta, String bulletType) {
        _id = id;
        _baseDirection = baseDirection;
        _numberOfBullets = numberOfBullets;
        _dividingAngle = dividingAngle;
        _deltaTheta = deltaTheta;
        this.rateOfFire = rateOfFire;
        _bulletType = bulletType;
        _type = "FirePatternComponent";
    }

    public float getBaseDirection() {
        return _baseDirection;
    }

    public void setBaseDirection(float baseDirection) {
        _baseDirection = baseDirection;
    }

    public int getNumberOfBullets() {
        return _numberOfBullets;
    }

    public void setNumberOfBullets(int numberOfBullets) {
        _numberOfBullets = numberOfBullets;
    }

    public float getDividingAngle() {
        return _dividingAngle;
    }

    public void setDividingAngle(float dividingAngle) {
        _dividingAngle = dividingAngle;
    }

    public float getDeltaTheta() { return _deltaTheta; }

    public void setDeltaTheta(float deltaTheta) {
        _deltaTheta = deltaTheta;
    }

    public String getBulletType() {
        return _bulletType;
    }

    public void setBulletType(String bulletType) {
        _bulletType = bulletType;
    }
}
