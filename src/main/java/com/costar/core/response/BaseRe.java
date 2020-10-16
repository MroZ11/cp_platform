package com.costar.core.response;


import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by cloud on 2019/6/13.
 */
@Getter
@Setter
public class BaseRe {

    private Integer succeed;

    private Integer code;

    private String describe;

    private Object result;

    public String toJsonStr() {
        return JSON.toJSONString(this);
    }

    public BaseRe(Integer succeed) {
        this.succeed = succeed;
    }

    public static BaseRe success() {
        BaseRe baseRe = new BaseRe(1);
        return baseRe;
    }

    public static BaseRe fail() {
        BaseRe baseRe = new BaseRe(0);
        return baseRe;
    }

    public BaseRe code(ReCode reCode) {
        this.code = reCode.getCode();
        this.describe = reCode.getDescribe();
        return this;
    }

    public BaseRe result(Object result) {
        this.result = result;
        return this;
    }


}
