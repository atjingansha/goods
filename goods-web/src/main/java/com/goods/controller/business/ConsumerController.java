package com.goods.controller.business;

import com.goods.business.service.ConsumerService;
import com.goods.common.model.business.Consumer;
import com.goods.common.model.business.Product;
import com.goods.common.response.ResponseBean;
import com.goods.common.vo.business.ConsumerVO;
import com.goods.common.vo.business.InStockVO;
import com.goods.common.vo.business.ProductVO;
import com.goods.common.vo.system.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author WangJin
 * @create 2022-08-26 14:17
 */

@RestController
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/business/consumer/findConsumerList")
    public ResponseBean findConsumerList(@RequestParam Integer pageNum,
                                         @RequestParam Integer pageSize,
                                         ConsumerVO  consumerVO){


        PageVO<ConsumerVO> inStockVOPageVO= consumerService.findProductList(pageNum,pageSize,consumerVO);


        return ResponseBean.success(inStockVOPageVO);
    }


    @PostMapping("consumer/add")
    public ResponseBean add(@RequestBody ConsumerVO consumerVO) {
        consumerService.add(consumerVO);
        return ResponseBean.success();

    }


    @GetMapping("business/consumer/edit/{id}")
    public ResponseBean edit(@PathVariable Integer id) {

        Consumer consumer = consumerService.edit(id);
        return ResponseBean.success(consumer);
    }


    @PutMapping("business/consumer/update/{id}")
    public ResponseBean update(@PathVariable Integer id, @RequestBody ConsumerVO consumerVO) {
        consumerService.update(id, consumerVO);
        return ResponseBean.success();

    }


    @DeleteMapping("business/consumer/delete/{id}")
    public ResponseBean delete(@PathVariable Long id){
        consumerService.delete(id);

        return ResponseBean.success();
    }


}
