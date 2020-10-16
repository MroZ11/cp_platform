package com.costar.core.utils;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by cloud on 2019/6/20.
 */
@Getter
@Setter
public class EPager implements Serializable {


    /**
     * 当前页码默认0
     */
    private Integer page = 0;


    /**
     * 当前每页条数默认10
     */
    private Integer size = 10;


    /**
     * 总条目数
     */
    private Long count = 0L;


    /**
     * 当前页数据
     */
    private List dataList;

    /**
     * 搜索条件
     */
    private Map<String, Object> search;


    public static EPager pageToEPager(EPager ePagerRequest, Page page) {
        Assert.notNull(ePagerRequest);
        Assert.notNull(page);
        //总数
        ePagerRequest.setCount(page.getTotalElements());
        //当页内容
        ePagerRequest.setDataList(page.getContent());
        return ePagerRequest;
    }

    /**
     * 根据前端page对象生成对应的PageRequest 用于查询
     * <p>默认排序参数为空时默认按createTime降序</p>
     *
     * @param orderProp      排序字段  （可以为空 默认按createTime降序）
     * @param orderDirection 排序方向 （可以为空 DESCENDING表示降序 其他表示升序）
     * @return PageRequest {@link PageRequest}
     */
    public PageRequest buildDefaultPageRequestWithSort(String orderProp, String orderDirection) {
        if (orderProp.equals("createTime") && StrUtil.isEmpty(orderDirection)) {
            orderDirection = "DESCENDING";
        }
        if (StrUtil.isEmpty(orderProp)) {
            orderProp = "createTime";
            orderDirection = "DESCENDING";
        }
        Sort.Direction direction = StrUtil.equalsAnyIgnoreCase(orderDirection, "DESCENDING") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, orderProp);
        PageRequest pg = PageRequest.of(this.getPage(), this.getSize(), sort);
        return pg;
    }


}
