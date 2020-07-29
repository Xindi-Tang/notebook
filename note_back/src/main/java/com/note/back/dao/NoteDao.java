package com.note.back.dao;

import com.note.back.pojo.Category;
import com.note.back.pojo.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface NoteDao extends JpaRepository<Note,Integer> , JpaSpecificationExecutor<Note> {
    List<Note> findAllByCategory(Category category);
    Page<Note> findAll(Specification<Note> specification, Pageable pageable);
}
