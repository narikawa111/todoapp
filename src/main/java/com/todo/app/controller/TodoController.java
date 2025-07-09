package com.todo.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todo.app.entity.Todo;
import com.todo.app.mapper.TodoMapper;

@Controller
public class TodoController {

    @Autowired
    private TodoMapper todoMapper;

    @RequestMapping(value = "/")
    public String index(Model model) {
        List<Todo> list = todoMapper.selectIncomplete();
        List<Todo> doneList = todoMapper.selectComplete();
        model.addAttribute("todos", list);
        model.addAttribute("doneTodos", doneList);
        return "index";
    }

    @PostMapping("/add")
    @ResponseBody
    public Todo add(@ModelAttribute Todo todo) {
        todo.setDone_flg(0);
        todoMapper.add(todo);
        return todo;
    }

    @PostMapping("/update")
    @ResponseBody
    public void update(@ModelAttribute Todo todo) {
        todoMapper.update(todo);
    }

    @PostMapping("/delete")
    @ResponseBody
    public void delete() {
        todoMapper.delete();
    }
}
