package com.taotao.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageRowBounds;
import com.taotao.pojo.BasePojo;
import com.taotao.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 小浪浪
 * 封装常用Service方法
 * @version 1.0
 * @date 2019-08-01 9:09
 */
public abstract class BaseService<T extends BasePojo> {

    @Autowired
    private Mapper<T> mapper;
    
    /**
     * 功能:<按id查询>
     * @author
     * @date 2019-08-01 09:25:00 
     * @param id
     * @return {@link T}
     **/
    public T queryById(Long id){
        return this.mapper.selectByPrimaryKey(id);
    }

    /**
     * 功能:<查询所有>
     * @author
     * @date 2019-08-01 09:25:22
     * @return {@link List<T>}
     **/
    public List<T> queryAll(){
        return this.mapper.select(null);
    }

    /**
     * 功能:<根据条件查询一条数据>
     * @author
     * @date 2019-08-01 09:25:37
     * @param record
     * @return {@link T}
     **/
    public T queryOne(T record){
        return this.mapper.selectOne(record);
    }

    /**
     * 功能:<根据条件查询数据列表>
     * @author
     * @date 2019-08-01 09:26:59
     * @param record
     * @return {@link List<T>}
     **/
    public List<T> queryListByWhere(T record){
        return this.mapper.select(record);
    }

    /**
     * 功能:<分页查询数据列表>
     * @author
     * @date 2019-08-01 09:30:55
     * @param page
     * @param rows
     * @param record
     * @return {@link PageInfo<T>}
     **/
    public PageInfo<T> queryPageListByWhere(Integer page,Integer rows,T record){
        PageHelper.startPage(page,rows);
        List<T> list=this.mapper.select(record);
        return new PageInfo<T>(list);
    }
    
    /**
     * 功能:<新增>
     * @author 
     * @date 2019-08-01 10:05:44 
     * @param t
     * @return {@link Integer}
     **/
    public Integer save(T t){
        t.setCreated(new Date());
        t.setUpdated(t.getCreated());
        return this.mapper.insert(t);
    }
    
    /**
     * 功能:<选择不为null的字段插入>
     * @author
     * @date 2019-08-01 10:06:53 
     * @param t
     * @return {@link Integer}
     **/
    public Integer saveService(T t){
        t.setCreated(new Date());
        t.setUpdated(t.getCreated());

        return this.mapper.insertSelective(t);
    }

    /**
     * 功能:<更新>
     * @author
     * @date 2019-08-01 10:05:44
     * @param t
     * @return {@link Integer}
     **/
    public Integer update(T t){
        t.setUpdated(new Date());
        return this.mapper.updateByPrimaryKey(t);
    }

    /**
     * 功能:<选择不为null的字段更新>
     * @author
     * @date 2019-08-01 10:06:53
     * @param t
     * @return {@link Integer}
     **/
    public Integer updateService(T t){
        t.setUpdated(new Date());
        return this.mapper.updateByPrimaryKeySelective(t);
    }
    
    /**
     * 功能:<根据id删除>
     * @author 
     * @date 2019-08-01 10:11:11 
     * @param id
     * @return {@link Integer}
     **/
    public Integer deleteById(Long id){
        return  this.mapper.deleteByPrimaryKey(id);
    }

    /**
     * 功能:<根据id批量删除>
     * @author
     * @date 2019-08-01 10:16:26
     * @param clazz
     * @param property
     * @param values
     * @return {@link Integer}
     **/
    public Integer deleteByIds(Class clazz,String property,List<Object> values){
        Example example=new Example(clazz);
        example.createCriteria().andIn(property,values);
        return this.mapper.deleteByExample(example);
    }

    /**
     * 功能:<根据条件删除>
     * @author 
     * @date 2019-08-01 10:18:44 
     * @param record
     * @return {@link Integer}
     **/
    public Integer deleteByWhere(T record){
        return this.mapper.delete(record);
    }

}