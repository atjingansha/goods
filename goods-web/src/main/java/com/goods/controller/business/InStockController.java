package com.goods.controller.business;

import com.goods.business.service.InStockService;
import com.goods.common.model.business.InStock;
import com.goods.common.response.ResponseBean;
import com.goods.common.vo.business.InStockDetailVO;
import com.goods.common.vo.business.InStockVO;
import com.goods.common.vo.business.ProductVO;
import com.goods.common.vo.system.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-08-25 11:11
 */
@RequestMapping("/business/inStock")
@RestController
public class InStockController {

//    business/inStock/findInStockList?pageNum=1&pageSize=10&status=0

    @Autowired
    private InStockService inStockService;

    @GetMapping("findInStockList")
    public ResponseBean findProductList(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        InStockVO inStockVO){

        PageVO<InStockVO> inStockVOPageVO= inStockService.findProductList(pageNum,pageSize,inStockVO);


        return ResponseBean.success(inStockVOPageVO);
    }

    @GetMapping("detail/{id}")
    public ResponseBean  detail(@PathVariable Integer id,@RequestParam Integer pageNum){
        InStockDetailVO inStockVOPageVO=  inStockService.getDetail(id,pageNum);

        return ResponseBean.success(inStockVOPageVO);
    }


    @PutMapping("remove/{id}")
    public ResponseBean remove(@PathVariable Integer id){
        inStockService.remove(id);

        return ResponseBean.success();
    }


    @PutMapping("back/{id}")
    public ResponseBean back(@PathVariable Integer id){
        inStockService.back(id);

        return ResponseBean.success();
    }


    @PostMapping("addIntoStock")
    public ResponseBean addIntoStock(@RequestBody InStockVO inStockVO){
       InStock inStock= inStockService.addIntoStock(inStockVO);

        return ResponseBean.success(inStock);
    }

    @PutMapping("publish/{id}")
    public ResponseBean publish(@PathVariable Integer id){
        inStockService.publish(id);

        return ResponseBean.success();
    }


}
