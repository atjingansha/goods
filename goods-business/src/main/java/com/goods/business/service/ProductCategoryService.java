package com.goods.business.service;

import com.goods.common.model.business.ProductCategory;
import com.goods.common.vo.business.ProductCategoryTreeNodeVO;
import com.goods.common.vo.business.ProductCategoryVO;
import com.goods.common.vo.system.MenuNodeVO;
import com.goods.common.vo.system.PageVO;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-08-24 11:14
 */
public interface ProductCategoryService {

    /**
     * 获取分页
     * @param pageNum
     * @param pageSize
     * @param productCategoryTreeNodeVO
     * @return
     */
    PageVO<ProductCategoryTreeNodeVO> findCategoryList(Integer pageNum, Integer pageSize, ProductCategoryTreeNodeVO productCategoryTreeNodeVO);


    /**
     * 获取父节点
     * @return
     */
    List<ProductCategoryTreeNodeVO> getParentCategoryTree();

    /**
     * 添加
     * @param productCategoryVO
     */
    void add(ProductCategoryVO productCategoryVO);

    /**
     * 修改回显
     * @param id
     * @return
     */
    ProductCategory edit(Long id);


    /**
     * 修改
     * @param productCategoryVO
     * @return
     */
   void update(Long id,ProductCategoryVO productCategoryVO);


    /**
     * 删除
     * @param id
     */
    void delete(Long id);
}
