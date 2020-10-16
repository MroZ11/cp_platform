package com.costar.core.response;

/**
 * Created by cloud on 2019/6/13.
 */
public class SuccessRe extends BaseRe {

    public SuccessRe(Integer succeed) {
        super(succeed);
    }

    public static SuccessRe createRe(ReCode reCode) {
        SuccessRe re = new SuccessRe(1);
        re.code(reCode);
        return re;
    }

    public static SuccessRe createRe(ReCode reCode, Object result) {
        SuccessRe re = new SuccessRe(1);
        re.code(reCode);
        re.result(result);
        return re;
    }

}
