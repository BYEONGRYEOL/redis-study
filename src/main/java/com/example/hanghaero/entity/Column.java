package com.example.hanghaero.entity;

import com.example.hanghaero.dto.column.ColRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "columns")
@Builder
@AllArgsConstructor
public class Column {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String title;

	int position;

	@ManyToOne
	@JoinColumn(name = "board_id")
	private Board board;

	public Column(Board board, ColRequestDto requestDto, int lastPosition) {
		this.title = requestDto.getTitle();
		this.position = lastPosition + 1;
		this.board = board;
	}

	public void update(ColRequestDto requestDto) {
		this.title = requestDto.getTitle();
	}

	public void updatePosition(int newPosition) {
		this.position = newPosition;
	}
}
