package com.example.hanghaero.entity;

import com.example.hanghaero.dto.board.BoardRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "boards")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	public Board(BoardRequestDto boardRequestDto) {
		this.name = boardRequestDto.getName();
	}

	public void update(BoardRequestDto boardRequestDto) {
		this.name = boardRequestDto.getName();
	}
}
