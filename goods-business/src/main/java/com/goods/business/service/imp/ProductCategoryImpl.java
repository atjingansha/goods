package com.goods.business.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goods.business.converter.ProductConverter;
import com.goods.business.mapper.ProductCategoryMapper;
import com.goods.business.mapper.ProductMapper;
import com.goods.business.service.ProductCategoryService;
import com.goods.common.enums.system.UserTypeEnum;
import com.goods.common.model.business.Product;
import com.goods.common.model.business.ProductCategory;
import com.goods.common.model.system.Department;
import com.goods.common.utils.CategoryTreeBuilder;
import com.goods.common.vo.business.ProductCategoryTreeNodeVO;
import com.goods.common.vo.business.ProductCategoryVO;
import com.goods.common.vo.system.DepartmentVO;
import com.goods.common.vo.system.MenuNodeVO;
import com.goods.common.vo.system.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author WangJin
 * @create 2022-08-24 11:14
 */
@Service
public class ProductCategoryImpl implements ProductCategoryService {


    @Autowired
 private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public PageVO<ProductCategoryTreeNodeVO> findCategoryList(Integer pageNum, Integer pageSize, ProductCategoryTreeNodeVO productCategoryTreeNodeVO) {


        List<ProductCategory> businesses = productCategoryMapper.selectAll();

        List<ProductCategoryTreeNodeVO> productCategoryVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(businesses)){
            for (ProductCategory productCategory : businesses){
                ProductCategoryTreeNodeVO p = new ProductCategoryTreeNodeVO();
                BeanUtils.copyProperties(productCategory,p);
                productCategoryVOS.add(p);
            }

        }

        List<ProductCategoryTreeNodeVO> build = CategoryTreeBuilder.build(productCategoryVOS);
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<ProductCategory> info = new PageInfo<>(businesses);
        return new PageVO<>(info.getPages(),build);
    }



    @Override
    public List<ProductCategoryTreeNodeVO> getParentCategoryTree() {

        List<ProductCategory> businesses = productCategoryMapper.selectAll();

        List<ProductCategoryTreeNodeVO> productCategoryVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(businesses)){
            for (ProductCategory productCategory : businesses){
                ProductCategoryTreeNodeVO p = new ProductCategoryTreeNodeVO();
                BeanUtils.copyProperties(productCategory,p);
                productCategoryVOS.add(p);
            }

        }
        List<ProductCategoryTreeNodeVO> build = CategoryTreeBuilder.buildParent(productCategoryVOS);

        return build;
    }

    @Override
    public void add(ProductCategoryVO productCategoryVO) {


        ProductCategory product=new ProductCategory();


        BeanUtils.copyProperties(productCategoryVO,product);

        product.setCreateTime(new Date());
        product.setModifiedTime(new Date());
        productCategoryMapper.insert(product) ;
    }



    @Override
    public ProductCategory edit(Long id) {


        ProductCategory productCategory = productCategoryMapper.selectByPrimaryKey(id);
        return productCategory;
    }



    @Override
    public void update(Long id,ProductCategoryVO productCategoryVO) {

        ProductCategory productCategory = productCategoryMapper.selectByPrimaryKey(id);

        productCategory.setName(productCategoryVO.getName());
        productCategory.setSort(productCategoryVO.getSort());
        productCategory.setRemark(productCategoryVO.getRemark());
        productCategory.setModifiedTime(new Date());

        productCategoryMapper.updateByPrimaryKey(productCategory);

    }



    @Override
    public void delete(Long id) {
        productCategoryMapper.deleteByPrimaryKey(id);
    }


}
