package com.goods.business.mapper;

import com.goods.common.model.business.InStock;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author WangJin
 * @create 2022-08-25 11:19
 */
@Repository
public interface InStockMapper extends Mapper<InStock> {
}
