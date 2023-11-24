package com.example.hanghaero.entity;

import com.example.hanghaero.dto.card.CardRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "card")
@NoArgsConstructor
public class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private int position;

	@ManyToOne
	@JoinColumn(name = "column_id")
	private Column column;

	public Card(CardRequestDto requestDto, Board board, Column column) {
		this.name = requestDto.getName();
		this.column = column;
	}

	public void update(CardRequestDto requestDto) {
		this.name = requestDto.getName();
	}

	public void move(Column column) {
		this.column = column;
	}
}
