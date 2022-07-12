package com.nhnacademy.marketgg.server.exception.asset;

public class AssetNotFoundException extends IllegalArgumentException {

    public AssetNotFoundException(String msg) {
        super(msg);
    }

}
