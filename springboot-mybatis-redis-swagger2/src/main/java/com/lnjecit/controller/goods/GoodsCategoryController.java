package com.lnjecit.controller.goods;

import com.github.pagehelper.PageInfo;
import com.lnjecit.common.constants.Constants;
import com.lnjecit.common.page.PageBuiler;
import com.lnjecit.common.result.Result;
import com.lnjecit.entity.goods.GoodsCategory;
import com.lnjecit.service.goods.GoodsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @create 2018-04-27 13:21
 **/
@Api(value = "商品分类", description = "商品分类api")
@RequestMapping("/goodsCategory")
@RestController
public class GoodsCategoryController {

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @ApiOperation(value = "列表查询（分页）", notes = "列表查询（分页）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, defaultValue = "10", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "orderBy", value = "排序", dataType = "int", paramType = "query", example = "updateTime desc, id asc"),
            @ApiImplicitParam(name = "name", value = "名称", dataType = "string", paramType = "query"),
    })
    @ResponseBody
    @PostMapping("/list")
    public Result list(@RequestParam(required = false) @ApiIgnore Map<String, Object> param) {
        PageInfo<?> pageInfo = PageBuiler.builder(param);
        return goodsCategoryService.queryPage(param, pageInfo);
    }

    @ApiOperation(value = "以树形结构返回商品列表", notes = "以树形结构返回商品列表")
    @ResponseBody
    @PostMapping("/listTree")
    public Result listTree() {
        List<GoodsCategory> goodsCategoryList = goodsCategoryService.getGoodsCategoryTreeList();
        Map<String, List<GoodsCategory>> resultMap = new HashMap<>();
        resultMap.put(Constants.LIST, goodsCategoryList);
        return Result.success(resultMap);
    }

    @ApiOperation(value = "新增", notes = "新增", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @PostMapping("/save")
    public Result save(@Valid @RequestBody GoodsCategory goodsCategory, BindingResult bindingResult) {
        //如果验证不通过
        if (bindingResult.hasErrors()) {
            return Result.error(bindingResult.getFieldError().getDefaultMessage());
        }
        return goodsCategoryService.insert(goodsCategory);
    }

    @ApiOperation(value = "修改", notes = "修改")
    @ResponseBody
    @PostMapping("/update")
    public Result update(@Valid @RequestBody GoodsCategory goodsCategory, BindingResult bindingResult) {
        //如果验证不通过
        if (bindingResult.hasErrors()) {
            return Result.error(bindingResult.getFieldError().getDefaultMessage());
        }
        return goodsCategoryService.update(goodsCategory);
    }

    @ApiOperation(value = "删除", notes = "根据id来删除")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "query")
    @ResponseBody
    @PostMapping("/delete")
    public Result delete(@RequestParam(name = "id") Long id) {
        return goodsCategoryService.deleteLogical(id);
    }

    @ApiOperation(value = "删除", notes = "根据id来删除")
    @ApiImplicitParam(name = "ids", value = "ids", required = true, dataType = "Long", paramType = "query")
    @ResponseBody
    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestParam(name = "ids") Long[] ids) {
        return goodsCategoryService.deleteLogicalBatch(ids);
    }

}
