package com.todo.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.todo.app.entity.Todo;

@Mapper
public interface TodoMapper {
	List<Todo> selectAll();

	List<Todo> selectIncomplete();

	List<Todo> selectComplete();

	void add(Todo todo);

	void update(Todo todo);

	void delete();
}
