package com.goods.business.service;

import com.goods.common.model.business.InStock;
import com.goods.common.model.business.InStockInfo;
import com.goods.common.vo.business.InStockDetailVO;
import com.goods.common.vo.business.InStockItemVO;
import com.goods.common.vo.business.InStockVO;
import com.goods.common.vo.business.ProductStockVO;
import com.goods.common.vo.system.PageVO;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-08-25 11:17
 */
public interface InStockService {
    PageVO<InStockVO> findProductList(Integer pageNum, Integer pageSize, InStockVO inStockVO);


    /**
     * 明细
     * @param id
     * @return
     */
    InStockDetailVO getDetail(Integer id, Integer pageNum);


    /**
     *放入回收站
     * @param id
     */
    void remove(Integer id);

    void back(Integer id);


    /**
     * 入库
     * @param inStockVO
     * @return
     */
    InStock addIntoStock(InStockVO inStockVO);


    /**
     * 审核通过
     * @param id
     */
    void publish(Integer id);

    PageVO<InStockVO> findAllStocks(Integer pageNum, Integer pageSize);

}
