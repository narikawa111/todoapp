$(function() {

	// 完了済みの個数取得・表示
	let doneCount = $('#donetodes').children("tr").length;
	$('#done_count').text(doneCount);

	// 更新処理
	$('.todo input').change(function() {
		const todo = $(this).closest('.todo');
		const id = todo.find('input[name="id"]').val();
		const title = todo.find('input[name="title"]').val();
		const timeLimit = todo.find('input[name="time_limit"]').val();
		const isDone = todo.find('input[name="done_flg"]').prop("checked");
		const doneFlg = isDone ? 1 : 0;

		const params = {
			id: id,
			title: title,
			time_limit: timeLimit,
			done_flg: doneFlg
		};

		$.post("/update", params);

		if ($(this).prop('name') == "done_flg") {
			if (isDone) {
				todo.appendTo('#donetodes');
				todo.find('input[name="title"]').css('text-decoration', 'line-through');
				todo.find('input[name="time_limit"]').hide();
				doneCount++;
			} else {
				todo.appendTo('#todes');
				todo.find('input[name="title"]').css('text-decoration', 'none');
				todo.find('input[name="time_limit"]').show();
				doneCount--;
			}
			$("#done_count").text(doneCount);
		}
	});

	// 完了済みタスク表示/非表示切り替え
	$('.button_for_show').click(function() {
		let showState = $('#done_table').css('display');
		if (showState == "none") {
			$('#done_table').show();
			$(this).css({ transform: 'rotate(225deg)', 'bottom': '-4px' });
		} else {
			$('#done_table').hide();
			$(this).css({ transform: 'rotate(45deg)', 'bottom': '4px' });
		}
	});
	// 追加処理
	$('#add').click(function(e) {
		e.preventDefault();

		const form = $('#add_form')[0];
		const formData = new FormData(form);
		$.ajax({
			url: "/add",
			type: "POST",
			data: formData,
			processData: false,
			contentType: false,
			success: function(json) {

				const clone = $('#todes tr:first').clone(true);
				clone.find('input[name="id"]').val(json.id);
				clone.find('input[name="title"]').val(json.title);
				clone.find('input[name="time_limit"]').val(json.time_limit);
				clone.find('input[name="done_flg"]').prop('checked', false);

				$('#todes').append(clone[0]);

				form.reset();

				$('#modal').modal('hide');
			},
			error: function(xhr, status, error) {
				alert("追加に失敗しました: " + error);
			}
		});
	});



	// 削除処理
	$('#delete').click(function() {
		$.post("/delete").done(function() {
			$('#donetodes').empty();
			$('#done_count').text(0);
		});
	});

});
