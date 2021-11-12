package com.go2shop;

import java.util.ArrayList;

public interface CatalogueCallBack {
    void onSuccess(ArrayList<String> products);
    void onFail(String msg);
}