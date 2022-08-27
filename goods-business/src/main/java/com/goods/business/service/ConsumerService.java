package com.goods.business.service;

import com.goods.common.model.business.Consumer;
import com.goods.common.vo.business.ConsumerVO;
import com.goods.common.vo.system.PageVO;

/**
 * @author WangJin
 * @create 2022-08-26 14:18
 */
public interface ConsumerService {


    /**
     *
     * @param pageNum
     * @param pageSize
     * @param consumerVO
     * @return
     */
    PageVO<ConsumerVO> findProductList(Integer pageNum, Integer pageSize, ConsumerVO consumerVO);

    void add(ConsumerVO consumerVO);


    Consumer edit(Integer id);

    void update(Integer id, ConsumerVO consumerVO);

    void delete(Long id);

}
