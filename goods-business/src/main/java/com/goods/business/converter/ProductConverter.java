package com.goods.business.converter;

import com.goods.common.model.business.Product;
import com.goods.common.model.business.ProductCategory;
import com.goods.common.vo.business.ProductCategoryTreeNodeVO;
import com.goods.common.vo.system.MenuNodeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangJin
 * @create 2022-08-24 15:32
 */
public class ProductConverter {


    /**
     * 转成menuVO(只包含菜单)List
     * @param menus
     * @return
     */
    public static List<ProductCategoryTreeNodeVO> converterToMenuNodeVO(List<ProductCategory> menus){
        //先过滤出用户的菜单
        List<ProductCategoryTreeNodeVO> menuNodeVOS=new ArrayList<>();
        if(!CollectionUtils.isEmpty(menus)){
            for (ProductCategory menu : menus) {
                if(menu.getPid()==0){
                    ProductCategoryTreeNodeVO menuNodeVO = new ProductCategoryTreeNodeVO();
                    BeanUtils.copyProperties(menu,menuNodeVO);
                    menuNodeVOS.add(menuNodeVO);
                }
            }
        }
        return menuNodeVOS;
    }
}
