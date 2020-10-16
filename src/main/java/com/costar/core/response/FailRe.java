package com.costar.core.response;

/**
 * Created by cloud on 2019/6/13.
 */
public class FailRe extends BaseRe{

    public FailRe(Integer succeed) {
        super(succeed);
    }

    public static FailRe createRe(ReCode reCode){
        FailRe re  = new FailRe(0);
        re.code(reCode);
        return re;
    }

    public static FailRe createRe(ReCode reCode,Object result){
        FailRe re  = new FailRe(0);
        re.code(reCode);
        re.result(result);
        return re;
    }

}
