package com.goods.business.service;

import com.goods.common.model.business.Supplier;
import com.goods.common.vo.business.SupplierVO;
import com.goods.common.vo.system.PageVO;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-08-24 18:10
 */
public interface SupplierService {


    /**
     * 物资来源分页列表展示
     * @param pageNum
     * @param pageSize
     * @param supplierVO
     * @return
     */
    PageVO<SupplierVO> findSupplierList(Integer pageNum, Integer pageSize, SupplierVO supplierVO);


    /**
     * 添加
     * @param supplierVO
     */
    void add(SupplierVO supplierVO);


    /**
     * 修改回显
     * @param id
     * @return
     */
    Supplier edit(Long id);


    /**
     * 修改
     * @param id
     * @param supplierVO
     */
    void update(Long id, SupplierVO supplierVO);

    /**
     * 删除
     * @param id
     */
    void delete(Long id);

    List<Supplier> findAll();


}
