package com.example.hanghaero.dto.board;

import com.example.hanghaero.entity.Board;

import lombok.Getter;

@Getter
public class BoardResponseDto {

	private String name;

	public BoardResponseDto(Board board) {
		this.name = board.getName();
	}
}
