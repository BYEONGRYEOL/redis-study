package com.example.hanghaero;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.hanghaero.controller.ColumnController;
import com.example.hanghaero.entity.Board;
import com.example.hanghaero.entity.Column;
import com.example.hanghaero.repository.BoardRepository;
import com.example.hanghaero.repository.ColumnRepository;

@SpringBootTest
public class RedisTest {
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private ColumnRepository columnRepository;
	@Autowired
	private ColumnController columnController;

	@BeforeEach
	public void beforeEach() {
		Board saveBoard = boardRepository.save(Board.builder().id(1L).name("board1").build());
		columnRepository.save(Column.builder().id(1L).board(saveBoard).position(1).build());
		columnRepository.save(Column.builder().id(2L).board(saveBoard).position(2).build());
	}

}
