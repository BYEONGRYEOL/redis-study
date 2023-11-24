package com.example.hanghaero.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hanghaero.dto.board.BoardRequestDto;
import com.example.hanghaero.dto.board.BoardResponseDto;
import com.example.hanghaero.entity.Board;
import com.example.hanghaero.repository.BoardRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository boardRepository;

	public BoardResponseDto createBoard(BoardRequestDto boardRequestDto) {
		Board board = new Board(boardRequestDto);
		boardRepository.save(board);
		return new BoardResponseDto(board);
	}

	@Transactional
	public BoardResponseDto updateBoard(BoardRequestDto boardRequestDto, Long id) {
		Board board = boardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재하지않는 보드입니다."));
		board.update(boardRequestDto);
		return new BoardResponseDto(board);

	}

	public void deleteBoard(Long id) {
		Board board = boardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재하지않는 보드입니다."));

		boardRepository.delete(board);
	}
}
