package com.goods.controller.business;

import com.goods.business.service.SupplierService;
import com.goods.common.model.business.ProductCategory;
import com.goods.common.model.business.Supplier;
import com.goods.common.response.ResponseBean;
import com.goods.common.vo.business.ProductCategoryTreeNodeVO;
import com.goods.common.vo.business.ProductCategoryVO;
import com.goods.common.vo.business.SupplierVO;
import com.goods.common.vo.system.DepartmentVO;
import com.goods.common.vo.system.PageVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WangJin
 * @create 2022-08-24 17:54
 */
@RestController
@RequestMapping("business/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;


    @GetMapping("/findSupplierList")
    public ResponseBean<PageVO<SupplierVO>> findSupplierList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                             @RequestParam(value = "pageSize") Integer pageSize,
                                                             SupplierVO supplierVO) {
        PageVO<SupplierVO> departmentsList = supplierService.findSupplierList(pageNum, pageSize, supplierVO);
        return ResponseBean.success(departmentsList);
    }


    @PostMapping("add")
    public ResponseBean add(@RequestBody SupplierVO supplierVO) {
        supplierService.add(supplierVO);
        return ResponseBean.success();

    }


    @GetMapping("edit/{id}")
    public ResponseBean edit(@PathVariable Long id) {

        Supplier supplier = supplierService.edit(id);
        return ResponseBean.success(supplier);
    }

    @PutMapping("update/{id}")
    public ResponseBean update(@PathVariable Long id, @RequestBody  SupplierVO supplierVO) {
        supplierService.update(id, supplierVO);
        return ResponseBean.success();

    }



    @DeleteMapping("delete/{id}")
    public ResponseBean delete(@PathVariable Long id){
        supplierService.delete(id);

        return ResponseBean.success();
    }

    @GetMapping("findAll")
    public ResponseBean findAll() {

        List<Supplier> supplier = supplierService.findAll();
        return ResponseBean.success(supplier);
    }


}
