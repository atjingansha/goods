package com.goods.controller.business;

import com.goods.business.mapper.InStockInfoMapper;
import com.goods.business.service.*;
import com.goods.common.model.business.InStock;
import com.goods.common.model.business.InStockInfo;
import com.goods.common.model.business.Product;
import com.goods.common.response.ResponseBean;
import com.goods.common.vo.business.*;
import com.goods.common.vo.system.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author WangJin
 * @create 2022-08-25 8:32
 */
@RequestMapping("/business/product")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

@Autowired
private ProductStockService productStockService;

@Autowired
private InStockService inStockService;

    @GetMapping("findProductList")
    public ResponseBean findProductList(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                       ProductVO productVO){

        PageVO<ProductVO> productVOPageVO= productService.findProductList(pageNum,pageSize,productVO);


        return ResponseBean.success(productVOPageVO);
    }


    @PostMapping("add")
    public ResponseBean add(@RequestBody ProductVO productVO) {
        productService.add(productVO);
        return ResponseBean.success();

    }


    @GetMapping("edit/{id}")
    public ResponseBean edit(@PathVariable Integer id) {

        Product product = productService.edit(id);
        return ResponseBean.success(product);
    }

    @PutMapping("update/{id}")
    public ResponseBean update(@PathVariable Integer id, @RequestBody ProductVO productVO) {
        productService.update(id, productVO);
        return ResponseBean.success();

    }

    @PutMapping("remove/{id}")
    public ResponseBean remove(@PathVariable Integer id){
        productService.remove(id);

        return ResponseBean.success();
    }


    @PutMapping("back/{id}")
    public ResponseBean back(@PathVariable Integer id){
        productService.back(id);

        return ResponseBean.success();
    }



    @PutMapping("publish/{id}")
    public ResponseBean publish(@PathVariable Integer id){
        productService.publish(id);

        return ResponseBean.success();
    }

    @GetMapping("findProducts")
    public ResponseBean findProducts(@RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "1") Integer pageSize,
                                     ProductVO productVO){
        PageVO<ProductVO> productVOPageVO= productService.findProducts(pageNum,pageSize,productVO);

        return ResponseBean.success(productVOPageVO);
    }


    @GetMapping("findProductStocks")
    public ResponseBean findProductStocks(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "1") Integer pageSize,
                                      ProductVO productVO){

        PageVO<ProductVO>  pageVO=   productService.findProductStocks(pageNum,pageSize,productVO);

        return ResponseBean.success(pageVO);
    }

    @GetMapping("findAllStocks")
    public ResponseBean findAllStocks(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "1") Integer pageSize){

        PageVO<InStockVO>  pageVO=   inStockService.findAllStocks(pageNum,pageSize);

        return ResponseBean.success(pageVO);
    }

}
