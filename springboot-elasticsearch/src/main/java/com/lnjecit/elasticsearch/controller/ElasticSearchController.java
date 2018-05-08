package com.lnjecit.elasticsearch.controller;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @create 2018-05-08 22:10
 **/
@RequestMapping("/elasticSearch")
@RestController
public class ElasticSearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchController.class);

    // 索引
    private static final String INDEX = "book";
    // 类型
    private static final String TYPE = "novel";

    @Autowired
    private TransportClient transportClient;

    @GetMapping("/get/book/novel")
    public ResponseEntity get(@RequestParam(name = "id", defaultValue = "") String id) {
        if (id.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        // 查询结果
        GetResponse result = transportClient.prepareGet(INDEX, TYPE, id)
                .get();
        if (!result.isExists()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(result.getSource(), HttpStatus.OK);
    }

    @PostMapping("add/book/novel")
    public ResponseEntity add(@RequestParam(name = "title") String title,
                              @RequestParam(name = "author") String author,
                              @RequestParam(name = "wordCount") String wordCount,
                              @RequestParam(name = "publishDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date publishDate) {
        try {
            // 构建文档
            XContentBuilder contentBuilder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("title", title)
                    .field("author", author)
                    .field("word_count", wordCount)
                    .field("publish_date", publishDate.getTime())
                    .endObject();
            // 新增文档
            IndexResponse result = transportClient.prepareIndex(INDEX, TYPE)
                    .setSource(contentBuilder)
                    .get();
            return new ResponseEntity(result.getId(), HttpStatus.OK);
        } catch (IOException e) {
            LOGGER.error("add error", e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("update/book/novel")
    public ResponseEntity add(@RequestParam(name = "id") String id,
                              @RequestParam(name = "title", required = false) String title,
                              @RequestParam(name = "author", required = false) String author,
                              @RequestParam(name = "wordCount", required = false) String wordCount,
                              @RequestParam(name = "publishDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date publishDate) {
        try {
            UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, id);
            // 构建文档
            XContentBuilder contentBuilder = XContentFactory.jsonBuilder()
                    .startObject();
            if (title != null) {
                contentBuilder.field("title", title);
            }
            if (author != null) {
                contentBuilder.field("author", author);
            }
            if (wordCount != null) {
                contentBuilder.field("word_count", wordCount);
            }
            if (publishDate != null) {
                contentBuilder.field("publish_date", publishDate.getTime());
            }
            contentBuilder.endObject();
            updateRequest.doc(contentBuilder);
            UpdateResponse result = transportClient.update(updateRequest).get();
            return new ResponseEntity(result.getId(), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("update error", e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/book/novel")
    public ResponseEntity delete(@RequestParam(name = "id", defaultValue = "") String id) {
        // 查询结果
        DeleteResponse result = transportClient.prepareDelete(INDEX, TYPE, id)
                .get();
        return new ResponseEntity(result.getResult(), HttpStatus.OK);
    }

    @PostMapping("query/book/novel")
    @ResponseBody
    public ResponseEntity query(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "author", required = false) String author,
            @RequestParam(name = "gtWordCount", defaultValue = "0") int gtWordCount,
            @RequestParam(name = "ltWordCount", required = false) Integer ltWordCount
    ) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (author != null) {
            queryBuilder.must(QueryBuilders.matchQuery("author", author));
        }
        if (title != null) {
            queryBuilder.must(QueryBuilders.matchQuery("title", title));
        }
        // 大于
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("word_count").from(gtWordCount);
        // 小于
        if (ltWordCount != null && ltWordCount > 0) {
            rangeQueryBuilder.to(ltWordCount);
        }
        //过滤条件
        queryBuilder.filter(rangeQueryBuilder);
        SearchRequestBuilder builder = transportClient.prepareSearch(INDEX)
                .setTypes(TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(queryBuilder)
                .setFrom(0)
                .setSize(10);
        SearchResponse response = builder.get();
        List<Map<String, Object>> result = new ArrayList<>();
        //将命中的数据放入List
        for (SearchHit hit : response.getHits()) {
            result.add(hit.getSourceAsMap());
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

}
