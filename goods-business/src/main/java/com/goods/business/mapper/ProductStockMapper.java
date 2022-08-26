package com.goods.business.mapper;

import com.goods.common.model.business.ProductStock;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author WangJin
 * @create 2022-08-26 10:08
 */
@Repository
public interface ProductStockMapper extends Mapper<ProductStock> {
}
