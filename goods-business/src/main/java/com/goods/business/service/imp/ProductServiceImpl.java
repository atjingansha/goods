package com.goods.business.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goods.business.mapper.ProductMapper;
import com.goods.business.mapper.ProductStockMapper;
import com.goods.business.service.ProductService;
import com.goods.common.model.business.Product;
import com.goods.common.model.business.ProductStock;
import com.goods.common.model.system.Department;
import com.goods.common.vo.business.ProductVO;
import com.goods.common.vo.business.SupplierVO;
import com.goods.common.vo.system.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author WangJin
 * @create 2022-08-25 8:37
 */
@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductStockMapper productStockMapper;

    @Override
    public PageVO<ProductVO> findProductList(Integer pageNum, Integer pageSize, ProductVO productVO) {

        PageHelper.startPage(pageNum, pageSize);
        Example o = new Example(Product.class);
        if (productVO.getName() != null && !"".equals(productVO.getName())) {
            o.createCriteria().andLike("name", "%" + productVO.getName() + "%");
        }


        if (productVO.getCategorys() != null && !"".equals(productVO.getCategorys())) {

            Long[] categorys = productVO.getCategorys();

            productVO.setOneCategoryId(categorys[0]);
            productVO.setTwoCategoryId(categorys[1]);
            productVO.setThreeCategoryId(categorys[2]);

            if (productVO.getOneCategoryId() != null && !"".equals(productVO.getOneCategoryId())) {
                o.createCriteria().andEqualTo("oneCategoryId",  + productVO.getOneCategoryId());
            }
            if (productVO.getTwoCategoryId() != null && !"".equals(productVO.getTwoCategoryId())) {
                o.createCriteria().andEqualTo("twoCategoryId",  + productVO.getTwoCategoryId());
            }
            if (productVO.getThreeCategoryId() != null && !"".equals(productVO.getThreeCategoryId())) {
                o.createCriteria().andEqualTo("threeCategoryId",  + productVO.getThreeCategoryId());
            }
        }
        if (productVO.getStatus() != null && !"".equals(productVO.getStatus())) {
            o.createCriteria().andEqualTo("status",  productVO.getStatus() );
        }
        List<Product> products = productMapper.selectByExample(o);
        //转vo
        List<ProductVO> productVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(products)) {
            for (Product product : products) {
                ProductVO d = new ProductVO();
                BeanUtils.copyProperties(product, d);

                productVOS.add(d);
            }
        }
        PageInfo<Product> info = new PageInfo<>(products);
        return new PageVO<>(info.getTotal(), productVOS);

    }


    @Override
    public void add(ProductVO productVO) {
        Product product=new Product();

        BeanUtils.copyProperties(productVO, product);

        product.setCreateTime(new Date());
        product.setModifiedTime(new Date());
        product.setStatus(0);


      Long[] categoryKeys = productVO.getCategoryKeys();
        product.setOneCategoryId(categoryKeys[0]);
        product.setTwoCategoryId(categoryKeys[1]);
        product.setThreeCategoryId(categoryKeys[2]);

        productMapper.insert(product);
    }


    @Override
    public Product edit(Integer id) {
        Product product = productMapper.selectByPrimaryKey(id);
        return product;
    }

    @Override
    public void update(Integer id, ProductVO productVO) {

        Product product = productMapper.selectByPrimaryKey(id);

        BeanUtils.copyProperties(productVO, product);

        product.setModifiedTime(new Date());
        Long[] categoryKeys = productVO.getCategoryKeys();
        product.setOneCategoryId(categoryKeys[0]);
        product.setTwoCategoryId(categoryKeys[1]);
        product.setThreeCategoryId(categoryKeys[2]);

        productMapper.updateByPrimaryKey(product);


    }

    @Override
    public void remove(Integer id) {

        Product product = productMapper.selectByPrimaryKey(id);

        product.setStatus(1);

        productMapper.updateByPrimaryKey(product);
    }

    @Override
    public void back(Integer id) {
        Product product = productMapper.selectByPrimaryKey(id);

        product.setStatus(0);

        productMapper.updateByPrimaryKey(product);
    }

    @Override
    public void publish(Integer id) {
        Product product = productMapper.selectByPrimaryKey(id);

        product.setStatus(0);

        productMapper.updateByPrimaryKey(product);
    }

    @Override
    public PageVO<ProductVO> findProducts(Integer pageNum, Integer pageSize, ProductVO productVO) {
        PageHelper.startPage(pageNum, pageSize);


        Example example=new Example(Product.class);

        example.createCriteria().andEqualTo("status",productVO.getStatus());

        List<Product> products = productMapper.selectByExample(example);


        List<ProductVO> productVOList=new ArrayList<>();


        for (Product product : products) {
            ProductVO productVO1=new ProductVO();

            BeanUtils.copyProperties(product,productVO1);

            productVOList.add(productVO1);
        }

        PageInfo<Product> pageInfo=new PageInfo<>(products);
        return new PageVO<>(pageInfo.getTotal(),productVOList);
    }


    @Override
    public PageVO<ProductVO> findProductStocks(Integer pageNum, Integer pageSize, ProductVO productVO) {
        PageHelper.startPage(pageNum, pageSize);



        Example example=new Example(Product.class);

        example.createCriteria().andEqualTo("status",productVO.getStatus());

        List<Product> products = productMapper.selectByExample(example);


        List<ProductVO> productVOList=new ArrayList<>();


        for (Product product : products) {
            ProductVO productVO1=new ProductVO();

            Example example1=new Example(ProductStock.class);

            example1.createCriteria().andEqualTo("pNum",product.getPNum());
            ProductStock productStock = productStockMapper.selectOneByExample(example1);


            BeanUtils.copyProperties(product,productVO1);
            productVO1.setStock(productStock.getStock());
            productVOList.add(productVO1);
        }

        PageInfo<Product> pageInfo=new PageInfo<>(products);
        return new PageVO<>(pageInfo.getTotal(),productVOList);
    }
}
