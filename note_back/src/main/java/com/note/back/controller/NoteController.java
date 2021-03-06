package com.note.back.controller;

import com.note.back.Response.Response;
import com.note.back.pojo.Category;
import com.note.back.pojo.Note;
import com.note.back.pojo.User;
import com.note.back.service.CategoryService;
import com.note.back.service.NoteService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class NoteController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    NoteService noteService;

    @CrossOrigin
    @GetMapping("/api/categories")
    @ResponseBody
    public List<Category> getCategoryList(){
        return categoryService.getAll();
    }

    @CrossOrigin
    @PostMapping("/api/category/update")
    @ResponseBody
    public Response updateCategoryInfo(@RequestBody Category requestCategory){
        Category category = categoryService.getById(requestCategory.getId());
        category.setName(requestCategory.getName());
        categoryService.updateCategory(category);
        return new Response(200,"成功",null);
    }

    @CrossOrigin
    @GetMapping("/api/category/delete/{id}")
    @ResponseBody
    public Response deleteCategory(@PathVariable("id") int id){
        categoryService.deleteById(id);
        return new Response(200,"成功",null);
    }

    @CrossOrigin
    @PostMapping("/api/category/add")
    @ResponseBody
    public Response addCategory(@RequestBody Category requestCategory){
        Category category = new Category();
        category.setName(requestCategory.getName());
        categoryService.updateCategory(category);
        return new Response(200,"成功",null);
    }


    @CrossOrigin
    @GetMapping("/api/notes")
    @ResponseBody
    public List<Note> getNoteList(){
        return noteService.getAll();
    }

    @CrossOrigin
    @GetMapping("/api/categories/{id}/notes")
    @ResponseBody
    public List<Note> getNoteByCategory(@PathVariable("id") int id){
        return noteService.getNotesByCategory(id);
    }

    @CrossOrigin
    @GetMapping("/api/categories/{id}/notes/length")
    @ResponseBody
    public int getNoteNumByCategory(@PathVariable("id") int id){
        return noteService.getNotesNumByCategory(id);
    }

    @CrossOrigin
    @GetMapping("/api/categories/{cid}/notes/{pid}/{pSize}")
    @ResponseBody
    public List<Note> getNoteByPage(@PathVariable("cid") Integer cid, @PathVariable("pid") int pid, @PathVariable("pSize") int pSize){
        System.out.println(cid);
        return noteService.getNotesByPage(pid,pSize,0,cid);
    }


    @CrossOrigin
    @PostMapping("/api/update/{type}/note/{id}")
    @ResponseBody
    public Response updateNoteInfo(@RequestBody Note requestNote, @PathVariable("id") int id, @PathVariable("type") String type){
        Note note = noteService.getById(requestNote.getId());
        if(type.equals("info")){
            note.setName(requestNote.getName());
            note.setAbs(requestNote.getAbs());
        }
        if(type.equals("content")){
            note.setContentHtml(requestNote.getContentHtml());
            note.setContentMd(requestNote.getContentMd());
        }
        note.setLastModifiedTime(new Timestamp(System.currentTimeMillis()));
        noteService.updateNote(note);
        return new Response(200,"成功",null);
    }

    @CrossOrigin
    @PostMapping("/api/update/category/{id}/note/add")
    @ResponseBody
    public Response addNote(@RequestBody Note requestNote,@PathVariable("id") int id){
        Note note = new Note();
        note.setName(requestNote.getName());
        note.setAbs(requestNote.getAbs());
        Category category = categoryService.getById(id);
        note.setCategory(category);
        note.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        note.setLastModifiedTime(new Timestamp(System.currentTimeMillis()));
        note.setAuthor(noteService.getById(1).getAuthor());
        noteService.updateNote(note);
        return new Response(200,"成功",null);
    }

    @CrossOrigin
    @GetMapping("/api/note/delete/{id}")
    @ResponseBody
    public Response deleteNote(@PathVariable("id") int id){
        noteService.deleteById(id);
        return new Response(200,"成功",null);
    }

    @CrossOrigin
    @GetMapping("/api/note/{id}")
    @ResponseBody
    public Response getNoteById(@PathVariable("id") int id){
        Note note=noteService.getById(id);
        return new Response(200,"成功",note);
    }
}
