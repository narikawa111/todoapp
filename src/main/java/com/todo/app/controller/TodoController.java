package com.todo.app.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.todo.app.entity.Todo;
import com.todo.app.mapper.TodoMapper;

@Controller
public class TodoController {

	@Autowired
	private TodoMapper todoMapper;

	@RequestMapping("/")
	public String index(Model model) {
		List<Todo> incompleteList = todoMapper.selectIncomplete();
		List<Todo> completeList = todoMapper.selectComplete();

		model.addAttribute("todos", incompleteList);
		model.addAttribute("doneTodos", completeList);
		return "index";
	}

	@PostMapping("/add")
	@ResponseBody
	public Todo add(@ModelAttribute Todo todo,
			@RequestParam(value = "photo", required = false) MultipartFile photoFile) {

		
		try {
			todo.setPriority(Integer.parseInt(todo.getPriorityStr()));
		} catch (NumberFormatException e) {
			todo.setPriority(0); 
		}

		
		try {
			todo.setCategoryId(Integer.parseInt(todo.getCategoryStr()));
		} catch (NumberFormatException e) {
			todo.setCategoryId(0); 
		}

		// ファイルアップロード処理
		if (photoFile != null && !photoFile.isEmpty()) {
			try {
				String filename = UUID.randomUUID().toString() + "_" + photoFile.getOriginalFilename();

				File uploadDir = new File("uploads");
				if (!uploadDir.exists()) {
					uploadDir.mkdirs();
				}

				File dest = new File(uploadDir, filename);
				photoFile.transferTo(dest);

				todo.setPhoto(filename); // DBにはファイル名のみ保存
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 初期状態では「未完了」
		todo.setDone_flg(0);

		// DB登録
		todoMapper.add(todo);

		return todo;
	}

	// Todo更新
	@PostMapping("/update")
	@ResponseBody
	public void update(@ModelAttribute Todo todo) {
		todoMapper.update(todo);
	}

	// 完了済みTodo削除
	@PostMapping("/delete")
	@ResponseBody
	public void delete() {
		todoMapper.delete();
	}
}
