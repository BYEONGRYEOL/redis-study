package com.example.hanghaero.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.hanghaero.dto.board.BoardRequestDto;
import com.example.hanghaero.dto.board.BoardResponseDto;
import com.example.hanghaero.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;

	@PostMapping("/boards")
	public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto boardRequestDto) {

		return new ResponseEntity<>(boardService.createBoard(boardRequestDto), HttpStatus.CREATED);
	}

	@PutMapping("/boards/{id}")
	public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long id,
		@RequestBody BoardRequestDto boardRequestDto) {
		return new ResponseEntity<>(boardService.updateBoard(boardRequestDto, id), HttpStatus.OK);
	}

	@DeleteMapping("/boards/{id}")
	public ResponseEntity deleteBoard(@PathVariable Long id) {
		boardService.deleteBoard(id);
		return ResponseEntity.status(HttpStatus.OK).body("보드가 삭제되었습니다.");
	}

}
