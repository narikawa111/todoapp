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

	//受取用
	private String priorityStr;
	private String categoryStr;
	
}