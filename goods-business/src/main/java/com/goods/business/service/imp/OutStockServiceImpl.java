package com.goods.business.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goods.business.mapper.ConsumerMapper;
import com.goods.business.mapper.OutStockInfoMapper;
import com.goods.business.mapper.OutStockMapper;
import com.goods.business.mapper.ProductMapper;
import com.goods.business.service.OutStockService;
import com.goods.common.model.business.*;
import com.goods.common.vo.business.*;
import com.goods.common.vo.system.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangJin
 * @create 2022-08-26 14:59
 */
@Service
public class OutStockServiceImpl implements OutStockService {

    @Autowired
    private OutStockMapper outStockMapper;

    @Autowired
    private OutStockInfoMapper outStockInfoMapper;

    @Autowired
    private ProductMapper productMapper;


    @Autowired
    private ConsumerMapper consumerMapper;
    @Override
    public PageVO<OutStockVO> findOutStockList(Integer pageNum, Integer pageSize, OutStockVO outStockVO) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(OutStock.class);
        if (outStockVO.getOutNum() != null && !"".equals(outStockVO.getOutNum())) {
            example.createCriteria().andEqualTo("outNum", outStockVO.getOutNum() );
        }

        if (outStockVO.getType() != null && !"".equals(outStockVO.getType())) {
            example.createCriteria().andEqualTo("type",  outStockVO.getType() );
        }

        if (outStockVO.getStatus() != null && !"".equals(outStockVO.getStatus())) {
            example.createCriteria().andEqualTo("status",  outStockVO.getStatus());
        }


        List<OutStock> outStocks = outStockMapper.selectByExample(example);
        //转vo
        List<OutStockVO> outStockVOS= new ArrayList<>();
        if (!CollectionUtils.isEmpty(outStocks)) {
            for (OutStock outStock : outStocks) {


                OutStockVO d = new OutStockVO();


                BeanUtils.copyProperties(outStock, d);



                outStockVOS.add(d);
            }
        }
        PageInfo<OutStock> info = new PageInfo<>(outStocks);
        return new PageVO<>(info.getTotal(), outStockVOS);

    }

    @Override
    public OutStockDetailVO getDetail(Integer id, Integer pageNum) {
        OutStockDetailVO outStockDetailVO=new OutStockDetailVO();

        OutStock outStock = outStockMapper.selectByPrimaryKey(id);

        Integer pageSize=1;

        outStockDetailVO.setStatus(outStock.getStatus());


        Example infoExample=new Example(OutStockInfo.class);
        infoExample.createCriteria().andEqualTo("outNum",outStock.getOutNum());

        List<OutStockInfo> stockInfoList = outStockInfoMapper.selectByExample(infoExample);

        List<OutStockItemVO> itemVOList=new ArrayList<>();
        for (OutStockInfo outStockInfo : stockInfoList) {


            Example proExample=new Example(Product.class);
            proExample.createCriteria().andEqualTo("pNum",outStockInfo.getPNum());
            Product product = productMapper.selectOneByExample(proExample);


            OutStockItemVO itemVO=new OutStockItemVO();
            itemVO.setName(product.getName());
            itemVO.setCount(outStockInfo.getProductNumber());
            itemVO.setImageUrl(product.getImageUrl());
            itemVO.setUnit(product.getUnit());
            itemVO.setPNum(product.getPNum());
            itemVO.setModel(product.getModel());

            itemVOList.add(itemVO);


        }
        outStockDetailVO.setTotal(itemVOList.size());
        outStockDetailVO.setItemVOS(itemVOList);

        Consumer consumer = consumerMapper.selectByPrimaryKey(outStock.getConsumerId());

        ConsumerVO consumerVO=new ConsumerVO();
        BeanUtils.copyProperties(consumer, consumerVO);

        outStockDetailVO.setConsumerVO(consumerVO);




        PageHelper.startPage(pageNum,pageSize);


        return  outStockDetailVO;
    }


    @Override
    public void remove(Integer id) {

        OutStock outStock = outStockMapper.selectByPrimaryKey(id);

        outStock.setStatus(1);

        outStockMapper.updateByPrimaryKey(outStock);
    }



    @Override
    public void back(Integer id) {
        OutStock outStock= outStockMapper.selectByPrimaryKey(id);

        outStock.setStatus(0);

        outStockMapper.updateByPrimaryKey(outStock);
    }

}
