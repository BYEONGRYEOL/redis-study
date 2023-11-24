package com.example.hanghaero.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.hanghaero.dto.card.CardRequestDto;
import com.example.hanghaero.service.CardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CardController {
	private final CardService cardService;

	// 카드 생성
	@PostMapping("/boards/{boardId}/columns/{columnId}/cards")
	public ResponseEntity<?> createCard(@PathVariable Long boardId, @PathVariable Long columnId,
		@RequestBody CardRequestDto requestDto) {
		return cardService.createCard(boardId, columnId, requestDto);
	}

	// 카드 수정
	@PutMapping("/cards/{cardId}")
	public ResponseEntity<?> updateCard(@PathVariable Long cardId, @RequestBody CardRequestDto requestDto) {
		return cardService.updateCard(cardId, requestDto);
	}

	// 카드 이동
	@PutMapping("/cards/{cardId}/move/{toColumnId}")
	public ResponseEntity<?> moveCard(@PathVariable Long cardId, @PathVariable Long toColumnId) {
		return cardService.moveCard(cardId, toColumnId);
	}

	// 카드 삭제
	@DeleteMapping("/cards/{cardId}")
	public ResponseEntity<String> deleteCard(@PathVariable Long cardId) {
		return cardService.deleteCard(cardId);
	}
}
