package com.goods.controller.business;

import com.github.pagehelper.IPage;
import com.github.pagehelper.Page;
import com.goods.business.service.ProductCategoryService;
import com.goods.common.model.business.Product;
import com.goods.common.model.business.ProductCategory;
import com.goods.common.response.ResponseBean;
import com.goods.common.vo.business.ProductCategoryTreeNodeVO;
import com.goods.common.vo.business.ProductCategoryVO;
import com.goods.common.vo.business.ProductVO;
import com.goods.common.vo.system.DepartmentVO;
import com.goods.common.vo.system.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-08-24 10:45
 */
@RequestMapping("/business/productCategory")
@RestController
public class ProductController {


    @Autowired
   private ProductCategoryService productCategoryService;



    @GetMapping("categoryTree")
    public ResponseBean findCategoryList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize") Integer pageSize,
                                       ProductCategoryTreeNodeVO productCategoryTreeNodeVO){


        PageVO<ProductCategoryTreeNodeVO> productCategoryServiceCategoryList = productCategoryService.findCategoryList(pageNum, pageSize, productCategoryTreeNodeVO);

        return ResponseBean.success(productCategoryServiceCategoryList);


    }

    @GetMapping("getParentCategoryTree")
    public ResponseBean getParentCategoryTree(){
       List<ProductCategoryTreeNodeVO> productCategoryTreeNodeVOS= productCategoryService.getParentCategoryTree();

       return ResponseBean.success(productCategoryTreeNodeVOS);
    }

    @PostMapping("add")
    public ResponseBean add(@RequestBody ProductCategoryVO productCategoryVO){
        productCategoryService.add(productCategoryVO);

        return ResponseBean.success();
    }

    @GetMapping("edit/{id}")
    public ResponseBean edit(@PathVariable Long id){

       ProductCategory productCategory= productCategoryService.edit(id);
        return ResponseBean.success(productCategory);
    }

    @PutMapping("update/{id}")
    public ResponseBean update(@PathVariable Long id,@RequestBody ProductCategoryVO productCategoryVO){
       productCategoryService.update(id,productCategoryVO);
        return ResponseBean.success();
    }

    @DeleteMapping("delete/{id}")
    public ResponseBean delete(@PathVariable Long id){
        productCategoryService.delete(id);

        return ResponseBean.success();
    }

}
