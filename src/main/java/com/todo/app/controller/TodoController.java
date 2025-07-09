package com.todo.app.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

	@PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public Todo add(@ModelAttribute Todo todo,
			@RequestParam(value = "photo", required = false) MultipartFile photoFile) {

		// 優先度文字列を数値に変換
		switch (todo.getPriorityStr()) {
		case "高":
			todo.setPriority(3);
			break;
		case "中":
			todo.setPriority(2);
			break;
		case "低":
			todo.setPriority(1);
			break;
		default:
			todo.setPriority(0);
		}

		// カテゴリ文字列をIDに変換
		switch (todo.getCategoryStr()) {
		case "仕事":
			todo.setCategoryId(1);
			break;
		case "私用":
			todo.setCategoryId(2);
			break;
		case "その他":
			todo.setCategoryId(3);
			break;
		default:
			todo.setCategoryId(0);
		}

		// ファイルアップロード処理（uploadsフォルダを作成）
		if (photoFile != null && !photoFile.isEmpty()) {
			try {
				String filename = UUID.randomUUID().toString() + "_" + photoFile.getOriginalFilename();

				File uploadDir = new File("uploads");
				if (!uploadDir.exists()) {
					uploadDir.mkdirs();
				}

				File dest = new File(uploadDir, filename);
				photoFile.transferTo(dest);

				todo.setPhoto(filename); // DBにファイル名だけ保存
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		todo.setDone_flg(0);

		todoMapper.add(todo); // DB登録

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
