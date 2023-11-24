package com.example.hanghaero.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hanghaero.dto.card.CardRequestDto;
import com.example.hanghaero.dto.card.CardResponseDto;
import com.example.hanghaero.entity.Board;
import com.example.hanghaero.entity.Card;
import com.example.hanghaero.entity.Column;
import com.example.hanghaero.repository.BoardRepository;
import com.example.hanghaero.repository.CardRepository;
import com.example.hanghaero.repository.ColumnRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardService {
	private final CardRepository cardRepository;
	private final BoardRepository boardRepository;
	private final ColumnRepository colRepository;

	public ResponseEntity<?> createCard(Long boardId, Long columnId, CardRequestDto requestDto) {
		Board board = boardRepository.findById(boardId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 보드"));
		Column column = colRepository.findById(columnId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 컬럼"));

		Card card = new Card(requestDto, board, column);
		cardRepository.save(card);
		return ResponseEntity.ok().body(new CardResponseDto(card));
	}

	@Transactional
	public ResponseEntity<?> updateCard(Long cardId, CardRequestDto requestDto) {
		Card card = cardRepository.findById(cardId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 카드"));

		card.update(requestDto);
		cardRepository.save(card);

		return ResponseEntity.ok().body(new CardResponseDto(card));
	}

	@Transactional
	public ResponseEntity<?> moveCard(Long cardId, Long toColumnId) {
		Card card = cardRepository.findById(cardId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 카드"));
		Column column = colRepository.findById(toColumnId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 컬럼"));

		card.move(column);
		cardRepository.save(card);

		return ResponseEntity.ok().body(new CardResponseDto(card));
	}

	@Transactional
	public ResponseEntity<String> deleteCard(Long cardId) {
		Card card = cardRepository.findById(cardId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 카드"));

		cardRepository.delete(card);

		return ResponseEntity.ok("카드가 삭제되었습니다.");
	}

}