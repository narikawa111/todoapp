package com.todo.app.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class Todo {

	private Integer id;
	private String title;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate time_limit;

	private Integer done_flg;
	private String memo;
	private String photo;

	private Integer priority; // DB保存用（1〜3）
	private Integer categoryId; // DB保存用（1〜3）

	private String priorityStr;
	private String categoryStr;

	//親子関係追加
	private Integer parentId;
	private List<Todo> children = new ArrayList<>();

	//getter/setter
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getTime_limit() {
		return time_limit;
	}

	public void setTime_limit(LocalDate time_limit) {
		this.time_limit = time_limit;
	}

	public Integer getDone_flg() {
		return done_flg;
	}

	public void setDone_flg(Integer done_flg) {
		this.done_flg = done_flg;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getPriorityStr() {
		return priorityStr;
	}

	public void setPriorityStr(String priorityStr) {
		this.priorityStr = priorityStr;
	}

	public String getCategoryStr() {
		return categoryStr;
	}

	public void setCategoryStr(String categoryStr) {
		this.categoryStr = categoryStr;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public List<Todo> getChildren() {
		return children;
	}

	public void setChildren(List<Todo> children) {
		this.children = children;
	}
}
