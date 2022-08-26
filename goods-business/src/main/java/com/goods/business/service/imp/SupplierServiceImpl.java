package com.goods.business.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goods.business.mapper.SupplierMapper;
import com.goods.business.service.SupplierService;
import com.goods.common.enums.system.UserTypeEnum;
import com.goods.common.model.business.ProductCategory;
import com.goods.common.model.business.Supplier;
import com.goods.common.model.system.Department;
import com.goods.common.vo.business.ProductCategoryVO;
import com.goods.common.vo.business.SupplierVO;
import com.goods.common.vo.system.DepartmentVO;
import com.goods.common.vo.system.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author WangJin
 * @create 2022-08-24 18:10
 */
@Service
public class SupplierServiceImpl implements SupplierService {



    @Autowired
  private   SupplierMapper supplierMapper;

    @Override
    public PageVO<SupplierVO> findSupplierList(Integer pageNum, Integer pageSize, SupplierVO supplierVO) {
        PageHelper.startPage(pageNum, pageSize);
        Example o = new Example(Department.class);
        if (supplierVO.getName() != null && !"".equals(supplierVO.getName())) {
            o.createCriteria().andLike("name", "%" + supplierVO.getName() + "%");
        }

        if (supplierVO.getContact() != null && !"".equals(supplierVO.getContact())) {
            o.createCriteria().andLike("contact", "%" + supplierVO.getContact() + "%");
        }
        if (supplierVO.getAddress() != null && !"".equals(supplierVO.getAddress())) {
            o.createCriteria().andLike("address", "%" + supplierVO.getAddress() + "%");
        }
        List<Supplier> suppliers = supplierMapper.selectByExample(o);
        //转vo
        List<SupplierVO> supplierVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(suppliers)) {
            for (Supplier supplier : suppliers) {
                SupplierVO d = new SupplierVO();
                BeanUtils.copyProperties(supplier, d);

                supplierVOS.add(d);
            }
        }
        PageInfo<Supplier> info = new PageInfo<>(suppliers);
        return new PageVO<>(info.getTotal(), supplierVOS);
    }



    @Override
    public void add(SupplierVO supplierVO) {

        Supplier supplier= new Supplier();
        BeanUtils.copyProperties(supplierVO, supplier);

        supplier.setCreateTime(new Date());
        supplier.setModifiedTime(new Date());
        supplierMapper.insert(supplier);
    }



    @Override
    public Supplier edit(Long id) {
        Supplier supplier = supplierMapper.selectByPrimaryKey(id);
        return supplier;
    }




    @Override
    public void update(Long id, SupplierVO supplierVO) {

        Supplier supplier = supplierMapper.selectByPrimaryKey(id);



        supplier.setId(supplierVO.getId());
        supplier.setName(supplierVO.getName());

        supplier.setSort(supplierVO.getSort());
        supplier.setAddress(supplierVO.getAddress());
        supplier.setContact(supplier.getContact());
        supplier.setEmail(supplier.getEmail());
        supplier.setPhone(supplier.getPhone());
        supplier.setModifiedTime(new Date());

        supplierMapper.updateByPrimaryKey(supplier);

    }

    @Override
    public void delete(Long id) {
        supplierMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Supplier>  findAll() {

        List<Supplier> suppliers = supplierMapper.selectAll();
        return suppliers;
    }
}
