package com.lnjecit.common.page;

import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * PageBuilder 用于构建page对象
 *
 * @author lnj
 * @create 2018-03-03 21:00
 **/
public final class PageBuiler {

    private transient Logger logger = LoggerFactory.getLogger(this.getClass());

    //默认页码
    private static final int DEFAULT_PAGE_NUM = 1;
    // 默认每页记录数
    private static final int DEFAULT_PAGE_SIZE = 10;
    // 页码
    private int pageNum;
    // 每页记录数
    private int pageSize;

    public PageBuiler() {

    }

    public PageBuiler(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public PageBuiler(Map<String, Object> params) {
        init(params);
    }

    private void init(Map<String, Object> params) {
        if (null != params) {
            try {
                Object start = params.get("pageNum");
                Object rows = params.get("pageSize");
                if (start != null) {
                    pageNum = Integer.parseInt(start.toString());
                } else {
                    pageNum = DEFAULT_PAGE_NUM;
                    logger.warn("pageNum 为空，将设置其值为1");
                }
                if (rows != null) {
                    pageSize = Integer.parseInt(rows.toString());
                } else {
                    pageSize = DEFAULT_PAGE_SIZE;
                    logger.warn("pageNum 为空，将设置其值为10");
                }
            } catch (NumberFormatException ex) {
                logger.error("分页参数出错", ex);
                throw new RuntimeException("分页参数出错", ex);
            }
        }
    }

    public static PageInfo<?> builder(Map<String, Object> params) {
        return new PageBuiler(params).build();
    }

    public PageInfo<?> build() {
        PageInfo<?> pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        return pageInfo;
    }
}
