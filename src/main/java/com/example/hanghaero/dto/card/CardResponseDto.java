package com.example.hanghaero.dto.card;

import com.example.hanghaero.entity.Card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardResponseDto {
	private String name;

	public CardResponseDto(Card card) {
		this.name = card.getName();
	}
}
