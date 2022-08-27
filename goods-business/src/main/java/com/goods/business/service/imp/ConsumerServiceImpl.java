package com.goods.business.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goods.business.mapper.ConsumerMapper;
import com.goods.business.service.ConsumerService;
import com.goods.common.model.business.Consumer;
import com.goods.common.model.business.InStock;
import com.goods.common.model.business.Product;
import com.goods.common.vo.business.ConsumerVO;
import com.goods.common.vo.business.InStockVO;
import com.goods.common.vo.business.ProductVO;
import com.goods.common.vo.system.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author WangJin
 * @create 2022-08-26 14:18
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {


@Autowired
private ConsumerMapper consumerMapper;


    @Override
    public PageVO<ConsumerVO> findProductList(Integer pageNum, Integer pageSize, ConsumerVO consumerVO) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(Consumer.class);
        if (consumerVO.getAddress() != null && !"".equals(consumerVO.getAddress())) {
            example.createCriteria().andEqualTo("address", consumerVO.getAddress() );
        }

        if (consumerVO.getContact() != null && !"".equals(consumerVO.getContact())) {
            example.createCriteria().andEqualTo("contact",  consumerVO.getContact() );
        }

        if (consumerVO.getName() != null && !"".equals(consumerVO.getName() )) {
            example.createCriteria().andLike("name", "%"+ consumerVO.getName() +"%");
        }


        List<Consumer> consumers = consumerMapper.selectByExample(example);
        //转vo
        List<ConsumerVO> consumerVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(consumers)) {
            for (Consumer consumer : consumers) {


                ConsumerVO d = new ConsumerVO();


                BeanUtils.copyProperties(consumer, d);


                consumerVOS.add(d);
            }
        }
        PageInfo<Consumer> info = new PageInfo<>(consumers);
        return new PageVO<>(info.getTotal(), consumerVOS);

    }

    @Override
    public void add(ConsumerVO consumerVO) {
        Consumer consumer=new Consumer();

        BeanUtils.copyProperties(consumerVO, consumer);

        consumer.setCreateTime(new Date());
        consumer.setModifiedTime(new Date());
        consumerMapper.insert(consumer);
    }


    @Override
    public Consumer edit(Integer id) {
        Consumer consumer= consumerMapper.selectByPrimaryKey(id);
        return consumer;
    }


    @Override
    public void update(Integer id, ConsumerVO consumerVO) {

        Consumer consumer = consumerMapper.selectByPrimaryKey(id);

        BeanUtils.copyProperties(consumerVO, consumer);

        consumer.setModifiedTime(new Date());

        consumerMapper.updateByPrimaryKey(consumer);


    }

    @Override
    public void delete(Long id) {
        consumerMapper.deleteByPrimaryKey(id);
    }



}
