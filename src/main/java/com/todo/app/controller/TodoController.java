package com.todo.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    private TodoMapper todoMapper;

    // カテゴリ文字列→ID変換（サンプル）
    private int categoryNameToId(String categoryStr) {
        switch(categoryStr) {
            case "仕事": return 1;
            case "私用": return 2;
            case "その他": return 3;
            default: return 0;
        }
    }

    // 優先度文字列→数字変換（サンプル）
    private int priorityStrToInt(String priorityStr) {
        switch(priorityStr) {
            case "高": return 1;
            case "中": return 2;
            case "低": return 3;
            default: return 0;
        }
    }

    @RequestMapping(value = "/")
    public String index(Model model) {
        logger.info("アクセス: /");

        List<Todo> list = todoMapper.selectIncomplete();
        List<Todo> doneList = todoMapper.selectComplete();

        model.addAttribute("todos", list);
        model.addAttribute("doneTodos", doneList);

        return "index";
    }

    @PostMapping(value = "/add")
    @ResponseBody
    public Todo add(@ModelAttribute Todo todo) {
        logger.info("アクセス: /add");
        logger.debug("追加するタスク内容(変換前): {}", todo);

        // 文字列→数字へ変換
        todo.setPriority(priorityStrToInt(todo.getPriorityStr()));
        todo.setCategoryId(categoryNameToId(todo.getCategoryStr()));

        todoMapper.add(todo);
        logger.debug("追加したタスクID: {}", todo.getId());
        return todo;
    }

    @PostMapping(value = "/update")
    @ResponseBody
    public void update(@ModelAttribute Todo todo) {
        logger.info("アクセス: /update");
        // 変換処理
        todo.setPriority(priorityStrToInt(todo.getPriorityStr()));
        todo.setCategoryId(categoryNameToId(todo.getCategoryStr()));

        todoMapper.update(todo);
    }

    @PostMapping(value = "/delete")
    @ResponseBody
    public void delete() {
        logger.info("アクセス: /delete");
        todoMapper.delete();
    }
}
