package com.goods.business.mapper;

import com.goods.common.model.business.ProductCategory;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


/**
 * @author WangJin
 * @create 2022-08-24 11:18
 */
@Repository
public interface ProductCategoryMapper  extends Mapper<ProductCategory> {
}
