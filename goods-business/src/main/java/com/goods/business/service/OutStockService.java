package com.goods.business.service;

import com.goods.common.vo.business.OutStockDetailVO;
import com.goods.common.vo.business.OutStockVO;
import com.goods.common.vo.system.PageVO;

/**
 * @author WangJin
 * @create 2022-08-26 14:55
 */
public interface OutStockService {
    PageVO<OutStockVO> findOutStockList(Integer pageNum, Integer pageSize, OutStockVO outStockVO);


    OutStockDetailVO getDetail(Integer id, Integer pageNum);

    void remove(Integer id);

    void back(Integer id);
}
