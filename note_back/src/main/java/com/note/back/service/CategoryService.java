package com.note.back.service;

import com.note.back.dao.CategoryDao;
import com.note.back.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryDao categaryDao;

    public List<Category> getAll(){
        return categaryDao.findAll(Sort.by(Sort.Direction.DESC,"id"));
    }

    public void deleteById(int id){
        categaryDao.deleteById(id);
    }
}
