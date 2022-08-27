package com.goods.controller.business;

import com.goods.business.service.InStockService;
import com.goods.business.service.OutStockService;
import com.goods.common.model.business.OutStock;
import com.goods.common.response.ResponseBean;
import com.goods.common.vo.business.InStockDetailVO;
import com.goods.common.vo.business.InStockVO;
import com.goods.common.vo.business.OutStockDetailVO;
import com.goods.common.vo.business.OutStockVO;
import com.goods.common.vo.system.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author WangJin
 * @create 2022-08-26 14:53
 */

@RequestMapping("/business/outStock")
@RestController
public class OutStockController {


    @Autowired
    private OutStockService outStockService;

    @GetMapping("findOutStockList")
    public ResponseBean findOutStockList(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        OutStockVO outStockVO){

        PageVO<OutStockVO> outStockVOPageVO= outStockService.findOutStockList(pageNum,pageSize,outStockVO);


        return ResponseBean.success(outStockVOPageVO);
    }


    @GetMapping("detail/{id}")
    public ResponseBean  detail(@PathVariable Integer id, @RequestParam Integer pageNum){
        OutStockDetailVO outStockDetailVO=  outStockService.getDetail(id,pageNum);

        return ResponseBean.success(outStockDetailVO);
    }


    @PutMapping("remove/{id}")
    public ResponseBean remove(@PathVariable Integer id){
        outStockService.remove(id);

        return ResponseBean.success();
    }


    @PutMapping("back/{id}")
    public ResponseBean back(@PathVariable Integer id){
        outStockService.back(id);

        return ResponseBean.success();
    }



}
