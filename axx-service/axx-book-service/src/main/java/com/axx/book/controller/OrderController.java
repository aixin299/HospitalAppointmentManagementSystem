package com.axx.book.controller;

import com.axx.book.pojo.Order;
import com.axx.book.service.OrderService;
import com.axx.book.service.ScheduleService;
import com.axx.entity.PageResult;
import com.axx.entity.Result;
import com.axx.entity.StatusCode;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:axx
 * @Description:
 * @Date 2021/2/1 14:19
 *****/
@Api(tags = "OrderController")
@RestController
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ScheduleService scheduleService;

    /***
     * 查询Order全部数据
     * @return
     */
    @ApiOperation(value = "查询所有Order（分页）",notes = "查询所Order有方法详情",tags = {"OrderController"})
    @GetMapping
    public Result<List<Order>> findAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size){
        //调用OrderService实现查询所有Order（分页）
        PageHelper.startPage(page,size);
        List<Order> list = orderService.findAll();
        return new Result<List<Order>>(true, StatusCode.OK,"查询成功",list) ;
    }

    /***
     * Order分页条件搜索实现
     * @param order
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "Order条件分页查询",notes = "分页条件查询Order方法详情",tags = {"OrderController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true)
    })
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageResult<Order>> findPage(@RequestBody(required = false) @ApiParam(name = "Order对象",value = "传入JSON数据",required = false) Order order, @PathVariable  int page, @PathVariable  int size){
        //调用OrderService实现分页条件查询Order
        PageResult<Order> pageResult = orderService.findPage(order, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }

    /***
     * Order分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "Order分页查询",notes = "分页查询Order方法详情",tags = {"OrderController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true)
    })
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageResult<Order>> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用OrderService实现分页查询Order
        PageResult<Order> pageResult = orderService.findPage(page, size);
        return new Result<PageResult<Order>>(true,StatusCode.OK,"查询成功",pageResult);
    }

    /***
     * 多条件搜索品牌数据
     * @param order
     * @return
     */
    @ApiOperation(value = "Order条件查询",notes = "条件查询Order方法详情",tags = {"OrderController"})
    @PostMapping(value = "/search" )
    public Result<List<Order>> findList(@RequestBody(required = false) @ApiParam(name = "Order对象",value = "传入JSON数据",required = false) Order order){
        //调用OrderService实现条件查询Order
        List<Order> list = orderService.findList(order);
        return new Result<List<Order>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Order根据ID删除",notes = "根据ID删除Order方法详情",tags = {"OrderController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        //调用OrderService实现根据主键删除
        orderService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改Order数据
     * @param order
     * @param id
     * @return
     */
    @ApiOperation(value = "Order根据ID修改",notes = "根据ID修改Order方法详情",tags = {"OrderController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @PutMapping(value="/{id}")
    public Result update(@RequestBody @ApiParam(name = "Order对象",value = "传入JSON数据",required = false) Order order,@PathVariable Integer id){
        //设置主键值
        order.setId(id);
        //调用OrderService实现修改Order
        orderService.update(order);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增Order数据
     * @param order
     * @return
     */
    @ApiOperation(value = "Order添加",notes = "添加Order方法详情",tags = {"OrderController"})
    @PostMapping
    public Result add(@ApiParam(name = "Order对象",value = "传入JSON数据",required = true) Order order){
        //调用OrderService实现添加Order
        System.out.println(order);
        try {
            //调用OrderService实现添加Order
            orderService.add(order);
            // 余量减一
            scheduleService.reduceSchedule(String.valueOf(order.getScheduleId()));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,300,"失败");
        }
        return new Result(true,200,"添加成功");
    }

    /***
     * 根据ID查询Order数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Order根据ID查询",notes = "根据ID查询Order方法详情",tags = {"OrderController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @GetMapping("/{id}")
    public Result<Order> findById(@PathVariable Integer id){
        //调用OrderService实现根据主键查询Order
        Order order = orderService.findById(id);
        return new Result<Order>(true,StatusCode.OK,"查询成功",order);
    }


}
