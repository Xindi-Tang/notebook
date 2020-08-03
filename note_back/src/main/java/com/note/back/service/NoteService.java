package com.note.back.service;


import com.note.back.dao.CategoryDao;
import com.note.back.dao.NoteDao;
import com.note.back.pojo.Category;
import com.note.back.pojo.Note;
import jdk.nashorn.internal.runtime.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;
import java.util.*;

import java.util.List;

@Service
public class NoteService {
    @Autowired
    NoteDao noteDao;
    @Autowired
    CategoryDao categoryDao;

    public List<Note> getAll(){
        return noteDao.findAll(Sort.by(Sort.Direction.DESC,"id"));
    }

    public List<Note> getNotesByCategory(int id){
        Category category = categoryDao.getOne(id);
        return noteDao.findAllByCategory(category);
    }

    public Note getById(int id){
        return noteDao.findById(id).get();
    }

    public void updateNote(Note note){
        noteDao.save(note);
    }

    public void deleteById(int id) {
        noteDao.deleteById(id);
    }

    /**
     * 获取分页结果集
     *
     * @param pageNo
     * @param pageSize
     * @param userId
     * @param categoryId
     * @return
     */
    public List<Note> getNotesByPage(int pageNo, int pageSize, Integer userId, Integer categoryId) {
        List<Note> result = null;
        System.out.println("inMostOut");
        System.out.println(userId.toString()+categoryId.toString());

        // 构造自定义查询条件
        Specification<Note> queryCondition = new Specification<Note>() {
            @Override
            public Predicate toPredicate(Root<Note> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                System.out.println("inP");
                if (userId != null) {
                    System.out.println("inU");
                    predicateList.add(criteriaBuilder.equal(root.get("author"), 4));
                }
                if (categoryId != null) {
                    System.out.println("in");
                    predicateList.add(criteriaBuilder.equal(root.get("category"), categoryId));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

        // 分页和不分页，这里按起始页和每页展示条数为0时默认为不分页，分页的话按创建时间降序
        try {
            if (pageNo == 0 && pageSize == 0) {
                System.out.println("in");
//                result = this.getNotesByCategory(categoryId.intValue());
            } else {
                System.out.println("in");
                result = noteDao.findAll(queryCondition, PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdTime"))).getContent();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return result;
    }

}
