package com.todo.app.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Todo {
	private long id;
	private String title;
	private int done_flg;
	private String time_limit;
	private String memo;
	private String photo;
	private String comment;
	private int priority;
	private int parentId;
	private int categoryId;

	private String priorityStr; // 追加用
	private String categoryStr; // 追加用
}
