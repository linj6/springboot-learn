package com.lnjecit.common.base;

import com.github.pagehelper.PageInfo;
import com.lnjecit.common.constants.Constants;
import com.lnjecit.common.page.PageBuiler;
import com.lnjecit.common.result.Result;
import com.lnjecit.common.util.SpringContextUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * BaseController
 *
 * @author lnj
 * @create 2018-03-02 14:49
 **/
public class BaseController<S extends BaseService<E>, E extends BaseEntity> {

    private S service;

    private Class<E> entityClass ;

    public BaseController() {
        Type type =  getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) type ;
        Class<S> serviceClass = (Class<S>) pt.getActualTypeArguments()[0];
        entityClass =(Class<E>) pt.getActualTypeArguments()[1];
        service = SpringContextUtils.getBean(serviceClass);
        Assert.notNull(service,"注入服务类:"+serviceClass.getName()+"不允许为空,请检查是否注入该服务");
    }

    /**
     * 获取request对象
     *
     * @return
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * 详情
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取详情", notes = "根据id来获取详情")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "query")
    @ResponseBody
    @PostMapping("getById")
    protected Result getById(@RequestParam("id") Long id) {
        E entity = service.getById(id);
        if (null != entity) {
            entityAfter(entity);
        }
        return Result.success(entity);
    }

    /**
     * 查询前处理
     * @param param
     */
    protected void beforeList(Map<String, Object> param) {

    }

    /**
     * 列表查询
     * @param param
     * @return
     */
    @ApiOperation(value = "查询", notes = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "orderBy", value = "排序", dataType = "int", paramType = "query", example = "updateTime desc, id asc")
    })
    @ResponseBody
    @PostMapping("/list")
    public Result list(@RequestParam(required = false) Map<String, Object> param) {
        beforeList(param);
        PageInfo<?> pageInfo = PageBuiler.builder(param);
        Result result = service.queryPage(param, pageInfo) ;
        Map<String, Object> data = (Map<String, Object>) result.getData();
        List<E> entities = (List<E>) data.get(Constants.LIST);
        if (entities != null && entities.size() > 0) {
            for (E entity : entities) {
                entityAfter(entity);
            }
        }
        return result;
    }

    /**
     * 查询后处理
     * @param entity
     */
    protected void entityAfter(E entity){

    }

    /**
     * 新增前处理
     * @param entity
     */
    protected void beforeSave(E entity) {

    }

    /**
     * 新增
     * @param entity
     * @param bindingResult 校验结果
     * @return
     */
    @ApiOperation(value = "新增", notes = "新增", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @PostMapping("/save")
    public Result save(@Valid E entity, BindingResult bindingResult) {
        //如果验证不通过
        if(bindingResult.hasErrors()) {
            return Result.error(bindingResult.getFieldError().getDefaultMessage());
        }
        beforeSave(entity);
        Result result = service.insert(entity);
        afterSave(result);
        return result;
    }

    /**
     * 新增后处理
     * @param result
     */
    protected void afterSave(Result result) {

    }

    /**
     * 修改前处理
     * @param entity
     */
    protected void beforeUpdate(E entity) {

    }

    /**
     * 修改
     * @param entity
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "修改", notes = "修改")
    @ResponseBody
    @PostMapping("/update")
    public Result update(@Valid E entity, BindingResult bindingResult) {
        //如果验证不通过
        if(bindingResult.hasErrors()) {
            return Result.error(bindingResult.getFieldError().getDefaultMessage());
        }
        beforeUpdate(entity);
        Result result = service.update(entity);
        afterUpdate(result);
        return result;
    }

    /**
     * 修改后处理
     * @param result
     */
    protected void afterUpdate(Result result) {

    }

    /**
     * 删除前处理
     * @param id
     */
    protected void beforeDelete(Long id) {

    }

    @ApiOperation(value = "删除", notes = "根据id来删除")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "query")
    @ResponseBody
    @PostMapping("/delete")
    public Result delete(@RequestParam(name = "id") Long id) {
        beforeDelete(id);
        Result result = service.deleteLogical(id);
        afterDelete(result);
        return result;
    }

    /**
     * 删除后处理
     * @param result
     */
    protected void afterDelete(Result result) {

    }

}
