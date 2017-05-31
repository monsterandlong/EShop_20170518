package com.feicuiedu.eshop_20170518.manger.base;

import com.feicuiedu.eshop_20170518.entity.Status;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 张志龙 on 2017/5/26.
 */

public abstract class ResponseEntity {
    @SerializedName("status")
    private Status mStatus;

    public Status getStatus() {
        return mStatus;
    }
}
