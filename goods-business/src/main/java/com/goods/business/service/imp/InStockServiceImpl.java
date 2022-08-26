package com.goods.business.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goods.business.mapper.*;
import com.goods.business.service.InStockService;
import com.goods.common.model.business.*;
import com.goods.common.vo.business.*;
import com.goods.common.vo.system.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WangJin
 * @create 2022-08-25 11:17
 */
@Service
public class InStockServiceImpl implements InStockService {

    @Autowired
    private InStockMapper inStockMapper;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private InStockInfoMapper inStockInfoMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductStockMapper productStockMapper;

    @Override
    public PageVO<InStockVO> findProductList(Integer pageNum, Integer pageSize, InStockVO inStockVO) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(InStock.class);
        if (inStockVO.getInNum() != null && !"".equals(inStockVO.getInNum())) {
            example.createCriteria().andEqualTo("inNum", inStockVO.getInNum() );
        }

        if (inStockVO.getType() != null && !"".equals(inStockVO.getType())) {
            example.createCriteria().andEqualTo("type",  inStockVO.getType() );
        }

        if (inStockVO.getCreateTime() != null && !"".equals(inStockVO.getCreateTime())) {
            example.createCriteria().andLike("createTime", "%"+ inStockVO.getCreateTime()+"%");
        }
        if (inStockVO.getModified() != null && !"".equals(inStockVO.getModified())) {
            example.createCriteria().andLike("modifiedTime", "%"+ inStockVO.getCreateTime()+"%");
        }

        if (inStockVO.getStatus() != null && !"".equals(inStockVO.getStatus())) {
            example.createCriteria().andEqualTo("status",  inStockVO.getStatus());
        }


        List<InStock> inStocks = inStockMapper.selectByExample(example);
        //转vo
        List<InStockVO> inStockVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(inStocks)) {
            for (InStock inStock : inStocks) {

                Long supplierId = inStock.getSupplierId();
                InStockVO d = new InStockVO();
                Supplier supplier = supplierMapper.selectByPrimaryKey(supplierId);

                BeanUtils.copyProperties(inStock, d);


                d.setPhone(supplier.getPhone());
                d.setSupplierName(supplier.getName());
                inStockVOS.add(d);
            }
        }
        PageInfo<InStock> info = new PageInfo<>(inStocks);
        return new PageVO<>(info.getTotal(), inStockVOS);

    }



    @Override
    public InStockDetailVO getDetail(Integer id, Integer pageNum) {
        InStockDetailVO inStockDetailVO=new InStockDetailVO();

        InStock inStock = inStockMapper.selectByPrimaryKey(id);

        Integer pageSize=1;

        inStockDetailVO.setStatus(inStock.getStatus());


        Example infoExample=new Example(InStockInfo.class);
        infoExample.createCriteria().andEqualTo("inNum",inStock.getInNum());

        List<InStockInfo> stockInfoList = inStockInfoMapper.selectByExample(infoExample);

        List<InStockItemVO> itemVOList=new ArrayList<>();
        for (InStockInfo inStockInfo : stockInfoList) {


            Example proExample=new Example(Product.class);
            proExample.createCriteria().andEqualTo("pNum",inStockInfo.getPNum());
           Product product = productMapper.selectOneByExample(proExample);


                InStockItemVO itemVO=new InStockItemVO();
                itemVO.setName(product.getName());
                itemVO.setCount(inStockInfo.getProductNumber());
                itemVO.setImageUrl(product.getImageUrl());
                itemVO.setUnit(product.getUnit());
                itemVO.setPNum(product.getPNum());
                itemVO.setModel(product.getModel());

                itemVOList.add(itemVO);


        }
        inStockDetailVO.setTotal(itemVOList.size());
        inStockDetailVO.setItemVOS(itemVOList);

        Supplier supplier = supplierMapper.selectByPrimaryKey(inStock.getSupplierId());

        SupplierVO supplierVO=new SupplierVO();
        BeanUtils.copyProperties(supplier, supplierVO);

        inStockDetailVO.setSupplierVO(supplierVO);




        PageHelper.startPage(pageNum,pageSize);


        return  inStockDetailVO;
    }

    @Override
    public void remove(Integer id) {

        InStock inStock = inStockMapper.selectByPrimaryKey(id);

        inStock.setStatus(1);

        inStockMapper.updateByPrimaryKey(inStock);
    }



    @Override
    public void back(Integer id) {
        InStock inStock = inStockMapper.selectByPrimaryKey(id);

        inStock.setStatus(0);

        inStockMapper.updateByPrimaryKey(inStock);
    }


    @Override
    public InStock addIntoStock(InStockVO inStockVO) {
        InStock inStock=new InStock();
        BeanUtils.copyProperties(inStockVO,inStock);

        //判断
        if (inStockVO.getSupplierId()==null){
            Supplier supplier=new Supplier();

            supplier.setName(inStockVO.getName());
            supplier.setEmail(inStockVO.getEmail());
            supplier.setAddress(inStockVO.getAddress());
            supplier.setSort(inStockVO.getSort());
            supplier.setPhone(inStockVO.getPhone());
            supplier.setModifiedTime(new Date());
            supplier.setCreateTime(new Date());
            supplier.setContact(inStockVO.getContact());
            supplierMapper.insert(supplier);


            Example example=new Example(Supplier.class);
            example.createCriteria().andEqualTo("phone",inStockVO.getPhone());

            List<Supplier> suppliers = supplierMapper.selectByExample(example);

            for (Supplier supplier1 : suppliers) {
                inStock.setSupplierId(supplier1.getId());
            }

        }


        List<Object> products = inStockVO.getProducts();

        LinkedHashMap map= (LinkedHashMap) products.get(0);

        String uuid = UUID.randomUUID().toString();

        uuid.replaceAll("-","");



        Object productNumber = map.get("productNumber");

        Object productId = map.get("productId");

        Product product = productMapper.selectByPrimaryKey(productId);

        InStockInfo inStockInfo=new InStockInfo();
        inStockInfo.setCreateTime(new Date());
        inStockInfo.setModifiedTime(new Date());
        inStockInfo.setPNum(product.getPNum());
        inStockInfo.setProductNumber((Integer) productNumber);
        inStockInfo.setInNum(uuid);
        inStockInfoMapper.insert(inStockInfo);


        inStock.setProductNumber((Integer) productNumber);


        ProductStock productStock=new ProductStock();

        productStock.setStock((Long) productNumber);
        productStock.setPNum(product.getPNum());
        productStockMapper.insert(productStock);

        inStock.setInNum(uuid);
        inStock.setStatus(2);
        inStock.setCreateTime(new Date());
        inStock.setModified(new Date());
        inStock.setOperator("admin");
        inStock.setSupplierId(inStockVO.getSupplierId());



        inStockMapper.insert(inStock);


        return inStock;
    }

    @Override
    public void publish(Integer id) {
        InStock inStock = inStockMapper.selectByPrimaryKey(id);

        inStock.setStatus(0);

        inStock.setModified(new Date());
        inStockMapper.updateByPrimaryKey(inStock);
    }

    @Override
    public PageVO<InStockVO> findAllStocks(Integer pageNum, Integer pageSize) {


        List<InStock> inStocks = inStockMapper.selectAll();

        List<InStockVO> inStockVOS=new ArrayList<>();
        for (InStock inStock : inStocks) {
            InStockVO inStockVO=new InStockVO();

            inStockVO.setProductNumber(inStock.getProductNumber());
            inStockVOS.add(inStockVO);
        }

        PageInfo pageInfo=new PageInfo(inStocks);

        return  new PageVO<>(pageInfo.getTotal(),inStockVOS);

    }
}
