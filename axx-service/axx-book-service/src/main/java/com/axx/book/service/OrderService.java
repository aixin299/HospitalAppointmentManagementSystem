package com.axx.book.service;

import com.axx.book.pojo.Order;

import com.axx.entity.PageResult;

import java.util.List;

/****
 * @Author:axx
 * @Description:Order业务层接口
 * @Date 2021/2/1 14:19
 *****/

public interface OrderService {

    /***
     * Order多条件分页查询
     * @param order
     * @param page
     * @param size
     * @return
     */
    PageResult<Order> findPage(Order order, int page, int size);

    /***
     * Order分页查询
     * @param page
     * @param size
     * @return
     */
    PageResult<Order> findPage(int page, int size);

    /***
     * Order多条件搜索方法
     * @param order
     * @return
     */
    List<Order> findList(Order order);

    /***
     * 删除Order
     * @param id
     */
    void delete(Integer id);

    /***
     * 修改Order数据
     * @param order
     */
    void update(Order order);

    /***
     * 新增Order
     * @param order
     */
    void add(Order order);

    /**
     * 根据ID查询Order
     * @param id
     * @return
     */
     Order findById(Integer id);

    /***
     * 查询所有Order
     * @return
     */
    List<Order> findAll();
}
