package com.example.hanghaero.dto.column;

import com.example.hanghaero.entity.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColResponseDto {
	private String title;
	private int position;
	private Long boardId;

	public ColResponseDto(Column columns) {
		this.title = columns.getTitle();
		this.position = columns.getPosition();
		this.boardId = columns.getBoard().getId();
	}
}
