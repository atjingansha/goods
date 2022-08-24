package com.goods.business.mapper;

import com.goods.common.model.business.Product;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author WangJin
 * @create 2022-08-24 12:58
 */
@Repository
public interface ProductMapper extends Mapper<Product> {
}
