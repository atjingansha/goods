package com.goods.business.service;

import com.goods.common.model.business.Product;
import com.goods.common.vo.business.ProductVO;
import com.goods.common.vo.system.PageVO;

/**
 * @author WangJin
 * @create 2022-08-25 8:37
 */
public interface ProductService {


    /**
     * 物资资料分页列表展示
     * @param pageNum
     * @param pageSize
     * @param productVO
     * @return
     */
    PageVO<ProductVO> findProductList(Integer pageNum, Integer pageSize, ProductVO productVO);

    /**
     * 添加
     * @param productVO
     */
    void add(ProductVO productVO);


    /**
     * 修改回显
     * @param id
     * @return
     */
    Product edit(Integer id);


    /**
     * 修改
     * @param id
     * @param productVO
     */
    void update(Integer id, ProductVO productVO);


    /**
     *
     * @param id
     */
    void remove(Integer id);


    /**
     * 取消删除
     * @param id
     */
    void back(Integer id);

    /**
     * 审核
     * @param id
     */
    void publish(Integer id);


    /**
     * 获取product
     * @param pageNum
     * @param pageSize
     * @param productVO
     * @return
     */
    PageVO<ProductVO> findProducts(Integer pageNum, Integer pageSize, ProductVO productVO);


    /**
     * 查询库存
     * @param pageNum
     * @param pageSize
     * @param productVO
     * @return
     */
    PageVO<ProductVO> findProductStocks(Integer pageNum, Integer pageSize, ProductVO productVO);

}
