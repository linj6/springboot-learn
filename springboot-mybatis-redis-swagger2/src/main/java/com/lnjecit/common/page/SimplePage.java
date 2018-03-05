package com.lnjecit.common.page;

import lombok.Getter;
import lombok.Setter;

/**
 * SimplePage 接口的返回的分页信息
 *
 * @author
 * @create 2018-03-03 21:19
 **/
@Getter
@Setter
public class SimplePage {

    // 总记录数
    private long total;
    // 当前页
    private int currentPageNum;
    // 每页的记录数
    private int pageSize;
    // 总页数
    private int totalPages;
}
